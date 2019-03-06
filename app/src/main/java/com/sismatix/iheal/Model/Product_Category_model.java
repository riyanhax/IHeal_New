package com.sismatix.iheal.Model;

public class Product_Category_model {
    String category_name, value;


    public Product_Category_model(String category_name, String value) {

        this.category_name = category_name;
        this.value = value;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
