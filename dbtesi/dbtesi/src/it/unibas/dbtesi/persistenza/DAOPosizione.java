/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import it.unibas.dbtesi.modello.Posizione;
import it.unibas.dbtesi.modello.Struttura;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class DAOPosizione extends DAOGenericoHibernate<Posizione> implements IDAOPosizione {

    private final static Logger logger = LoggerFactory.getLogger(DAOCarica.class);

    public DAOPosizione(Class<Posizione> persistentClass) {
        super(persistentClass);
    }

    @Override
    public List<Posizione> findByCoordinate(int xalto, int yalto, int xbasso, int ybasso) throws DAOException {
        logger.info(xalto + "xalto findByCoordinate");
        List<Posizione> listaPosizioni = findByCriteria(Restrictions.eq("mini", xalto));
        listaPosizioni.addAll(findByCriteria(Restrictions.eq("minj", yalto)));
        listaPosizioni.addAll(findByCriteria(Restrictions.eq("maxi", xbasso)));
        listaPosizioni.addAll(findByCriteria(Restrictions.eq("maxj", ybasso)));
        logger.info("dimensioni listaPosizioni findByCoordinate " + listaPosizioni.size());
        return listaPosizioni;
    }

}
