package com.robopoes.robotics.car.drivers;

import com.robopoes.robotics.enums.DrivingCommand;

public interface DrivingController {
	public void start();
	public void stop();
	
	public default void addCommandToQueue(DrivingCommand cmd) { }
}
