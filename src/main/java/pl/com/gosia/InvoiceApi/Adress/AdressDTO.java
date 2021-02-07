package pl.com.gosia.InvoiceApi.Adress;

import lombok.Value;

@Value
public class AdressDTO {
    Long addressId;
    String street;
    String city;
    String zip_code;
}