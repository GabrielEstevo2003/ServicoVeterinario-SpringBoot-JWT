package com.example.API_Animal.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenValidatorService {
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean validarToken(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void>entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response =restTemplate.exchange(
                "http://localhost:8083/sistema-veterinario/validar",
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getStatusCode() == HttpStatus.OK;
        }catch (HttpClientErrorException | ResourceAccessException e){
            return false;
        }
    }
}
