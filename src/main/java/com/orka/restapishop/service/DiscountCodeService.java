package com.orka.restapishop.service;

import com.orka.restapishop.model.DiscountCode;
import com.orka.restapishop.repository.DiscountCodeRepository;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
public class DiscountCodeService {
    private DiscountCodeRepository discountCodeRepository;

    public DiscountCodeService(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    @PostConstruct
    public void fillDatabase() {//uzupełniam testowymi danymi
        DiscountCode discountCode1 = new DiscountCode("summersale",0.2, LocalDate.now().plusMonths(3));
        DiscountCode discountCode2 = new DiscountCode("springsale",0.4, LocalDate.now().plusMonths(1));

        discountCodeRepository.save(discountCode1);
        discountCodeRepository.save(discountCode2);
    }
}
