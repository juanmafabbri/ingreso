package com.galaxia.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.galaxia.constant.ClimaEnum;
import com.galaxia.entity.Clima;
import com.galaxia.exception.GalaxiaException;

public interface GalaxiaRepository extends CrudRepository<Clima, Integer>
{
    List<Clima> findByClima(ClimaEnum clima) throws GalaxiaException;

    List<Clima> findAllByOrderByPerimetroDesc() throws GalaxiaException;
}
