package com.robopoes.robotics.sensors;

public interface SensorValueListener<T> {

	public void handleSensorValue(T value);
	
}
