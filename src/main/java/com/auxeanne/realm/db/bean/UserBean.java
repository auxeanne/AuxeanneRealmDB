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

import com.auxeanne.realm.db.RecordUser;
import com.auxeanne.realm.db.RecordUserGroup;
import com.auxeanne.realm.db.util.Hasher;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.login.LoginException;

/**
 * Managing users in a database.
 *
 * @author Jean-Michel Tanguy
 */
@Stateless
public class UserBean {

    public static final String USER_GROUP = "USER";

    @PersistenceContext(unitName = "JAVADB-PU")
    private EntityManager em;

    /**
     * creating a new user with default role "USER" giving access to the private
     * page as defined in the web.xml
     *
     * @param username
     * @param password
     * @throws LoginException
     */
    public void add(String username, String password) throws LoginException {
        RecordUser user = new RecordUser();
        user.setLogin(username);
        user.setPassword(Hasher.hash(Hasher.DB_DIGEST_ALGORITHM_SHA256, password));
        em.persist(user);

        RecordUserGroup group = new RecordUserGroup();
        group.setLogin(username);
        group.setRecordUser(user);
        group.setRole(USER_GROUP);
        em.persist(group);
    }

    /**
     * removing all users
     */
    public void clearUsers() {
        List<RecordUserGroup> groupList = em.createQuery("SELECT group FROM RecordUserGroup group").getResultList();
        for (RecordUserGroup group : groupList) {
            em.remove(group);
        }
        List<RecordUser> userList = em.createQuery("SELECT user FROM RecordUser user").getResultList();
        for (RecordUser user : userList) {
            em.remove(user);
        }
    }

    /**
     * listing existing users
     * @return list of entities representing users
     */
    public List<RecordUser> getUserList() {
        return em.createQuery("SELECT user FROM RecordUser user").getResultList();
    }

}
