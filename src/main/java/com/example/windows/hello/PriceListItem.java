package com.example.windows.hello;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceListItem {

    private String modelNumber;
    private BigDecimal price;
    private ChangeType changeType;

}
