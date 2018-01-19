package com.jpm.processor;

import com.jpm.helper.Sales;

import java.math.BigDecimal;

/**
 * Created by rahulranjan on 17/01/2018.
 * 
 * This class has been created just to accept different format of incoming message from the external application.
 * Incoming message for the sale could be one of the following types:
 *  Message Type 1 – contains the details of 1 sale E.g apple at 10p
 *  Message Type 2 – contains the details of a sale and the number of occurrences of that sale. e.g 20 sales of apples at 10p each.
 *  Message Type 3 – contains the details of a sale and an adjustment operation to be applied to all stored sales of this product type. Operations can be add, subtract, or multiply e.g Add 20p apples would instruct your application to add 20p to each sale of apples you have recorded.
 *
 */
public class SalesBuilder {

	private Sales sales = new Sales();

	public SalesBuilder createSales(String productType, BigDecimal value) {
		sales.setProductType(productType);
		sales.setValue(value);
		sales.setCount(1);
		return this;
	}

	public SalesBuilder withAction(Sales.Action action) {
		sales.setAction(action);
		return this;
	}

	public SalesBuilder withCount(long count) {
		sales.setCount(count);
		return this;
	}

	public Sales build() {
		return sales;
	}

}
