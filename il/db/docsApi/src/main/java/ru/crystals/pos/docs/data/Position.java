package ru.crystals.pos.docs.data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.StringJoiner;

public class Position {

    private String item;

    private String barcode;

    private String name;

    private BigDecimal count;

    private BigDecimal price;

    private Map<String, String> extraFields;

    public Position(String name, BigDecimal count) {
        this.name = name;
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<String, String> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(Map<String, String> extraFields) {
        this.extraFields = extraFields;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Position.class.getSimpleName() + "[", "]")
            .add("item='" + item + "'")
            .add("barcode='" + barcode + "'")
            .add("name='" + name + "'")
            .add("count=" + count)
            .add("price=" + price)
            .add("extraFields=" + extraFields)
            .toString();
    }
}
