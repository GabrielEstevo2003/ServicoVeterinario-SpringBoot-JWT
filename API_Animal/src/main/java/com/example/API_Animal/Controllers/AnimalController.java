package com.example.API_Animal.Controllers;

import com.example.API_Animal.DTOs.AnimalDTO;
import com.example.API_Animal.Models.Animal;
import com.example.API_Animal.Service.AnimalService;
import com.example.API_Animal.Service.TokenValidatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("sistema-veterinario/animal")
@Tag(name = "animais", description = "Gerenciamento de Animais")
@CrossOrigin(origins = "*")
public class AnimalController {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private TokenValidatorService tokenValidatorService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Animal animal,
                                         @RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        String token = authHeader.replace("Bearer ", "");
        Animal novo = animalService.save(animal, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAnimal(@PathVariable String id,
                                             @RequestBody Animal animalAtualizado,
                                             @RequestHeader("Authorization") String authHeader) {

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        String token = authHeader.replace("Bearer ", "");
        try {
            Animal animal = animalService.atualizarAnimal(id, animalAtualizado);
            return ResponseEntity.ok(animal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        return ResponseEntity.ok(animalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Animal>> findById(@PathVariable("id") String id,
                                      @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(animalService.findById(id));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id,
                                           @RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        animalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
