package com.sforce.soap;

/**
 * Generated class, please do not edit.
 */
public class Login_element implements com.sforce.ws.bind.XMLizable {

  /**
   * Constructor
   */
  public Login_element() {
  }
    
  
  /**
   * element  : username of type {http://www.w3.org/2001/XMLSchema}string
   * java type: java.lang.String
   */
  private static final com.sforce.ws.bind.TypeInfo username__typeInfo =
    new com.sforce.ws.bind.TypeInfo("http://api.zuora.com/","username","http://www.w3.org/2001/XMLSchema","string",0,1,true);

  private boolean username__is_set = false;

  private java.lang.String username;

  public java.lang.String getUsername() {
    return username;
  }

  

  public void setUsername(java.lang.String username) {
    this.username = username;
    username__is_set = true;
  }
  
  /**
   * element  : password of type {http://www.w3.org/2001/XMLSchema}string
   * java type: java.lang.String
   */
  private static final com.sforce.ws.bind.TypeInfo password__typeInfo =
    new com.sforce.ws.bind.TypeInfo("http://api.zuora.com/","password","http://www.w3.org/2001/XMLSchema","string",0,1,true);

  private boolean password__is_set = false;

  private java.lang.String password;

  public java.lang.String getPassword() {
    return password;
  }

  

  public void setPassword(java.lang.String password) {
    this.password = password;
    password__is_set = true;
  }
  

  /**
   */
  public void write(javax.xml.namespace.QName __element,
      com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
      throws java.io.IOException {
    __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
    
    writeFields(__out, __typeMapper);
    __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
  }

  protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
      com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException {
   
    __typeMapper.writeString(__out, username__typeInfo, username, username__is_set);
    __typeMapper.writeString(__out, password__typeInfo, password, password__is_set);
  }


  public void load(com.sforce.ws.parser.XmlInputStream __in,
      com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
    __typeMapper.consumeStartTag(__in);
    loadFields(__in, __typeMapper);
    __typeMapper.consumeEndTag(__in);
  }

  protected void loadFields(com.sforce.ws.parser.XmlInputStream __in,
      com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
   
    __in.peekTag();
    if (__typeMapper.isElement(__in, username__typeInfo)) {
      setUsername((java.lang.String)__typeMapper.readString(__in, username__typeInfo, java.lang.String.class));
    }
    __in.peekTag();
    if (__typeMapper.isElement(__in, password__typeInfo)) {
      setPassword((java.lang.String)__typeMapper.readString(__in, password__typeInfo, java.lang.String.class));
    }
  }

  public String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder();
    sb.append("[Login_element ");
    
    sb.append(" username=");
    sb.append("'"+com.sforce.ws.util.Verbose.toString(username)+"'\n");
    sb.append(" password=");
    sb.append("'"+com.sforce.ws.util.Verbose.toString(password)+"'\n");
    sb.append("]\n");
    return sb.toString();
  }
}