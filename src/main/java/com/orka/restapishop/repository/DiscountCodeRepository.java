package com.orka.restapishop.repository;

import com.orka.restapishop.model.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode,Long> {

    Optional<DiscountCode> findByName(String name);

}
