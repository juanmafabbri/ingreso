package com.galaxia.util;

import com.galaxia.data.Coordenada;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MathUtilTest {

    @Test
    public void puntosAlineados() throws Exception {
        //given
        Coordenada coordenada1 = getCoordenada(2d, 2d);
        Coordenada coordenada2 = getCoordenada(3d, 3d);
        Coordenada coordenada3 = getCoordenada(1d, 1d);
        //when
        boolean alineados = MathUtil.puntosAlineados(coordenada1, coordenada2, coordenada3);
        //then
        assertThat(alineados).isTrue();
    }

    @Test
    public void puntosNoAlineados() throws Exception {
        //given
        Coordenada coordenada1 = getCoordenada(5d, 10d);
        Coordenada coordenada2 = getCoordenada(5d, 5d);
        Coordenada coordenada3 = getCoordenada(1d, 1d);
        //when
        boolean alineados = MathUtil.puntosAlineados(coordenada1, coordenada2, coordenada3);
        //then
        assertThat(alineados).isFalse();
    }

    @Test
    public void distanciaEntreDosPuntos() throws Exception {
        //given
        Coordenada coordenada1 = getCoordenada(0d, 2d);
        Coordenada coordenada2 = getCoordenada(0d, 4d);
        //when
        double distancia = MathUtil.distanciaEntreDosPuntos(coordenada1, coordenada2);
        //then
        assertThat(distancia).isEqualTo(2d);
    }

    @Test
    public void signo() throws Exception {
        Coordenada coordenada1 = getCoordenada(-5d, -10d);
        Coordenada coordenada2 = getCoordenada(-5d, -5d);
        Coordenada coordenada3 = getCoordenada(-1d, -1d);
        //when
        double signo = MathUtil.signo(coordenada1, coordenada2, coordenada3);
        //then
        assertThat(signo).isLessThan(0d);
    }

    @Test
    public void puntoDentroDeTriangulo() throws Exception {
        //when
        Coordenada alOrigen = getCoordenada(0d, 0d);
        Coordenada coordenada1 = getCoordenada(1d, 3d);
        Coordenada coordenada2 = getCoordenada(2d, -2d);
        Coordenada coordenada3 = getCoordenada(-2d, 0d);
        //when
        boolean dentroDelTriangulo = MathUtil.puntoDentroDeTriangulo(alOrigen, coordenada1, coordenada2, coordenada3);
        //then
        assertThat(dentroDelTriangulo).isTrue();
    }

    private Coordenada getCoordenada(double t, double t2) {
        Coordenada coordenada1 = mock(Coordenada.class);
        when(coordenada1.getCoordX()).thenReturn(t);
        when(coordenada1.getCoordY()).thenReturn(t2);
        return coordenada1;
    }
}