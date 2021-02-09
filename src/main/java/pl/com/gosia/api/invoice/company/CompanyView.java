package pl.com.gosia.api.invoice.company;

import lombok.Setter;
import lombok.Value;

@Value
public class CompanyView {
    Long companyId;
    String companyName;
    String adress;
    String nip;
}
