package pl.com.gosia.InvoiceApi.Company;

import lombok.Value;
import pl.com.gosia.InvoiceApi.Adress.AdressDTO;

@Value
public class NewCompany {
    String companyName;
    String adress;
    String nip;
}
