package com.demo.clientes.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ClienteRequest {

	@NotEmpty(message = "Por favor, ingrese un nombre")
	private String nombre;

	@NotEmpty(message = "Por favor, ingrese un apellido")
	private String apellido;
	
	@NotEmpty(message = "Por favor, ingrese una edad")
	private int edad;
	
	@NotEmpty(message = "Por favor, ingrese una fecha de nacimiento (yyyy-mm-dd)")
	private LocalDate fechaNacimiento;

}