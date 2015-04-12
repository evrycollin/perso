package org.jboss.as.quickstart.hibernate4.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.as.quickstart.hibernate4.domain.Commande;

@ApplicationScoped
public class CommandeRepository {

    @Inject
    private EntityManager em;

    @Inject
    private Event<Commande> commandeEventSrc;

    public Commande findById(Long id) {
	return em.find(Commande.class, id);
    }

    public List<Commande> findAll() {
	return (List<Commande>) em.createQuery("SELECT c FROM Commande c",
		Commande.class).getResultList();
    }

    public void save(Commande commande) {

	Session session = (Session) em.getDelegate();
	session.merge(commande);
	commandeEventSrc.fire(commande);

    }

    public void delete(Long id) {
	Session session = (Session) em.getDelegate();

	Commande commande = findById(id);
	session.delete(commande);

	commandeEventSrc.fire(commande);
    }
}
