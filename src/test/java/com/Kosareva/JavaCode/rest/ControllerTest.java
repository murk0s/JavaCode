package com.Kosareva.JavaCode.rest;

import com.Kosareva.JavaCode.DTO.OperationDTO;
import com.Kosareva.JavaCode.DTO.WalletDTO;
import com.Kosareva.JavaCode.Util.OperationNotCreatedException;
import com.Kosareva.JavaCode.Util.WalletNotFoundException;
import com.Kosareva.JavaCode.models.Operation;
import com.Kosareva.JavaCode.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(Controller.class)
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    private WalletDTO testWalletDTO;
    private UUID id;

    private OperationDTO testOperationDTO;

    @BeforeEach
    void setUp() {
        testWalletDTO = new WalletDTO();
        id = UUID.randomUUID();
        testWalletDTO.setId(id);
        testWalletDTO.setName("Test wallet");
        testWalletDTO.setBalance(1000.0d);

        testOperationDTO = new OperationDTO();
        testOperationDTO.setValletId(id);
        testOperationDTO.setOperationType(Operation.OperationType.DEPOSIT);
        testOperationDTO.setAmount(1000.0d);

    }

    @Test
    @DisplayName("/api/v1/wallet/{id} returns 200 Ok")
    public void getWallet_Success() throws Exception {
        when(this.walletService.findDTOById(id)).thenReturn(testWalletDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets/{id}", testWalletDTO.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testWalletDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testWalletDTO.getName()));
    }

    @Test
    @DisplayName("/api/v1/wallet/{id} returns 404 Not found")
    public void getWallet_NotFound() throws Exception {
        when(this.walletService.findDTOById(id)).thenThrow(WalletNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets/{id}", testWalletDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Wallet with this uuid does not exist"));
    }


    @Test
    void createOperation_Success() throws Exception {
        when(this.walletService.save(any(Operation.class))).thenReturn(any(Operation.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOperationDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void createOperation_Bad() throws Exception {
        when(this.walletService.save(any(Operation.class))).thenThrow(OperationNotCreatedException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOperationDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}