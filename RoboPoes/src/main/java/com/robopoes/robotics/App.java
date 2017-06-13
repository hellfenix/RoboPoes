package com.robopoes.robotics;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.robopoes.robotics.inject.ReleaseModule;
import com.robopoes.robotics.webserver.SparkWebServer;


public class App 
{
	//private static MotorHat motorHat;
	//private static final int MAX_SPEED = 255;
	//private static final int MIN_SPEED = 0;

	@Inject private SparkWebServer webServer;
	
	public static void main(String[] args)
	{
		App app = new App();
		app.startAndWait();
		System.exit(0);
		/*
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				System.out.println("Shutting down");
				motorHat.setMotorDirection(0, MotorDirection.RELEASE);
				motorHat.setMotorDirection(1, MotorDirection.RELEASE);
				motorHat.setMotorDirection(2, MotorDirection.RELEASE);
				motorHat.setMotorDirection(3, MotorDirection.RELEASE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));

		motorHat = new MotorHat();

		motorHat.setMotorDirection(0, MotorDirection.FORWARD);
		motorHat.setMotorDirection(1, MotorDirection.FORWARD);
		motorHat.setMotorDirection(2, MotorDirection.FORWARD);
		motorHat.setMotorDirection(3, MotorDirection.FORWARD);

		motorHat.setMotorSpeed(0, 235);
		motorHat.setMotorSpeed(1, 255);
		motorHat.setMotorSpeed(2, 235);
		motorHat.setMotorSpeed(3, 255);

		Uninterruptibles.sleepUninterruptibly(4, TimeUnit.SECONDS);

		for (int i = 254; i >= 0; i--) {
			motorHat.setMotorSpeed(1, i);
			motorHat.setMotorSpeed(3, i);
			if(i < 245){
				motorHat.setMotorSpeed(0, i - 10);
				motorHat.setMotorSpeed(2, i - 10);
			}
		}

		Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

		System.out.println("Reverse");
		motorHat.setMotorDirection(0, MotorDirection.BACKWARD);
		motorHat.setMotorDirection(1, MotorDirection.BACKWARD);
		motorHat.setMotorDirection(2, MotorDirection.BACKWARD);
		motorHat.setMotorDirection(3, MotorDirection.BACKWARD);

		motorHat.setMotorSpeed(0, 255);
		motorHat.setMotorSpeed(1, 255);
		motorHat.setMotorSpeed(2, 255);
		motorHat.setMotorSpeed(3, 255);

		Uninterruptibles.sleepUninterruptibly(4, TimeUnit.SECONDS);

		System.out.println("Slowing down reverse");
		for (int i = 254; i >= 0; i--) {
			motorHat.setMotorSpeed(0, i);
			motorHat.setMotorSpeed(1, i);
			motorHat.setMotorSpeed(2, i);
			motorHat.setMotorSpeed(3, i);	
		}


		Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

		System.out.println("Stopping");
		motorHat.setMotorDirection(0, MotorDirection.RELEASE);
		motorHat.setMotorDirection(1, MotorDirection.RELEASE);
		motorHat.setMotorDirection(2, MotorDirection.RELEASE);
		motorHat.setMotorDirection(3, MotorDirection.RELEASE);

		Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);

		System.exit(0);
		*/
	}

	private void startAndWait() {
		Injector injector = Guice.createInjector(new ReleaseModule());
		injector.injectMembers(this);
		
		//WebControlServer webControl = new WebControlServer(1234);
		webServer.startServer();
		
		try(Scanner s = new Scanner(System.in)){
			s.nextLine();
		}
		
		webServer.stopServer();
	}
}
