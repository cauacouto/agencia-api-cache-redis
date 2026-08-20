package com.coutodev.agencia_api;

import com.coutodev.agencia_api.domin.Agencia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AgenciaTests {


    @Test
    void cadastratAgencia(){
        Agencia agencia = new Agencia("teste","444");
        Assertions.assertEquals("teste",agencia.getNome());
        Assertions.assertEquals(444,agencia.getCnpj());
          }
}
