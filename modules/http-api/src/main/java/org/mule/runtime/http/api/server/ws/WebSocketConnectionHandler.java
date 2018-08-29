package org.mule.runtime.http.api.server.ws;

import java.util.Optional;

public interface WebSocketConnectionHandler {

  Optional<String> onConnect(WebSocketRequest request) throws WebSocketConnectionRejectedException;
}
