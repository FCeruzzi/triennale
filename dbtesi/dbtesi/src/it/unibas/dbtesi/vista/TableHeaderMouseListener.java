/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.vista;

import it.unibas.dbtesi.Applicazione;
import it.unibas.dbtesi.Costanti;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ceruz
 */
public class TableHeaderMouseListener implements MouseListener {

    private final static Logger logger = LoggerFactory.getLogger(TableHeaderMouseListener.class);

    private JTable table;

    public TableHeaderMouseListener(JTable table) {
        this.table = table;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        Point point = event.getPoint();
        int column = table.columnAtPoint(point);
        String nomeCampo = table.getColumnName(column);
        logger.info("nome Campo " + nomeCampo);
//        Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo);
        switch (nomeCampo.trim().toLowerCase()) {
            case "nome":
                Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo + "=nome struttura");
                break;
            case "area":
                Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo + "=area struttura");
                break;
            case "posizione":
                Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo + "=(minX,minY),(maxX,maxY)_tolleranza");
                break;
            case "contrasto":
                Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo + "=contrasto struttura");
                break;
            case "lunghezza media":
                Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo + "=lunghezza media struttura");
                break;
            case "false detection probability":
                Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo + "=false detection probability struttura");
                break;
            case "anno":
                Applicazione.getInstance().getVistaPrincipale().setQuery(nomeCampo + "=anno");
                break;
            default:
//                Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore inaspettato");
                logger.info("errore");
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
    }

}
