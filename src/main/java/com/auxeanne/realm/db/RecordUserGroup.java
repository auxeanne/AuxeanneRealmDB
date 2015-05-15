/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Auxeanne and/or its affiliates. All rights reserved.
 *
 */

package com.auxeanne.realm.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for user's roles.
 * 
 * @author Jean-Michel Tanguy
 */
@Entity
@Table(name = "record_user_group")
@XmlRootElement
public class RecordUserGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id_")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "login_")
    private String login;
    @Basic(optional = false)
    @Column(name = "role_")
    private String role;
    @JoinColumn(name = "record_user_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private RecordUser recordUser;

    public RecordUserGroup() {
    }

    public RecordUserGroup(Integer id) {
        this.id = id;
    }

    public RecordUserGroup(Integer id, String login, String role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RecordUser getRecordUser() {
        return recordUser;
    }

    public void setRecordUser(RecordUser recordUser) {
        this.recordUser = recordUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecordUserGroup)) {
            return false;
        }
        RecordUserGroup other = (RecordUserGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RecordUserGroup[ id=" + id + " ]";
    }
    
}
