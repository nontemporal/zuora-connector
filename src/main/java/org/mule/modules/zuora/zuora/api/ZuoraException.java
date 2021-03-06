/**
 * Mule Zuora Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.zuora.zuora.api;

public class ZuoraException extends RuntimeException
{
    private static final long serialVersionUID = -5507667830649176803L;

    public ZuoraException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ZuoraException(Throwable cause)
    {
        super(cause);
    }

}
