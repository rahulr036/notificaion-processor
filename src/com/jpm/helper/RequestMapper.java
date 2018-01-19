package com.jpm.helper;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jpm.helper.Sales.Action;
import com.jpm.processor.SalesBuilder;

/**
 * Created by rahulranjan on 17/01/2018.
 * 
 * This file has been created to read input from the external source. In the
 * real time application will be receiving inputs from the external application
 * 
 */
public class RequestMapper {

	public static List<Sales> getInputFromSource() {
		JSONParser parser = new JSONParser();
		List<Sales> salesList = new ArrayList<>();

		try {

			Object obj = parser.parse(new FileReader("resources/messages/messages.json"));
			JSONArray jsonArray = (JSONArray) obj;

			for (Object jsonObject : jsonArray) {
				long count = 1;
				Action action = null;
				JSONObject sales = (JSONObject) jsonObject;

				String productType = (String) sales.get("product_type");
				BigDecimal value = new BigDecimal((String) sales.get("value"));
				if (null != sales.get("count")) {
					count = Long.valueOf((String) sales.get("count"));
				}
				if (null != sales.get("action")) {
					action = Action.valueOf((String) sales.get("action"));
				}

				salesList.add((new SalesBuilder().createSales(productType, value).withCount(count).withAction(action)
						.build()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sales list from input source is : " + salesList);
		return salesList;
	}

}
