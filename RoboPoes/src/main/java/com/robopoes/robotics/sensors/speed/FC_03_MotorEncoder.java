package com.robopoes.robotics.sensors.speed;

import java.util.ArrayList;
import java.util.List;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.robopoes.robotics.sensors.SensorValueListener;

public class FC_03_MotorEncoder implements GpioPinListenerDigital {

	private GpioController gpio;
	private GpioPinDigitalInput DO;
	
	private int divisions;
	private Integer currentRPM;
	
	private List<SensorValueListener<Integer>> listeners;
	
	public FC_03_MotorEncoder(Pin doPin, int numOfDivisions){
		gpio = GpioFactory.getInstance();
		
		DO = gpio.provisionDigitalInputPin(doPin, "DigitalInput");
		DO.addListener(this);
		
		currentRPM = 0;
		divisions = numOfDivisions;
		
		listeners = new ArrayList<>();
	}
	
	public void startReadingAsync(){
		readingTh.start();
	}
	
	public void stop(){
		readingTh.interrupt();
		
		synchronized(listeners){
			listeners.clear();
		}
	}
	
	public void close(){
		close();
		gpio.shutdown();
	}
	
	public void addListener(SensorValueListener<Integer> listener){
		synchronized(listeners){
			listeners.add(listener);
		}
	}
	
	public void removeListener(SensorValueListener<Integer> listener){
		synchronized(listeners){
			listeners.remove(listener);
		}
	}
	
	private Thread readingTh = new Thread(() -> {
		while(!Thread.currentThread().isInterrupted() && Thread.currentThread().isAlive()){
			
		}
	});

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		// TODO change this
		currentRPM = divisions;
		synchronized(listeners){
			listeners.forEach(x -> x.handleSensorValue(currentRPM));
		}
	}
	
}
