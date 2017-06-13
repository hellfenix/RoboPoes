package com.robopoes.robotics.webserver;

import static spark.Spark.awaitInitialization;
import static spark.Spark.get;
import static spark.Spark.init;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.stop;
import static spark.Spark.webSocket;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.robopoes.robotics.webserver.api.RestApiController;
import com.robopoes.robotics.webserver.control.ControlManualHandler;
import com.robopoes.robotics.webserver.data.LiveDataHandler;
import com.robopoes.robotics.webserver.index.IndexController;
import com.robopoes.robotics.webserver.login.LoginController;

import spark.ResponseTransformer;
import spark.template.freemarker.FreeMarkerEngine;

public class SparkWebServer {

	private int portNumber;
	private ResponseTransformer respTransformer;
	
	@Inject public RestApiController restApiController;
	
	@Inject
	public SparkWebServer(@Named("serverPort") int port){
		portNumber = port;
		respTransformer = new GsonResponseTransformer();
	}
	
	public void startServer(){
		port(portNumber);
	    staticFiles.location("/public");
	    staticFiles.expireTime(600L);
	    
	    // WebSockets
	    webSocket(Routes.WebSockets.CONTROL, ControlManualHandler.class);
	    webSocket(Routes.WebSockets.LIVE_DATA, LiveDataHandler.class);
	    
	    // API REST
	    post(Routes.Api.DRIVING_MODE, restApiController.changeDrivingMode, respTransformer);
	    
	    // Web
	    get(Routes.Web.LOGIN, LoginController.serveLoginPage, new FreeMarkerEngine());
	    get(Routes.Web.INDEX, IndexController.serveIndexPage, new FreeMarkerEngine());
	    
	    post(Routes.Web.LOGIN, LoginController.handleLoginPost);
	    post(Routes.Web.LOGOUT, LoginController.handleLogoutPost, new FreeMarkerEngine());
	    
	    init();
	}
	
	public void awaitForInitialization(){
		awaitInitialization();
	}
	
	public void stopServer(){
		stop();
	}
}
