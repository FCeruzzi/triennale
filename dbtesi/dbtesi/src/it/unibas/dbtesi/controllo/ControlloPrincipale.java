/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.controllo;

import it.unibas.dbtesi.Applicazione;
import it.unibas.dbtesi.Costanti;
import it.unibas.dbtesi.modello.ComparatoreData;
import it.unibas.dbtesi.modello.Posizione;
import it.unibas.dbtesi.modello.Struttura;
import it.unibas.dbtesi.persistenza.DAOException;
import it.unibas.dbtesi.persistenza.DAOUtilHibernate;
import it.unibas.dbtesi.vista.VistaPrincipale;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SHORT_DESCRIPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class ControlloPrincipale {

    private final static Logger logger = LoggerFactory.getLogger(ControlloPrincipale.class);

    private AzioneCaricaImmagine azioneCaricaImmagine = new AzioneCaricaImmagine();
    private AzioneMatlabSobel azioneMatlabSobel = new AzioneMatlabSobel();
    private AzioneMatlabIndividuaStruttura azioneMatlabIndividuaStruttura = new AzioneMatlabIndividuaStruttura();
    private AzioneMostraImmagineSelezionata azioneMostraImmagineSelezionata = new AzioneMostraImmagineSelezionata();
    private AzioneCaricaStruttureDatabase azioneCaricaStruttureDatabase = new AzioneCaricaStruttureDatabase();
    private AzioneCaricaStrutturaSingolaDatabase azioneCaricaStrutturaSingolaDatabase = new AzioneCaricaStrutturaSingolaDatabase();
    private AzioneScaricaDatabase azioneScaricaDatabase = new AzioneScaricaDatabase();
    private AzioneChangeDetection azioneChangeDetection = new AzioneChangeDetection();

    private class AzioneChangeDetection extends AbstractAction {

        public AzioneChangeDetection() {
            putValue(NAME, "Change detection");
            this.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO da implementare
        }
    }

    private class AzioneScaricaDatabase extends AbstractAction {

        public AzioneScaricaDatabase() {
            putValue(NAME, "Query Footprints Database");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            int query = Applicazione.getInstance().getVistaPrincipale().getIndiceCombo();
            String queryStringaIniziale = Applicazione.getInstance().getVistaPrincipale().getQuery();
            String queryStringa = queryStringaIniziale.trim().toLowerCase();
            if (isValida(queryStringa)) {
                try {
                    DAOUtilHibernate.beginTransaction();
                    List<Struttura> listaStrutture = new ArrayList<>();
                    String query = Applicazione.getInstance().getVistaPrincipale().getQuery();
                    String secondoMembro = "";
                    String primoMembro = "";
                    double valoreSecondoMembroDouble = 0.;
                    int valoreSecondoMembroInt = 0;
                    String valoreSecondoMembroString = "";
                    if (query.indexOf('<') != -1) {
                        primoMembro = query.substring(0, query.indexOf('<'));
                        secondoMembro = query.substring(query.indexOf('<') + 1);
                    } else if (query.indexOf('>') != -1) {
                        primoMembro = query.substring(0, query.indexOf('>'));
                        secondoMembro = query.substring(query.indexOf('>') + 1);
                    } else {//if (query.indexOf('=') != -1) 
                        primoMembro = query.substring(0, query.indexOf('='));
                        logger.info("primomembro" + primoMembro);
                        secondoMembro = query.substring(query.indexOf('=') + 1);
                        logger.info("secondomembro" + secondoMembro);
                    }
                    switch (primoMembro.toLowerCase().trim()) {
                        case "nome":
                            valoreSecondoMembroString = secondoMembro;
                            listaStrutture = Applicazione.getInstance().getDaoStruttura().findByNome(valoreSecondoMembroString);
                            break;
                        case "area":
                            valoreSecondoMembroDouble = Double.parseDouble(secondoMembro);
                            listaStrutture = Applicazione.getInstance().getDaoStruttura().findByArea(valoreSecondoMembroDouble);
                            break;
                        case "posizione":
                            valoreSecondoMembroString = secondoMembro;
                            String xaltoStringa = secondoMembro.substring(1, secondoMembro.indexOf(','));
                            secondoMembro = secondoMembro.substring(secondoMembro.indexOf(','));
                            String yaltoStringa = secondoMembro.substring(1, secondoMembro.indexOf(')'));
                            secondoMembro = secondoMembro.substring(secondoMembro.indexOf('('));
                            String xbassoStringa = secondoMembro.substring(1, secondoMembro.indexOf(','));
                            secondoMembro = secondoMembro.substring(secondoMembro.indexOf(','));
                            String ybassoStringa = secondoMembro.substring(1, secondoMembro.indexOf(')'));
                            logger.info("xaltoStringa" + xaltoStringa);
                            logger.info("yaltoStringa" + yaltoStringa);
                            logger.info("xbassoStringa" + xbassoStringa);
                            logger.info("ybassoStringa" + ybassoStringa);
                            int xalto = Integer.parseInt(xaltoStringa);
                            int yalto = Integer.parseInt(yaltoStringa);
                            int xbasso = Integer.parseInt(xbassoStringa);
                            int ybasso = Integer.parseInt(ybassoStringa);
                            int rangeMassimo = 15;
                            if (valoreSecondoMembroString.indexOf("_") != -1) {
                                rangeMassimo = Integer.parseInt(valoreSecondoMembroString.substring(valoreSecondoMembroString.indexOf("_") + 1));
                                logger.info(rangeMassimo + " rangeMassimo");
                            }
                            List<Posizione> listaPosizioni = new ArrayList<>();
                            for (int j = -rangeMassimo; j < rangeMassimo; j++) {
                                for (int i = -rangeMassimo; i < rangeMassimo; i++) {
                                    int xaltoTmp = xalto + i;
                                    int yaltoTmp = yalto + j;
                                    int xbassoTmp = xbasso + i;
                                    int ybassoTmp = ybasso + j;
                                    logger.info("xaltoTmp " + xaltoTmp);
                                    logger.info("xbassoTmp  " + xbassoTmp);
                                    logger.info("for i: " + i + " j: " + j);
                                    List<Posizione> listaPosizioniTmp = Applicazione.getInstance().getDaoPosizione().findByCoordinate(xaltoTmp, yaltoTmp, xbassoTmp, ybassoTmp);
                                    logger.info("listaPosizioniTmp " + listaPosizioniTmp.size());
                                    listaPosizioni.addAll(listaPosizioniTmp);
                                }
                            }
                            listaPosizioni = eliminaDuplicati(listaPosizioni);
                            logger.info("dimensione listaPosizioni" + listaPosizioni.size());
                            for (Posizione posizione : listaPosizioni) {
                                logger.info("id " + posizione.getId());
                                List<Struttura> listaStruttureTmp = Applicazione.getInstance().getDaoStruttura().findStrutturaByPosizione(posizione.getId());
                                listaStrutture.addAll(listaStruttureTmp);
                            }
                            //TODO da implementare
                            break;
                        case "contrasto":
                            valoreSecondoMembroInt = Integer.parseInt(secondoMembro);
                            listaStrutture = Applicazione.getInstance().getDaoStruttura().findByContrasto(valoreSecondoMembroInt);
                            break;
                        case "lunghezza":
                            valoreSecondoMembroDouble = Double.parseDouble(secondoMembro);
                            listaStrutture = Applicazione.getInstance().getDaoStruttura().findByLunghezza(valoreSecondoMembroDouble);
                            break;
                        case "falsedetectionprobability":
                            valoreSecondoMembroInt = Integer.parseInt(secondoMembro);
                            listaStrutture = Applicazione.getInstance().getDaoStruttura().findByFDP(valoreSecondoMembroInt);
                            break;
                        case "anno":
                            valoreSecondoMembroInt = Integer.parseInt(secondoMembro);
                            logger.info(secondoMembro);
                            listaStrutture = Applicazione.getInstance().getDaoStruttura().findByData(valoreSecondoMembroInt);
                            break;
                        default:
                            Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore inaspettato nell'import della base di dati");
                            break;
                    }
                    Collections.sort(listaStrutture, new ComparatoreData());
                    Applicazione.getInstance().getModello().putBean(Costanti.LISTA_STRUTTURE_DF, listaStrutture);
                    Applicazione.getInstance().getVistaPrincipale().aggiornaTabella();
                    DAOUtilHibernate.commit();
                    Applicazione.getInstance().getVistaPrincipale().visualizzaMessaggio("Query eseguita");
                } catch (DAOException ex) {
                    DAOUtilHibernate.rollback();
                    Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore " + ex.getLocalizedMessage());
                } catch (NumberFormatException nfe) {
                    DAOUtilHibernate.rollback();
                    Applicazione.getInstance().getVistaPrincipale().visualizzaMessaggio("Valori scorretti nella query");
                }
            }
        }

        private boolean isValida(String queryStringa) {
            String errori = convalida(queryStringa);
            if (!errori.isEmpty()) {
                Applicazione.getInstance().getVistaPrincipale().visualizzaErrori(errori);
                return false;
            }
            return true;
        }

        private String convalida(String query) {
            StringBuilder sb = new StringBuilder();
            if (query.equals("")) {
                sb.append("Query vuota");
                //TODO da modificare che ritorna tutto
            }
            if (query.indexOf('<') == -1 && query.indexOf('>') == -1 && query.indexOf('=') == -1) {
                sb.append("Non hai inserito un segno di confronto");
            }
            return sb.toString();
        }

//        private boolean isNotPresente(List<Posizione> listaPosizioniTmp, List<Posizione> listaPosizioni) {
//            for (Posizione posizione : listaPosizioniTmp) {
//                if (listaPosizioni.contains(posizione)) {
//                    return false;
//                }
//            }
//            return true;
//        }
        private List<Posizione> eliminaDuplicati(List<Posizione> listaPosizioni) {
            List<Posizione> listaPosizioneFinale = new ArrayList();
            for (Posizione posizione : listaPosizioni) {
                if (!listaPosizioneFinale.contains(posizione)) {
                    listaPosizioneFinale.add(posizione);
                }
            }
            return listaPosizioneFinale;
        }

    }

    private class AzioneCaricaStrutturaSingolaDatabase extends AbstractAction {

        public AzioneCaricaStrutturaSingolaDatabase() {
            putValue(NAME, "Carica la struttura sul database");
            this.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int numeroRiga = Applicazione.getInstance().getVistaPrincipale().getRigaSelezionata();
            if (numeroRiga != -1) {
                try {
                    DAOUtilHibernate.beginTransaction();
                    List<Struttura> listaStrutture = (List<Struttura>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_STRUTTURE);
                    Struttura s = listaStrutture.get(numeroRiga);
                    Posizione p = s.getPosizione();
                    Applicazione.getInstance().getDaoStruttura().makePersistent(s); // Basta solo questa istruzione se c'è Cascade.ALL nel mapping tra giocatore e carta
                    Applicazione.getInstance().getDaoPosizione().makePersistent(p);
                    DAOUtilHibernate.commit();
                } catch (DAOException ex) {
                    DAOUtilHibernate.rollback();
                    Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore nel caricamento della struttura " + ex.getLocalizedMessage());
                }
            } else {
                Applicazione.getInstance().getVistaPrincipale().visualizzaMessaggio("riga non selezionata");
            }
            Applicazione.getInstance().getVistaPrincipale().visualizzaMessaggio("Caricamento effettuato");
        }
    }

    private class AzioneCaricaStruttureDatabase extends AbstractAction {

        public AzioneCaricaStruttureDatabase() {
            putValue(NAME, "Carica tutte le strutture sul database");
            this.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO da implementare
        }
    }

    private class AzioneMostraImmagineSelezionata extends AbstractAction {

        public AzioneMostraImmagineSelezionata() {
            putValue(NAME, "Mostra struttura selezionata");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VistaPrincipale vistaPrincipale = Applicazione.getInstance().getVistaPrincipale();
            int rigaSelezionata = Applicazione.getInstance().getVistaPrincipale().getRigaSelezionata();
            if (rigaSelezionata == -1) {
                Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Non hai selezionato alcuna riga");
            } else {
                logger.info("riga selezionata" + rigaSelezionata);
                BufferedImage image = Applicazione.getInstance().getDaoCarica().caricaStruttura(rigaSelezionata);
                vistaPrincipale.getNuovaImmagineDialog(image);
            }
        }
    }

    private class AzioneMatlabIndividuaStruttura extends AbstractAction {

        public AzioneMatlabIndividuaStruttura() {
            this.putValue(NAME, "Individua struttura");
            this.putValue(SHORT_DESCRIPTION, "E' richiesto prima l'esecuzione di Sobel");
            this.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizza();
        }
    }

    private class AzioneMatlabSobel extends AbstractAction {

        public AzioneMatlabSobel() {
            this.putValue(NAME, "Sobel");
            this.putValue(SHORT_DESCRIPTION, "E' richiesto prima il caricamento dell'immagine");
            this.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            Applicazione.getInstance().getVistaPrincipale().aggiornaStato("sobel");
            Applicazione.getInstance().getDaoMatlab().eseguiSobel();
            Applicazione.getInstance().getDaoCarica().caricaSobel();
            BufferedImage image = (BufferedImage) Applicazione.getInstance().getModello().getBean(Costanti.IMMAGINE_SCELTA);
            Applicazione.getInstance().getVistaPrincipale().getNuovaImmagineDialog(image);
            Applicazione.getInstance().getVistaFrame().attivaIndividuaStruttura();
//            Applicazione.getInstance().getVistaImmagine().visualizza();
//            Applicazione.getInstance().getVistaImmagine().mostraImmagine(image);
        }
    }

    private class AzioneCaricaImmagine extends AbstractAction {

        public AzioneCaricaImmagine() {
            this.putValue(NAME, "Carica immagine");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int anno = Applicazione.getInstance().getVistaPrincipale().getAnno();
            if (anno != -1) {
                Applicazione.getInstance().getModello().putBean(Costanti.ANNO, anno);
                Applicazione.getInstance().getDaoCarica().caricaTiff();
                BufferedImage image = (BufferedImage) Applicazione.getInstance().getModello().getBean(Costanti.IMMAGINE_SCELTA);
                Applicazione.getInstance().getVistaPrincipale().getNuovaImmagineDialog(image);
                Applicazione.getInstance().getVistaFrame().attivaSobel();
            }
        }

    }

    public AzioneCaricaStruttureDatabase getAzioneCaricaStruttureDatabase() {
        return azioneCaricaStruttureDatabase;
    }

    public AzioneCaricaStrutturaSingolaDatabase getAzioneCaricaStrutturaSingolaDatabase() {
        return azioneCaricaStrutturaSingolaDatabase;
    }

    public AzioneCaricaImmagine getAzioneCaricaImmagine() {
        return azioneCaricaImmagine;
    }

    public AzioneMatlabSobel getAzioneMatlabSobel() {
        return azioneMatlabSobel;
    }

    public AzioneMatlabIndividuaStruttura getAzioneMatlabIndividuaStruttura() {
        return azioneMatlabIndividuaStruttura;
    }

    public AzioneMostraImmagineSelezionata getAzioneMostraImmagineSelezionata() {
        return azioneMostraImmagineSelezionata;
    }

    public AzioneScaricaDatabase getAzioneScaricaDatabase() {
        return azioneScaricaDatabase;
    }

    public AzioneChangeDetection getAzioneChangeDetection() {
        return azioneChangeDetection;
    }

}
