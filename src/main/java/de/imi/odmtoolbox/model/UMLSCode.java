package de.imi.odmtoolbox.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class represents a semantic code from the Unified Medical Language
 * System.
 *
 */
@Entity
@Table(name = "umlscode")
public class UMLSCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;

    public UMLSCode() {
    }

    public UMLSCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UMLSCode)) {
            return false;
        }
        final UMLSCode other = (UMLSCode) obj;
        return getCode().equals(other.getCode());
    }
}
