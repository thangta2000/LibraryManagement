/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tkang_85a
 */
@Entity
@Table(name = "Readers", catalog = "booktique", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Readers.findAll", query = "SELECT r FROM Readers r"),
    @NamedQuery(name = "Readers.findById", query = "SELECT r FROM Readers r WHERE r.id = :id"),
    @NamedQuery(name = "Readers.findByMemberCard", query = "SELECT r FROM Readers r WHERE r.memberCard = :memberCard"),
    @NamedQuery(name = "Readers.findByFullName", query = "SELECT r FROM Readers r WHERE r.fullName = :fullName"),
    @NamedQuery(name = "Readers.findByIdentityCard", query = "SELECT r FROM Readers r WHERE r.identityCard = :identityCard"),
    @NamedQuery(name = "Readers.findByAddress", query = "SELECT r FROM Readers r WHERE r.address = :address"),
    @NamedQuery(name = "Readers.findByEmail", query = "SELECT r FROM Readers r WHERE r.email = :email"),
    @NamedQuery(name = "Readers.findByPhone", query = "SELECT r FROM Readers r WHERE r.phone = :phone"),
    @NamedQuery(name = "Readers.findByGender", query = "SELECT r FROM Readers r WHERE r.gender = :gender"),
    @NamedQuery(name = "Readers.findByBirthDay", query = "SELECT r FROM Readers r WHERE r.birthDay = :birthDay"),
    @NamedQuery(name = "Readers.findByWorkPlace", query = "SELECT r FROM Readers r WHERE r.workPlace = :workPlace"),
    @NamedQuery(name = "Readers.findByJobTitle", query = "SELECT r FROM Readers r WHERE r.jobTitle = :jobTitle")})
public class Readers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "MemberCard")
    private String memberCard;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "IdentityCard")
    private String identityCard;
    @Column(name = "Address")
    private String address;
    @Column(name = "Email")
    private String email;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Gender")
    private Boolean gender;
    @Column(name = "BirthDay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDay;
    @Column(name = "WorkPlace")
    private String workPlace;
    @Column(name = "JobTitle")
    private String jobTitle;
    @JoinColumn(name = "CountryId", referencedColumnName = "Id")
    @ManyToOne
    private Countries countryId;
    @OneToMany(mappedBy = "readerId")
    private List<BookRequests> bookRequestsList;
    @OneToMany(mappedBy = "readerId")
    private List<Borrows> borrowsList;

    public Readers() {
    }

    public Readers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(String memberCard) {
        this.memberCard = memberCard;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Countries getCountryId() {
        return countryId;
    }

    public void setCountryId(Countries countryId) {
        this.countryId = countryId;
    }

    public List<BookRequests> getBookRequestsList() {
        return bookRequestsList;
    }

    public void setBookRequestsList(List<BookRequests> bookRequestsList) {
        this.bookRequestsList = bookRequestsList;
    }

    public List<Borrows> getBorrowsList() {
        return borrowsList;
    }

    public void setBorrowsList(List<Borrows> borrowsList) {
        this.borrowsList = borrowsList;
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
        if (!(object instanceof Readers)) {
            return false;
        }
        Readers other = (Readers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Readers[ id=" + id + " ]";
    }
    
}
