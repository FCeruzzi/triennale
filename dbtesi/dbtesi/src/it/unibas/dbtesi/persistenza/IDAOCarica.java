/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import it.unibas.dbtesi.modello.Struttura;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author Francesco
 */
public interface IDAOCarica {

    public BufferedImage caricaTiff();

    public void caricaSobel();

    public List<Struttura> caricaIndividuaStruttura();

    public BufferedImage caricaStruttura(int numeroStruttura);

    public void cancellaCartelle();
}
