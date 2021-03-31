package com.demo.clientes;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.clientes.dto.ClienteRequest;
import com.demo.clientes.dto.ClienteResponse;
import com.demo.clientes.dto.ClienteKpiResponse;
import com.demo.clientes.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.demo.clientes.service.impl.Utils.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Tag(description = "API de Clientes", name = "Clientes")
public class ClienteController {

	private ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping(value = "/creacliente")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(description = "Creación de Cliente", summary = "Creación de Cliente")
	public Mono<ResponseEntity<ClienteResponse>> crearCliente(@Valid @RequestBody ClienteRequest cliente) {
		return clienteService.crearCliente(validarEdad(cliente))
				.map(ResponseEntity::ok);
	} 

	@GetMapping(value = "/listclientes")
	@Operation(description = "Obtener listado de Clientes", summary = "Obtener listado de Clientes")
	public Flux<ClienteResponse> getClientes() {
		return clienteService.getClientes();
	}
 
	@GetMapping(value = "/kpideclientes")
	@Operation(description = "KPI de Clientes", summary = "KPI de Clientes")
	public Mono<ClienteKpiResponse> getClientesKpi() {
		return clienteService.getClientesKpi();
	}

}