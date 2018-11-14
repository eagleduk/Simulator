package com.iss.simulator.models;

import java.math.BigDecimal;
import java.util.Random;

public class RedisData {

	String RedisKey;
	String MinValue;
	String MaxValue;
	String Value_String;
	String Value_Boolean;
	String RedisType;
	
	public RedisData(String RedisKey, String MaxValue, String MinValue, String Value_String, String Value_Boolean, String RedisType) {
		this.RedisKey = RedisKey;
		this.MaxValue = MaxValue;
		this.MinValue = MinValue;
		this.Value_String = Value_String;
		this.Value_Boolean = Value_Boolean;
		this.RedisType = RedisType;
	}

	public String getRedisKey() {
		return this.RedisKey;
	}
	
	public String getType() {
		return this.RedisType;
	}

	public String getQuality() {
		return "GOOD";
	}
	
	public Object getValue(int randomCount) {
		Object value = "";
		if("STRING".equals(this.RedisType)) {
			value = this.Value_String;
		} else if("BOOLEAN".equals(this.RedisType)) {
			value = this.Value_Boolean;
		} else {
			double max = Double.parseDouble(this.MaxValue);
			double min = Double.parseDouble(this.MinValue);
			
			int imax = (int)max;
			int imin = (int)min;
			
			Random random = new Random(System.currentTimeMillis() + randomCount);

			int ivalue = random.nextInt(imax < 1 ? 1 : imax);
			double dvalue = random.nextDouble();
			
			if(ivalue < imin) ivalue = imin;
			
			value = ivalue + setDoubleScale(dvalue, 4);
			
			if((double)value > max) value = max;
			if((double)value < min) value = min;
			
		}
		return value;
	}
	
	public static double setDoubleScale(Double inputData, int scale) {
		BigDecimal bd = new BigDecimal(inputData);
		BigDecimal result = null;
		  
		result = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
}
