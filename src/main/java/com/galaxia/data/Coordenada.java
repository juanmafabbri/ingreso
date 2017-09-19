package com.galaxia.data;


public class Coordenada
{
    private double coordX;
    private double coordY;

    public Coordenada()
    {

    }

    public Coordenada(double coordX, double coordY)
    {
        setCoordX(coordX);
        setCoordY(coordY);
    }

    public Coordenada alOrigen()
    {
        setCoordX(0);
        setCoordY(0);
        return this;
    }

    public double getCoordX()
    {
        return coordX;
    }

    public void setCoordX(double coordX)
    {
        this.coordX = coordX;
    }

    public double getCoordY()
    {
        return coordY;
    }

    public void setCoordY(double coordY)
    {
        this.coordY = coordY;
    }
}
