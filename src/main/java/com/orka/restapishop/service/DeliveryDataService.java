package com.orka.restapishop.service;

import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.model.Address;
import com.orka.restapishop.model.DeliveryData;
import com.orka.restapishop.repository.AddressRepository;
import com.orka.restapishop.repository.DeliveryDataRepository;
import org.springframework.stereotype.Service;



@Service
public class DeliveryDataService {

    private DeliveryDataRepository deliveryDataRepository;
    private AddressRepository addressRepository;

    public DeliveryDataService(DeliveryDataRepository deliveryDataRepository, AddressRepository addressRepository) {
        this.deliveryDataRepository = deliveryDataRepository;
        this.addressRepository = addressRepository;
    }


    public DeliveryData createAndSaveDeliveryData( DeliveryDataDto deliveryDataDto){
        Address address = new Address(deliveryDataDto.getCity(), deliveryDataDto.getStreet(), deliveryDataDto.getBuildingNumber(),
                deliveryDataDto.getFlatNumber(), deliveryDataDto.getPostCode(), deliveryDataDto.getCountry());
        DeliveryData deliveryData = new DeliveryData(address, deliveryDataDto.getFirstName(), deliveryDataDto.getLastName(), deliveryDataDto.getEmail());
        addressRepository.save(address);
        deliveryDataRepository.save(deliveryData);
        return deliveryData;
    }
}
