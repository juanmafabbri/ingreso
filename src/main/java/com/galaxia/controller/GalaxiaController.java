package com.galaxia.controller;

import javax.ws.rs.core.MediaType;

import com.galaxia.constant.Ruta;
import com.galaxia.dto.InfoSistema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.galaxia.dto.ClimaRespuesta;
import com.galaxia.dto.Consideracion;
import com.galaxia.dto.Respuesta;
import com.galaxia.exception.GalaxiaException;
import com.galaxia.service.GalaxiaService;

@RestController
@RequestMapping(path = Ruta.RAIZ, produces = { MediaType.APPLICATION_JSON })
public class GalaxiaController
{
    @Autowired
    GalaxiaService galaxiaService;

    @RequestMapping(path = Ruta.INICIAR)
    Respuesta iniciar() throws GalaxiaException
    {
        return galaxiaService.iniciar();
    }

    @RequestMapping(path = Ruta.REINICIAR)
    Respuesta reiniciar() throws GalaxiaException
    {
        return galaxiaService.reiniciar();
    }

    @RequestMapping(path = Ruta.CLIMA)
    ClimaRespuesta obtenerClimaPorDia(@RequestParam(name = "dia", required = true) String dia) throws GalaxiaException
    {
        return galaxiaService.obtenerClimaPorDia(dia);
    }

    @RequestMapping(path = Ruta.PERIODOS)
    int obtenerPeriodosPorClima(@RequestParam(name = "tipo", required = true) String tipo) throws GalaxiaException
    {
        return galaxiaService.obtenerPeriodosPorClima(tipo);
    }

    @RequestMapping(path = Ruta.MAXIMO)
    int obtenerMaximoDeLluvia() throws GalaxiaException
    {
        return galaxiaService.obtenerMaximoDeLluvia();
    }

    @RequestMapping(path = Ruta.ACERCA_DE)
    InfoSistema infoSistema() throws GalaxiaException
    {
        InfoSistema infoSistema = new InfoSistema();
        infoSistema.setEstadoGalaxia(galaxiaService.galaxiaCreada() ? "Iniciada" : "No iniciada");
        Consideracion c = new Consideracion();
        c.setConsideracion("Si desea iniciar nuevamente la galaxia: " + InfoSistema.RUTA_CLOUD + Ruta.REINICIAR);
        infoSistema.getConsideraciones().add(c);
        c = new Consideracion();
        c.setConsideracion("Los planetas inician su trayectoria posicionados sobre el eje X a la distancia correspondiende del Sol.");
        infoSistema.getConsideraciones().add(c);
        c = new Consideracion();
        c.setConsideracion("Cuando los planetas forman un triángulo que no contiene al Sol, el clima es Normal.");
        infoSistema.getConsideraciones().add(c);
        c = new Consideracion();
        c.setConsideracion("Se decidió utilizar una precisión de 2 (dos) decimales para realizar los cálculos posicionales y de alineación.");
        infoSistema.getConsideraciones().add(c);
        return infoSistema;
    }

    @ExceptionHandler(GalaxiaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Respuesta handleException(GalaxiaException ge)
    {
        Respuesta resp = new Respuesta();
        resp.setEstado(Respuesta.ERROR);
        resp.setMensaje(ge.getMessage());
        return resp;
    }
}
