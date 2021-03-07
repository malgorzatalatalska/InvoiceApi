package pl.com.gosia.api.invoice.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@lombok.Value
public class CompanyStatement {

    public CompanyStatement(@Value("${sql.company.select-company-by-nip}") String selectCompanyByNip,
                            @Value("${sql.company.insert-company}") String insertCompany) {
        this.selectCompanyByNip = selectCompanyByNip;
        this.insertCompany = insertCompany;
    }

    String selectCompanyByNip;
    String insertCompany;
}
