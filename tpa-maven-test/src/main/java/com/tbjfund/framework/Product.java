package com.tbjfund.framework;


import com.tbjfund.framework.tpa.lang.annotation.PrimaryKey;
import com.tbjfund.framework.tpa.lang.annotation.Table;

@Table(name = "product")
public class Product {

    @PrimaryKey
	private Integer id;

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
