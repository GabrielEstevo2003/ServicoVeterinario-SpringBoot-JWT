package com.example.API_Servico.Controllers;


import com.example.API_Servico.DTOs.ServicoDTO;
import com.example.API_Servico.DTOs.ServicoDTOCriacao;
import com.example.API_Servico.Models.Servico;
import com.example.API_Servico.Services.ServicoService;
import com.example.API_Servico.Services.TokenValidatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("sistema-veterinario/servico")
@Tag(name = "servicos", description = "Gerenciamento de Servicos Veterinarios")
@CrossOrigin(origins = "*")
public class ServicoController {
    @Autowired
    private ServicoService servicoService;
    @Autowired
    private TokenValidatorService tokenValidatorService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ServicoDTOCriacao servicoDTO,
                                    @RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }
        String token = authHeader.replace("Bearer ","");
        Servico novo = servicoService.save(servicoDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PostMapping("/popular")
    public ResponseEntity<?> insertTest(@RequestBody Servico servico){

        Servico novo = servicoService.inserirTeste(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable String id,
                                              @RequestBody Servico servicoAtualizado,
                                              @RequestHeader("Authorization") String authHeader) {

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }
        String token = authHeader.replace("Bearer ", "");
        try {
            Servico servico = servicoService.atualizarServico(id, servicoAtualizado);
            return ResponseEntity.ok(servico);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        return ResponseEntity.ok(servicoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarServicoDetalhado(@PathVariable String id,
                                                    @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        try {
            ServicoDTO servicoDTO = servicoService.buscarServicoDetalhado(id, token);
            return ResponseEntity.ok(servicoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id,
                                        @RequestHeader("Authorization") String authHeader){

        if (!tokenValidatorService.validarToken(authHeader.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        servicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
