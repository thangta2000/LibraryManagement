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
@Table(name = "Authors", catalog = "booktique", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Authors.findAll", query = "SELECT a FROM Authors a"),
    @NamedQuery(name = "Authors.findById", query = "SELECT a FROM Authors a WHERE a.id = :id"),
    @NamedQuery(name = "Authors.findByFullName", query = "SELECT a FROM Authors a WHERE a.fullName = :fullName")})
public class Authors implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "FullName")
    private String fullName;
    @JoinColumn(name = "CountryId", referencedColumnName = "Id")
    @ManyToOne
    private Countries countryId;
    @OneToMany(mappedBy = "authorId")
    private List<BooksByAuthors> booksByAuthorsList;

    public Authors() {
    }

    public Authors(Integer id) {
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

    public Countries getCountryId() {
        return countryId;
    }

    public void setCountryId(Countries countryId) {
        this.countryId = countryId;
    }

    public List<BooksByAuthors> getBooksByAuthorsList() {
        return booksByAuthorsList;
    }

    public void setBooksByAuthorsList(List<BooksByAuthors> booksByAuthorsList) {
        this.booksByAuthorsList = booksByAuthorsList;
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
        if (!(object instanceof Authors)) {
            return false;
        }
        Authors other = (Authors) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Authors[ id=" + id + " ]";
    }
    
}
