/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.http.api.server.ws;

public final class WebSocketResource {

  private final String path;
  private final WebSocketConnectionHandler connectionHandler;
  private final WebSocketMessageHandler messageHandler;

  public WebSocketResource(String path, WebSocketConnectionHandler connectionHandler, WebSocketMessageHandler messageHandler) {
    this.path = path;
    this.connectionHandler = connectionHandler;
    this.messageHandler = messageHandler;
  }

  public String getPath() {
    return path;
  }

  public WebSocketConnectionHandler getConnectionHandler() {
    return connectionHandler;
  }

  public WebSocketMessageHandler getMessageHandler() {
    return messageHandler;
  }
}
