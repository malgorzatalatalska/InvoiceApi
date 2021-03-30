package pl.com.gosia.api.invoice.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@lombok.Value
class CompanyStatement {

    CompanyStatement(@Value("${sql.company.select-company-by-nip}") String selectCompanyByNipQuery,
                            @Value("${sql.company.insert-company}") String insertCompanyQuery) {
        this.selectCompanyByNipQuery = selectCompanyByNipQuery;
        this.insertCompanyQuery = insertCompanyQuery;
    }

    String selectCompanyByNipQuery;
    String insertCompanyQuery;
}
