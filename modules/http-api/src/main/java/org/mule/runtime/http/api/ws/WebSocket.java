/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.http.api.ws;

import org.mule.runtime.api.metadata.MediaType;

import java.io.IOException;
import java.io.InputStream;

public interface WebSocket {

  enum WebSocketType {

    INBOUND,
    OUTBOUND
  }

  String getId();

  WebSocketType getType();

  String getPath();

  void send(InputStream content, MediaType mediaType) throws IOException;

  void close();
}
