package ru.crystals.pos.bl.api.sale.goods;

import java.math.BigDecimal;
import java.util.Map;

public class Position {

    private String item;

    private String barcode;

    private String name;

    private BigDecimal count;

    private BigDecimal price;

    private Map<String, String> extraFields;

}
