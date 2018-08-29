package org.mule.runtime.http.api.server.ws;

import org.mule.runtime.http.api.ws.WebSocketMessage;

public interface WebSocketMessageHandler {

  void onMessage(WebSocketMessage message);
}
