/*
 * Copyright 2015 Jean-Michel.
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
package com.auxeanne.realm.db.web;

import com.auxeanne.realm.db.RecordUser;
import com.auxeanne.realm.db.bean.UserBean;
import com.auxeanne.realm.db.util.AuxeannePrincipal;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;


/**
 * Web interface back office. 
 * The site is designed to be minimalist as the goal is to test and prove 
 * JNDI connection between AuxeanneRealmAPI and delegated bean for JAAS.
 * Though it is a valid reference design to implement this mechanism in projects.
 * 
 * @author Jean-Michel
 */
@Named(value = "userMB")
@RequestScoped
public class UserMB {

    /**
     * managing users from the database
     */
    @EJB
    private UserBean userBean;

    /**
     * form element
     */
    private String login;
    /**
     * form element
     */
    private String password;

    /**
     * Creates a new instance of UserMB
     */
    public UserMB() {
    }

    /**
     * creating a user with role to access the private page
     * @throws LoginException 
     */
    public void addUser() throws LoginException {
        userBean.add(login, password);
        login = null;
        password = null;
    }
    
    /**
     * removing all existing users
     */
    public void clearUsers() {
        userBean.clearUsers();
    }
    
    /**
     * providing ID of the authenticated user as allowed by the custom principal
     * 
     * @return database ID of authenticated user
     * @throws PolicyContextException
     * @throws LoginException 
     */
    public Integer getUserId() throws PolicyContextException, LoginException {
        Subject subject = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");
        Set<AuxeannePrincipal> principals = subject.getPrincipals(AuxeannePrincipal.class);
        for (AuxeannePrincipal principal : principals) {
            return principal.getId();
        }
        throw new LoginException();
        
    }
    
    /**
     * programmatic login
     * 
     * @return path to the private page
     * @throws javax.servlet.ServletException
     */
    public String login() throws ServletException {
         getRequest().login(login, password);
         return "/private/index.jsf?faces-redirect=true";
    }
    
    private HttpServletRequest getRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Object request = externalContext.getRequest();
        return request instanceof HttpServletRequest ? (HttpServletRequest) request : null;
    }
    
     /**
     * form logout by session invalidation
     * 
     * @return path to the home page
     * @throws IOException 
     */
    public String logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.jsf?faces-redirect=true";
    }

    /**
     * registered user list
     * @return 
     */
    public List<RecordUser> getUserList() {
        return userBean.getUserList();
    }

    //--------------------------------------------------------------------------
    // Getters & Setters
    //--------------------------------------------------------------------------
    
    public String getLogin() {
        // login textfield is always blank
        return null;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        // passowrd textfield is always blank
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
