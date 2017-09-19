package com.galaxia.dto;


public class Respuesta
{
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    private String estado;
    private String mensaje;

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }
}

