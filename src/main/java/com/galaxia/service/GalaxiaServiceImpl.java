package com.galaxia.service;

import com.galaxia.constant.ClimaEnum;
import com.galaxia.data.Coordenada;
import com.galaxia.data.Planeta;
import com.galaxia.dto.ClimaRespuesta;
import com.galaxia.dto.Respuesta;
import com.galaxia.entity.Clima;
import com.galaxia.exception.GalaxiaException;
import com.galaxia.repository.GalaxiaRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static com.galaxia.util.MathUtil.*;


@Service
@PropertySource({"classpath:application.properties"})
public class GalaxiaServiceImpl implements GalaxiaService
{
	private static final Logger logger = Logger.getLogger(GalaxiaServiceImpl.class);

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
			String mensaje = "La galaxia ya se encuentra iniciada.";
			logger.error(mensaje);
			throw new GalaxiaException(mensaje);
		}
		
		Clima clima = null;
		crearGalaxia();
		
		for (int i = 0; i < dias; i ++)
		{
			posicionarPlanetas(i);
			clima = registrarDia(i);
			if(estanAlineados())
			{
				registrarClimaPlanetasAlineados(clima);
			}
			else
			{
				registrarClimaPlanetasNoAlineados(clima);
			}
			galaxiaRepository.save(clima);
		}
		logger.info("Galaxia iniciada correctamente.");
		return crearRespuesta();
	}

	public Respuesta reiniciar() throws GalaxiaException
	{
		validarExistenciaDeGalaxia();
		galaxiaRepository.deleteAll();
		logger.info("Se eliman los datos persistidos de la galaxia para el reinicio.");
		return this.iniciar();
	}

	public ClimaRespuesta obtenerClimaPorDia(String dia) throws GalaxiaException
	{
		logger.info(String.format("Obteniendo clima para el día %s", dia));
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

	public int obtenerPeriodosPorClima(String tipo) throws GalaxiaException
	{
		logger.info(String.format("Obteniendo periodos por tipo de clima %s", tipo));
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
		logger.info("Obteniendo máximo de lluvia.");
		validarExistenciaDeGalaxia();

		return galaxiaRepository.findAllByOrderByPerimetroDesc().stream().findFirst().get().getDia();
	}

	public boolean galaxiaCreada() throws GalaxiaException
	{
		return galaxiaRepository.count() > 0;
	}

	private Clima registrarDia(int dia) {
		Clima clima = new Clima();
		clima.setDia(dia);
		return clima;
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

	private void validarExistenciaDeGalaxia() throws GalaxiaException {
		if(!galaxiaCreada())
		{
			throw new GalaxiaException("La galaxia no se encuentra iniciada.");
		}
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

	private void registrarClimaPlanetasAlineados(Clima clima) {
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

	private Respuesta crearRespuesta() {
		Respuesta resp = new Respuesta();
		resp.setEstado(Respuesta.OK);
		resp.setMensaje("Galaxia creada exitosamente");
		return resp;
	}

	private void registrarClimaPlanetasNoAlineados(Clima clima) {
		clima.setPerimetro(obtenerPerimetro());
		ClimaEnum tipoDeClima = null;
		if(solEstaDentroDelTriangulo())
		{
			tipoDeClima = ClimaEnum.Lluvia;
		}
		else
		{
			tipoDeClima = ClimaEnum.Normal;
		}
		clima.setClima(tipoDeClima);
	}
}
