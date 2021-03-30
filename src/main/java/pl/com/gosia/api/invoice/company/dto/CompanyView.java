package pl.com.gosia.api.invoice.company.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
public class CompanyView {
    Long companyId;
    String companyName;
    String address;
    String nip;
}
