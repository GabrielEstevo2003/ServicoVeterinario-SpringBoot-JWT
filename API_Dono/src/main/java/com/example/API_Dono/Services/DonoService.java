package com.example.API_Dono.Services;

import com.example.API_Dono.DTOs.AnimalDTO;
import com.example.API_Dono.DTOs.DonoDTO;
import com.example.API_Dono.DTOs.EnderecoDTO;
import com.example.API_Dono.Models.Dono;
import com.example.API_Dono.Models.Endereco;
import com.example.API_Dono.Repositories.DonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DonoService {
    @Autowired
    private DonoRepository donoRepository;
    @Autowired
    private ViaCepService viaCepService;
    @Autowired
    private RestTemplate restTemplate;

    public Dono save(Dono dono, String cep, String token){
        if (!verificarAnimalExiste(dono.getIdAnimal(), token)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal com esse ID nao cadastrado");
        }
        Endereco endereco = viaCepService.buscarEnderecoPorCEP(cep);
        dono.setEndereco(endereco);
        return donoRepository.save(dono);
    }

    public boolean verificarAnimalExiste(String animalId, String token) {
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
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (HttpClientErrorException.BadRequest e) {
            return false;  // Trata erro 400 também
        } catch (Exception e) {
            return false;  // Qualquer outro erro, considera que não existe
        }
    }

    public List<Dono> findAll(){
        return donoRepository.findAll();
    }

    public DonoDTO buscarDonoDetalhado(String id, String token) {
        Dono dono = donoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dono não encontrado"));

        AnimalDTO animalDTO = null;
        if (dono.getIdAnimal() != null && !dono.getIdAnimal().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String urlAnimal = "http://localhost:8081/sistema-veterinario/animal/" + dono.getIdAnimal();
            ResponseEntity<AnimalDTO> response = restTemplate.exchange(
                    urlAnimal,
                    HttpMethod.GET,
                    entity,
                    AnimalDTO.class
            );

            animalDTO = response.getBody();
        }

        EnderecoDTO enderecoDTO = new EnderecoDTO(dono.getEndereco());

        return new DonoDTO(dono, enderecoDTO, animalDTO);
    }

    public Optional<Dono> findById(String id){
        return donoRepository.findById(id);
    }

    public void deleteById(String id){
        donoRepository.deleteById(id);
    }

    public Dono inserirTeste(Dono dono, String cep){
        Endereco endereco = viaCepService.buscarEnderecoPorCEP(cep);
        dono.setEndereco(endereco);
        return donoRepository.save(dono);
    }
}

