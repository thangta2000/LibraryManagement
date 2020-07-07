/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tkang_85a
 */
@Entity
@Table(name = "Borrows", catalog = "booktique", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Borrows.findAll", query = "SELECT b FROM Borrows b"),
    @NamedQuery(name = "Borrows.findById", query = "SELECT b FROM Borrows b WHERE b.id = :id"),
    @NamedQuery(name = "Borrows.findByBorrowDate", query = "SELECT b FROM Borrows b WHERE b.borrowDate = :borrowDate"),
    @NamedQuery(name = "Borrows.findByPlanReturnDate", query = "SELECT b FROM Borrows b WHERE b.planReturnDate = :planReturnDate"),
    @NamedQuery(name = "Borrows.findByReturnDate", query = "SELECT b FROM Borrows b WHERE b.returnDate = :returnDate"),
    @NamedQuery(name = "Borrows.findByChargeFee", query = "SELECT b FROM Borrows b WHERE b.chargeFee = :chargeFee"),
    @NamedQuery(name = "Borrows.findByChargeReason", query = "SELECT b FROM Borrows b WHERE b.chargeReason = :chargeReason")})
public class Borrows implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Long id;
    @Column(name = "BorrowDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowDate;
    @Column(name = "PlanReturnDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date planReturnDate;
    @Column(name = "ReturnDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ChargeFee")
    private BigDecimal chargeFee;
    @Column(name = "ChargeReason")
    private Integer chargeReason;
    @JoinColumn(name = "BookId", referencedColumnName = "Id")
    @ManyToOne
    private Books bookId;
    @JoinColumn(name = "ReaderId", referencedColumnName = "Id")
    @ManyToOne
    private Readers readerId;

    public Borrows() {
    }

    public Borrows(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getPlanReturnDate() {
        return planReturnDate;
    }

    public void setPlanReturnDate(Date planReturnDate) {
        this.planReturnDate = planReturnDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public Integer getChargeReason() {
        return chargeReason;
    }

    public void setChargeReason(Integer chargeReason) {
        this.chargeReason = chargeReason;
    }

    public Books getBookId() {
        return bookId;
    }

    public void setBookId(Books bookId) {
        this.bookId = bookId;
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
        if (!(object instanceof Borrows)) {
            return false;
        }
        Borrows other = (Borrows) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Borrows[ id=" + id + " ]";
    }
    
}
