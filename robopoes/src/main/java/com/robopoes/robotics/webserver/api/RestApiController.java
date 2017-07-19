package com.robopoes.robotics.webserver.api;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.robopoes.robotics.car.RoboPoes;
import com.robopoes.robotics.webserver.api.dto.DTO;
import com.robopoes.robotics.webserver.api.dto.DTO.ChangeDrivingMode;

import spark.Request;
import spark.Response;
import spark.Route;

public class RestApiController {
	private final Gson gson = new Gson();
	private RoboPoes carControl;
	
	@Inject
	public RestApiController(RoboPoes carControl){
		this.carControl = carControl;
	}
	
	public Route changeDrivingMode = (Request req, Response res) -> {
		ChangeDrivingMode reqData = gson.fromJson(req.body(), DTO.ChangeDrivingMode.class);
		carControl.setActiveDrivingMode(reqData.getDrivingMode());
		res.status(200);
		return null;
	};
}
