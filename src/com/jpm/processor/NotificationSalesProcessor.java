package com.jpm.processor;

import com.jpm.helper.Sales;
import com.jpm.processor.MessageProcessor;
import java.util.List;

import static com.jpm.helper.RequestMapper.getInputFromSource;
import static com.jpm.report.MessageReport.generateSalesReport;
import static com.jpm.report.MessageReport.generateAdjustmentReport;

/**
 * Created by rahulranjan on 17/01/2018.
 * 
 * This is the main class from where notification processor will start.
 */
public class NotificationSalesProcessor {

	/**
	 * Main method to run the application
	 * 
	 */
	public static void main(String[] args) {

		MessageProcessor processor = new MessageProcessor();

		System.out.println("Reading messages to be processed.");
		List<Sales> salesList = getInputFromSource();
		int countProcessedMessage = 0;

		for (Sales sales : salesList) {
			if (null != sales.getAction()) {
				processor.adjustRate(sales);
				System.out.println("Product rate for " + sales + " has been adjusted successfully");
			} else {
				processor.recordSaleDetails(sales);
				System.out.println("Product " + sales + " has been recorded successfully");
			}

			countProcessedMessage++;

			if (countProcessedMessage % 10 == 0) {
				generateSalesReport();
			}
			if (countProcessedMessage == 50) {
				System.out.println("Message processing paused to generate the adjustment report...");
				generateAdjustmentReport();
				System.out.println("Adjustment Report generated successfully");
			}
		}

	}

}
