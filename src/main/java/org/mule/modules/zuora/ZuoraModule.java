/**
 * Mule Development Kit
 * Copyright 2010-2011 (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mule.modules.zuora;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.zuora.zobject.ZObject;
import org.mule.modules.zuora.zuora.api.SfdcZuoraClient;
import org.mule.modules.zuora.zuora.api.ZuoraClient;
import org.mule.modules.zuora.zuora.api.ZuoraClientAdaptor;
import org.mule.modules.zuora.zuora.api.ZuoraException;

import com.sforce.soap.AmendRequest;
import com.sforce.soap.AmendResult;
import com.sforce.soap.DeleteResult;
import com.sforce.soap.SaveResult;
import com.sforce.soap.SubscribeRequest;
import com.sforce.soap.SubscribeResult;

import java.util.List;


@Module(name="zuora",
        namespace="http://repository.mulesoft.org/releases/org/mule/modules/mule-module-zuora",
        schemaLocation="http://repository.mulesoft.org/releases/org/mule/modules/mule-module-zuora/1.0-SNAPSHOT/mule-zuora.xsd")
public class ZuoraModule 
{
    @Configurable
    @Optional
    private ZuoraClient<ZuoraException> client;
    @Configurable
    private String username;
    @Configurable
    private String password;
    @Configurable
    private String enpoint;
    
    /**
     * Batch creation of ZObjects associated to Subscriptions
     *
     * {@code <subscribe subscriptions-ref="#[variable:subscribtions]"/>} 
     * @param subscriptions
     * @return a subscription results list, one for each subscription 
     */
    @Processor
    public List<SubscribeResult> subscribe(List<SubscribeRequest> subscriptions)
    {
        return client.subscribe(subscriptions);
    }

    /**
     * Batch creation of ZObjects
     *
     * {@code <create>
     *          <zobjects>
     *              <zobject ref="#[variable:anObject]"/>
     *           </zobject>
     *         </create>}   
     * @param zobjects
     * @return a list of SaveResult, one for each ZObject  
     */
    @Processor
    public List<SaveResult> create(List<ZObject> zobjects)
    {
        return client.create(zobjects);
    }
    
    /**
     * Batch creation of invoces for accounts
     * 
     * {@code <generate zobjects-ref="#[variable:accounts]"/>}
     * @param zobjects 
     * @return a list of SaveResult, one for each ZObject
     */
    @Processor
    public List<SaveResult> generate(List<ZObject> zobjects)
    {
        return client.generate(zobjects);
    }

    /**
     * Batch update of ZObjects
     *
     * {@code <update zobjects-ref="#[variable:objects]"/>}
     * @param zobjects
     * @return a list of SaveResult, one for each ZObject 
     */
    @Processor
    public List<SaveResult> update(List<ZObject> zobjects)
    {
        return client.update(zobjects);
    }
    
    /**
     * Batch delete of ZObjects
     * 
     * {@code <delete ids-ref="#[variable:ids]" type="#[variable:zobjectType]"/>}
     * @param type the type of ZObjects to delete 
     * @param ids 
     * @return a list of DeleteResults, one for each id 
     */
    @Processor
    public List<DeleteResult> delete(String type, List<String> ids)
    {
        return client.delete(type, ids);
    }
    
    /**
     * Lazily retrieves ZObject that match a given query, 
     * written in Zuora native query language
     *
     * {@code <find query="#[header:queryString]" />}
     * @param zquery
     * @return a ZObjects iterable
     */
    @Processor
    public Iterable<ZObject> find(String zquery)
    {
        return client.find(zquery);
    }
    
    /**
     * Answers user information
     *
     * {@code <get-user-info userId="#[header:userId]""/>}
     * @param userid
     * @return a User 
     */
    @Processor
    public String getUserInfo(String userid)
    {
        return client.getUserInfo(userid);
    }
    
    /**
     * Amends subscriptions
     *
     * {@code <amend amendaments-ref="#[header:amendamentsList]"/>}
     * @param amendaments 
     * @return a list of AmmendResults, one for each amendament
     */
    @Processor
    public List<AmendResult> amend(List<AmendRequest> amendaments)
    {
        return client.amend(amendaments);
    }    
    
    //@Start
    public void init() throws Exception
    {
        if(client == null)
        {
            setClient(new SfdcZuoraClient(username, password, enpoint));
        }
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void setClient(ZuoraClient<?> client)
    {
        this.client = ZuoraClientAdaptor.adapt(client);
    }
    
    public void setEnpoint(String enpoint)
    {
        this.enpoint = enpoint;
    }
    
    public String getEnpoint()
    {
        return enpoint;
    }
}
