package org.jboss.as.quickstart.hibernate4.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.jboss.as.quickstart.hibernate4.domain.Produit;
import org.jboss.as.quickstart.hibernate4.repository.ProduitRepository;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ProduitService {

    @Inject
    private ProduitRepository produitRepository;

    @Inject
    private EntityManager em;

    public Produit create(Produit produit) throws Exception {
	Session session = (Session) em.getDelegate();
	session.persist(produit);
	return produit;
    }

    public boolean save(Produit produit) {
	produitRepository.save(produit);
	return true;
    }

    public boolean delete(Long id) {
	produitRepository.delete(id);
	return true;
    }

    public Produit findById(Long id) {

	return produitRepository.findById(id);
    }

    public List<Produit> findAll() {
	return produitRepository.findAll();
    }
}
