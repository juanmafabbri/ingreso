package com.galaxia.service;

import com.galaxia.constant.ClimaEnum;
import com.galaxia.data.Coordenada;
import com.galaxia.data.Planeta;
import com.galaxia.dto.ClimaRespuesta;
import com.galaxia.dto.Respuesta;
import com.galaxia.entity.Clima;
import com.galaxia.exception.GalaxiaException;
import com.galaxia.repository.GalaxiaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static com.galaxia.util.MathUtil.*;


@Service
@PropertySource({"classpath:application.properties"})
public class GalaxiaServiceImpl implements GalaxiaService
{

	@Autowired
	private Environment env;
	
	@Autowired
    GalaxiaRepository galaxiaRepository;
	
	private Planeta ferengi;
	private Planeta betasoide;
	private Planeta vulcano;
	
	private final int dias = 365*10;
	
	public Respuesta iniciar() throws GalaxiaException
	{
		if(galaxiaCreada())
		{
			throw new GalaxiaException("La galaxia ya se encuentra iniciada.");
		}
		
		Clima clima = null;
		crearGalaxia();
		
		for (int i = 0; i < dias; i ++)
		{
			posicionarPlanetas(i);
			clima = new Clima();
			clima.setDia(i);
			if(estanAlineados())
			{
				registrarClimaPlanetasNoAlineados(clima);
			}
			else
			{
				registrarClimaPlanetasAlineados(clima);
			}
			galaxiaRepository.save(clima);
		}
		
		Respuesta resp = new Respuesta();
		resp.setEstado(Respuesta.OK);
		resp.setMensaje("Galaxia creada exitosamente");
		return resp;
	}

	public Respuesta reiniciar() throws GalaxiaException
	{
		validarExistenciaDeGalaxia();
		galaxiaRepository.deleteAll();
		return this.iniciar();
	}

	public ClimaRespuesta obtenerClimaPorDia(String dia) throws GalaxiaException
	{
		validarExistenciaDeGalaxia();

		int iDia;

		try
		{
			iDia = Integer.parseInt(dia);
		}
		catch(Exception ex)
		{
			throw new GalaxiaException("El día ingresado es inválido.");
		}

		Clima clima = galaxiaRepository.findOne(iDia);
		if(clima == null)
		{
			throw new GalaxiaException("Día no encontrado.");
		}

		return new ClimaRespuesta(clima.getDia(), clima.getClima());
	}

	private void validarExistenciaDeGalaxia() throws GalaxiaException {
		if(!galaxiaCreada())
		{
			throw new GalaxiaException("La galaxia no se encuentra iniciada.");
		}
	}

	public int obtenerPeriodosPorClima(String tipo) throws GalaxiaException
	{
		validarExistenciaDeGalaxia();

		ClimaEnum tipoEnum;

		try
		{
			tipoEnum = ClimaEnum.valueOf(tipo);
		}
		catch(Exception ex)
		{
			throw new GalaxiaException("Tipo de clima inválido. Los tipos válidos son: Optimo, Sequia, Lluvia, Normal");
		}

		return galaxiaRepository.findByClima(tipoEnum).size();
	}

	public int obtenerMaximoDeLluvia() throws GalaxiaException
	{
		validarExistenciaDeGalaxia();

		return galaxiaRepository.findAllByOrderByPerimetroDesc().get(0).getDia();
	}

	public boolean galaxiaCreada() throws GalaxiaException
	{
		if(galaxiaRepository.count() > 0)
			return true;
		return false;
	}

	private boolean estanAlineados()
	{
		return puntosAlineados(ferengi.getCoordenadas(), betasoide.getCoordenadas(), vulcano.getCoordenadas());
	}

	private boolean estanAlineadosConSol()
	{
		boolean b1 = puntosAlineados(new Coordenada().alOrigen(), ferengi.getCoordenadas(), betasoide.getCoordenadas());
		boolean b2 = puntosAlineados(new Coordenada().alOrigen(), ferengi.getCoordenadas(), vulcano.getCoordenadas());

		return b1 == b2;
	}

	private boolean solEstaDentroDelTriangulo()
	{
		return puntoDentroDeTriangulo(new Coordenada().alOrigen(), ferengi.getCoordenadas(), betasoide.getCoordenadas(), vulcano.getCoordenadas());
	}

	private double obtenerPerimetro()
	{
		double d1 = distanciaEntreDosPuntos(ferengi.getCoordenadas(), betasoide.getCoordenadas());
		double d2 = distanciaEntreDosPuntos(betasoide.getCoordenadas(), vulcano.getCoordenadas());
		double d3 = distanciaEntreDosPuntos(vulcano.getCoordenadas(), ferengi.getCoordenadas());
		return d1 + d2 + d3;
	}

	private void posicionarPlanetas(int dia)
	{
		ferengi.getCoordenadas().setCoordX(obtenerCoordX(ferengi.getVelocidad(), ferengi.getDistancia(), dia));
		ferengi.getCoordenadas().setCoordY(obtenerCoordY(ferengi.getVelocidad(), ferengi.getDistancia(), dia));

		betasoide.getCoordenadas().setCoordX(obtenerCoordX(betasoide.getVelocidad(), betasoide.getDistancia(), dia));
		betasoide.getCoordenadas().setCoordY(obtenerCoordY(betasoide.getVelocidad(), betasoide.getDistancia(), dia));

		vulcano.getCoordenadas().setCoordX(obtenerCoordX(vulcano.getVelocidad(), vulcano.getDistancia(), dia));
		vulcano.getCoordenadas().setCoordY(obtenerCoordY(vulcano.getVelocidad(), vulcano.getDistancia(), dia));
	}

	private void crearGalaxia()
	{
		ferengi = new Planeta();
		ferengi.setVelocidad(new Integer(env.getProperty("galaxia.planeta.ferengi.velocidad")));
		ferengi.setDistancia(new Integer(env.getProperty("galaxia.planeta.ferengi.distancia")));

		betasoide = new Planeta();
		betasoide.setVelocidad(new Integer(env.getProperty("galaxia.planeta.betasoide.velocidad")));
		betasoide.setDistancia(new Integer(env.getProperty("galaxia.planeta.betasoide.distancia")));

		vulcano = new Planeta();
		vulcano.setVelocidad(new Integer(env.getProperty("galaxia.planeta.vulcano.velocidad")));
		vulcano.setDistancia(new Integer(env.getProperty("galaxia.planeta.vulcano.distancia")));
	}

	private void registrarClimaPlanetasNoAlineados(Clima clima) {
		clima.setPerimetro(0);
		if(estanAlineadosConSol())
		{
			clima.setClima(ClimaEnum.Sequia);
		}
		else
		{
			clima.setClima(ClimaEnum.Optimo);
		}
	}

	private void registrarClimaPlanetasAlineados(Clima clima) {
		clima.setPerimetro(obtenerPerimetro());
		if(solEstaDentroDelTriangulo())
		{
			clima.setClima(ClimaEnum.Lluvia);
		}
		else
		{
			clima.setClima(ClimaEnum.Normal);
		}
	}
}
