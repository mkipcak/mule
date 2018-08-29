package org.mule.runtime.http.api.server.ws;

import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.domain.message.MessageWithHeaders;
import org.mule.runtime.http.api.domain.request.ClientConnection;
import org.mule.runtime.http.api.domain.request.ServerConnection;

public interface WebSocketRequest extends MessageWithHeaders {

  String getPath();

  String getContentType();

  MultiMap<String, String> getQueryParams();

  String getScheme();

  /**
   * @return the server connection descriptor
   */
  ServerConnection getServerConnection();

  /**
   * @return the client connection descriptor
   */
  ClientConnection getClientConnection();
}
