package pl.com.gosia.api.invoice.invoice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceConfig {

    @Bean
    InvoiceStatement invoiceStatement(@Value("${sql.invoice.select-all}") String selectAllInvoices) {
        return new InvoiceStatement(selectAllInvoices);
    }

}
