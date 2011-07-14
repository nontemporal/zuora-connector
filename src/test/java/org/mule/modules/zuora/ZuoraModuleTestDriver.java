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

import org.mule.modules.zuora.zobject.ZObject;
import org.mule.modules.zuora.zobject.ZObjectType;

import com.zuora.api.object.Account;
import com.zuora.api.object.DeleteResult;
import com.zuora.api.object.SaveResult;

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
        // for(ZObject z : module.find("select id from Account"))
        // {
        // module.delete("Account", Arrays.asList((String) ((Account)
        // z).getField("Id")));
        // }
    }

    @Test
    public void createAndDelete() throws Exception
    {
        SaveResult result = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0);
        assertTrue(result.getSuccess());

        DeleteResult deleteResult = module.delete(ZObjectType.Account, Arrays.asList(result.getId())).get(0);
        assertTrue(deleteResult.getSuccess());
    }

    @Test
    @SuppressWarnings("serial")
    public void createAndDeleteStatic() throws Exception
    {
        final String accountId = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0).getId();
        try
        {
            SaveResult result = module.create(ZObjectType.Contact,
                Collections.<Map<String, Object>> singletonList(new HashMap<String, Object>()
                {
                    {
                        put("Country", "US");
                        put("FirstName", "John");
                        put("LastName", "Doe");
                        put("AccountId", accountId);
                    }
                })).get(0);
            System.out.println(result);
            assertTrue(result.getSuccess());

            DeleteResult deleteResult = module.delete(ZObjectType.Contact, Arrays.asList(result.getId())).get(0);
            assertTrue(deleteResult.getSuccess());
        }
        finally
        {
            module.delete(ZObjectType.Account, Arrays.asList(accountId)).get(0);
        }
    }

    @Test
    public void findNoResult() throws Exception
    {
        Iterator<ZObject> result = module.find("SELECT Id FROM Account").iterator();
        assertFalse(result.hasNext());
    }

    @Test
    public void findOneResult() throws Exception
    {
        String id = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0).getId();
        try
        {
            Iterator<ZObject> result = module.find("SELECT Id FROM Account").iterator();
            assertTrue(result.hasNext());
            assertNotNull(((Account) result.next()).getField("Id"));
            assertFalse(result.hasNext());
        }
        finally
        {
            module.delete(ZObjectType.Account, Arrays.asList(id));
        }
    }

    @Test
    public void getUserInfo() throws Exception
    {
        assertNotNull(module.getUserInfo());
    }

    @SuppressWarnings("serial")
    private Map<String, Object> testAccount()
    {
        return new HashMap<String, Object>()
        {
            {
                put("Name", "foo");
                put("Currency", "USD");
                put("BillCycleDay", 1);
                put("AccountNumber", "501");
                put("AllowInvoiceEdit", false);
                put("AutoPay", false);
                put("Notes", "foobar");
                put("Status", "Draft");
            }
        };
    }

}
