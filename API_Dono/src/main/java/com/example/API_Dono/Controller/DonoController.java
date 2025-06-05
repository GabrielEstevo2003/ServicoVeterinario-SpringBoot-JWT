package com.example.API_Dono.Controller;


import com.example.API_Dono.DTOs.DonoDTO;
import com.example.API_Dono.Models.Dono;
import com.example.API_Dono.Models.Endereco;
import com.example.API_Dono.Services.DonoService;
import com.example.API_Dono.Services.TokenValidatorService;
import com.example.API_Dono.Services.ViaCepService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("sistema-veterinario/dono")
@Tag(name = "donos", description = "Gerenciamento de Donos dos Pets")
@CrossOrigin(origins = "*")
public class DonoController {
    @Autowired
    private DonoService donoService;
    @Autowired
    private TokenValidatorService tokenValidatorService;
    @Autowired
    private ViaCepService viaCepService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Dono dono, @RequestParam String cep,
                                       @RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        String token = authHeader.replace("Bearer ", "");

        Dono novo = donoService.save(dono, cep, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDono(@PathVariable String id,
                                        @RequestBody Dono donoAtualizado,
                                        @RequestParam("cep") String cep,
                                        @RequestHeader("Authorization") String authHeader) {

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        String token = authHeader.replace("Bearer ", "");

        Optional<Dono> donoExistente = donoService.findById(id);
        if (!donoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dono não encontrado");
        }

        if (!donoService.verificarAnimalExiste(donoAtualizado.getIdAnimal(), token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Animal informado não existe");
        }

        Endereco endereco = viaCepService.buscarEnderecoPorCEP(cep);
        Dono dono = donoExistente.get();
        dono.setNome(donoAtualizado.getNome());
        dono.setCpf(donoAtualizado.getCpf());
        dono.setTelefone(donoAtualizado.getTelefone());
        dono.setIdAnimal(donoAtualizado.getIdAnimal());
        dono.setEndereco(endereco);

        Dono atualizado = donoService.save(dono, cep, token);
        return ResponseEntity.ok(atualizado);
    }


    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        return ResponseEntity.ok(donoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Dono>> findById(@PathVariable String id,
                                                 @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(donoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id,
                                           @RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        donoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
