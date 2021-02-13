package pl.com.gosia.api.invoice.invoice.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class InvoiceItems {
    String name;
    UnitMeasure unitMeasure;
    double quantity;
    VatRate vatRate;
    BigDecimal netPrice;
    BigDecimal netValue;
    BigDecimal grossValue;

}
