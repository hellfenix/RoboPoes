package com.robopoes.robotics.webserver.control;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.inject.Inject;
import com.robopoes.robotics.car.RoboPoes;
import com.robopoes.robotics.enums.DrivingCommand;

@WebSocket
public class ControlManualHandler {
	private static final Logger log = LogManager.getLogger();
	
	private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

	private RoboPoes carControl;
	
	@Inject
	public ControlManualHandler(RoboPoes carControl) {
		this.carControl = carControl;
	}
	
	@OnWebSocketConnect
	public void connected(Session session) {
		sessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		sessions.remove(session);
	}

	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		log.debug("Got: {}", message);
		carControl.addManualCommand(DrivingCommand.valueOf(message));
	}	
}
