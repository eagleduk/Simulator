package com.iss.simulator.services;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.JTable;

import com.iss.simulator.models.RedisData;
import com.iss.simulator.models.ValueTag;
import com.iss.simulator.models.ValueTagModel;
import com.iss.simulator.models.WayPoint;
import com.iss.simulator.models.WayPointModel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SimulatorService extends TimerTask {

	final String[] PARAMETERS = {"Value", "Timestamp", "Quality"};
	
	String ip;
	int port, timeout, runtime, currentWayPoint, runCount;
	
	double latitudePerSecond, longitudePerSecond;
	
	WayPointModel wpm;
	ValueTagModel vtm;
	
	public SimulatorService(String ip, String port, String timeout, String runtime) {
		this.ip = ip;
		this.port = Integer.parseInt(port);
		this.timeout = Integer.parseInt(timeout);
		this.runtime = Integer.parseInt(runtime);
		
		this.currentWayPoint = 0;
		this.runCount = 0;
	}
	
	public void setTableDatas(Map<String, JTable> map) {
		wpm = (WayPointModel)(map.get("WayPoints")).getModel();
		vtm = (ValueTagModel)(map.get("ValueTag")).getModel();
		
		if(wpm.getRowCount() > 0) setWayPointPerSecond();
	}
	
	public void setWayPointPerSecond() {
		WayPoint nowPoint = wpm.getData(currentWayPoint);
		WayPoint nextPoint = wpm.getData(currentWayPoint+1);
		
		latitudePerSecond = (nextPoint.getLatitude() - nowPoint.getLatitude()) / nowPoint.getLeadTime();
		longitudePerSecond = (nextPoint.getLongitude() - nowPoint.getLongitude()) / nowPoint.getLeadTime();
	}
	
	@Override
	public void run() {
		JedisPool jedisPool = null;
		Jedis jedis = null;
		
		try {
			jedisPool = new JedisPool(new JedisPoolConfig(), ip, port, timeout);
			jedis = jedisPool.getResource();
			
			if(jedis.isConnected()) {
				Date time = new Date();
				time.getTime();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(jedis != null) jedis.close();
			jedisPool.destroy();
		}
	}
	
	public void RunWayPoint(Jedis jedis, Date time) {
		WayPoint wp = wpm.getData(currentWayPoint);
		
		double Latitude = wp.getLatitude();
		double Longitude = wp.getLongitude();
		double heading = wp.getHeading();
		double fwdDraft = wp.getForwardDraft();
		double aftDraft = wp.getAfterDraft();
		
		Latitude = Latitude + (latitudePerSecond * runtime * runCount);
		Longitude = Longitude + (longitudePerSecond * runtime * runCount);
		
		// 도, 분으로 변경
		double temp_lat = (Latitude - Math.floor(Latitude)) * 60;
		Latitude = (Math.floor(Latitude) * 100) + temp_lat;
		
		double temp_lon = (Longitude - Math.floor(Longitude)) * 60;
		Longitude = (Math.floor(Longitude) * 100) + temp_lon;
		
		String currentLat = Latitude > 0 ? Latitude + " N" : Latitude + " S";
		String currentLon = Longitude > 0 ? Longitude + " E" : Longitude + " W";
		
		String latitudeRedisKey = "PositionLat";
		jedis.mset(latitudeRedisKey + "." + PARAMETERS[0], String.valueOf(currentLat));
		jedis.mset(latitudeRedisKey + "." + PARAMETERS[1], String.valueOf(time.getTime()));
		jedis.mset(latitudeRedisKey + "." + PARAMETERS[2], "GOOD");
		
		String longitudeRedisKey = "PositionLon";
		jedis.mset(longitudeRedisKey + "." + PARAMETERS[0], String.valueOf(currentLon));
		jedis.mset(longitudeRedisKey + "." + PARAMETERS[1], String.valueOf(time.getTime()));
		jedis.mset(longitudeRedisKey + "." + PARAMETERS[2], "GOOD");
		
		String headingRedisKey = "CourseHdgTrue";
		jedis.mset(headingRedisKey + "." + PARAMETERS[0], String.valueOf(heading));
		jedis.mset(headingRedisKey + "." + PARAMETERS[1], String.valueOf(time.getTime()));
		jedis.mset(headingRedisKey + "." + PARAMETERS[2], "GOOD");

		String fwdDraftRedisKey = "BW001";
		jedis.mset(fwdDraftRedisKey + "." + PARAMETERS[0], String.valueOf(fwdDraft));
		jedis.mset(fwdDraftRedisKey + "." + PARAMETERS[1], String.valueOf(time.getTime()));
		jedis.mset(fwdDraftRedisKey + "." + PARAMETERS[2], "GOOD");
		
		String aftDraftRedisKey = "BW002";
		jedis.mset(aftDraftRedisKey + "." + PARAMETERS[0], String.valueOf(aftDraft));
		jedis.mset(aftDraftRedisKey + "." + PARAMETERS[1], String.valueOf(time.getTime()));
		jedis.mset(aftDraftRedisKey + "." + PARAMETERS[2], "GOOD");
		
	}
	
	public void RunValueTag(Jedis jedis, Date time) {
		for(int i=0; i<vtm.getRowCount(); i++) {
			ValueTag vt = vtm.getData(i);
			
			String RedisKey = vt.getRedisKey();
			double MaxValue = Double.parseDouble(vt.getMaxValue());
			double MinValue = Double.parseDouble(vt.getMinValue());
			String RedisType = vt.getRedisType();
			String Value_String = vt.getString_Value();
			boolean Value_Boolean = vt.getBoolean_Value();
			
			RedisData data = new RedisData(RedisKey, MaxValue, MinValue, Value_String, Value_Boolean, RedisType);

			jedis.mset(data.getRedisKey() + "." + PARAMETERS[0], String.valueOf(data.getValue(vtm.getRowCount())));
			jedis.mset(data.getRedisKey() + "." + PARAMETERS[1], String.valueOf(time.getTime()));
			jedis.mset(data.getRedisKey() + "." + PARAMETERS[2], data.getQuality());
			
		}
	}
}
