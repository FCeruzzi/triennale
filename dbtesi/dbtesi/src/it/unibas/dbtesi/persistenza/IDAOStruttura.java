/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import it.unibas.dbtesi.modello.Struttura;
import java.util.List;

/**
 *
 * @author Francesco
 */
public interface IDAOStruttura extends IDAOGenerico<Struttura> {

    public List<Struttura> findByData(int anno) throws DAOException;

    public List<Struttura> findStrutturaByPosizione(Long id) throws DAOException;

    public List<Struttura> findByNome(String nome) throws DAOException;

    public List<Struttura> findByArea(double area) throws DAOException;

    public List<Struttura> findByContrasto(double contrasto) throws DAOException;

    public List<Struttura> findByLunghezza(double lunghezza) throws DAOException;

    public List<Struttura> findByFDP(int fdf) throws DAOException;

}
