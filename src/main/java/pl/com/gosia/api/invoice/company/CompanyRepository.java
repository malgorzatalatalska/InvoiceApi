package pl.com.gosia.api.invoice.company;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
import pl.com.gosia.api.invoice.company.dto.NewCompany;

import java.util.Optional;

import static pl.com.gosia.api.invoice.company.CompanyJdbcUtils.COMPANY_VIEW_ROW_MAPPER;

@Repository
@RequiredArgsConstructor
class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CompanyStatement companyStatement;

    Optional<CompanyView> findCompanyByNip(String nip) {

        final var selectCompanyByNipQuery = companyStatement.getSelectCompanyByNipQuery();

        return jdbcTemplate.query(
                selectCompanyByNipQuery,
                preparedStatement -> preparedStatement.setString(1, nip),
                COMPANY_VIEW_ROW_MAPPER)
                .stream().findFirst();
    }

    CompanyView saveCompany(NewCompany newCompany) {

        final var insertCompanyQuery = companyStatement.getInsertCompanyQuery();

        jdbcTemplate.update(insertCompanyQuery, newCompany.getCompanyName(), newCompany.getAddress(), newCompany.getNip());

        return findCompanyByNip(newCompany.getNip())
                .orElseThrow(() ->
                        new IllegalArgumentException("Could not find company for NIP: " + newCompany.getNip()));
    }
}
