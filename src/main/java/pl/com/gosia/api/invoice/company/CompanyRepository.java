package pl.com.gosia.api.invoice.company;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
import pl.com.gosia.api.invoice.company.dto.NewCompany;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    static RowMapper<CompanyView> companyViewRowMapper = (rs, rowNum) ->
            new CompanyView(
                    rs.getLong("company_id"),
                    rs.getString("company_name"),
                    rs.getString("adress"),
                    rs.getString("nip")
            );

    Optional<CompanyView> findCompanyByNip(String nip) {
        String sqlQuery = "SELECT * FROM Company WHERE nip = ?";
        return jdbcTemplate.query(sqlQuery,
                preparedStatement -> preparedStatement.setString(1, nip),
                companyViewRowMapper).stream().findFirst();
    }

    CompanyView saveCompany(NewCompany newCompany) {
        String sqlQuery = "INSERT INTO Company (company_name, adress, nip) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, newCompany.getCompanyName(), newCompany.getAdress(), newCompany.getNip());
        return findCompanyByNip(newCompany.getNip()).orElseThrow(() -> new IllegalArgumentException("Company save error, not found in the database"));
    }
}
