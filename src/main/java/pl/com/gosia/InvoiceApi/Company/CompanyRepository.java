package pl.com.gosia.InvoiceApi.Company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CompanyRepository {

    Optional<CompanyView> findCompanyByNip(String nip) {
        return Optional.empty();
    }

    CompanyView saveCompany(NewCompany newCompany) {
        return null;
    }
}
