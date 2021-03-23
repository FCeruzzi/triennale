/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.controllo;

import it.unibas.dbtesi.Applicazione;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class ControlloFrame {

    private final static Logger logger = LoggerFactory.getLogger(ControlloFrame.class);

    private AzioneEsci azioneEsci = new AzioneEsci();

    private class AzioneEsci extends AbstractAction {

        public AzioneEsci() {
            this.putValue(NAME, "Esci");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Applicazione.getInstance().getDaoMatlab().chiudiSessioneMatlab();
            Applicazione.getInstance().getDaoCarica().cancellaCartelle();
            System.exit(0);
        }
    }

    public AzioneEsci getAzioneEsci() {
        return azioneEsci;
    }

    //    private class AzioneSalvaImmagine extends AbstractAction {
//
//        public AzioneSalvaImmagine() {
//            this.putValue(NAME, "Salva immagine");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            BufferedImage image = (BufferedImage) Applicazione.getInstance().getModello().getBean(Costanti.IMMAGINE_CARICATA);//TODO da cambiare con immagine salvata dopo che lo hai implementato
////            Applicazione.getInstance().getDaoCarica().salvaTiff(image);
//        }
//    }
    //    public AzioneSalvaImmagine getAzioneSalvaImmagine() {
//        return azioneSalvaImmagine;
//    }
}
