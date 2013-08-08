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

import static org.junit.Assert.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.zuora.api.object.Account;
import com.zuora.api.object.Contact;
import com.zuora.api.object.ZObject;

/**
 * {@link ZObjectUnitTest}
 *
 * @author flbulgarelli
 */
@RunWith(Theories.class)
public class ZObjectUnitTest {
	static DatatypeFactory factory = null;
	static {
		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
    @Theory
    public void zobjectsAreDynamic(ZObject object) {
    	int originalSize =  object.getAny().size();

        object.setAt("Foo2", "Bar");

        assertEquals(originalSize  + 1 , object.getAny().size());
        assertEquals("Bar", object.getField("Foo2"));
    }

    @Test
    public void accountIsStatic() {
        Account a = new Account();
        a.setAccountNumber("1590");
        assertEquals("1590", a.getAccountNumber());
    }

    @Test
    public void accountStaticPropertiesMayBeSetAsDynamicProperties() {
        Account a = new Account();
        a.setAt("AccountNumber", "151");
        assertEquals("151", a.getAccountNumber());
    }

    @Test
    public void accountStaticPropertiesMayBeGetAsDynamicProperties() {
        Account a = new Account();
        a.setAccountNumber("98");
        assertEquals("98", a.getAt("AccountNumber"));
    }

    @Test
    public void accountStaticBooleanPropertiesMayBeGetAsDynamicProperties() {
        Account a = new Account();
        a.setAllowInvoiceEdit(true);
        assertEquals(true, a.getAt("AllowInvoiceEdit"));
    }

    @Test
    public void testUsingStringDatesInStaticField() {
        Account a = new Account();
        a.setCustomField("customField", factory.newXMLGregorianCalendar("2001-04-05T03:18:09Z"));

        assertEquals(4, a.getCreatedDate().getMonth());
        assertEquals(2001, a.getCreatedDate().getYear());
    }

    @Test
    public void testUsingStringDatesInDinamicField() {
        Account a = new Account();
        a.setField("dateTestField", "2001-01-01T03:18:09Z");

        assertEquals("2001-01-01T03:18:09Z", a.getField("dateTestField"));
    }

    @Theory
    public void dynamicProperiesAndAnyHaveSameSize(ZObject a) {
        assertEquals(a.getAny().size(), a.dynamicProperties().size());
    }

}
