package com.galaxia.service;

import com.galaxia.dto.ClimaRespuesta;
import com.galaxia.dto.Respuesta;
import com.galaxia.exception.GalaxiaException;

public interface GalaxiaService
{
	public Respuesta iniciar() throws GalaxiaException;
	
	public Respuesta reiniciar() throws GalaxiaException;
	
	public ClimaRespuesta obtenerClimaPorDia(String dia) throws GalaxiaException;

	public int obtenerPeriodosPorClima(String tipo) throws GalaxiaException;
	
	public int obtenerMaximoDeLluvia() throws GalaxiaException;
	
	public boolean galaxiaCreada() throws GalaxiaException;
}
