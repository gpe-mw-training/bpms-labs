package org.acme.insurance;

import java.util.Date;

public class PolicyBinding {
    private int price;
    private Date bindingDate;

    public PolicyBinding(){}

    public PolicyBinding(int p, Date dt) {
        price = p; bindingDate = dt;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public Date getBindingDate() {
        return bindingDate;
    }
    public void setBindingDate(Date bindingDate) {
        this.bindingDate = bindingDate;
    }

    public String toString() {
        return "PolicyBinding [price=" + price + ", bindingDate=" + bindingDate + "]";
    }
}
