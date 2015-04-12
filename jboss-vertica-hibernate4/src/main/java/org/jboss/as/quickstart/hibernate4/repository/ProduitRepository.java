package org.jboss.as.quickstart.hibernate4.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.jboss.as.quickstart.hibernate4.domain.Produit;

@ApplicationScoped
public class ProduitRepository {

    @Inject
    private EntityManager em;

    @Inject
    private Event<Produit> produitEventSrc;

    public Produit findById(Long id) {
	return em.find(Produit.class, id);
    }

    public List<Produit> findAll() {
	return (List<Produit>) em.createQuery("SELECT p FROM Produit p",
		Produit.class).getResultList();
    }

    public void save(Produit produit) {

	Session session = (Session) em.getDelegate();
	session.merge(produit);
	produitEventSrc.fire(produit);

    }

    public void delete(Long id) {
	Session session = (Session) em.getDelegate();

	Produit produit = findById(id);
	session.delete(produit);

	produitEventSrc.fire(produit);
    }
}
