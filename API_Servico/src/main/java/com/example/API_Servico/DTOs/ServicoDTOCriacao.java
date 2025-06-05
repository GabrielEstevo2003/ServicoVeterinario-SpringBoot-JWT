package com.example.API_Servico.DTOs;

import com.example.API_Servico.Models.Servico;

import java.util.Date;

public class ServicoDTOCriacao {
    private String tipo;
    private Date dtServico;
    private Double valor;
    private String idAnimalAtendido;
    private String status;

    public ServicoDTOCriacao(Servico servico, AnimalDTO animal){
        this.tipo = servico.getTipo();
        this.dtServico = servico.getDtServico();
        this.valor = servico.getValor();
        this.status = servico.getStatus();
        this.idAnimalAtendido = animal.getId();
    }

    public ServicoDTOCriacao(){

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
