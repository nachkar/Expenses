/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author noelachkar
 */
@Entity
@Table(name = "Operations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operations.findAll", query = "SELECT o FROM Operations o"),
    @NamedQuery(name = "Operations.findById", query = "SELECT o FROM Operations o WHERE o.id = :id"),
    @NamedQuery(name = "Operations.findByDescription", query = "SELECT o FROM Operations o WHERE o.description = :description"),
    @NamedQuery(name = "Operations.findByAmout", query = "SELECT o FROM Operations o WHERE o.amout = :amout"),
    @NamedQuery(name = "Operations.findByUser", query = "SELECT o FROM Operations o WHERE o.userid.id = :userid")})

public class Operations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amout")
    private int amout;
    @JoinColumn(name = "categoryid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categories categoryid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userid;

    public Operations() {
    }

    public Operations(Integer id) {
        this.id = id;
    }

    public Operations(Integer id, String description, int amout) {
        this.id = id;
        this.description = description;
        this.amout = amout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public Categories getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Categories categoryid) {
        this.categoryid = categoryid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
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
        if (!(object instanceof Operations)) {
            return false;
        }
        Operations other = (Operations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Classes.Operations[ id=" + id + " ]";
    }
    
}
