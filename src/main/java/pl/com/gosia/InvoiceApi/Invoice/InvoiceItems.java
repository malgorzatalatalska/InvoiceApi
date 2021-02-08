package pl.com.gosia.InvoiceApi.Invoice;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class InvoiceItems {
    String name;
    Enum<Unit> unit;
    double quantity;
    Enum<VatRate> vatRate;
    BigDecimal netPrice;
    BigDecimal netValue;
    BigDecimal grossValue;

}
