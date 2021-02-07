package pl.com.gosia.InvoiceApi.Company;

import lombok.Value;
import pl.com.gosia.InvoiceApi.Adress.AdressDTO;

@Value
public class CompanyDTO {
    Long companyId;
    String companyName;
    AdressDTO adressDTO;
    String nip;
}
