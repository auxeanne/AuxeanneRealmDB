/*
 * Copyright 2015 Jean-Michel Tanguy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.auxeanne.realm.db.bean;

import com.auxeanne.realm.RealmDelegate;
import com.auxeanne.realm.db.RecordUser;
import com.auxeanne.realm.db.util.AuxeannePrincipal;
import com.auxeanne.realm.db.util.Hasher;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.login.LoginException;

/**
 * <p>Bean used as delegate by the realm implementing the AuxeanneRealmAPI.
 * Even if the security is still handled by the container, the authentication
 * and authorization are performed here.</p>
 * 
 * <p>JNDI name space must be correctly set in AuxeanneRealm attribute LOCAL_BEAN_JNDI.</p>
 * 
 * @author Jean-Michel Tanguy
 */
@Stateless
@Local
public class DelegateBean implements RealmDelegate {

    @PersistenceContext(unitName = "JAVADB-PU")
    private EntityManager em;

    /**
     * Called by JAAS. Matching user in database from its login / password.
     *
     * @param username
     * @param password in clear text as password is hashed
     * @throws LoginException if unsuccessful for any reason
     */
    @Override
    public Principal authenticate(Properties properties, String username, String password) throws LoginException {
        String passwordHash = Hasher.hash(properties.getProperty(Hasher.DB_DIGEST_ALGORITHM_PROPERTY, Hasher.DB_DIGEST_ALGORITHM_SHA256), password);
        RecordUser user = (RecordUser) em.createQuery("SELECT ru FROM RecordUser ru WHERE ru.login = :login AND ru.password = :password")
                .setParameter("login", username).setParameter("password", passwordHash).setMaxResults(1).getSingleResult();
        if (user == null) {
            throw new LoginException();
        }
        return new AuxeannePrincipal(user.getId(), username);
    }

    /**
     * Called by JAAS. Getting roles from database.
     *
     * @param username
     * @return
     * @throws InvalidOperationException
     * @throws NoSuchUserException
     */
    @Override
    public Enumeration getGroupNames(Properties properties, String username) throws InvalidOperationException, NoSuchUserException {
        List list = em.createQuery("SELECT rug.role FROM RecordUserGroup rug WHERE rug.login = :login").setParameter("login", username).getResultList();
        if (list.isEmpty()) {
            throw new NoSuchUserException("");
        }
        return Collections.enumeration(list);
    }

    

}
