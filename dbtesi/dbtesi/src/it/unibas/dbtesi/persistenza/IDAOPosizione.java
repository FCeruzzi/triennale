/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import it.unibas.dbtesi.modello.Posizione;
import it.unibas.dbtesi.modello.Struttura;
import java.util.List;

/**
 *
 * @author Francesco
 */
public interface IDAOPosizione extends IDAOGenerico<Posizione> {

    public List<Posizione> findByCoordinate(int xalto,int yalto,int  xbasso,int ybasso) throws DAOException;
}
