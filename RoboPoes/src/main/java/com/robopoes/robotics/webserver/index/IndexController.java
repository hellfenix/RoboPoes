package com.robopoes.robotics.webserver.index;

import java.util.HashMap;
import java.util.Map;

import com.robopoes.robotics.webserver.Routes;
import com.robopoes.robotics.webserver.login.LoginController;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class IndexController {

	public static TemplateViewRoute serveIndexPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		return new ModelAndView(model, Routes.Templates.INDEX);
	};

}
