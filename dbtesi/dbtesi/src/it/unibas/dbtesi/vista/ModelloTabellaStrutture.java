/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.vista;

import it.unibas.dbtesi.modello.ComparatoreData;
import it.unibas.dbtesi.modello.Struttura;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Francesco
 */
public class ModelloTabellaStrutture extends AbstractTableModel {

    private List<Struttura> listaStrutture = new ArrayList<>();

    public ModelloTabellaStrutture(List<Struttura> listaStrutture) {
        this.listaStrutture = listaStrutture;
    }

    @Override
    public int getRowCount() {
        if (listaStrutture == null) {
            return 0;
        }
        return listaStrutture.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Struttura struttura = listaStrutture.get(rowIndex);
        if (columnIndex == 0) {
            String nomeFile = struttura.getImmagine().getNome();
            nomeFile = nomeFile.replace(".tiff", "");
            if (nomeFile.contains("francobolloML")) {
                nomeFile = nomeFile.replace("francobolloML", "");
            }
            nomeFile = nomeFile.trim();
            return nomeFile;
        }
        if (columnIndex == 1) {
            return struttura.getArea();
        }
        if (columnIndex == 2) {
            return "(" + struttura.getPosizione().getMini() + "," + struttura.getPosizione().getMinj() + "),"
                    + "(" + struttura.getPosizione().getMaxi() + "," + struttura.getPosizione().getMaxj() + ") ";
        }
        if (columnIndex == 3) {
            return Math.round(struttura.getContrasto() * 100) + "%";
        }
        if (columnIndex == 4) {
            return Math.round(struttura.getLunghezzaMedia() * 100.0) / 100.0;
        }
        return Math.round(struttura.getFalseDetectionProbability() * 100) + "%";
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Nome";
        }
        if (columnIndex == 1) {
            return "Area";
        }
        if (columnIndex == 2) {
            return "Posizione";
        }
        if (columnIndex == 3) {
            return "Contrasto";
        }
        if (columnIndex == 4) {
            return "Lunghezza media";
        }
        return "False Detection Probability";
    }

    public void aggiornaContenuto() {
        super.fireTableDataChanged();
    }
}
