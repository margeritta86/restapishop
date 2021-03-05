package com.orka.restapishop.repository;


import com.orka.restapishop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE p.price >= ?1")
    Collection<Product> findProductsByMinPrice(Double minPrice);
    @Query("SELECT p FROM Product p WHERE p.price <= ?1")
    Collection<Product> findProductsByMaxPrice(Double maxPrice);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %?1%")
    Collection<Product> findProductsByKeyword(String keyword);

}
