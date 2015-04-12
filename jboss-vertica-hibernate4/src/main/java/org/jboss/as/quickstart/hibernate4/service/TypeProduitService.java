package org.jboss.as.quickstart.hibernate4.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.jboss.as.quickstart.hibernate4.domain.TypeProduit;
import org.jboss.as.quickstart.hibernate4.repository.TypeProduitRepository;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class TypeProduitService {

    @Inject
    private TypeProduitRepository typeProduitRepository;

    @Inject
    private EntityManager em;

    @Inject
    private Event<TypeProduit> typeProduitEventSrc;

    public TypeProduit create(TypeProduit typeProduit) throws Exception {
	Session session = (Session) em.getDelegate();
	session.persist(typeProduit);
	typeProduitEventSrc.fire(typeProduit);
	return typeProduit;
    }

    public boolean save(TypeProduit typeProduit) {
	typeProduitRepository.save(typeProduit);
	typeProduitEventSrc.fire(typeProduit);

	return true;
    }

    public boolean delete(Long id) {
	typeProduitRepository.delete(id);
	return true;
    }

    public TypeProduit findById(Long id) {

	return typeProduitRepository.findById(id);
    }

    public List<TypeProduit> findAll() {
	return typeProduitRepository.findAll();
    }
}
