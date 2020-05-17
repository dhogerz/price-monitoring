package com.fabelio.test.repository;

import com.fabelio.test.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yoga.pramugia01
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByName(String name);
}
