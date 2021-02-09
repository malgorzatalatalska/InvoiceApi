package pl.com.gosia.api.invoice.company;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.gosia.api.invoice.invoice.InvoiceDTOOut;

import java.util.List;

@RestController
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping(value = "/api/company")
    CompanyView findAllInvoices(@RequestParam String nip) {
        return companyService.getCompany(nip);
    }
}
