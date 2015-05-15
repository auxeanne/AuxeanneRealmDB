/**
 * <p>Test and reference project for the AuxeanneRealmAPI implementing database configuration as JAAS delegate....</p>
 * 
* <p>AuxeanneRealmAPI targets Glassfish application server to extend easily 
 * Java Authentication and Authorization Service (JAAS).</p>
 * 
 * <p>This API delegates these tasks to a local EJB bean deployed in the
 * Glassfish instance (WAR, EAR) allowing:</p>
 * <ul>
 * <li>full usage of the container features (JPA, CDI, ...)</li>
 * <li>handling custom login, remember me, multi-tenant, ...</li>
 * <li>hot deployment (no need to restart the server instance)</li>
 * <li>while keeping the benefits of container managed security</li>
 * </ul>
 * 
 * <h2>Glassfish Setup</h2>
 * 
 * <ol>
 * <li>Download the AuxeanneRealmAPI JAR</li>
 * <li>Add the JAR to the domain "lib" folder</li>
 * <li>Complete the "login.conf" file in the "config" directory with :
 * <pre>{@code AuxeanneRealm {
 *    com.auxeanne.realm.LoginModule required;
 * };}</pre></li>
 * <li>Create a realm in Configuration : server-config : Security : realms<br>
 * - give the name "AuxeanneRealm"<br>
 * - set the custom class name as "com.auxeanne.realm.Realm"<br>
 * - add the property LOCAL_BEAN_JNDI with the JNDI value "java:global/AuxeanneRealmDB/DelegateBean" <br> 
 * </li>
 * <li>Make sure the JavaDB server is running</li>
 * </ol>
 * 
 * <h2>AuxeanneRealmDB Setup</h2>
 * 
 * <p>Just deploy the WAR to Glassfish and access the site : http://localhost:8080/AuxeanneRealmDB </p>
 * <p>This interface is using a relational database (JavaDB by default, but any is just fine) to store and retrieve credentials.</p>
 * <p>Add or clear users allowed to connect to the "private" page. Access is controlled by the container as defined in the
 * web.xml, but authentication and authorization are delegated to the DelegatedBean EJB bean through JNDI.</p>
 * 
 */
package com.auxeanne.realm.db;
