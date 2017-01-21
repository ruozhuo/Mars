package com.yiran.ruozhuo.model;

/**
 * Created by ruozhuo on 2017/1/20.
 */
public class OrderExt extends Order {

    String catogory;

    public String getCatogory() {
        return catogory;
    }

    public void setCatogory(String catogory) {
        this.catogory = catogory;
    }

    @Override
    public String toString() {
        return "OrderExt{" +
                "orderid=" + orderid +
                ", openid='" + openid + '\'' +
                ", goodscode='" + goodscode + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalcost=" + totalcost +
                ", createtime='" + createtime + '\'' +
                ", paymentid=" + paymentid +
                ", catogory='" + catogory + '\'' +
                '}';
    }
}
