package com.jpm.helper;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by rahulranjan on 17/01/2018.
 * 
 * This POJO has been created to temporarily store the sales details at the time
 * of processing the message.
 * 
 */
public class Sales {

	private String productType;
	private BigDecimal value;
	private long count;
	private Action action;

	public enum Action {
		ADD, SUBTRACT, MULTIPLY
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value.setScale(2, ROUND_HALF_UP);
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String toString() {
		return new StringBuilder().append("productType:").append(this.getProductType()).append(", value:")
				.append(this.getValue()).append(", count:").append(this.count).append(", action:").append(this.action)
				.toString();
	}

}
