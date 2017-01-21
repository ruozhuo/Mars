package com.yiran.ruozhuo.model;

/**
 * Created by ruozhuo on 2017/1/12.
 */
public class Order {

    int orderid;
    String openid;
    String goodscode;
    int count;
    int price;
    int totalcost;
    String createtime;
    int paymentid;

    public Order() {
    }

    public Order(int orderid, String openid, String goodscode, int count,
                 int price, int totalcost, String createtime, int paymentid) {
        this.orderid = orderid;
        this.openid = openid;
        this.goodscode = goodscode;
        this.count = count;
        this.price = price;
        this.totalcost = totalcost;
        this.createtime = createtime;
        this.paymentid = paymentid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(int totalcost) {
        this.totalcost = totalcost;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(int paymentid) {
        this.paymentid = paymentid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderid=" + orderid +
                ", openid='" + openid + '\'' +
                ", goodscode='" + goodscode + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalcost=" + totalcost +
                ", createtime='" + createtime + '\'' +
                ", paymentid=" + paymentid +
                '}';
    }
}
