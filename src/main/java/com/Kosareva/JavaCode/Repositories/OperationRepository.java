package com.Kosareva.JavaCode.Repositories;

import com.Kosareva.JavaCode.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository  extends JpaRepository<Operation, Integer> {
}
