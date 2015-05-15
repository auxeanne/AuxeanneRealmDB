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
package com.auxeanne.realm.db.util;

import java.io.Serializable;
import java.security.Principal;

/**
 * <p>JAAS and the requesting Web application are loosely coupled. So little
 * information are exchanged beside username and password.</p>
 *
 * <p>JAAS is passing back a Subject and its Principals to the Web application. But default implementation
 * is just a mirror of the username with little customization possible.</p>
 *
 * <p>Some applications may require a specific control of the username and password
 * (ex: remember me feature) and additional information (Ex: id, real name, ...).</p>
 *
 * <p>The API allows adding a custom principal to the subject.
 * This custom principal is then retrieved in the client application by requesting the subject :<p>
 * <pre>{@code Subject subject = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");
 * Set<MyCustomPrincipal> principals = subject.getPrincipals(MyCustomPrincipal.class);
 * for (MyCustomPrincipal principal : principals) {
 *   // do necessary tasks
 * } }</pre>
 *
 * @author Jean-Michel Tanguy
 */
public class AuxeannePrincipal implements Principal, Serializable {

    Integer id;
    String name;

    public AuxeannePrincipal(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
