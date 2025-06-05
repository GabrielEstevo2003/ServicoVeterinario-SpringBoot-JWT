package com.example.API_Servico.Services;


import com.example.API_Servico.DTOs.AnimalDTO;
import com.example.API_Servico.DTOs.DonoDTO;
import com.example.API_Servico.DTOs.ServicoDTO;
import com.example.API_Servico.DTOs.ServicoDTOCriacao;
import com.example.API_Servico.Models.Servico;
import com.example.API_Servico.Repositories.ServicoRepository;
import com.example.API_Servico.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Servico save(ServicoDTOCriacao servicoDTOCriacao, String token){
        validarAnimalEDono(servicoDTOCriacao.getIdAnimalAtendido(), token);


        Servico servico = new Servico();
        servico.setTipo(servicoDTOCriacao.getTipo());
        servico.setDtServico(servicoDTOCriacao.getDtServico());
        servico.setValor(servicoDTOCriacao.getValor());
        servico.setIdAnimalAtendido(servicoDTOCriacao.getIdAnimalAtendido());
        servico.setStatus(servicoDTOCriacao.getStatus());

        return servicoRepository.save(servico);
    }

    public AnimalDTO verificarAnimalExiste(String animalId, String token) {
        String url = "http://localhost:8081/sistema-veterinario/animal/" + animalId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<AnimalDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    AnimalDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (HttpClientErrorException.BadRequest e) {
            return null;  // Trata erro 400 também
        } catch (Exception e) {
            return null;  // Qualquer outro erro, considera que não existe
        }
    }

    public Servico inserirTeste(Servico servico){
        return servicoRepository.save(servico);
    }

    public boolean verificarDonoExiste(String donoId, String token) {
        String url = "http://localhost:8084/sistema-veterinario/dono/" + donoId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);


        try {
            ResponseEntity<DonoDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    DonoDTO.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    public void validarAnimalEDono(String animalId, String token) {
        AnimalDTO animal = verificarAnimalExiste(animalId, token);
        if (animal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
    }

    public Servico atualizarServico(String id, Servico servicoAtualizado) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servicoExistente.setTipo(servicoAtualizado.getTipo());
        servicoExistente.setDtServico(servicoAtualizado.getDtServico());
        servicoExistente.setValor(servicoAtualizado.getValor());
        servicoExistente.setStatus(servicoAtualizado.getStatus());
        servicoExistente.setIdAnimalAtendido(servicoAtualizado.getIdAnimalAtendido());

        return servicoRepository.save(servicoExistente);
    }


    public List<Servico> findAll(){
        return servicoRepository.findAll();
    }

    public ServicoDTO buscarServicoDetalhado(String id, String token) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        String idAnimal = servico.getIdAnimalAtendido();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8081/sistema-veterinario/animal/" + idAnimal;
        ResponseEntity<AnimalDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                AnimalDTO.class
        );

        AnimalDTO animalDTO = response.getBody();

        return new ServicoDTO(servico, animalDTO);
    }

    public void deleteById(String id){
        servicoRepository.deleteById(id);
    }
}

