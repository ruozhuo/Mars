package com.yiran.ruozhuo.model;

/**
 * Created by ruozhuo on 2017/1/20.
 */
public class Goods {

    private int goodsid;
    private String goodscode;
    private int count;
    private int price;
    private String catogory;

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
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

    public String getCatogory() {
        return catogory;
    }

    public void setCatogory(String catogory) {
        this.catogory = catogory;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsid=" + goodsid +
                ", goodscode='" + goodscode + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", catogory='" + catogory + '\'' +
                '}';
    }
}
