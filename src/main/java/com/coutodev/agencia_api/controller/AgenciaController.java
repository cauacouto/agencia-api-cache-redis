package com.coutodev.agencia_api.controller;

import com.coutodev.agencia_api.domin.Agencia;
import com.coutodev.agencia_api.dto.AgenciaRequest;
import com.coutodev.agencia_api.service.AgenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {

    private final AgenciaService agenciaService;

    public AgenciaController(AgenciaService agenciaService) {
        this.agenciaService = agenciaService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarAgencia(@RequestBody AgenciaRequest request){
        this.agenciaService.CadastrarAgencia(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agencia> buscarPorId(@PathVariable Integer id){
        Agencia agencia = agenciaService.buscarPorId(id);
        return ResponseEntity.ok().body(agencia);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        this.agenciaService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
