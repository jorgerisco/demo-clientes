package com.demo.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data 
@AllArgsConstructor
@NoArgsConstructor
public class ClienteKpiResponse {

	private double promedioEdad;

	private double desviacionEstandarEdad;

}