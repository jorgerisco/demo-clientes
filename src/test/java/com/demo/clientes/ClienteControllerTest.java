package com.demo.clientes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import com.demo.clientes.dto.ClienteResponse;
import com.demo.clientes.repository.ClienteRepository;
import com.demo.clientes.service.ClienteService;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(ClienteController.class)
public class ClienteControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ClienteRepository repository;

	@MockBean
	private ClienteService service;
	
	@Test
	public void listClientes() {		
		ClienteResponse cliente1 = ClienteResponse.builder()
				.clienteId(1L)
				.nombre("Juan")
				.apellido("Perez")
				.edad(31)
				.fechaNacimiento(LocalDate.parse("1990-01-25"))
				.build();

		ClienteResponse cliente2 = ClienteResponse.builder()
				.clienteId(2L)
				.nombre("Raul")
				.apellido("Gutierrez")
				.edad(32)
				.fechaNacimiento(LocalDate.parse("1988-06-15"))
				.build();
		
		BDDMockito.given(this.service.getClientes()).willReturn(Flux.just(cliente1, cliente2));
		
		this.webTestClient.get().uri("/listclientes").accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectBodyList(ClienteResponse.class)
				.hasSize(2)
				.contains(cliente1, cliente2);
	}	
}