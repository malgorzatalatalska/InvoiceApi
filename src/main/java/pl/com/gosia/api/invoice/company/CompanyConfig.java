package pl.com.gosia.api.invoice.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyConfig {
    @Bean
    CompanyStatement companyStatement(@Value("${sql.company.select-company-by-nip}") String selectCompanyByNip,
                                      @Value("${sql.company.insert-company}") String insertCompany) {
        return new CompanyStatement(selectCompanyByNip, insertCompany);
    }
}
