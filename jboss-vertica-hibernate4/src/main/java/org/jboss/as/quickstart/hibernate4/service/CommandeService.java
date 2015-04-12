package org.jboss.as.quickstart.hibernate4.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.jboss.as.quickstart.hibernate4.domain.Commande;
import org.jboss.as.quickstart.hibernate4.repository.CommandeRepository;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class CommandeService {

    @Inject
    private CommandeRepository commandeRepository;

    @Inject
    private EntityManager em;

    public Commande create(Commande commande) throws Exception {
	Session session = (Session) em.getDelegate();
	session.persist(commande);
	return commande;
    }

    public boolean save(Commande commande) {
	commandeRepository.save(commande);
	return true;
    }

    public boolean delete(Long id) {
	commandeRepository.delete(id);
	return true;
    }

    public Commande findById(Long id) {

	return commandeRepository.findById(id);
    }

    public List<Commande> findAll() {
	return commandeRepository.findAll();
    }
}
