package com.demo.clientes.service.impl;

import java.lang.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import com.demo.clientes.dto.ClienteRequest;
import com.demo.clientes.model.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class Utils {
	
	public static Calendar localDateToCalendar(LocalDate localDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 0, 0, 0);
		return calendar;
	}
	
	public static LocalDate calendarToLocalDate(Calendar calendar) {
		return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	} 
	
	public static LocalDate calcularFechaProbableFallecimiento(LocalDate fechaNacimiento, double promedioEdad) {
		
		LocalDate fechaProbableFallecimiento = null;
		
		if (promedioEdad > calcularEdad(fechaNacimiento)) {
		
			Calendar calendar = localDateToCalendar(fechaNacimiento);
			double anios = Math.floor(promedioEdad);
			double meses = 0;
			double dias = 0;
			
			if (anios > 0) {	
				calendar.add(Calendar.YEAR, (int) anios);
				meses = Math.floor((promedioEdad - anios) * 12);
			}
			
			if (meses > 0) {
				calendar.add(Calendar.MONTH, (int) meses);
				dias = Math.floor((((promedioEdad - anios) * 12) - meses) * 30);	
			}
			
			if (dias > 0)
				calendar.add(Calendar.DAY_OF_YEAR, (int) dias);
			
			fechaProbableFallecimiento = calendarToLocalDate(calendar);
		}
		
		return fechaProbableFallecimiento;
	}
	
	public static <U> Mono<U> asMono(Supplier<U> supplier){
		return Mono.fromFuture(CompletableFuture.supplyAsync(supplier));
	}
	
	public static ToDoubleFunction getEdad(){
		return c -> ((Cliente)c).getEdad();
	}
	
	public static ClienteRequest validarEdad(ClienteRequest request) {
		
		int edadCalculada = calcularEdad(request.getFechaNacimiento());
		
		if( !(edadCalculada == request.getEdad()) ) {
			throw new RuntimeException("Por favor, valide la edad y fecha de nacimiento.");
		}
		
		return request;
	}
	
	public static int calcularEdad(LocalDate fechaNacimiento) {
		
		LocalDate fechaActual = LocalDate.now();
		if ((fechaNacimiento != null) && (fechaActual != null))
			return Period.between(fechaNacimiento, fechaActual).getYears();
		else
			return 0;
	}
}
