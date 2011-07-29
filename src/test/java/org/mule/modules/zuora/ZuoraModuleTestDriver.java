/**
 * Mule Zuora Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/**
 * This file was automatically generated by the Mule Development Kit
 */

package org.mule.modules.zuora;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.mule.modules.zuora.zobject.ZObjectType;

import com.zuora.api.DeleteResult;
import com.zuora.api.SaveResult;
import com.zuora.api.object.ZObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ZuoraModuleTestDriver
{
    private ZuoraModule module;

    @Before
    public void setup() throws Exception
    {
        module = new ZuoraModule();
        module.setPassword(System.getenv("zuoraPassword"));
        module.setUsername(System.getenv("zuoraUsername"));
        module.setEndpoint("https://apisandbox.zuora.com/apps/services/a/29.0");
        module.init();
        for (ZObject z : module.find("select id from Account"))
        {
            module.delete(ZObjectType.Account, Arrays.asList(z.getId()));
        }
    }
    
    /**
     * Test for creating dynamic zobjects
     */
    @Test
    public void createAndDelete() 
    {
        SaveResult result = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0);
        assertTrue(result.isSuccess());
        
        System.out.println(result.getId());

        DeleteResult deleteResult = module.delete(ZObjectType.Account, Arrays.asList(result.getId())).get(0);
        assertTrue(deleteResult.isSuccess());
    }

    /**
     * Test for creating static zobjects
     */
    @Test
    @SuppressWarnings("serial")
    public void createAndDeleteStatic() 
    {
        SaveResult saveResult = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0);
        assertTrue(saveResult.isSuccess());
        
        final String accountId = saveResult.getId();
        try
        {
            SaveResult result = module.create(ZObjectType.Contact,
                Collections.<Map<String, String>> singletonList(new HashMap<String, String>()
                {
                    {
                        put("Country", "US");
                        put("FirstName", "John");
                        put("LastName", "Doe");
                        put("AccountId", accountId);
                    }
                })).get(0);
            System.out.println(result);
            assertTrue(result.isSuccess());

            DeleteResult deleteResult = module.delete(ZObjectType.Contact, Arrays.asList(result.getId())).get(0);
            assertTrue(deleteResult.isSuccess());
        }
        finally
        {
            module.delete(ZObjectType.Account, Arrays.asList(accountId)).get(0);
        }
    }

    /**
     * Test for fetching zobjects when there is no object that matches the query 
     */
    @Test
    public void findNoResult() 
    {
        Iterator<ZObject> result = module.find("SELECT Id FROM Account").iterator();
        assertFalse(result.hasNext());
    }

    /**
     * Test for fetching zobjects when there is an object that matches the query
     */
    @Test
    public void findOneResult() 
    {
        String id = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0).getId();
        try
        {
            Iterator<ZObject> result = module.find("SELECT Id FROM Account").iterator();
            assertTrue(result.hasNext());
            assertNotNull(result.next().getId());
            assertFalse(result.hasNext());
        }
        finally
        {
            module.delete(ZObjectType.Account, Arrays.asList(id));
        }
    }

    @Test
    public void getUserInfo() 
    {
        assertNotNull(module.getUserInfo());
    }

    @SuppressWarnings("serial")
    private Map<String, String> testAccount()
    {
        return new HashMap<String, String>()
        {
            {
                put("Name", "foo");
                put("Currency", "USD");
                put("BillCycleDay", "1");
                put("AccountNumber", "7891");
                put("AllowInvoiceEdit", "false");
                put("AutoPay", "false");
                put("Notes", "foobar");
                put("Status", "Draft");
            }
        };
    }

}
