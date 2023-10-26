package com.example.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_deliveries")
public class OrderDelivery extends TimeEntity implements Serializable {

	@Column(name = "order_id")
	private Integer orderId;

	@Id
	@Column(name = "shipping_code")
	private String shippingCode;

	@Column(name = "shipping_date")
	private Date shippingDate;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "delivery_timezone")
	private String deliveryTimezone;

	public OrderDelivery() {
		// デフォルトコンストラクタの実装
	}

	public OrderDelivery(Integer id, String shippingCode, Date shippingDate, Date deliveryDate, String deliveryTimezone,
			String status) {
		this.orderId = id;
		this.shippingCode = shippingCode;
		this.shippingDate = shippingDate;
		this.deliveryDate = deliveryDate;
		this.deliveryTimezone = deliveryTimezone;
	}
}
