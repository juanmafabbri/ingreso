package com.galaxia.dto;


import com.galaxia.constant.ClimaEnum;

public class ClimaRespuesta
{
    private int dia;
    private ClimaEnum clima;

    public ClimaRespuesta(int dia, ClimaEnum clima)
    {
        this.dia = dia;
        this.clima = clima;
    }

    public int getDia()
    {
        return dia;
    }

    public void setDia(int dia)
    {
        this.dia = dia;
    }

    public ClimaEnum getClima()
    {
        return clima;
    }

    public void setClima(ClimaEnum clima)
    {
        this.clima = clima;
    }
}

