package com.example.API_Servico.DTOs;

import com.example.API_Servico.Models.Servico;

import java.util.Date;

public class ServicoDTO {
    private String tipo;
    private Date dtServico;
    private Double valor;
    private AnimalDTO animalDTO;
    private String status;

public ServicoDTO(Servico servico, AnimalDTO animal){
    this.tipo = servico.getTipo();
    this.dtServico = servico.getDtServico();
    this.valor = servico.getValor();
    this.status = servico.getStatus();
    this.animalDTO = animal;
}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public AnimalDTO getAnimalDTO() {
        return animalDTO;
    }

    public void setIdAnimalAtendido(AnimalDTO animalDTO) {
        this.animalDTO = animalDTO;
    }
}
