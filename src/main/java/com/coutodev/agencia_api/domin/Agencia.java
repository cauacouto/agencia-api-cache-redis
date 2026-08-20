package com.coutodev.agencia_api.domin;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "agencia_db")
@Data
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cnpj;
    @Embedded
    private Endereco endereco;

    public Agencia(){}

    public Agencia( String nome, String cnpj) {

        this.nome = nome;
        this.cnpj = cnpj;
    }
}
