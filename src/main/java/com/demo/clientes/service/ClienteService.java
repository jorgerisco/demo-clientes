package com.demo.clientes.service;

import com.demo.clientes.dto.ClienteRequest;
import com.demo.clientes.dto.ClienteResponse;
import com.demo.clientes.dto.ClienteKpiResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

 	Mono<ClienteResponse> crearCliente(ClienteRequest cliente);
	
	Flux<ClienteResponse> getClientes();

	Mono<ClienteKpiResponse> getClientesKpi();
}