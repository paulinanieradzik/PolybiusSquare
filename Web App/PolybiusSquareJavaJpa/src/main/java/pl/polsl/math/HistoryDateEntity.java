package pl.polsl.math;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class represents a database table HistoryDateEntity.
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
@Entity
public class HistoryDateEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    /**
     * Date of translation
     */
    private String date;

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(String date) {
        this.date = date;
    }

    private String idTranslation;

    /**
     * Get the value of idTranslation
     *
     * @return the value of idTranslation
     */
    public String getIdTranslation() {
        return idTranslation;
    }

    /**
     * Set the value of idTranslation
     *
     * @param idTranslation new value of idTranslation
     */
    public void setIdTranslation(String idTranslation) {
        this.idTranslation = idTranslation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HistoryDateEntity)) {
            return false;
        }
        HistoryDateEntity other = (HistoryDateEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.math.HistoryDateEntity[ id=" + id + " ]";
    }

}
