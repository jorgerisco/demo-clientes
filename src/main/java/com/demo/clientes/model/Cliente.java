package com.demo.clientes.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CLIENTE")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENTE_ID")
	private Long clienteId;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "APELLIDO")
	private String apellido;

	@Column(name = "EDAD")
	private int edad;

	@Column(name = "FECHA_NACIMIENTO")
	private LocalDate fechaNacimiento;
	
	public static ClienteBuilder builder() {
		return new ClienteBuilder();
	}
	
	public static class ClienteBuilder {
	 
		private Long clienteId;
		private String nombre;
		private String apellido;
		private int edad; 
		private LocalDate fechaNacimiento;
		
		public ClienteBuilder clienteId(Long clienteId) {
			this.clienteId = clienteId;
			return this;
		}
		
		public ClienteBuilder nombre(String nombre) {
			this.nombre = nombre;
			return this;
		}
		public ClienteBuilder apellido(String apellido) {
			this.apellido = apellido;
			return this;
		}
		public ClienteBuilder edad(int edad) {
			this.edad = edad;
			return this;
		} 
		public ClienteBuilder fechaNacimiento(LocalDate fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
			return this;
		} 
		
		public Cliente build() {
			return new Cliente(clienteId, nombre, apellido, edad, fechaNacimiento);
		}
		
	}
}