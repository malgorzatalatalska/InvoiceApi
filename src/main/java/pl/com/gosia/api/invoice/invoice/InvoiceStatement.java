package pl.com.gosia.api.invoice.invoice;

import lombok.Value;

@Value
public class InvoiceStatement {

    String selectInvoices;
    String selectInvoicesByInvoiceId;
    String selectInvoiceItemsByInvoiceId;
    String insertInvoice;
    String insertInvoiceItems;
    String updateInvoiceByInvoiceId;
    String deleteInvoice;
    String deleteInvoiceItems;

}
