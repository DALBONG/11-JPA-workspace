package helljpa.jpql;

import jdk.jfr.DataAmount;

@DataAmount
public class ProductDTO {

    private String name;

    private int price;

    private int stockAmount;

    public ProductDTO( String name, int price, int stockAmount) {
        this.name = name;
        this.price = price;
        this.stockAmount = stockAmount;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockAmount() {
        return stockAmount;
    }
}
