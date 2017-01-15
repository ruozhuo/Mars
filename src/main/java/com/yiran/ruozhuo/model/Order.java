package com.yiran.ruozhuo.model;

/**
 * Created by ruozhuo on 2017/1/12.
 */
public class Order {

    private int orderid;
    private String openid;
    private String goodscode;
    private int count;
    private int price;
    private int totalcost;
    private String createtime;
    private int paymentid;

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
