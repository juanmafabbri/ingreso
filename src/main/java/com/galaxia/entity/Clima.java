package com.galaxia.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.galaxia.constant.ClimaEnum;

@Entity
@Table(name = "Clima")
public class Clima
{
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int dia;

    @Column(name = "clima")
    private ClimaEnum clima;

    @Column(name = "perimetro")
    private double perimetro;

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
    public double getPerimetro()
    {
        return perimetro;
    }
    public void setPerimetro(double perimetro)
    {
        this.perimetro = perimetro;
    }

    public String toString()
    {
        return "dia: " + dia + ", clima: " + clima + ", perimetro: " + perimetro;
    }
}
