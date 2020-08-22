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
 * @author admin
 */
@Entity
@Table(name = "Books", catalog = "booktique", schema = "dbo")
@NamedQueries(
{
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
    @NamedQuery(name = "Books.findById", query = "SELECT b FROM Books b WHERE b.id = :id"),
    @NamedQuery(name = "Books.findByCode", query = "SELECT b FROM Books b WHERE b.code = :code"),
    @NamedQuery(name = "Books.findByCreatedDate", query = "SELECT b FROM Books b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "Books.findByStatus", query = "SELECT b FROM Books b WHERE b.status = :status"),
    @NamedQuery(name = "Books.updateStatus", query = "UPDATE Books b SET b.status = 0 WHERE b.id = :id")
})
public class Books implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Code")
    private String code;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "Status")
    private Integer status;
    @JoinColumn(name = "BookTitleId", referencedColumnName = "Id")
    @ManyToOne
    private BookTitles bookTitleId;
    @OneToMany(mappedBy = "bookId")
    private List<Borrows> borrowsList;

    public Books()
    {
    }

    public Books(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public BookTitles getBookTitleId()
    {
        return bookTitleId;
    }

    public void setBookTitleId(BookTitles bookTitleId)
    {
        this.bookTitleId = bookTitleId;
    }

    public List<Borrows> getBorrowsList()
    {
        return borrowsList;
    }

    public void setBorrowsList(List<Borrows> borrowsList)
    {
        this.borrowsList = borrowsList;
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
        if (!(object instanceof Books))
        {
            return false;
        }
        Books other = (Books) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Model.Books[ id=" + id + " ]";
    }
    
}
