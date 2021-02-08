package pl.com.gosia.InvoiceApi.Company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    CompanyView getCompany(String nip) {
        String clearTransferNip = clearTransferNip(nip);
        return companyRepository.findCompanyByNip(clearTransferNip).orElseGet(() -> findAndSaveCompanyWithApi(clearTransferNip));
    }

    private String clearTransferNip(String nip) {
        return null;
    }

    private Optional<NewCompany> findCompanyWithApi(String nip) {
        return Optional.empty();
    }

    private CompanyView findAndSaveCompanyWithApi(String nip) {
        NewCompany newCompany = findCompanyWithApi(nip).orElseThrow(() -> new IllegalArgumentException("Incorrect nip"));
        return companyRepository.saveCompany(newCompany);
    }


}
