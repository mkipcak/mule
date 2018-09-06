/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.http.api.server.ws;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.domain.message.MessageWithHeaders;
import org.mule.runtime.http.api.domain.request.ClientConnection;
import org.mule.runtime.http.api.domain.request.ServerConnection;

import com.google.common.annotations.Beta;

@Beta
@NoImplement
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
