package com.demo.clientes.service.impl;

import java.util.List; 
import java.util.stream.Collectors; 
import org.springframework.stereotype.Service;

import com.demo.clientes.dto.ClienteKpiResponse;
import com.demo.clientes.dto.ClienteRequest;
import com.demo.clientes.dto.ClienteResponse; 
import com.demo.clientes.model.Cliente;
import com.demo.clientes.model.TupleCliente; 
import com.demo.clientes.repository.ClienteRepository;
import com.demo.clientes.service.ClienteService;
 
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static com.demo.clientes.service.impl.Utils.*;

@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public Flux<ClienteResponse> getClientes(){
		return asMono(() -> clienteRepository.findAll())
			.zipWhen( f -> getPromedioEdad(f))
			.flatMapMany( t -> Flux.fromIterable(t.getT1())
								.map( c -> new TupleCliente(c, t.getT2()))
						)
			.map( r -> ClienteResponse.builder()
									.clienteId(r.getCliente().getClienteId())
									.nombre(r.getCliente().getNombre())
									.apellido(r.getCliente().getApellido())
									.edad(r.getCliente().getEdad())
									.fechaNacimiento(r.getCliente().getFechaNacimiento())
									.fechaProbableFallecimiento(calcularFechaProbableFallecimiento(r.getCliente().getFechaNacimiento(), r.getPromedio()))
									.build()
			);
	}
 
	@Override
	public Mono<ClienteResponse> crearCliente(ClienteRequest request) {
		return asMono(() -> clienteRepository.save(Cliente.builder()
					.apellido(request.getApellido())
					.nombre(request.getNombre())
					.edad(request.getEdad())
					.fechaNacimiento(request.getFechaNacimiento())
					.build()))
				.map( c -> ClienteResponse.builder()
									.clienteId(c.getClienteId())
						 			.nombre(c.getNombre())
						 			.apellido(c.getApellido())
						 			.edad(c.getEdad())
						 			.fechaNacimiento(c.getFechaNacimiento())
						 			.build())
				.zipWhen( c -> getPromedioEdad())
				.map( t ->  {
					t.getT1().setFechaProbableFallecimiento(calcularFechaProbableFallecimiento(t.getT1().getFechaNacimiento(), t.getT2()));
					return t.getT1();
				});
	}	
 
	@SuppressWarnings("unchecked")
	private Mono<Double> getPromedioEdad(Iterable<Cliente> c){
		return Flux.fromIterable(c)
					.collect(Collectors.averagingDouble(Utils.getEdad()));
	}
	
	@SuppressWarnings({"unchecked" })
	private Mono<Double> getPromedioEdad(){
		return Utils.asMono(() -> clienteRepository.findAll())
					.flatMapMany( f -> Flux.fromIterable(f))
					.collect(Collectors.averagingDouble(Utils.getEdad()));
	}

	@Override
	public Mono<ClienteKpiResponse> getClientesKpi() {
		return asMono(() -> clienteRepository.findAll())
					.zipWhen( f -> getPromedioEdad(f))
					.flatMap( t -> createResponse(t));
	}
	
	private Mono<ClienteKpiResponse> createResponse(Tuple2<List<Cliente>,Double> tuple) {
		
		final Integer n = tuple.getT1().size();
		return Flux.fromStream(tuple.getT1().stream())
					.map( c ->  Math.pow( c.getEdad() - tuple.getT2(), 2))
					.collect(Collectors.summingDouble( s -> s))
					.map(sum -> n > 1 ? Math.sqrt ( sum / ( double ) ( n - 1 )) : 0) 
					.map( des ->  new ClienteKpiResponse(tuple.getT2(),des));
	}

}