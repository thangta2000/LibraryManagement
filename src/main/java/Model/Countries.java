/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "Countries", catalog = "booktique", schema = "dbo")
@NamedQueries(
{
    @NamedQuery(name = "Countries.findAll", query = "SELECT c FROM Countries c"),
    @NamedQuery(name = "Countries.findById", query = "SELECT c FROM Countries c WHERE c.id = :id"),
    @NamedQuery(name = "Countries.findByName", query = "SELECT c FROM Countries c WHERE c.name = :name")
})
public class Countries implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Name")
    private String name;
    @OneToMany(mappedBy = "countryId")
    private Collection<Staffs> staffsCollection;
    @OneToMany(mappedBy = "countryId")
    private Collection<Authors> authorsCollection;
    @OneToMany(mappedBy = "countryId")
    private Collection<BookTitles> bookTitlesCollection;
    @OneToMany(mappedBy = "countryId")
    private Collection<Readers> readersCollection;
    @OneToMany(mappedBy = "countryId")
    private Collection<Publishers> publishersCollection;
    @OneToMany(mappedBy = "countryId")
    private Collection<BookRequests> bookRequestsCollection;

    public Countries()
    {
    }

    public Countries(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Collection<Staffs> getStaffsCollection()
    {
        return staffsCollection;
    }

    public void setStaffsCollection(Collection<Staffs> staffsCollection)
    {
        this.staffsCollection = staffsCollection;
    }

    public Collection<Authors> getAuthorsCollection()
    {
        return authorsCollection;
    }

    public void setAuthorsCollection(Collection<Authors> authorsCollection)
    {
        this.authorsCollection = authorsCollection;
    }

    public Collection<BookTitles> getBookTitlesCollection()
    {
        return bookTitlesCollection;
    }

    public void setBookTitlesCollection(Collection<BookTitles> bookTitlesCollection)
    {
        this.bookTitlesCollection = bookTitlesCollection;
    }

    public Collection<Readers> getReadersCollection()
    {
        return readersCollection;
    }

    public void setReadersCollection(Collection<Readers> readersCollection)
    {
        this.readersCollection = readersCollection;
    }

    public Collection<Publishers> getPublishersCollection()
    {
        return publishersCollection;
    }

    public void setPublishersCollection(Collection<Publishers> publishersCollection)
    {
        this.publishersCollection = publishersCollection;
    }

    public Collection<BookRequests> getBookRequestsCollection()
    {
        return bookRequestsCollection;
    }

    public void setBookRequestsCollection(Collection<BookRequests> bookRequestsCollection)
    {
        this.bookRequestsCollection = bookRequestsCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Countries))
        {
            return false;
        }
        Countries other = (Countries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Model.Countries[ id=" + id + " ]";
    }
    
}
