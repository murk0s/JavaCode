package com.Kosareva.JavaCode.rest;


import com.Kosareva.JavaCode.DTO.OperationDTO;
import com.Kosareva.JavaCode.DTO.WalletDTO;
import com.Kosareva.JavaCode.Util.ErrorResponse;
import com.Kosareva.JavaCode.Util.OperationNotCreatedException;
import com.Kosareva.JavaCode.Util.WalletNotFoundException;
import com.Kosareva.JavaCode.models.Operation;
import com.Kosareva.JavaCode.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1")
public class Controller {
    private final WalletService walletService;

    @Autowired
    public Controller(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable("walletId")  UUID id) {
        return ResponseEntity.ok().body(walletService.findDTOById(id));
    }

    @PostMapping("/wallet")
    public ResponseEntity<HttpStatus> createOperation(@RequestBody @Valid OperationDTO operationDTO,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorsMsg.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("\n");
            }

            throw new OperationNotCreatedException(errorsMsg.toString());
        }
        Operation o = walletService.save(convertToOperation(operationDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(WalletNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Wallet with this uuid does not exist",
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> createOperationNotCreatedException(OperationNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Object onYourCustomException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private Operation convertToOperation(@Valid OperationDTO operationDTO) {
        Operation operation = new Operation();
        operation.setWallet(walletService.findById(operationDTO.getValletId()));
        operation.setType(operationDTO.getOperationType());

        operation.setAmount(operationDTO.getAmount());

        return operation;
    }



}
