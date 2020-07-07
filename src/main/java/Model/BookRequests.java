/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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

/**
 *
 * @author tkang_85a
 */
@Entity
@Table(name = "BookRequests", catalog = "booktique", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "BookRequests.findAll", query = "SELECT b FROM BookRequests b"),
    @NamedQuery(name = "BookRequests.findById", query = "SELECT b FROM BookRequests b WHERE b.id = :id"),
    @NamedQuery(name = "BookRequests.findByBookName", query = "SELECT b FROM BookRequests b WHERE b.bookName = :bookName"),
    @NamedQuery(name = "BookRequests.findByAuthorName", query = "SELECT b FROM BookRequests b WHERE b.authorName = :authorName"),
    @NamedQuery(name = "BookRequests.findByPublishYear", query = "SELECT b FROM BookRequests b WHERE b.publishYear = :publishYear"),
    @NamedQuery(name = "BookRequests.findByPublisherName", query = "SELECT b FROM BookRequests b WHERE b.publisherName = :publisherName"),
    @NamedQuery(name = "BookRequests.findByIbsn", query = "SELECT b FROM BookRequests b WHERE b.ibsn = :ibsn")})
public class BookRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Long id;
    @Column(name = "BookName")
    private String bookName;
    @Column(name = "AuthorName")
    private String authorName;
    @Column(name = "PublishYear")
    private Integer publishYear;
    @Column(name = "PublisherName")
    private String publisherName;
    @Column(name = "IBSN")
    private String ibsn;
    @JoinColumn(name = "CountryId", referencedColumnName = "Id")
    @ManyToOne
    private Countries countryId;
    @JoinColumn(name = "ReaderId", referencedColumnName = "Id")
    @ManyToOne
    private Readers readerId;

    public BookRequests() {
    }

    public BookRequests(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getIbsn() {
        return ibsn;
    }

    public void setIbsn(String ibsn) {
        this.ibsn = ibsn;
    }

    public Countries getCountryId() {
        return countryId;
    }

    public void setCountryId(Countries countryId) {
        this.countryId = countryId;
    }

    public Readers getReaderId() {
        return readerId;
    }

    public void setReaderId(Readers readerId) {
        this.readerId = readerId;
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
        if (!(object instanceof BookRequests)) {
            return false;
        }
        BookRequests other = (BookRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.BookRequests[ id=" + id + " ]";
    }
    
}
