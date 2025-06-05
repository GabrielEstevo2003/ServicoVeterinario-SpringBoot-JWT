package com.example.API_Animal.Service;

import com.example.API_Animal.DTOs.AnimalDTO;
import com.example.API_Animal.DTOs.DonoDTO;
import com.example.API_Animal.Models.Animal;
import com.example.API_Animal.Repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Animal save(Animal animal, String token){
        if(!verificarDonoExiste(animal.getIdDono(), token)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dono com esse ID nao Cadastrado");
        }
        return animalRepository.save(animal);
    }

    public Animal atualizarAnimal(String id, Animal animalAtualizado) {
        Animal animalExistente = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        animalExistente.setNome(animalAtualizado.getNome());
        animalExistente.setRaca(animalAtualizado.getRaca());
        animalExistente.setIdade(animalAtualizado.getIdade());
        animalExistente.setIdDono(animalAtualizado.getIdDono());

        return animalRepository.save(animalExistente);
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
        } catch (HttpClientErrorException.BadRequest e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Animal> findAll(){
        return animalRepository.findAll();
    }

    public Optional<Animal> findById(String id){
        return animalRepository.findById(id);
    }

    public void deleteById(String id){
        animalRepository.deleteById(id);
    }

    public Animal inserirTeste(Animal animal){
        return animalRepository.save(animal);
    }
}

