package com.robopoes.robotics.car.drivers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.util.concurrent.Uninterruptibles;
import com.robopoes.robotics.enums.DrivingCommand;
import com.robopoes.tools.motorhat4j.MotorHat;

public class ManualDrivingController implements DrivingController {

	private static final Logger log = LogManager.getLogger();

	private MotorHat motorsController; // TODO TO BE INJECTED ? -> REMOVE CLOSE ON STOP

	private BlockingQueue<DrivingCommand> queue;
	private Thread drivingTh;
	private boolean isRunning;

	public ManualDrivingController() {
		/*try {
			motorsController = new MotorHat();
		} catch (UnsupportedBusNumberException | IOException e) {
			log.debug("Error loading motor hat");
		}*/
		queue = new ArrayBlockingQueue<>(20);
		isRunning = false;
	}

	@Override
	public void start() {
		if(!isRunning) {
			log.debug("Starting manual driving controller");			
			drivingTh = new Thread(drivingRunnable);
			isRunning = true;
			drivingTh.start();
		}
	}

	@Override
	public void stop() {
		if(isRunning) {
			log.debug("Stopping manual driving controller");
			isRunning = false;
			drivingTh.interrupt();
			Uninterruptibles.joinUninterruptibly(drivingTh, 2, TimeUnit.SECONDS);
			drivingTh = null;
			/*try {
				motorsController.close();
			} catch (Exception e) {}*/
		}
	}

	private Runnable drivingRunnable = () -> {
		try {
			DrivingCommand cmd;
			boolean noCmd = false;
			boolean stopped = true;
			while(isRunning) {
				cmd = queue.poll(250, TimeUnit.MILLISECONDS);
				if(cmd == null) {
					if(noCmd && !stopped) { // STOP THE CAR
						log.debug("No commands received in 2 time slots, stopping the car");
						try {
							motorsController.stopAll();
						} catch (Exception e) {
							log.debug("Error trying to stop motors");
						}
						stopped = true;
					} else {
						noCmd = true;
					}
				} else {
					noCmd = false;
					stopped = false;
					log.debug("Command received -> {}", cmd);
					switch(cmd) {
						case FORWARD:
							break;
						case BACKWARDS:
							break;
						case LEFT:
							break;
						case RIGHT:
							break;
					}
				}
			}
		} catch (InterruptedException e) {
			log.debug("Interrupted while waiting for a new command");
		}
	};

	public void addCommandToQueue(DrivingCommand cmd) {
		if(!queue.offer(cmd)) {
			log.debug("Queue was full, ignored command");
		}
	}

}
