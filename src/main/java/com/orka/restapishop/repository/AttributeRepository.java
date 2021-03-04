package com.orka.restapishop.repository;

import com.orka.restapishop.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository  extends JpaRepository<Attribute,Long> {


}
