## AuxeanneRealmDB
Test and reference project for the AuxeanneRealmAPI implementing database configuration as JAAS delegate.

AuxeanneRealmAPI targets Glassfish application server to extend easily Java Authentication and Authorization Service (JAAS).

This API delegates these tasks to a local EJB bean deployed in the Glassfish instance (WAR, EAR) allowing:

- full usage of the container features (JPA, CDI, ...)
- handling custom login, remember me, multi-tenant, ...
- hot deployment (no need to restart the server instance)
- while keeping the benefits of container managed security

## Glassfish Setup
1 - Download the AuxeanneRealmAPI JAR

2 - Add the JAR to the domain "lib" folder

3 - Complete the "login.conf" file in the "config" directory with : 

    AuxeanneRealm {
        com.auxeanne.realm.LoginModule required;
     };
   
4 - Create a realm in Configuration > server-config > Security > realms

 - give the name "AuxeanneRealm"
 - set the custom class name as "com.auxeanne.realm.Realm"
 - add the property LOCAL_BEAN_JNDI with the JNDI value "java:global/AuxeanneRealmDB/DelegateBean" 
 
5 - Make sure the JavaDB server is running

## AuxeanneRealmDB Setup

Just deploy the WAR to Glassfish and access the site : http://localhost:8080/AuxeanneRealmDB 

This interface is using a relational database (JavaDB by default, but any is just fine) to store and retrieve credentials.

Add or clear users allowed to connect to the "private" page. Access is controlled by the container as defined in the web.xml, but authentication and authorization are delegated to the DelegatedBean EJB bean through JNDI.
