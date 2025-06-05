package com.example.API_Autenticacao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RequestMapping("sistema-veterinario")
@RestController
@CrossOrigin(origins = "*")
public class CredentialController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${credential.username}")
    private String username;

    @Value("${credential.password}")
    private String password;

    @PostMapping("/autenticar")
    public ResponseToken getToken(@RequestBody User user){
        if (user.getUser().equals(username) && user.getPassword().equals(password)){
            String token = jwtTokenProvider.generateToken(user.getUser());
            return new ResponseToken("Authenticated", token);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid User");
    }

    @GetMapping("/validar")
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token nao fornecido ou mal formatado");
        }

        String token = authHeader.substring(7);
        boolean valido = jwtTokenProvider.validarToken(token);

        if (valido){
            return ResponseEntity.ok("Token Valido");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Invalido ou Expirado");
        }
    }
}
