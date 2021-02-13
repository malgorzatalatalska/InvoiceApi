package pl.com.gosia.api.invoice.company;

import lombok.Value;

@Value
public class CompanyStatement {
    String selectCompanyByNip;
    String insertCompany;
}
