package com.orka.restapishop.repository;

import com.orka.restapishop.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AttributeRepository  extends JpaRepository<Attribute,Long> {


}
