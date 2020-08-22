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
 * @author admin
 */
@Entity
@Table(name = "BookTitles", catalog = "booktique", schema = "dbo")
@NamedQueries(
{
    @NamedQuery(name = "BookTitles.findAll", query = "SELECT b FROM BookTitles b"),
    @NamedQuery(name = "BookTitles.findById", query = "SELECT b FROM BookTitles b WHERE b.id = :id"),
    @NamedQuery(name = "BookTitles.findByTitle", query = "SELECT b FROM BookTitles b WHERE b.title = :title"),
    @NamedQuery(name = "BookTitles.findByPublishYear", query = "SELECT b FROM BookTitles b WHERE b.publishYear = :publishYear"),
    @NamedQuery(name = "BookTitles.findByPages", query = "SELECT b FROM BookTitles b WHERE b.pages = :pages"),
    @NamedQuery(name = "BookTitles.findByWidth", query = "SELECT b FROM BookTitles b WHERE b.width = :width"),
    @NamedQuery(name = "BookTitles.findByIbsn", query = "SELECT b FROM BookTitles b WHERE b.ibsn = :ibsn"),
    @NamedQuery(name = "BookTitles.findByStatus", query = "SELECT b FROM BookTitles b WHERE b.status = :status"),
    @NamedQuery(name = "BookTitles.findByAuthor", query = "SELECT b FROM BookTitles b WHERE b.author = :author")
})
public class BookTitles implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Title")
    private String title;
    @Column(name = "PublishYear")
    private Integer publishYear;
    @Column(name = "Pages")
    private Integer pages;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Width")
    private Double width;
    @Column(name = "IBSN")
    private String ibsn;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "Author")
    private String author;
    @JoinColumn(name = "CategoryId", referencedColumnName = "Id")
    @ManyToOne
    private Categories categoryId;
    @JoinColumn(name = "CountryId", referencedColumnName = "Id")
    @ManyToOne
    private Countries countryId;
    @JoinColumn(name = "PublisherId", referencedColumnName = "Id")
    @ManyToOne
    private Publishers publisherId;
    @OneToMany(mappedBy = "bookTitleId")
    private List<Books> booksList;

    public BookTitles()
    {
    }

    public BookTitles(Integer id)
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getPublishYear()
    {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear)
    {
        this.publishYear = publishYear;
    }

    public Integer getPages()
    {
        return pages;
    }

    public void setPages(Integer pages)
    {
        this.pages = pages;
    }

    public Double getWidth()
    {
        return width;
    }

    public void setWidth(Double width)
    {
        this.width = width;
    }

    public String getIbsn()
    {
        return ibsn;
    }

    public void setIbsn(String ibsn)
    {
        this.ibsn = ibsn;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public Categories getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId)
    {
        this.categoryId = categoryId;
    }

    public Countries getCountryId()
    {
        return countryId;
    }

    public void setCountryId(Countries countryId)
    {
        this.countryId = countryId;
    }

    public Publishers getPublisherId()
    {
        return publisherId;
    }

    public void setPublisherId(Publishers publisherId)
    {
        this.publisherId = publisherId;
    }

    public List<Books> getBooksList()
    {
        return booksList;
    }

    public void setBooksList(List<Books> booksList)
    {
        this.booksList = booksList;
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
        if (!(object instanceof BookTitles))
        {
            return false;
        }
        BookTitles other = (BookTitles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Model.BookTitles[ id=" + id + " ]";
    }
    
}
