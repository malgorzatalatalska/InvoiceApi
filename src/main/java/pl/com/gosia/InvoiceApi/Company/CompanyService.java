package pl.com.gosia.InvoiceApi.Company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.gosia.InvoiceApi.Adress.AdressDTO;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private String clearTransferNip(String nip) {
        return null;
    }

    private boolean checkNipInDB(String nip) {
        return false;
    }

    private CompanyView getCompanyWithDB(String nip) {
        return companyRepository.findCompanyByNip(nip);
    }

    private CompanyView findAndSaveCompanyWithApi(String nip) {
        NewCompany newCompany = new NewCompany("xx","xx","xx");
        return companyRepository.saveCompany(newCompany);
    }

    CompanyView addCompany(String nip) {
        String clearTransferNip = clearTransferNip(nip);
        if(checkNipInDB(clearTransferNip) == false) {
            findAndSaveCompanyWithApi(clearTransferNip);
        }
        return getCompanyWithDB(clearTransferNip);
    }


}
