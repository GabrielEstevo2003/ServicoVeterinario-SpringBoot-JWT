package com.example.API_Dono.DTOs;

import com.example.API_Dono.Models.Endereco;

public class EnderecoDTO {
    private String cep;
    private String localidade;
    private String logradouro;
    private String uf;

    public EnderecoDTO(Endereco endereco){
        this.cep = endereco.getCep();
        this.localidade = endereco.getLocalidade();
        this.logradouro = endereco.getLogradouro();
        this.uf = endereco.getUf();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
