package com.example.API_Dono.Services;

import com.example.API_Dono.Models.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {
    private static final String URL = "https://viacep.com.br/ws/{cep}/json/";

    public Endereco buscarEnderecoPorCEP(String cep){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(URL, Endereco.class, cep);
    }
}
