package pl.polsl.math;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class represents a database table TranslationEntity.
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
@Entity
public class TranslationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    /**
     * given word value
     */
    private String givenWord;

    /**
     * Get the value of givenWord
     *
     * @return the value of givenWord
     */
    public String getGivenWord() {
        return givenWord;
    }

    /**
     * Set the value of givenWord
     *
     * @param givenWord new value of givenWord
     */
    public void setGivenWord(String givenWord) {
        this.givenWord = givenWord;
    }

    /**
     * translated word value
     */
    private String translatedWord;

    /**
     * Get the value of translatedWord
     *
     * @return the value of translatedWord
     */
    public String getTranslatedWord() {
        return translatedWord;
    }

    /**
     * Set the value of translatedWord
     *
     * @param translatedWord new value of translatedWord
     */
    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
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
        if (!(object instanceof TranslationEntity)) {
            return false;
        }
        TranslationEntity other = (TranslationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.math.TranslationEntity[ id=" + id + " ]";
    }

}
