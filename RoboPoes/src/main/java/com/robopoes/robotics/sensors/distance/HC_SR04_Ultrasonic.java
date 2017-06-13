package com.robopoes.robotics.sensors.distance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.robopoes.robotics.sensors.SensorValueListener;

public class HC_SR04_Ultrasonic implements GpioPinListenerDigital {
	
	private static final double SOUND_SPEED_M_S = 340.29f;
    private static final int TIMEOUT_MILLIS = 2100;
	
	private GpioController gpio;
	private GpioPinDigitalOutput trigger;
	private GpioPinDigitalInput echo;
	
	private long startMeasureNanos;
	private long finishMeasureNanos;
	
	private Semaphore canStartNewMeasurement;
	
	private List<SensorValueListener<Double>> listeners;
	
	public HC_SR04_Ultrasonic(Pin triggerPin, Pin echoPin){
        gpio = GpioFactory.getInstance();

        trigger = gpio.provisionDigitalOutputPin(triggerPin, "Trigger", PinState.LOW);        
        
        echo = gpio.provisionDigitalInputPin(echoPin, "Echo");
        echo.addListener(this);
        
        trigger.setShutdownOptions(true, PinState.LOW);
        echo.setShutdownOptions(true, PinState.LOW);
        
        listeners = new ArrayList<>();
        
        canStartNewMeasurement = new Semaphore(1);
	}

	public void startReadingAsync(){
        readingTh.start();
	}
	
	public void stop(){
		readingTh.interrupt();
		
		startMeasureNanos = finishMeasureNanos = 0;
		
		if(canStartNewMeasurement.availablePermits() == 0){
			canStartNewMeasurement.release();
		}
		
		synchronized(listeners){
			listeners.clear();
		}
	}
	
	public void close(){
		stop();
		gpio.shutdown();
	}
	
	public void addListener(SensorValueListener<Double> listener){
		synchronized(listeners){
			listeners.add(listener);
		}
	}
	
	public void removeListener(SensorValueListener<Double> listener){
		synchronized(listeners){
			listeners.remove(listener);
		}
	}
	
	private void startMeasurement(){
		trigger.pulse(1);
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if(event.getState() == PinState.HIGH){
			startMeasureNanos = System.nanoTime();
			return;
		}
		
		finishMeasureNanos = System.nanoTime();
		canStartNewMeasurement.release();
		
		if(startMeasureNanos > 0 && finishMeasureNanos > 0){
			synchronized(listeners){
				listeners.forEach(x -> x.handleSensorValue(((finishMeasureNanos - startMeasureNanos) / 1000) * SOUND_SPEED_M_S / ( 2 * 10000 )));
			}
		}
	}
	
	private Thread readingTh = new Thread(() -> {
		while(!Thread.currentThread().isInterrupted() && Thread.currentThread().isAlive()){
			try {
				if(canStartNewMeasurement.tryAcquire(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)){
					startMeasurement();		
				} else {
					startMeasureNanos = 0;
					finishMeasureNanos = 0;
					canStartNewMeasurement.release();
					synchronized(listeners){
						listeners.forEach(x -> x.handleSensorValue(-1.0));
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	});
	
}
