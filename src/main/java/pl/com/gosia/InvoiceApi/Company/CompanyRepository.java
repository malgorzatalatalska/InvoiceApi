package pl.com.gosia.InvoiceApi.Company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CompanyRepository {

    CompanyView findCompanyByNip(String nip) {
        return null;
    }

    public CompanyView saveCompany(NewCompany newCompany) {
        return null;
    }
}
