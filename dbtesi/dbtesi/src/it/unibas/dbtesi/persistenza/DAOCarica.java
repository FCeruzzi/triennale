/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import it.unibas.dbtesi.Applicazione;
import it.unibas.dbtesi.Costanti;
import it.unibas.dbtesi.modello.ComparatoreFile;
import it.unibas.dbtesi.modello.Immagine;
import it.unibas.dbtesi.modello.Posizione;
import it.unibas.dbtesi.modello.Struttura;
import it.unibas.dbtesi.vista.VistaPrincipale;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class DAOCarica implements IDAOCarica {

    private final static Logger logger = LoggerFactory.getLogger(DAOCarica.class);

    @Override
    public BufferedImage caricaTiff() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new TiffFileFilter());
        VistaPrincipale vistaPrincipale = Applicazione.getInstance().getVistaPrincipale();
        int n = fileChooser.showOpenDialog(vistaPrincipale.getParent());
        File f = fileChooser.getSelectedFile(); //nel caso di un file
//        File f = fileChooser.getSelectedFile();//nel caso di più file
        if (n == JFileChooser.ERROR_OPTION) {
            Applicazione.getInstance().getVistaFrame().visualizzaErrori("File non caricato correttamente");
        }
        BufferedImage image = new BufferedImage(1, 1, Integer.BYTES);//TODO da controllare
        try {
            image = ImageIO.read(f);
            Applicazione.getInstance().getModello().putBean(Costanti.IMMAGINE_SCELTA, image);
            Applicazione.getInstance().getModello().putBean(Costanti.FILE_SCELTO, f);
        } catch (IllegalArgumentException | IOException e) {
            vistaPrincipale.visualizzaErrori("Errore nel caricamento dell'immagine");
        }
        return image;
    }

    @Override
    public void caricaSobel() {
        try {
            logger.info("prima di ftmp");
            File fTmp = (File) Applicazione.getInstance().getModello().getBean(Costanti.FILE_SCELTO);
//            String path = fTmp.getPath().replace(fTmp.getName(), "Sobel.tiff");
            String path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
            path = path + "\\Sobel.tiff";
            File file = new File(path);
            while (!(file.exists() && !file.isDirectory())) {
                Thread.sleep(1500);
                file = new File(path);
            }
            BufferedImage image = ImageIO.read(file);
            logger.info("nome file" + file.getName());
            Applicazione.getInstance().getModello().putBean(Costanti.IMMAGINE_SCELTA, image);
            Applicazione.getInstance().getModello().putBean(Costanti.FILE_SCELTO, file);
        } catch (IOException ex) {
            Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("IOException");
        } catch (InterruptedException ex) {
            logger.info("aspetto");
        }
    }

    @Override
    public List<Struttura> caricaIndividuaStruttura() {
        List<Struttura> listaStrutture = new ArrayList<>();
        List<Posizione> listaPosizioniFinali = new ArrayList<>();
        List<File> listaFileFinale = new ArrayList<>();
        boolean isStruttura = (boolean) Applicazione.getInstance().getModello().getBean(Costanti.IS_STRUTTURA);
        String prePath = "";
        if (isStruttura) {
//            prePath = "C:\\Users\\Ceruz\\Desktop\\Esempio\\risultatiFiltrati";
            prePath = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIFILTRATI);
        } else {
//            prePath = "C:\\Users\\Ceruz\\Desktop\\Esempio\\risultatiSoloStrutture";
            prePath = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATISOLOSTRUTTURE);
        }
        File cartella = new File(prePath);
        logger.info("prima del try");
        try {
            int numeroStrutturetmp = 1;
            int numeroStrutture = cartella.listFiles().length;
            while (numeroStrutture != numeroStrutturetmp) {
                numeroStrutturetmp = numeroStrutture;
                Thread.sleep(3500);
                numeroStrutture = cartella.listFiles().length;
            }
            logger.info("numero file attuali" + numeroStrutture);
            int tmp = 0;
            logger.info("prima del while");
            List<Double> listaContrasti = (List<Double>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_CONTRASTI);
            List<Double> listaAree = (List<Double>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_AREE);
            List<Double> listaLunghezzaMedia = (List<Double>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_LUNGHEZZA_MEDIA);
            List<Double> listaProbabilitaErrore = (List<Double>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_PROBABILITA_ERRORE);
            List<Posizione> listaPosizioni = (List<Posizione>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_POSIZIONI);
            File[] listaFile = cartella.listFiles();
            Arrays.sort(listaFile, new ComparatoreFile());
            int numeroStruttura = 0;
            while (tmp != numeroStrutture) {
                logger.info("dentro il while");
                File file = listaFile[tmp];
                logger.info("dopo image");
                Struttura s = new Struttura();
                logger.info("nome file " + file.getName());
                String nomeFile = file.getName();
                nomeFile = trovaNomeFile(nomeFile);
                logger.info("nome file " + nomeFile);
                numeroStruttura = Integer.parseInt(nomeFile);
                logger.info("numeroStruttura " + numeroStruttura);
                Immagine i = new Immagine(nomeFile);
                s.setImmagine(i);
                double cr = listaContrasti.get(numeroStruttura - 1);
                s.setContrasto(cr);
                logger.info(cr + "");
                double contrastoMassimo = Applicazione.getInstance().getVistaIndividuaStruttura().getValoreSliderContrasto();
                if ((isStruttura && cr > (contrastoMassimo / 100)) || !isStruttura) {
                    double area = listaAree.get(tmp);
                    double lunghezzaMedia = listaLunghezzaMedia.get(tmp);
                    double probabilitaErrore = listaProbabilitaErrore.get(tmp);
                    Posizione p = listaPosizioni.get(tmp);
                    int mini = p.getMini();
                    int minj = p.getMinj();
                    int maxi = p.getMaxi();
                    int maxj = p.getMaxj();
                    int anno = (int) Applicazione.getInstance().getModello().getBean(Costanti.ANNO);
                    s.setLunghezzaMedia(lunghezzaMedia);
                    s.setFalseDetectionProbability(probabilitaErrore);
                    s.setArea(area);
                    s.setPosizione(p);
                    s.setAnno(anno);
//                    s.setPosizione(p.getId());
                    p.setMaxi(maxi);
                    p.setMaxj(maxj);
                    p.setMini(mini);
                    p.setMinj(minj);
                    p.setStruttura(s);
//                    p.setStruttura(s.getId);
                    listaStrutture.add(s);
                    listaPosizioniFinali.add(p);
                    listaFileFinale.add(file);
                } else {
                    file.delete();
                }
                tmp++;
                logger.info("dopo addStruttura");
                logger.info(numeroStrutture + "");
                logger.info(tmp + " e " + numeroStrutture);
            }
            logger.info("dimensione lista file" + listaFile.length);
            File[] listaFileFinaleArray = new File[listaFileFinale.size()];
            listaFileFinale.toArray(listaFileFinaleArray);
            Arrays.sort(listaFileFinaleArray, new ComparatoreFile());
            Applicazione.getInstance().getModello().putBean(Costanti.LISTA_FILE, listaFileFinaleArray);
        } catch (IllegalArgumentException | InterruptedException | NullPointerException ex) {
            logger.info("Errore inaspettato");
        }
        logger.info(listaStrutture.size() + " strutture");
        return listaStrutture;

    }

    public String trovaNomeFile(String nomeFile) {
        nomeFile = nomeFile.replace(".tiff", "");
        logger.info("nome file " + nomeFile);
        if (nomeFile.contains("francobolloML")) {
            nomeFile = nomeFile.replace("francobolloML", "");
            logger.info("nome file " + nomeFile);
        }
        nomeFile = nomeFile.trim();
        return nomeFile;
    }

    @Override
    public BufferedImage caricaStruttura(int numeroStruttura) {
        File[] listaFile = (File[]) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_FILE);
        File file = listaFile[numeroStruttura];
