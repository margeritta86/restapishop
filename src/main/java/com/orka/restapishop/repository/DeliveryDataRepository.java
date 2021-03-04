package com.orka.restapishop.repository;

import com.orka.restapishop.model.DeliveryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDataRepository extends JpaRepository<DeliveryData,Long> {


}
