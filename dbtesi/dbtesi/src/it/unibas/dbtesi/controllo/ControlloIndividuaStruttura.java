/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.controllo;

import it.unibas.dbtesi.Applicazione;
import it.unibas.dbtesi.Costanti;
import it.unibas.dbtesi.modello.Struttura;
import it.unibas.dbtesi.vista.VistaIndividuaStruttura;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.SwingWorker;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class ControlloIndividuaStruttura {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ControlloIndividuaStruttura.class);

    private AzioneCalcola azioneCalcola = new AzioneCalcola();
    private AzioneEsci azioneEsci = new AzioneEsci();

    private class AzioneEsci extends AbstractAction {

        public AzioneEsci() {
            putValue(NAME, "Indietro");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Applicazione.getInstance().getVistaIndividuaStruttura().nascondi();
        }
    }

    private class AzioneCalcola extends AbstractAction {

        public AzioneCalcola() {
            putValue(NAME, "Calcola");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VistaIndividuaStruttura vis = Applicazione.getInstance().getVistaIndividuaStruttura();
            SwingWorker myWorker = new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    Applicazione.getInstance().getVistaPrincipale().disattivaBottoneScelgiStruttura();
                    if (Applicazione.getInstance().getVistaIndividuaStruttura().isStruttura()) {
                        Applicazione.getInstance().getModello().putBean(Costanti.IS_STRUTTURA, true);
                    } else {
                        Applicazione.getInstance().getModello().putBean(Costanti.IS_STRUTTURA, false);
                    }
                    int i = 0;
                    int j = 0;
                    int dimStruttura = 0;
                    int dimBox = 0;
                    int dimArea = 0;
                    int contrasto = 0;
                    double soglia = 0;
                    try {
                        i = (int) Applicazione.getInstance().getModello().getBean(Costanti.PIXEL_SELEZIONATO_Y);
                        j = (int) Applicazione.getInstance().getModello().getBean(Costanti.PIXEL_SELEZIONATO_X);
                        dimBox = Integer.parseInt(Applicazione.getInstance().getVistaIndividuaStruttura().getDimBox());
                        dimStruttura = Integer.parseInt(Applicazione.getInstance().getVistaIndividuaStruttura().getDimStruttura());
                        dimArea = Integer.parseInt(Applicazione.getInstance().getVistaIndividuaStruttura().getDimArea());
                        if (Applicazione.getInstance().getVistaIndividuaStruttura().isStruttura()) {
                            contrasto = Applicazione.getInstance().getVistaIndividuaStruttura().getValoreSliderContrasto();
                            soglia = Applicazione.getInstance().getVistaIndividuaStruttura().getValoreSliderSogliaStruttura();
                        }
                        String convalida = convalida(dimBox, dimStruttura, dimArea, contrasto, soglia, Applicazione.getInstance().getVistaIndividuaStruttura().isStruttura());
                        List<Struttura> listaStrutture = new ArrayList<>();
                        if (!convalida.trim().equals("")) {
                            Applicazione.getInstance().getVistaPrincipale().visualizzaErrori(convalida);
                        } else {
                            Applicazione.getInstance().getDaoMatlab().eseguiMatlabIndividuaStruttura(i, j, dimBox, dimStruttura, dimArea);
                            vis.aggiornaBar("Individuo le strutture",true);
                            Applicazione.getInstance().getDaoMatlab().ottimizza(i, j, dimBox, dimStruttura, dimArea);
                            List<Double> listaContrasti = Applicazione.getInstance().getDaoMatlab().getContrasto(i, j, dimArea);
                            Applicazione.getInstance().getModello().putBean(Costanti.LISTA_CONTRASTI, listaContrasti);
                            logger.info("prima di isStruttura: soglia " + soglia);
                            Applicazione.getInstance().getDaoMatlab().eseguiIndividuaPalazzo(soglia);
                            vis.aggiornaBar("Calcolo i parametri",true);
                            logger.info(listaContrasti.size() + " contrasti");
                            Applicazione.getInstance().getDaoMatlab().calcolaParametri();
                            vis.aggiornaBar("Ottimizzo i risultati",true);
                            listaStrutture = Applicazione.getInstance().getDaoCarica().caricaIndividuaStruttura();
                            logger.info(listaStrutture.size() + " strutture");
                            Applicazione.getInstance().getModello().putBean(Costanti.LISTA_STRUTTURE, listaStrutture);
                            Applicazione.getInstance().getVistaPrincipale().aggiornaTabella();
                            Applicazione.getInstance().getVistaPrincipale().attivaBottoneScelgiStruttura();
                            Applicazione.getInstance().getVistaPrincipale().attivaBottoneCaricaStrutturaDatabase();
                            vis.aggiornaBar("Terminato",true);
                            Thread.sleep(3000);
                            vis.visualizzaProgressBar();
                        }
                    } catch (NullPointerException npe) {
                        logger.info("npe");
                        Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Non hai selezionato alcun pixel");
                    } catch (NumberFormatException nfe) {
                        logger.info("nfe");
                        Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Non hai riempito i campi correttamente");
                    }
                    return null;
                }
            };
            myWorker.execute();

        }

        private String convalida(int dimBox, int dimStruttura, int dimArea, int contrasto, double soglia, boolean isStruttura) {
            StringBuilder sb = new StringBuilder();
            logger.info(dimBox + "");
            logger.info(dimStruttura + "");
            if (dimBox <= 0) {
                sb.append("Valore dimensione box scorretta \n");
            }
            if (dimStruttura <= 0) {
                sb.append("Valore dimensione struttura scorretto \n");
            }
            if (dimArea <= 0) {
                sb.append("Valore dimensione area scorretto \n");
            }
            if (contrasto == 0 && isStruttura) {
                sb.append("Non hai selezionato alcun contrasto \n");
            }
            if (soglia == 0 && isStruttura) {
                sb.append("Non hai selezionato alcuna soglia \n");
            }
            return sb.toString();
        }
    }

    public AzioneCalcola getAzioneCalcola() {
        return azioneCalcola;
    }

    public AzioneEsci getAzioneEsci() {
        return azioneEsci;
    }

}
