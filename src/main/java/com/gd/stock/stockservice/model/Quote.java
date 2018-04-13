package com.gd.stock.stockservice.model;

public class Quote {
    private String stockName;
    private String price;

    public Quote() {
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
