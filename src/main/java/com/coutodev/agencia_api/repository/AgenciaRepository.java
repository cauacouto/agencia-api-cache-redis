package com.coutodev.agencia_api.repository;

import com.coutodev.agencia_api.domin.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgenciaRepository extends JpaRepository<Agencia,Integer> {

    List<Agencia>findAllById(Integer id);

}
