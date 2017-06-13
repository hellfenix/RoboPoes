package com.robopoes.robotics.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.robopoes.robotics.enums.DrivingMode;

public class RoboPoes {
	private static final Logger log = LogManager.getLogger();
	
	private DrivingMode activeDrivingMode;

	@Inject
	public RoboPoes(){
		
	}
	
	public DrivingMode getActiveDrivingMode() {
		return activeDrivingMode;
	}

	public void setActiveDrivingMode(DrivingMode activeDrivingMode) {
		log.debug("Setting driving mode to: {}", activeDrivingMode);
		this.activeDrivingMode = activeDrivingMode;
	}
}
