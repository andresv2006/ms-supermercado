package com.example.ms_pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_pago.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}
