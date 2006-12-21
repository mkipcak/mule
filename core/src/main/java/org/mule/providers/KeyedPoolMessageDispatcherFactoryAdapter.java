/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers;

import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOMessageDispatcherFactory;

import org.apache.commons.pool.KeyedPoolableObjectFactory;

// TODO HH: explain what this does
public class KeyedPoolMessageDispatcherFactoryAdapter
    implements UMOMessageDispatcherFactory, KeyedPoolableObjectFactory
{
    private final UMOMessageDispatcherFactory factory;

    public KeyedPoolMessageDispatcherFactoryAdapter(UMOMessageDispatcherFactory factory)
    {
        super();
        this.factory = factory;
    }

    public void activateObject(Object key, Object obj) throws Exception
    {
        factory.activate((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public void destroyObject(Object key, Object obj) throws Exception
    {
        factory.destroy((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public Object makeObject(Object key) throws Exception
    {
        return factory.create((UMOImmutableEndpoint)key);
    }

    public void passivateObject(Object key, Object obj) throws Exception
    {
        factory.passivate((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public boolean validateObject(Object key, Object obj)
    {
        return factory.validate((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException
    {
        return factory.create(endpoint);
    }

    public void activate(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher) throws UMOException
    {
        factory.activate(endpoint, dispatcher);
    }

    public void destroy(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher)
    {
        factory.destroy(endpoint, dispatcher);
    }

    public void passivate(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher)
    {
        factory.passivate(endpoint, dispatcher);
    }

    public boolean validate(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher)
    {
        return factory.validate(endpoint, dispatcher);
    }

}
