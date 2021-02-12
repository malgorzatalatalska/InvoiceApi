package pl.com.gosia.api.invoice.invoice;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceDTOIn;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceDTOOut;

import java.util.List;

@RestController
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping(value = "/api/invoices")
    List<InvoiceDTOOut> findAllInvoices() {
        return invoiceService.findAllInvoices();
    }

    @PostMapping(value = "/api/invoices")
    InvoiceDTOOut addInvoice(@RequestBody InvoiceDTOIn invoiceDTOIn) {
        return invoiceService.addInvoice(invoiceDTOIn);
    }

    @PutMapping(value = "/api/invoices/{id}")
    InvoiceDTOOut updateInvoice(@RequestBody InvoiceDTOIn invoiceDTOIn, @PathVariable int id) {
        return invoiceService.updateInvoice(invoiceDTOIn, id);
    }

    @DeleteMapping(value = "/api/invoices/{id}")
    void deleteInvoice(@PathVariable int id) {
        invoiceService.deleteInvoice(id);
    }


}
