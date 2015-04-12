package org.jboss.as.quickstart.hibernate4.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A ProduitSimple.
 */
@Entity
@Table(name = "T_COMMANDE")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "commande", fetch = FetchType.EAGER)
    private Set<CommandeLigne> commandeLignes;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Set<CommandeLigne> getCommandeLignes() {
	return commandeLignes;
    }

    public void setCommandeLignes(Set<CommandeLigne> commandeLignes) {
	this.commandeLignes = commandeLignes;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}

	Commande produitSimple = (Commande) o;

	if (!Objects.equals(id, produitSimple.id))
	    return false;

	return true;
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(id);
    }

    @Override
    public String toString() {
	return "ProduitSimple{" + "id=" + id + '}';
    }
}
