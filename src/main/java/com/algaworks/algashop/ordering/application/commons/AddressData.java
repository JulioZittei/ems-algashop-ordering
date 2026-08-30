package com.algaworks.algashop.ordering.application.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressData {

    private String street;
    private String number;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
}
