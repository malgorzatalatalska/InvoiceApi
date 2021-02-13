package pl.com.gosia.api.invoice.company;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
import pl.com.gosia.api.invoice.company.dto.NewCompany;

import java.util.Optional;

import static pl.com.gosia.api.invoice.company.CompanyJdbcUtils.COMPANY_VIEW_ROW_MAPPER;

@Repository
@AllArgsConstructor
public class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CompanyStatement companyStatement;

    Optional<CompanyView> findCompanyByNip(String nip) {
        return jdbcTemplate.query(companyStatement.getSelectCompanyByNip(),
                preparedStatement -> preparedStatement.setString(1, nip),
                COMPANY_VIEW_ROW_MAPPER).stream().findFirst();
    }

    CompanyView saveCompany(NewCompany newCompany) {
        jdbcTemplate.update(companyStatement.getInsertCompany(), newCompany.getCompanyName(), newCompany.getAdress(), newCompany.getNip());
        return findCompanyByNip(newCompany.getNip()).orElseThrow(() -> new IllegalArgumentException("Company save error, not found in the database"));
    }
}
