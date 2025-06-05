package com.example.API_Servico.Repositories;

import com.example.API_Servico.Models.Servico;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServicoRepository extends MongoRepository<Servico, String> {
}
