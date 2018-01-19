package com.jpm.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.jpm.datasource.SalesInventory.getSalesInventory;
import static com.jpm.datasource.SalesInventory.getSalesHistory;
import static com.jpm.helper.Sales.Action.ADD;
import static com.jpm.helper.Sales.Action.SUBTRACT;
import static com.jpm.helper.Sales.Action.MULTIPLY;

/**
 * Created by rahulranjan on 17/01/2018.
 * 
 * This file has been created to generate different types of report required for
 * the business
 * 
 */
public class MessageReport {

	/**
	 * This method will generate sales report if notification processor has
	 * processed 10 messages.
	 * 
	 **/
	public static void generateSalesReport() {
		System.out.println("**********************************************************");
		System.out.println("10 messages have been processed so generating sales report");
		System.out.println("**********************************************************");
		System.out.println("Product" + "\t\t" + "Number of Sales" + "\t" + "Total Value");

		for (Map.Entry<Map<Object, BigDecimal>, Long> currentMap : getSalesInventory().entrySet()) {
			Object currentKey = currentMap.getKey().keySet().iterator().next();
			BigDecimal currentValue = currentMap.getKey().values().iterator().next();
			long count = currentMap.getValue();
			BigDecimal totalValue = currentValue.multiply(new BigDecimal(count));
			System.out.println(currentKey + "\t\t" + count + "\t\t" + totalValue);
		}
		System.out.println("**********************************************************");
	}

	/**
	 * This method will generate adjustment report if notification processor has
	 * processed 50 messages.
	 * 
	 **/
	public static void generateAdjustmentReport() {
		System.out.println("50 messages have been processed so generating adjustment report");

		List<String> adjustmentList = new ArrayList<>();
		Iterator<String> iterator = getSalesHistory().iterator();

		while (iterator.hasNext()) {
			String currentValue = iterator.next();
			if (currentValue.contains(ADD.toString()) || currentValue.contains(SUBTRACT.toString())
					|| currentValue.contains(MULTIPLY.toString())) {
				adjustmentList.add(currentValue);
			}
		}
		System.out.println("**********************************************************");
		System.out.println("Adjustment list is");
		System.out.println(adjustmentList);
		System.out.println("**********************************************************");

	}

}
