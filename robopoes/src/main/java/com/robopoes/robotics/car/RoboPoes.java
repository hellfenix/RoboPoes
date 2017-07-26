package com.robopoes.robotics.car;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.robopoes.robotics.car.drivers.ChargeDrivingController;
import com.robopoes.robotics.car.drivers.DrivingController;
import com.robopoes.robotics.car.drivers.FollowDrivingController;
import com.robopoes.robotics.car.drivers.ManualDrivingController;
import com.robopoes.robotics.car.drivers.PatrolDrivingController;
import com.robopoes.robotics.enums.DrivingCommand;
import com.robopoes.robotics.enums.DrivingMode;

public class RoboPoes {
	private static final Logger log = LogManager.getLogger();
	
	private DrivingMode activeDrivingMode;
	private Map<DrivingMode, DrivingController> drivingControllers;

	@Inject
	public RoboPoes(){
		drivingControllers = new HashMap<>();
		drivingControllers.put(DrivingMode.MANUAL, new ManualDrivingController());
		drivingControllers.put(DrivingMode.PATROL, new PatrolDrivingController());
		drivingControllers.put(DrivingMode.FOLLOW, new FollowDrivingController());
		drivingControllers.put(DrivingMode.CHARGE, new ChargeDrivingController());
		
		activeDrivingMode = DrivingMode.MANUAL;
		drivingControllers.get(activeDrivingMode).start();
	}
	
	public synchronized void start() {
		// until refactor start in constructor
	}
	
	public synchronized void stop() {
		drivingControllers.get(activeDrivingMode).stop();
	}
	
	public DrivingMode getActiveDrivingMode() {
		return activeDrivingMode;
	}

	public void setActiveDrivingMode(DrivingMode activeDrivingMode) {
		log.debug("Setting driving mode to: {}", activeDrivingMode);
		this.activeDrivingMode = activeDrivingMode;
	}
	
	public void addManualCommand(DrivingCommand cmd) {
		// Default implementation does nothing so no need to check if manual is active
		drivingControllers.get(activeDrivingMode).addCommandToQueue(cmd);
	}
}
