package com.galaxia.data;


public class Planeta
{
    private int velocidad;
    private int distancia;
    private Coordenada coordenadas;

    public int getVelocidad()
    {
        return velocidad;
    }

    public void setVelocidad(int velocidad)
    {
        this.velocidad = velocidad;
    }

    public int getDistancia()
    {
        return distancia;
    }

    public void setDistancia(int distancia)
    {
        this.distancia = distancia;
    }

    public Coordenada getCoordenadas()
    {
        if(coordenadas == null)
        {
            coordenadas = new Coordenada();
        }
        return coordenadas;
    }

    public void setCoordenadas(Coordenada coordenadas)
    {
        this.coordenadas = coordenadas;
    }
}
