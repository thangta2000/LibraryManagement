/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author tkang_85a
 */
@Entity
@Table(name = "Staffs", catalog = "booktique", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Staffs.findAll", query = "SELECT s FROM Staffs s"),
    @NamedQuery(name = "Staffs.findById", query = "SELECT s FROM Staffs s WHERE s.id = :id"),
    @NamedQuery(name = "Staffs.findByFullName", query = "SELECT s FROM Staffs s WHERE s.fullName = :fullName"),
    @NamedQuery(name = "Staffs.findByPhone", query = "SELECT s FROM Staffs s WHERE s.phone = :phone"),
    @NamedQuery(name = "Staffs.findByAddress", query = "SELECT s FROM Staffs s WHERE s.address = :address"),
    @NamedQuery(name = "Staffs.findByEmail", query = "SELECT s FROM Staffs s WHERE s.email = :email"),
    @NamedQuery(name = "Staffs.findByIdentityCard", query = "SELECT s FROM Staffs s WHERE s.identityCard = :identityCard"),
    @NamedQuery(name = "Staffs.findByPosition", query = "SELECT s FROM Staffs s WHERE s.position = :position"),
    @NamedQuery(name = "Staffs.findByGender", query = "SELECT s FROM Staffs s WHERE s.gender = :gender")})
public class Staffs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;
    @Column(name = "Email")
    private String email;
    @Column(name = "IdentityCard")
    private String identityCard;
    @Column(name = "Position")
    private Integer position;
    @Column(name = "Gender")
    private Boolean gender;
    @JoinColumn(name = "CountryId", referencedColumnName = "Id")
    @ManyToOne
    private Countries countryId;
    @OneToMany(mappedBy = "staffId")
    private List<Users> usersList;

    public Staffs() {
    }

    public Staffs(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Countries getCountryId() {
        return countryId;
    }

    public void setCountryId(Countries countryId) {
        this.countryId = countryId;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
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
        if (!(object instanceof Staffs)) {
            return false;
        }
        Staffs other = (Staffs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Staffs[ id=" + id + " ]";
    }
    
}
