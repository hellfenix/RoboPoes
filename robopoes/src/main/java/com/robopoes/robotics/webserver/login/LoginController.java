package com.robopoes.robotics.webserver.login;

import java.util.HashMap;
import java.util.Map;

import com.robopoes.robotics.webserver.Routes;
import com.robopoes.robotics.webserver.user.UserController;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateViewRoute;

public class LoginController {
	public static Route handleRootRequest = (Request request, Response response) -> {
		response.redirect(Routes.Web.LOGIN);
		
		return null;
	};
	
	public static TemplateViewRoute serveLoginPage = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		//model.put("loggedOut", removeSessionAttrLoggedOut(request));
		//model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
		return new ModelAndView(model, Routes.Templates.LOGIN);
	};


	public static Route handleLoginPost = (Request request, Response response) -> {
		synchronized(LoginController.class){
			Thread.sleep(1000); // Avoid brute force attacks
		}
		
		if (!UserController.authenticate(request.queryParams("username"),request.queryParams("password"))) {
			response.redirect(Routes.Web.LOGIN + "?authenticationFailed=true");
		} else {
			request.session().attribute("currentUser", request.queryParams("username"));

			if (request.queryParams("loginRedirect") != null) {
				response.redirect(request.queryParams("loginRedirect"));
			}

			response.redirect(Routes.Web.INDEX);
		}

		return null;
	};


	public static TemplateViewRoute handleLogoutPost = (Request request, Response response) -> {
		request.session().removeAttribute("currentUser");
		request.session().attribute("loggedOut", true);
		response.redirect(Routes.Web.LOGIN);
		return null;
	};


	// The origin of the request (request.pathInfo()) is saved in the session so
	// the user can be redirected back after login
	public static void ensureUserIsLoggedIn(Request request, Response response) {
		if (request.session().attribute("currentUser") == null) {
			request.session().attribute("loginRedirect", request.pathInfo());
			response.redirect(Routes.Web.LOGIN);
		}
	};
}
