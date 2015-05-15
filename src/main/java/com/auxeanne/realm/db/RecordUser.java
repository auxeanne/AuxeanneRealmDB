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
package com.auxeanne.realm.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for user credentials.
 * 
 * @author Jean-Michel Tanguy
 */
@Entity
@Table(name = "record_user")
@XmlRootElement
public class RecordUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id_")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "login_")
    private String login;
    @Basic(optional = false)
    @Column(name = "password_")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordUser")
    private List<RecordUserGroup> recordUserGroupList;
   
    public RecordUser() {
    }

    public RecordUser(Integer id) {
        this.id = id;
    }

    public RecordUser(Integer id, String login, String password, String role, boolean retired) {
        this.id = id;
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @XmlTransient
    public List<RecordUserGroup> getRecordUserGroupList() {
        return recordUserGroupList;
    }

    public void setRecordUserGroupList(List<RecordUserGroup> recordUserGroupList) {
        this.recordUserGroupList = recordUserGroupList;
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
        if (!(object instanceof RecordUser)) {
            return false;
        }
        RecordUser other = (RecordUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return login+" / "+password;
    }
    
}
