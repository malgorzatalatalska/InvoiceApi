package pl.com.gosia.api.invoice.invoice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceConfig {

    @Bean
    InvoiceStatement invoiceStatement(@Value("${sql.invoice.select-invoice}") String selectInvoices,
                                      @Value("${sql.invoice.select-invoice-items-by-invoice-id}") String selectInvoiceItemsByInvoiceId,
                                      @Value("${sql.invoice.insert-invoice}") String insertInvoice,
                                      @Value("${sql.invoice.insert-invoice-items}") String insertInvoiceItems,
                                      @Value("${sql.invoice.delete-invoice}") String deleteInvoice,
                                      @Value("${sql.invoice.delete-invoice-items}") String deleteInvoiceItems) {

        return new InvoiceStatement(selectInvoices, selectInvoiceItemsByInvoiceId, insertInvoice, insertInvoiceItems,
                deleteInvoice, deleteInvoiceItems);
    }

}
