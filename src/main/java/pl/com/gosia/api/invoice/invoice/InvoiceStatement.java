package pl.com.gosia.api.invoice.invoice;

import lombok.Value;

@Value
public class InvoiceStatement {

    String selectInvoicesQuery;
    String selectInvoicesByInvoiceIdQuery;
    String selectInvoiceItemsByInvoiceIdQuery;
    String insertInvoiceQuery;
    String insertInvoiceItemsQuery;
    String updateInvoiceByInvoiceIdQuery;
    String deleteInvoiceQuery;
    String deleteInvoiceItemsQuery;

}
