package com.galaxia.util;


import com.galaxia.data.Coordenada;

public class MathUtil
{
    public static boolean puntosAlineados(Coordenada coord1, Coordenada coord2, Coordenada coord3)
    {
        double hipotenusa = MathUtil.distanciaEntreDosPuntos(coord1, coord2);
        double opuesto = MathUtil.distanciaEntreDosPuntos(coord1, new Coordenada(coord2.getCoordX(), 0));

        double sin1 = opuesto / hipotenusa;

        hipotenusa = MathUtil.distanciaEntreDosPuntos(coord1, coord3);
        opuesto = MathUtil.distanciaEntreDosPuntos(coord1, new Coordenada(coord3.getCoordX(), 0));

        double sin2 = opuesto / hipotenusa;

        sin1 = Math.rint(sin1*100)/100;
        sin2 = Math.rint(sin2*100)/100;

        return sin1 == sin2;
    }

    public static double distanciaEntreDosPuntos(Coordenada coord1, Coordenada coord2)
    {
        double x = Math.pow(coord2.getCoordX() - coord1.getCoordX(), 2);
        double y = Math.pow(coord2.getCoordY() - coord1.getCoordY(), 2);
        return Math.sqrt(x + y);
    }

    public static double signo(Coordenada coord1, Coordenada coord2, Coordenada coord3)
    {
        return (coord1.getCoordX() - coord3.getCoordX()) * (coord2.getCoordY() - coord3.getCoordY()) -
                (coord2.getCoordX() - coord3.getCoordX()) * (coord1.getCoordY() - coord3.getCoordY());
    }

    public static boolean puntoDentroDeTriangulo(Coordenada punto, Coordenada coord1, Coordenada coord2, Coordenada coord3)
    {
        boolean b1, b2, b3;

        b1 = signo(punto, coord1, coord2) < 0;
        b2 = signo(punto, coord2, coord3) < 0;
        b3 = signo(punto, coord3, coord1) < 0;

        return ((b1 == b2) && (b2 == b3));
    }

    public static double obtenerCoordX(int velocidad, int distancia, int dia)
    {
        return Math.rint((distancia * Math.cos(velocidad * Math.PI/180 * dia))*100)/100;
    }

    public static double obtenerCoordY(int velocidad, int distancia, int dia)
    {
        return Math.rint((distancia * Math.sin(velocidad * Math.PI/180 * dia))*100)/100;
    }
}

