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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "Publishers", catalog = "booktique", schema = "dbo")
@NamedQueries(
{
    @NamedQuery(name = "Publishers.findAll", query = "SELECT p FROM Publishers p"),
    @NamedQuery(name = "Publishers.findById", query = "SELECT p FROM Publishers p WHERE p.id = :id"),
    @NamedQuery(name = "Publishers.findByName", query = "SELECT p FROM Publishers p WHERE p.name = :name"),
    @NamedQuery(name = "Publishers.findByFoundingYear", query = "SELECT p FROM Publishers p WHERE p.foundingYear = :foundingYear")
})
public class Publishers implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Name")
    private String name;
    @Column(name = "FoundingYear")
    private Integer foundingYear;
    @OneToMany(mappedBy = "publisherId")
    private Collection<BookTitles> bookTitlesCollection;
    @JoinColumn(name = "CountryId", referencedColumnName = "Id")
    @ManyToOne
    private Countries countryId;

    public Publishers()
    {
    }

    public Publishers(Integer id)
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

    public Integer getFoundingYear()
    {
        return foundingYear;
    }

    public void setFoundingYear(Integer foundingYear)
    {
        this.foundingYear = foundingYear;
    }

    public Collection<BookTitles> getBookTitlesCollection()
    {
        return bookTitlesCollection;
    }

    public void setBookTitlesCollection(Collection<BookTitles> bookTitlesCollection)
    {
        this.bookTitlesCollection = bookTitlesCollection;
    }

    public Countries getCountryId()
    {
        return countryId;
    }

    public void setCountryId(Countries countryId)
    {
        this.countryId = countryId;
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
        if (!(object instanceof Publishers))
        {
            return false;
        }
        Publishers other = (Publishers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Model.Publishers[ id=" + id + " ]";
    }
    
}
