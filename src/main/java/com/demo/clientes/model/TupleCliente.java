package com.demo.clientes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TupleCliente {

	private Cliente cliente;
	private double promedio;
	
}