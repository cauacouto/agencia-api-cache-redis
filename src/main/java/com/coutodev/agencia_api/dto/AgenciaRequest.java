package com.coutodev.agencia_api.dto;

import com.coutodev.agencia_api.domin.Endereco;

public record AgenciaRequest(
        String nome,
        String cnpj,
        Endereco endereco

) {
}
