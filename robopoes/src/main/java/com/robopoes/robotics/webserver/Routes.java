package com.robopoes.robotics.webserver;

public class Routes {
	public static class Web {
		public static final String ROOT = "/";
		public static final String INDEX = "/index";
		public static final String LOGIN = "/login";
		public static final String LOGOUT = "/logout";
	}
	
	public static class Api {
		public static final String DRIVING_MODE = "/api/driving/mode";
	}
	
	public static class WebSockets {
		public static final String CONTROL = "/control/manual";
		public static final String LIVE_DATA = "/data/live";
	}
	
	public static class Templates {
		public static final String LOGIN = "login.ftl";
		public static final String INDEX = "index.ftl";
	}
}
