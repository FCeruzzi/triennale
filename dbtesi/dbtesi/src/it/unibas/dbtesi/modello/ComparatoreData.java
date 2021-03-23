/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.modello;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author Francesco
 */
public class ComparatoreData implements Comparator<Struttura> {

    @Override
    public int compare(Struttura o1, Struttura o2) {
        return o2.getAnno() - o1.getAnno();
    }


}
