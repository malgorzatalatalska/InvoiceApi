package pl.com.gosia.api.invoice.company;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.com.gosia.api.invoice.company.dto.NewCompany;

import java.time.LocalDate;

@Service
public class CompanyApiClient {

    private final String apiUrl;

    public CompanyApiClient(@Value("${invoice.api-url}") String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public NewCompany findCompanyWithApi(String nip) {

        final var currentDate = LocalDate.now().toString();

        final var companyApiDto = new RestTemplate().getForObject(apiUrl + nip + "?date=" + currentDate, CompanyApiDto.class);

        if (companyApiDto == null) {
            throw new IllegalArgumentException("No company info found for nip: " + nip);
        }

        return NewCompany.builder()
                .companyName(companyApiDto.getResult().getSubject().getName())
                .adress(companyApiDto.getResult().getSubject().getWorkingAddress())
                .nip(companyApiDto.getResult().getSubject().getNip()).build();
    }

    @Data
    private static class CompanyApiDto {
        private ResultApiDto result;
    }

    @Data
    private static class ResultApiDto {
        private SubjectApiDto subject;
    }

    @Data
    private static class SubjectApiDto {
        private String name;
        private String nip;
        private String workingAddress;
    }
}
