package com.orka.restapishop.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class DeliveryDataDto {
    @NotNull
    @Size(min = 3, message = "Invalid city - It has to be at least 3 characters !")
    @Pattern (regexp = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+$",message = "Field city has to contain only characters ")
    private String city;
    @NotNull
    @Size(min = 3, message = "Invalid street - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+$")
    private String street;
    @NotNull
    @Size(min = 1, message = "Invalid building number - It has to be at least 3 characters !")
    @Pattern(regexp = "[1-9]+")
    private String buildingNumber;
    private String flatNumber;
    @NotNull
    @Size(min = 3, message = "Invalid city - It has to be at least 3 characters !")
    private String postCode;
    @NotNull
    @Size(min = 4, message = "Invalid country - It has to be at least 4 characters !")
    @Pattern(regexp = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+$")
    private String country;
    @NotNull
    @Size(min = 3, message = "Invalid first name - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+$")
    private String firstName;
    @NotNull
    @Size(min = 3, message = "Invalid last name - It has to be at least 3 characters !")
    @Pattern(regexp = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+$")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid mail format!")
    private String email;

}
