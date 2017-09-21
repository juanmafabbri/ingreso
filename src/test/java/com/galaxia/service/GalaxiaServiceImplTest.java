package com.galaxia.service;

import com.galaxia.constant.ClimaEnum;
import com.galaxia.dto.ClimaRespuesta;
import com.galaxia.exception.GalaxiaException;
import com.galaxia.repository.GalaxiaRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GalaxiaServiceImplTest {

    @Autowired
    GalaxiaService galaxiaService;

    @Autowired
    GalaxiaRepository galaxiaRepository;

    @Test
    public void iniciarGalaxiaVaciaDebeGenerarLaGalaxiaConElClimaPara10Años() throws Exception {
        //given  & when
        galaxiaService.iniciar();
        //then
        asercionParaGalaxiaIniciada();
    }

    @Test(expected = GalaxiaException.class)
    public void  crearUnaGalaxiaYaIniciadaDebeLanzarUnaExcepcion() throws Exception {
        //given
        galaxiaService.iniciar();
        //when & then
        galaxiaService.iniciar();
    }

    @Test
    public void seReiniciaUnaGalaxiaQueFueCreada() throws Exception {
        //given
        galaxiaService.iniciar();
        //when
        galaxiaService.reiniciar();
        //then
        asercionParaGalaxiaIniciada();
    }

    @Test
    public void climaObtenidoParaElDiaUnoEsLluvia() throws Exception {
        //given
        galaxiaService.iniciar();
        String dia = "1";
        //when
        ClimaRespuesta respuesta = galaxiaService.obtenerClimaPorDia(dia);
        //then
        asercionDiaObtenido(respuesta);
    }

    @Test
    public void obtenerPeriodoParaTipoDeClimaOptimo() throws Exception {
        //given
        galaxiaService.iniciar();
        //when
        int optimo = galaxiaService.obtenerPeriodosPorClima("Lluvia");
        //then
        assertThat(optimo).isEqualTo(1187);
    }

    @Test
    public void obtenerMaximoDeLluvia() throws Exception {
        //given
        galaxiaService.iniciar();
        //when
        int optimo = galaxiaService.obtenerMaximoDeLluvia();
        //then
        assertThat(optimo).isEqualTo(1368);
    }

    @Test
    public void galaxiaCreadaCuandoHayUnaGalaxiaIniciada() throws Exception {
        //given
        galaxiaService.iniciar();
        //when
        boolean creada = galaxiaService.galaxiaCreada();
        //then
        assertThat(creada).isTrue();
    }

    @Test
    public void galaxiaNoCreadaCuandoNoSeInicioUnaGalaxia() throws Exception {
        //given & when
        boolean creada = galaxiaService.galaxiaCreada();
        //then
        assertThat(creada).isFalse();
    }

    private void asercionParaGalaxiaIniciada() {
        assertThat(galaxiaRepository.count()).isEqualTo(3650L);
    }

    private void asercionDiaObtenido(ClimaRespuesta respuesta) {
        assertThat(respuesta.getClima()).isEqualByComparingTo(ClimaEnum.Normal);
        assertThat(respuesta.getDia()).isEqualByComparingTo(1);
    }

}