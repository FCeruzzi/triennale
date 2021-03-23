/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import it.unibas.dbtesi.modello.Struttura;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Francesco
 */
public class DAOStruttura extends DAOGenericoHibernate<Struttura> implements IDAOStruttura {

    public DAOStruttura(Class<Struttura> persistentClass) {
        super(persistentClass);
    }

    @Override
    public List<Struttura> findByData(int anno) throws DAOException {
        List<Struttura> listaStrutture = findByCriteria(Restrictions.eq("anno", anno));
        return listaStrutture;
    }

    @Override
    public List<Struttura> findStrutturaByPosizione(Long id) throws DAOException {
        return findByCriteria(Restrictions.eq("id", id));
    }

    @Override
    public List<Struttura> findByNome(String nome) throws DAOException {
        List<Struttura> listaStrutture = findByCriteria(Restrictions.eq("nome", nome));
        return listaStrutture;
    }

    @Override
    public List<Struttura> findByArea(double area) throws DAOException {
        List<Struttura> listaStrutture = findByCriteria(Restrictions.eq("area", area));
        return listaStrutture;
    }

    @Override
    public List<Struttura> findByContrasto(double contrasto) throws DAOException {
        List<Struttura> listaStrutture = findByCriteria(Restrictions.eq("contrasto", contrasto));
        return listaStrutture;
    }

    @Override
    public List<Struttura> findByLunghezza(double lunghezza) throws DAOException {
        List<Struttura> listaStrutture = findByCriteria(Restrictions.eq("lunghezza media", lunghezza));
        return listaStrutture;
    }

    @Override
    public List<Struttura> findByFDP(int fdf) throws DAOException {
        List<Struttura> listaStrutture = findByCriteria(Restrictions.eq("false detection probability", fdf));
        return listaStrutture;
    }

}
