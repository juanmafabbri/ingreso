package com.galaxia.dto;

import com.galaxia.constant.Ruta;

import java.util.ArrayList;
import java.util.List;

public class InfoSistema
{
	public static final String RUTA_CLOUD = "https://juan-galaxias.appspot.com" + Ruta.RAIZ;

	private String autor = "Juan Manuel Fabbri";
	private String email = "juanmanuelfabbri@gmail.com";

	private String jobGalaxia = RUTA_CLOUD + Ruta.INICIAR;
	private String estadoGalaxia;
	private String consultarDia = RUTA_CLOUD + Ruta.CLIMA + "?dia={1|3650}";
	private String consultarPeriodos = RUTA_CLOUD + Ruta.PERIODOS + "?tipo={Optimo|Sequia|Lluvia|Normal}";
	private String consultarMaximoLluvia = RUTA_CLOUD + Ruta.MAXIMO;

	private List<Consideracion> consideraciones = new ArrayList<Consideracion>();

	public String getAutor()
	{
		return autor;
	}

	public void setAutor(String autor)
	{
		this.autor = autor;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getJobGalaxia()
	{
		return jobGalaxia;
	}

	public void setJobGalaxia(String jobGalaxia)
	{
		this.jobGalaxia = jobGalaxia;
	}

	public String getEstadoGalaxia()
	{
		return estadoGalaxia;
	}

	public void setEstadoGalaxia(String estadoGalaxia)
	{
		this.estadoGalaxia = estadoGalaxia;
	}

	public String getConsultarDia()
	{
		return consultarDia;
	}

	public void setConsultarDia(String consultarDia)
	{
		this.consultarDia = consultarDia;
	}

	public String getConsultarPeriodos()
	{
		return consultarPeriodos;
	}

	public void setConsultarPeriodos(String consultarPeriodos)
	{
		this.consultarPeriodos = consultarPeriodos;
	}

	public String getConsultarMaximoLluvia()
	{
		return consultarMaximoLluvia;
	}

	public void setConsultarMaximoLluvia(String consultarMaximoLluvia)
	{
		this.consultarMaximoLluvia = consultarMaximoLluvia;
	}

	public List<Consideracion> getConsideraciones()
	{
		return consideraciones;
	}

	public void setConsideraciones(List<Consideracion> consideraciones)
	{
		this.consideraciones = consideraciones;
	}
}
