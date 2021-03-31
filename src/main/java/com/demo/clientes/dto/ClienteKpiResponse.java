package com.demo.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Data 
@AllArgsConstructor
@NoArgsConstructor
public class ClienteKpiResponse {

	private double promedioEdad;

	private double desviacionEstandarEdad;

}