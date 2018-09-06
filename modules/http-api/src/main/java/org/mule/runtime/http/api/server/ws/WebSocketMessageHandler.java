package org.mule.runtime.http.api.server.ws;

import org.mule.runtime.http.api.ws.WebSocketMessage;

import com.google.common.annotations.Beta;

@Beta
public interface WebSocketMessageHandler {

  void onMessage(WebSocketMessage message);
}
