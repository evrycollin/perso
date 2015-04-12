package org.jboss.as.quickstart.hibernate4.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * A ProduitSimple.
 */
@Entity
@Table(name = "T_COMMANDE_LIGNE")
public class CommandeLigne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Commande commande;

    @ManyToOne(fetch = FetchType.EAGER)
    private Produit produit;

    @Column
    private int quantity;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @JsonIgnore
    public Commande getCommande() {
	return commande;
    }

    public void setCommande(Commande commande) {
	this.commande = commande;
    }

    public Produit getProduit() {
	return produit;
    }

    public void setProduit(Produit produit) {
	this.produit = produit;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}

	CommandeLigne produitSimple = (CommandeLigne) o;

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
