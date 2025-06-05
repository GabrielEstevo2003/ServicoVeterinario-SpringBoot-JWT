package com.example.API_Servico.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;

@Document(collection = "Servico")
public class Servico {
    @Id
    private String id;
    private String tipo;
    private Date dtServico;
    private Double valor;
    private String idAnimalAtendido;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDtServico() {
        return dtServico;
    }

    public void setDtServico(Date dtServico) {
        this.dtServico = dtServico;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getIdAnimalAtendido() {
        return idAnimalAtendido;
    }

    public void setIdAnimalAtendido(String idAnimalAtendido) {
        this.idAnimalAtendido = idAnimalAtendido;
    }
}
