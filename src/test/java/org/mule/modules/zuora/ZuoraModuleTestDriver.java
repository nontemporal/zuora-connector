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

import com.zuora.api.*;
import com.zuora.api.object.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mule.construct.Flow;
import org.mule.modules.zuora.zobject.ZObjectType;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class ZuoraModuleTestDriver {
    private ZuoraModule module;
    private final String username = "mule@muletax.com";
    private final String password = "Mule2012";

    @Before
    public void setup() throws Exception {
        module = new ZuoraModule();
        module.setEndpoint("https://apisandbox.zuora.com");
        module.connect(username, password);
    }

    /**
     * Test for creating dynamic zobjects
     */
    @Test
    public void createAndDelete() throws Exception {
        SaveResult result = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0);
        assertTrue(result.isSuccess());


        DeleteResult deleteResult = module.delete(ZObjectType.Account, Arrays.asList(result.getId())).get(0);
        assertTrue(deleteResult.isSuccess());
    }

    /**
     * Test for creating zobjects with relationships
     */
    @Test
    @SuppressWarnings("serial")
    public void createAndDeleteRelated() throws Exception {
        SaveResult saveResult = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0);
        assertTrue(saveResult.isSuccess());

        final String accountId = saveResult.getId();
        try {
            SaveResult result = module.create(ZObjectType.Contact,
                    Collections.<Map<String, Object>>singletonList(new HashMap<String, Object>() {
                        {
                            put("Country", "US");
                            put("FirstName", "John");
                            put("LastName", "Doe");
                            put("AccountId", accountId);
                        }
                    })).get(0);
            assertTrue(result.isSuccess());

            DeleteResult deleteResult = module.delete(ZObjectType.Contact, Arrays.asList(result.getId())).get(0);
            assertTrue(deleteResult.isSuccess());
        } finally {
            module.delete(ZObjectType.Account, Arrays.asList(accountId)).get(0);
        }
    }

    /**
     * Test for fetching zobjects when there is no object that matches the query
     */
    @Test
    public void findNoResult() throws Exception {
        Iterator<ZObject> result = module.find("SELECT Id FROM Account where id ='not here!'").iterator();
        assertFalse(result.hasNext());
    }

    /**
     * Test for fetching zobjects when there is an object that matches the query
     */
    @Test
    public void findOneResult() throws Exception {
        String id = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0).getId();
        try {
            Iterator<ZObject> result = module.find("SELECT Id, Name, AccountNumber FROM Account WHERE AccountNumber = '7891'").iterator();
            assertTrue(result.hasNext());
            ZObject next = result.next();
            assertNotNull(next.getId());
            assertEquals(testAccount().get("Name"), next.getAt("Name"));
            assertFalse(result.hasNext());
        } finally {
            module.delete(ZObjectType.Account, Arrays.asList(id));
        }
    }


    

    @Test
    public void getUserInfo() throws Exception {
        User userInfo = module.getUserInfo();
        assertNotNull(userInfo);
        assertFalse(userInfo.getUserId().isEmpty());
        assertFalse(userInfo.getUserEmail().isEmpty());
        assertEquals(username, userInfo.getUsername());
        assertFalse(userInfo.getTenantId().isEmpty());
        assertFalse(userInfo.getTenantName().isEmpty());
    }

    @Test
    @SuppressWarnings("serial")
    public void createRetrieveAnAccountProfileAndDeleteRelatedAccount() throws Exception {
        SaveResult accountResult = module.create(ZObjectType.Account, Collections.singletonList(testAccount())).get(0);
        assertTrue(accountResult.isSuccess());

        final String accountId = accountResult.getId();
        try {
            SaveResult contactResult = module.create(ZObjectType.Contact,
                    Collections.<Map<String, Object>>singletonList(new HashMap<String, Object>() {
                        {
                            put("Country", "US");
                            put("FirstName", "John");
                            put("LastName", "Doe");
                            put("AccountId", accountId);
                        }
                    })).get(0);

            assertTrue(contactResult.isSuccess());

            Map<String, Object> accountMap = testAccount();
            accountMap.put("Id", accountId);
            accountMap.put("BillToId", contactResult.getId());

            SaveResult accountUpdateResult = module.update(ZObjectType.Account, Collections.singletonList(accountMap)).get(0);

            assertTrue(accountUpdateResult.isSuccess());

            Map<String, Object> accountProfile = module.accountProfile(accountId);


            assertEquals("Doe", ((Map<String, Object>) accountProfile.get("billTo")).get("lastName"));
        } finally {
            module.delete(ZObjectType.Account, Arrays.asList(accountId)).get(0);
        }
    }

    @SuppressWarnings("serial")
    private Map<String, Object> testAccount() {
        return new HashMap<String, Object>() {
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

    /**
     * Test for fetching zobjects when there is an object that matches the query
     */
    @Test
    public void getInvoice() throws Exception {

        //Setup Product details
        String productId = getTestProduct();
        String productRatePlanId = getTestProductRatePlan(productId);
        String productRateplanChargeId = getTestProductRatePlanCharge(productRatePlanId);

        assertNotNull(productId);
        assertNotNull(productRatePlanId);
        assertNotNull(productRateplanChargeId);

        SubscribeRequest subscribeReq = new SubscribeRequest();

        //subscribeReq.setAccount(testAccount());
        String uniqueString = UUID.randomUUID().toString();

        Contact contact = new Contact();
        contact.setFirstName(uniqueString);
        contact.setLastName(uniqueString);


        Account account = new Account();
        account.setName(uniqueString);
        account.setBillCycleDay(1);
        account.setCurrency("USD");
        account.setAllowInvoiceEdit(false);
        account.setAutoPay(false);
        account.setStatus("Draft");
        account.setPaymentTerm("Due Upon Receipt");
        account.setBatch("Batch1");

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setType("CreditCard");
        paymentMethod.setCreditCardNumber("5105105105105100");
        paymentMethod.setCreditCardType("Visa");
        paymentMethod.setCreditCardExpirationYear(2026);
        paymentMethod.setCreditCardExpirationMonth(5);
        paymentMethod.setCreditCardHolderName("Unit Test");

        GregorianCalendar calStart = new GregorianCalendar();
        calStart.add(Calendar.DATE, -1);
        XMLGregorianCalendar effectiveStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calStart);

        Subscription subscription = new Subscription();
        subscription.setContractAcceptanceDate(effectiveStartDate);
        subscription.setContractEffectiveDate(effectiveStartDate);
        subscription.setInitialTerm(12);
        subscription.setRenewalTerm(12);

        RatePlan ratePlan = new RatePlan();
        ratePlan.setProductRatePlanId(productRatePlanId);
        RatePlanData ratePlanData = new RatePlanData();
        ratePlanData.setRatePlan(ratePlan);

        SubscriptionData subscriptionData = new SubscriptionData();
        subscriptionData.setSubscription(subscription);
        subscriptionData.getRatePlanData().add(ratePlanData);

        subscribeReq.setAccount(account);
        subscribeReq.setBillToContact(contact);
        subscribeReq.setSoldToContact(contact);
        subscribeReq.setPaymentMethod(paymentMethod);
        subscribeReq.setSubscriptionData(subscriptionData);
        SubscribeResult subscribeResult = module.subscribe(Collections.singletonList(subscribeReq)).get(0);
        assertTrue(subscribeResult.isSuccess());
        assertEquals(0,subscribeResult.getErrors().size());

        Map<String, Object> result = module.getInvoice(subscribeResult.getInvoiceId());
        assertEquals("Posted",result.get("status"));
        assertEquals("amount",result.get("amount"));
        assertNotSame(0,((ArrayList)result.get("invoiceitems")).size());
        assertNotNull(result.get("billTo"));
        assertNotNull(result.get("soldTo"));

        DeleteResult deleteResultAccount = module.delete(ZObjectType.Account, Collections.singletonList(subscribeResult.getAccountId())).get(0);
        assertTrue(deleteResultAccount.isSuccess());

        DeleteResult deleteResultProduct = module.delete(ZObjectType.Product, Collections.singletonList(productId)).get(0);
        assertTrue(deleteResultProduct.isSuccess());
    }

    @SuppressWarnings("serial")
    private String getTestProduct() {

        Map<String, Object> returnMap = new HashMap<String, Object>();



        SaveResult saveResult = null;

        try {
            saveResult = module.create(ZObjectType.Product, Collections.<Map<String, Object>>singletonList(new HashMap<String, Object>() {
                {
                    put("Name", "UnitTestProduct");
                    put("EffectiveStartDate", "2011-01-01T20:00:00");
                    put("EffectiveEndDate", "2013-01-01T20:00:00");
                }
            })).get(0);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return saveResult.getId();

    }

    @SuppressWarnings("serial")
    private String getTestProductRatePlan(final String productId) {
        SaveResult saveResult = null;

        try {
            saveResult = module.create(ZObjectType.ProductRatePlan, Collections.<Map<String, Object>>singletonList(new HashMap<String, Object>() {
                {
                    put("ProductId", productId);
                    put("Name", "TestProductRatePlan");
                    put("EffectiveStartDate", "2011-01-01T20:00:00");
                    put("EffectiveEndDate", "2013-01-01T20:00:00");
                    put("Description", "Test product used in unit test.");
                }
            })).get(0);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return saveResult.getId();

    }

    @SuppressWarnings("serial")
    private String getTestProductRatePlanCharge(final String productRatePlanId) {
        ProductRatePlanChargeTier tier = new ProductRatePlanChargeTier();
        tier.setCurrency("USD");
        tier.setPrice(new BigDecimal(12.2));
        tier.setTier(1);
        tier.setActive(true);
        final ProductRatePlanChargeTierData productRatePlanChargeTierData = new ProductRatePlanChargeTierData();
        productRatePlanChargeTierData.getProductRatePlanChargeTier().add(tier);

        SaveResult saveResult = null;

        try {
            saveResult = module.create(ZObjectType.ProductRatePlanCharge, Collections.<Map<String, Object>>singletonList(new HashMap<String, Object>() {
                {
                    put("BillingPeriod", "Month");
                    put("ChargeModel", "FlatFee");
                    put("ChargeType", "Recurring");
                    put("DefaultQuantity", "1	");
                    put("Model", "PerUnit");
                    put("Name", "TestProductRatePlanCharge");
                    put("ProductRatePlanId", productRatePlanId);
                    put("ProductRatePlanChargeTierData", productRatePlanChargeTierData);
                    put("TriggerEvent", "ContractEffective");
                    put("Type", "Recurring");
                }
            })).get(0);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return saveResult.getId();

    }
}
