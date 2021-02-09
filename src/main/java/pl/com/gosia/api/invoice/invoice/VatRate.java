package pl.com.gosia.api.invoice.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VatRate {
    RATE_0_PERCENTS(0),
    RATE_5_PERCENTS(5),
    RATE_8_PERCENTS(8),
    RATE_23_PERCENTS(23);

    private final int value;

}
