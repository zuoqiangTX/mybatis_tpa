package com.tbjfund.framework;

import com.tbjfund.framework.tpa.annotation.Column;
import com.tbjfund.framework.tpa.annotation.Table;

@Table(name = "product", aliasName = "Product")
public class Product {

    @Column(name = "id", isPrimaryKey = true)
	private Integer id;

    @Column(name = "name")
	private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
