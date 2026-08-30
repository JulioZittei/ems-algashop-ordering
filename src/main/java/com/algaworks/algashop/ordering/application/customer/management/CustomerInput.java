package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInput {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String document;
    private LocalDate birthDate;
    private Boolean promotionNotificationsAllowed;
    private AddressData address;

    public String getAddressStreet() {
        return address.getStreet();
    }

    public String getAddressNumber() {
        return address.getNumber();
    }

    public String getAddressComplement() {
        return address.getComplement();
    }

    public String getAddressNeighborhood() {
        return address.getNeighborhood();
    }

    public String getAddressCity() {
        return address.getCity();
    }

    public String getAddressState() {
        return address.getState();
    }

    public String getAddressZipCode() {
        return address.getZipCode();
    }
}
