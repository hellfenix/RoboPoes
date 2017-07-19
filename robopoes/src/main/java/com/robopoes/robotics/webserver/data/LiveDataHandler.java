package com.robopoes.robotics.webserver.data;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.Gson;

@WebSocket
public class LiveDataHandler {
	private static final Logger log = LogManager.getLogger();
	private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
	private static final Gson gson = new Gson();

	@OnWebSocketConnect
	public void connected(Session session) {
		log.debug("Live Data WS new connection");
		sessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		log.debug("Live Data WS connection closed");
		sessions.remove(session);
	}
	
	public void sendLiveData(Object data) {
		log.debug("Senging live data to all sessions");
		sessions.forEach(x -> {
			try {
				x.getRemote().sendString(gson.toJson(data));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
