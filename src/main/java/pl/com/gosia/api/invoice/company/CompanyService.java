package pl.com.gosia.api.invoice.company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
import pl.com.gosia.api.invoice.company.dto.MainApiDto;
import pl.com.gosia.api.invoice.company.dto.NewCompany;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyView getCompany(String nip) {
        final String clearTransferNip = clearTransferNip(nip);
        return companyRepository.findCompanyByNip(clearTransferNip).orElseGet(() -> findAndSaveCompanyWithApi(clearTransferNip));
    }

    private String clearTransferNip(String nip) {
        final String clearNip = nip.replaceAll("\\D", "");
        if (clearNip.length() != 10) {
            throw new IllegalArgumentException("Incorrect number of digits in nip");
        }
        return clearNip;
    }

    private CompanyView findAndSaveCompanyWithApi(String nip) {
        NewCompany newCompany = findCompanyWithApi(nip).orElseThrow(() -> new IllegalArgumentException("Incorrect nip"));
        return companyRepository.saveCompany(newCompany);
    }

    private Optional<NewCompany> findCompanyWithApi(String nip) {
        final String apiUrl = "https://wl-api.mf.gov.pl/api/search/nip/";
        final String currentDate = LocalDate.now().toString();
        MainApiDto mainApiDto = new RestTemplate().getForObject(apiUrl + nip + "?date=" + currentDate, MainApiDto.class);
        NewCompany newCompany = NewCompany.builder()
                .companyName(mainApiDto.getResult().getSubject().getName())
                .adress(mainApiDto.getResult().getSubject().getWorkingAddress())
                .nip(mainApiDto.getResult().getSubject().getNip()).build();

        return Optional.ofNullable(newCompany);
    }


}
