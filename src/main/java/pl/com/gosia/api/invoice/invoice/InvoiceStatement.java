package pl.com.gosia.api.invoice.invoice;

import lombok.Value;

@Value
public class InvoiceStatement {

    String selectInvoices;
    String selectInvoiceItemsByInvoiceId;
    String insertInvoice;
    String insertInvoiceItems;
    String deleteInvoice;
    String deleteInvoiceItems;




}
