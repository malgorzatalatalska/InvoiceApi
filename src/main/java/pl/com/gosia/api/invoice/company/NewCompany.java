package pl.com.gosia.api.invoice.company;

import lombok.Value;

@Value
public class NewCompany {
    String companyName;
    String adress;
    String nip;
}