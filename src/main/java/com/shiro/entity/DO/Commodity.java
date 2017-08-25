package com.shiro.entity.DO;

import java.util.Date;

/**
 * Created by wuyiming on 2017/8/24.
 */
public class Commodity {
    //商品id
    private String commodityId;
    //种类id
    private String typeId;
    //品类id
    private String categoryId;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //状态 ON_SALE 上架中| OFF_SALE 下架/缺货| DELETE已删除
    private String status;

    //商品名
    private String name;
    //商品描述
    private String description;
    //商品图片
    private String pics;
    //商品计量单位
    private String unit;
    //商品价格
    private Double prize;
    //商品库存
    private Integer num;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityId='" + commodityId + '\'' +
                ", typeId='" + typeId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pics=" + pics +
                ", unit='" + unit + '\'' +
                ", prize=" + prize +
                ", num=" + num +
                '}';
    }
}
