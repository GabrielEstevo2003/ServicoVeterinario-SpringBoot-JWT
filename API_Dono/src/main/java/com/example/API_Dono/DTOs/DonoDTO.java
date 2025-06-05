package com.example.API_Dono.DTOs;

import com.example.API_Dono.Models.Dono;

public class DonoDTO {
    private String nome;
    private Long cpf;
    private EnderecoDTO endereco;
    private String telefone;
    private String idAnimal;

    private AnimalDTO animalDTO; // Incluímos se quiser detalhar o Animal também.

    public DonoDTO(Dono dono, EnderecoDTO endereco, AnimalDTO animalDTO) {
        this.nome = dono.getNome();
        this.cpf = dono.getCpf();
        this.endereco = endereco;
        this.telefone = dono.getTelefone();
        this.idAnimal = dono.getIdAnimal();
        this.animalDTO = animalDTO; // Opcional: se quiser trazer também.
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }
}
