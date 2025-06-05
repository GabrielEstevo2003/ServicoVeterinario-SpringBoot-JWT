package com.example.API_Dono.Repositories;

import com.example.API_Dono.Models.Dono;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DonoRepository extends MongoRepository<Dono, String> {
}
