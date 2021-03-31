package com.demo.clientes.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class ClienteResponse {

	private Long clienteId;
	
	private String nombre;

	private String apellido;
	
	private int edad;
	
	private LocalDate fechaNacimiento;

	private LocalDate fechaProbableFallecimiento;

}