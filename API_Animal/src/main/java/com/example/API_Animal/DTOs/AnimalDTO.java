package com.example.API_Animal.DTOs;

import com.example.API_Animal.Models.Animal;
import org.springframework.data.annotation.Id;

public class AnimalDTO {
    private String nome;
    private int idade;
    private String especie;
    private String raca;
    private String idDono;
    public AnimalDTO(Animal animal, DonoDTO dono){
        this.especie = animal.getEspecie();
        this.nome = animal.getNome();
        this.idade = animal.getIdade();
        this.raca = animal.getRaca();
        this.idDono = dono.getId();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getDono() {
        return idDono;
    }

    public void setDono(String idDono) {
        this.idDono = idDono;
    }
}
