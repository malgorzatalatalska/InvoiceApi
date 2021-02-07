package pl.com.gosia.InvoiceApi.Invoice;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping(value = "invoices")
    List<InvoiceDTOOut> findAllInvoices() {
        return invoiceService.findAllInvoices();
    }

    @PostMapping(value = "invoices")
    InvoiceDTOOut addInvoice(@RequestBody InvoiceDTOIn invoiceDTOIn) {
        return invoiceService.addInvoice(invoiceDTOIn);
    }

    @PutMapping(value = "invoices/{id}")
    InvoiceDTOOut updateInvoice(@RequestBody InvoiceDTOIn invoiceDTOIn,@RequestParam int id) {
        return invoiceService.updateInvoice(invoiceDTOIn,id);
    }

    @DeleteMapping(value = "invoices/{id}")
    boolean deleteInvoice(@RequestParam int id) {
        return invoiceService.deleteInvoice(id);
    }



}
