package com.example.API_Animal.Repositories;

import com.example.API_Animal.Models.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalRepository extends MongoRepository<Animal, String> {
}
