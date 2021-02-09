package pl.com.gosia.api.invoice.invoice;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class InvoiceItems {
    String name;
    UnitMeasure unitMeasure;
    double quantity;
    VatRate vatRate;
    BigDecimal netPrice;
    BigDecimal netValue;
    BigDecimal grossValue;

}
