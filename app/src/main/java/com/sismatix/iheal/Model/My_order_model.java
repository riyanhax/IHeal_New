package com.sismatix.iheal.Model;

public class My_order_model {
    String increment_id;
    String created_at;
    String name;
    String grand_total;
    String payment_method;
    String order_id;

    public My_order_model(String increment_id, String created_at, String name, String grand_total,String payment_method,String order_id) {
        this.increment_id = increment_id;
        this.created_at = created_at;
        this.name = name;
        this.grand_total = grand_total;
        this.payment_method = payment_method;
        this.order_id = order_id;
    }
    public String getIncrement_id() {
        return increment_id;
    }
    public void setIncrement_id(String increment_id) {
        this.increment_id = increment_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
