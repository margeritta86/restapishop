package com.orka.restapishop.repository;

import com.orka.restapishop.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BasketRepository extends JpaRepository<Basket,Long> {
}
