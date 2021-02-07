package pl.com.gosia.InvoiceApi.Company;

import lombok.Value;
import pl.com.gosia.InvoiceApi.Adress.AdressDTO;

@Value
public class CompanyView {
    Long companyId;
    String companyName;
    String adress;
    String nip;
}
