package com.shakhawat.mollah.repository;

import com.shakhawat.mollah.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
