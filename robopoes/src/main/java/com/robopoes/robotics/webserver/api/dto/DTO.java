package com.robopoes.robotics.webserver.api.dto;

import com.robopoes.robotics.enums.DrivingMode;

public class DTO {
	public static class ChangeDrivingMode {
		private DrivingMode drivingMode;

		public DrivingMode getDrivingMode() {
			return drivingMode;
		}

		public void setDrivingMode(DrivingMode drivingMode) {
			this.drivingMode = drivingMode;
		}
	}
}
