package com.galaxia.exception;

public class GalaxiaException extends Exception
{
    private static final long serialVersionUID = -4283752449093393228L;

    public GalaxiaException()
    {
        super();
    }

    public GalaxiaException(String mensaje)
    {
        super(mensaje);
    }
}
