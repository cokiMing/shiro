package com.shiro.entity.BO;

import java.util.Date;

/**
 * Created by wuyiming on 2017/9/18.
 */
public class Man {
    private String id;
    private String name;
    private byte age;
    private double height;
    private int weight;
    private Date createTime;
    private Date updateTime;
    private boolean isAlive;
    private long hairs;

    public Man() {

    }

    public Man(Builder builder) {
        id = builder._id;
        name = builder._name;
        age = builder._age;
        height = builder._height;
        weight = builder._weight;
        createTime = builder._createTime;
        updateTime = builder._updateTime;
        isAlive = builder._isAlive;
        hairs = builder._hairs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public long getHairs() {
        return hairs;
    }

    public void setHairs(long hairs) {
        this.hairs = hairs;
    }

    public static class Builder {
        private String _id;
        private String _name;
        private byte _age;
        private double _height;
        private int _weight;
        private Date _createTime;
        private Date _updateTime;
        private boolean _isAlive;
        private long _hairs;

        public Builder() {
        }

        public Builder id(String id) {
            _id = id;
            return this;
        }

        public Builder name(String name) {
            _name = name;
            return this;
        }

        public Builder age(byte age) {
            _age = age;
            return this;
        }

        public Builder height(double height) {
            _height = height;
            return this;
        }

        public Builder weight(int weight) {
            _weight = weight;
            return this;
        }

        public Builder createTime(Date createTime) {
            _createTime = createTime;
            return this;
        }

        public Builder updateTime(Date updateTime) {
            _updateTime = updateTime;
            return this;
        }

        public Builder isAlive(boolean isAlive) {
            _isAlive = isAlive;
            return this;
        }

        public Builder hairs(long hairs) {
            _hairs = hairs;
            return this;
        }

        public Man build() {
            return new Man(this);
        }
    }
}
