package com.tbjfund.framework.tpa.model;


import com.tbjfund.framework.tpa.lang.annotation.Column;
import com.tbjfund.framework.tpa.lang.annotation.PrimaryKey;
import com.tbjfund.framework.tpa.lang.annotation.Table;

@Table(name = "t_order_0", aliasName = "TOrder")
public class TradeOrder {

    @PrimaryKey
	private Integer orderId;

    @Column(name = "user_id")
	private Integer userId;

    @Column(name = "product_id")
	private Integer productId;

    @Column(name = "product_name")
	private String productName;

    @Column(name = "status")
	private Integer status;

    @Override
    public String toString() {
        return "orderId:" + orderId + " userId:" + userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
