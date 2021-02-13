package pl.com.gosia.api.invoice.company.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewCompany {
    String companyName;
    String adress;
    String nip;
}
