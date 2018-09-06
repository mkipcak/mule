package org.mule.runtime.http.api.server.ws;

import com.google.common.annotations.Beta;

import java.util.Optional;

@Beta
public interface WebSocketConnectionHandler {

  Optional<String> onConnect(WebSocketRequest request) throws WebSocketConnectionRejectedException;
}
