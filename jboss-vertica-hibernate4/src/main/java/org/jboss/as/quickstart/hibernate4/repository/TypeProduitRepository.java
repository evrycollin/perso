package org.jboss.as.quickstart.hibernate4.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.as.quickstart.hibernate4.domain.TypeProduit;

@ApplicationScoped
public class TypeProduitRepository {

    @Inject
    private EntityManager em;

    @Inject
    private Event<TypeProduit> typeProduitEventSrc;

    public TypeProduit findById(Long id) {
	return em.find(TypeProduit.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<TypeProduit> findAll() {
	Session session = (Session) em.getDelegate();
	Criteria cb = session.createCriteria(TypeProduit.class);
	return (List<TypeProduit>) cb.list();
    }

    public void save(TypeProduit typeProduit) {

	Session session = (Session) em.getDelegate();
	session.merge(typeProduit);
	typeProduitEventSrc.fire(typeProduit);

    }

    public void delete(Long id) {
	Session session = (Session) em.getDelegate();

	TypeProduit typeProduit = findById(id);
	session.delete(typeProduit);

	typeProduitEventSrc.fire(typeProduit);
    }
}
