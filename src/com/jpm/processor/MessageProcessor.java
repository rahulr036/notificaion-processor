package com.jpm.processor;

import com.jpm.helper.Sales;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.jpm.datasource.SalesInventory.getSalesHistory;
import static com.jpm.datasource.SalesInventory.getSalesInventory;
import static com.jpm.helper.Sales.Action.ADD;
import static com.jpm.helper.Sales.Action.SUBTRACT;
import static com.jpm.helper.Sales.Action.MULTIPLY;

/**
 * Created by rahulranjan on 17/01/2018.
 * 
 * This file has been created to fulfil the business requirement of notification
 * processor
 * 
 */

public class MessageProcessor {

	/**
	 * Method to store any product coming to notification processor from
	 * external source
	 */

	public void recordSaleDetails(Sales sales) {
		Map<Object, BigDecimal> salesKey = buildSalesDetails(sales);

		if (getSalesInventory().containsKey(salesKey)) {
			getSalesInventory().put(salesKey, getSalesInventory().get(salesKey) + sales.getCount());
		} else {
			getSalesInventory().put(buildSalesDetails(sales), sales.getCount());
		}

		getSalesHistory().add(
				sales.getCount() + " " + sales.getProductType() + " at rate " + sales.getValue() + " have been added");
	}

	/**
	 * Method to adjust rate of requested product. As part of adjustment product
	 * value can be added, subtracted and multiplied
	 */
	public void adjustRate(Sales sales) {

		BigDecimal value = sales.getValue();
		Sales.Action action = sales.getAction();

		Map<Map<Object, BigDecimal>, Long> newEntry = new ConcurrentHashMap<>();
		for (Map.Entry<Map<Object, BigDecimal>, Long> currentMap : getSalesInventory().entrySet()) {
			Map<Object, BigDecimal> oldSalesKey = buildSalesDetails(sales);
			Map<Object, BigDecimal> newSalesKey = new HashMap<>();

			Object currentKey = currentMap.getKey().keySet().iterator().next();
			BigDecimal currentValue = currentMap.getKey().values().iterator().next();

			if (oldSalesKey.keySet().equals(currentMap.getKey().keySet())) {

				if (ADD.equals(action)) {
					newSalesKey = buildSalesKey(currentKey, currentValue.add(value));
				} else if (SUBTRACT.equals(action)) {
					newSalesKey = buildSalesKey(currentKey, currentValue.subtract(value));
				} else if (MULTIPLY.equals(action)) {
					newSalesKey = buildSalesKey(currentKey, currentValue.multiply(value));
				}
				newEntry.put(newSalesKey, currentMap.getValue());
				getSalesInventory().remove(buildSalesKey(currentKey, currentValue));
			}
		}
		getSalesInventory().putAll(newEntry);
		getSalesHistory().add(action + " " + value + " to " + sales.getProductType());

	}

	private Map<Object, BigDecimal> buildSalesDetails(Sales sales) {
		Map<Object, BigDecimal> salesDetails = new HashMap<>();
		salesDetails.put(sales.getProductType(), sales.getValue());
		return salesDetails;
	}

	private Map<Object, BigDecimal> buildSalesKey(Object productType, BigDecimal value) {
		Map<Object, BigDecimal> salesDetails = new HashMap<>();
		salesDetails.put(productType, value);

		return salesDetails;
	}

}
