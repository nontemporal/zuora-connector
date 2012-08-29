/**
 * Mule Zuora Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.zuora.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.5.1
 * 2012-08-28T17:54:23.623-03:00
 * Generated source version: 2.5.1
 * 
 */
@WebService(targetNamespace = "http://api.zuora.com/", name = "Soap")
@XmlSeeAlso({ObjectFactory.class, com.zuora.api.fault.ObjectFactory.class, com.zuora.api.object.ObjectFactory.class})
public interface Soap {

    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "update", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Update")
    @WebMethod
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.UpdateResponse")
    public java.util.List<com.zuora.api.SaveResult> update(
        @WebParam(name = "zObjects", targetNamespace = "http://api.zuora.com/")
        java.util.List<com.zuora.api.object.ZObject> zObjects
    ) throws InvalidTypeFault, UnexpectedErrorFault;

    @WebResult(name = "results", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "amend", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Amend")
    @WebMethod
    @ResponseWrapper(localName = "amendResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.AmendResponse")
    public java.util.List<com.zuora.api.AmendResult> amend(
        @WebParam(name = "requests", targetNamespace = "http://api.zuora.com/")
        java.util.List<com.zuora.api.AmendRequest> requests
    ) throws UnexpectedErrorFault;

    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "create", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Create")
    @WebMethod
    @ResponseWrapper(localName = "createResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.CreateResponse")
    public java.util.List<com.zuora.api.SaveResult> create(
        @WebParam(name = "zObjects", targetNamespace = "http://api.zuora.com/")
        java.util.List<com.zuora.api.object.ZObject> zObjects
    ) throws InvalidTypeFault, UnexpectedErrorFault;

    /**
     * Gets the next batch of sObjects from a query
     * 			
     */
    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "queryMore", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.QueryMore")
    @WebMethod
    @ResponseWrapper(localName = "queryMoreResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.QueryMoreResponse")
    public com.zuora.api.QueryResult queryMore(
        @WebParam(name = "queryLocator", targetNamespace = "http://api.zuora.com/")
        java.lang.String queryLocator
    ) throws UnexpectedErrorFault, InvalidQueryLocatorFault;

    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "query", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Query")
    @WebMethod
    @ResponseWrapper(localName = "queryResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.QueryResponse")
    public com.zuora.api.QueryResult query(
        @WebParam(name = "queryString", targetNamespace = "http://api.zuora.com/")
        java.lang.String queryString
    ) throws UnexpectedErrorFault, MalformedQueryFault, InvalidQueryLocatorFault;

    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "generate", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Generate")
    @WebMethod
    @ResponseWrapper(localName = "generateResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.GenerateResponse")
    public java.util.List<com.zuora.api.SaveResult> generate(
        @WebParam(name = "zObjects", targetNamespace = "http://api.zuora.com/")
        java.util.List<com.zuora.api.object.ZObject> zObjects
    ) throws InvalidTypeFault, UnexpectedErrorFault;

    @RequestWrapper(localName = "getUserInfo", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.GetUserInfo")
    @WebMethod
    @ResponseWrapper(localName = "getUserInfoResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.GetUserInfoResponse")
    public void getUserInfo(
        @WebParam(mode = WebParam.Mode.OUT, name = "TenantId", targetNamespace = "http://api.zuora.com/")
        javax.xml.ws.Holder<java.lang.String> tenantId,
        @WebParam(mode = WebParam.Mode.OUT, name = "TenantName", targetNamespace = "http://api.zuora.com/")
        javax.xml.ws.Holder<java.lang.String> tenantName,
        @WebParam(mode = WebParam.Mode.OUT, name = "UserEmail", targetNamespace = "http://api.zuora.com/")
        javax.xml.ws.Holder<java.lang.String> userEmail,
        @WebParam(mode = WebParam.Mode.OUT, name = "UserFullName", targetNamespace = "http://api.zuora.com/")
        javax.xml.ws.Holder<java.lang.String> userFullName,
        @WebParam(mode = WebParam.Mode.OUT, name = "UserId", targetNamespace = "http://api.zuora.com/")
        javax.xml.ws.Holder<java.lang.String> userId,
        @WebParam(mode = WebParam.Mode.OUT, name = "Username", targetNamespace = "http://api.zuora.com/")
        javax.xml.ws.Holder<java.lang.String> username
    ) throws UnexpectedErrorFault;

    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "delete", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Delete")
    @WebMethod
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.DeleteResponse")
    public java.util.List<com.zuora.api.DeleteResult> delete(
        @WebParam(name = "type", targetNamespace = "http://api.zuora.com/")
        java.lang.String type,
        @WebParam(name = "ids", targetNamespace = "http://api.zuora.com/")
        java.util.List<java.lang.String> ids
    ) throws InvalidTypeFault, UnexpectedErrorFault, InvalidValueFault;

    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "subscribe", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Subscribe")
    @WebMethod
    @ResponseWrapper(localName = "subscribeResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.SubscribeResponse")
    public java.util.List<com.zuora.api.SubscribeResult> subscribe(
        @WebParam(name = "subscribes", targetNamespace = "http://api.zuora.com/")
        java.util.List<com.zuora.api.SubscribeRequest> subscribes
    ) throws UnexpectedErrorFault;

    @WebResult(name = "result", targetNamespace = "http://api.zuora.com/")
    @RequestWrapper(localName = "login", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.Login")
    @WebMethod
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://api.zuora.com/", className = "com.zuora.api.LoginResponse")
    public com.zuora.api.LoginResult login(
        @WebParam(name = "username", targetNamespace = "http://api.zuora.com/")
        java.lang.String username,
        @WebParam(name = "password", targetNamespace = "http://api.zuora.com/")
        java.lang.String password
    ) throws UnexpectedErrorFault, LoginFault;
}