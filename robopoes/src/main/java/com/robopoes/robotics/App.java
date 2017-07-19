package com.robopoes.robotics;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.robopoes.robotics.inject.ReleaseModule;
import com.robopoes.robotics.webserver.SparkWebServer;


public class App 
{
	@Inject private SparkWebServer webServer;
	
	public static void main(String[] args)
	{
		App app = new App();
		app.startAndWait();
		System.exit(0);
	}

	private void startAndWait() {
		Injector injector = Guice.createInjector(new ReleaseModule());
		injector.injectMembers(this);
		
		webServer.startServer();
		
		try(Scanner s = new Scanner(System.in)){
			s.nextLine();
		}
		
		webServer.stopServer();
	}
}
