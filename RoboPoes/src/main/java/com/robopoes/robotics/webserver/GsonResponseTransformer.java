package com.robopoes.robotics.webserver;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class GsonResponseTransformer implements ResponseTransformer {

	private Gson gson = new Gson();
	
	@Override
	public String render(Object model) throws Exception {
		return gson.toJson(model);
	}

}
