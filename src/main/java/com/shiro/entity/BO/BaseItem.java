package com.shiro.entity.BO;

import java.util.Set;

/**
 * Created by wuyiming on 2017/8/4.
 */
public class BaseItem {
    private String name;
    private String enName;
    private String description;
    private String type;
    private Set<String> itemPool;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getItemPool() {
        return itemPool;
    }

    public void setItemPool(Set<String> itemPool) {
        this.itemPool = itemPool;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