//        String path = file.getPath();
////        String path = "C:\\Users\\Ceruz\\Desktop\\Esempio\\risultatiSoloStrutture".concat("\\francobolloML" + numeroStruttura + ".tiff");
//        File f = new File(path);//nel caso di un file
        BufferedImage image = new BufferedImage(1, 1, Integer.BYTES);//TODO da controllare
        try {
            image = ImageIO.read(file);
//            image = ImageIO.read(f);
            Applicazione.getInstance().getModello().putBean(Costanti.IMMAGINE_SCELTA, image);
            Applicazione.getInstance().getModello().putBean(Costanti.FILE_SCELTO, file);
            //Applicazione.getInstance().getModello().putBean(Costanti.FILE_SCELTO, f);
        } catch (IOException e) {
            Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore nel caricamento dell'immagine");
        }
        return image;
    }

    private static class TiffFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String fname = f.getName().toLowerCase();
            return fname.endsWith("tiff") || fname.endsWith("tif");
        }

        @Override
        public String getDescription() {
            return "*.tiff, *.tif";
        }
    }

    @Override
    public void cancellaCartelle() {
        String lavoro = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
        String risultati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATI);
        String risultatiSoloStrutture = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATISOLOSTRUTTURE);
        String risultatiFiltrati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIFILTRATI);
        String risultatiEdgeOttimizzati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIEDGEOTTIMIZZATI);
        File lavoroFile = new File(lavoro);
        File risultatiFile = new File(risultati);
        File risultatiSoloStruttureFile = new File(risultatiSoloStrutture);
        File risultatiFiltratiFile = new File(risultatiFiltrati);
        File risultatiEdgeOttimizzatiFile = new File(risultatiEdgeOttimizzati);
        try {
            cancellaFile(risultatiFile);
            risultatiFile.delete();
            cancellaFile(risultatiSoloStruttureFile);
            risultatiSoloStruttureFile.delete();
            cancellaFile(risultatiFiltratiFile);
            risultatiFiltratiFile.delete();//TODO non la cancella manco a pagarla
            cancellaFile(risultatiEdgeOttimizzatiFile);
            risultatiEdgeOttimizzatiFile.delete();
            cancellaFile(lavoroFile);
            lavoroFile.delete();
        } catch (IOException ex) {
            System.out.println("non va");
        }
    }

    private void cancellaFile(File lavoroFile) throws IOException {
        if (lavoroFile != null && lavoroFile.listFiles().length != 0) {
            for (File file : lavoroFile.listFiles()) {
                logger.info("numerofile" + lavoroFile.listFiles().length);
                file.delete();
            }
        }

    }

}
