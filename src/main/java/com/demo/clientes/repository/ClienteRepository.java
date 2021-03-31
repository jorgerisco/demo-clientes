package com.demo.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.clientes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findById(long clienteId);

}