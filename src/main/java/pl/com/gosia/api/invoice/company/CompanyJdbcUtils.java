package pl.com.gosia.api.invoice.company;


import org.springframework.jdbc.core.RowMapper;
import pl.com.gosia.api.invoice.company.dto.CompanyView;

class CompanyJdbcUtils {

    static final RowMapper<CompanyView> COMPANY_VIEW_ROW_MAPPER =
            (rs, rowNum) ->
                    CompanyView.builder()
                            .companyId(rs.getLong("company_id"))
                            .companyName(rs.getString("company_name"))
                            .address(rs.getString("adress"))
                            .nip(rs.getString("nip")).build();

    private CompanyJdbcUtils() {
        throw new AssertionError();
    }
}
