package pl.com.gosia.InvoiceApi.Invoice;

import lombok.AllArgsConstructor;
import lombok.Value;


@AllArgsConstructor
public enum VatRate {
    RATE_0_PERCENTS(0),
    RATE_5_PERCENTS(5),
    RATE_8_PERCENTS(8),
    RATE_23_PERCENTS(23);

    int value;

}
