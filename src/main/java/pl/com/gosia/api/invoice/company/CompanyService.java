package pl.com.gosia.api.invoice.company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.gosia.api.invoice.company.dto.CompanyView;

import static com.google.common.base.Preconditions.checkArgument;
import static pl.com.gosia.api.invoice.company.NipUtils.clearNip;
import static pl.com.gosia.api.invoice.company.NipUtils.validateNip;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyApiClient companyApiClient;

    public CompanyView findCompany(String nip) {

        checkArgument(nip != null, "Expected not null nip");

        final var clearedNip = clearNip(nip);

        validateNip(clearedNip);

        return companyRepository.findCompanyByNip(clearedNip)
                .orElseGet(() -> findInPublicApi(clearedNip));
    }

    private CompanyView findInPublicApi(String nip) {
        final var newCompany = companyApiClient.findCompanyWithApi(nip);
        return companyRepository.saveCompany(newCompany);
    }

}
