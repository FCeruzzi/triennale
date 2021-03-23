/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import com.mathworks.engine.*;
import it.unibas.dbtesi.Applicazione;
import it.unibas.dbtesi.Costanti;
import it.unibas.dbtesi.modello.ComparatoreFile;
import it.unibas.dbtesi.modello.Posizione;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class DAOMatlab implements IDAOMatlab {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(DAOMatlab.class);

    private Future<MatlabEngine> engFuture;
    private MatlabEngine eng;

    @Override
    public void inizializzaSessioneMatlab() {
        if (engFuture == null || engFuture.isCancelled()) {
            engFuture = MatlabEngine.startMatlabAsync();
        }
    }

    @Override
    public void chiudiSessioneMatlab() {
        if (eng != null) {
            try {
                eng.close();
            } catch (EngineException ex) {
                Applicazione.getInstance().getVistaFrame().visualizzaErrori("Errore nella chiusura della sessione del matlab engine");
            }
        }

    }

    @Override
    public void eseguiSobel() {
        logger.info("prima del try");
        try {
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            File f = (File) Applicazione.getInstance().getModello().getBean(Costanti.FILE_SCELTO);
            eng.putVariableAsync("file", f.getPath().toCharArray());
            String path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
            eng.putVariableAsync("path", path.toCharArray());
            eng.evalAsync("I = imread(file);"
                    + "S = edge(I,'Sobel',0.20,'both');"
                    + "imwrite(S,strcat(path,'Sobel.tiff'));"//TODO 0.2 può essere impostato da fuori, è la soglia
                    + "imwrite(I,strcat(path,'ImmagineMultilookTemporale.tiff'));");
            logger.info("fine");
        } catch (InterruptedException | ExecutionException ex) {
            Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore inaspettato");
        }
    }

    @Override
    public void eseguiMatlabIndividuaStruttura(int i, int j, int dimBox, int dimStruttura, int dimArea) {
        //qui c'è IndividuaStruttura, Taglia e IsStruttura
        try {
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            String pathSobel = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
            pathSobel = pathSobel + "\\Sobel.tiff";
            eng.putVariableAsync("pathSobel", pathSobel.toCharArray());
            String pathRisultati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATI);
            eng.putVariableAsync("pathRisultati", pathRisultati.toCharArray());
            String pathSCUT = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
            pathSCUT = pathSCUT + "\\SCUT.tiff";
            eng.putVariableAsync("pathSCUT", pathSCUT.toCharArray());
            eng.putVariableAsync("dimArea", dimArea);
            eng.putVariableAsync("a", i);
            eng.putVariableAsync("b", j);
            eng.putVariableAsync("dimStruttura", dimStruttura);
            eng.putVariableAsync("dimBox", dimBox);
            eseguiIndividuaStrutture();
        } catch (InterruptedException | ExecutionException | IllegalStateException ex) {
            Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore inaspettato");
        }
    }

    @Override
    public void ottimizza(int i, int j, int dimBox, int dimStruttura, int dimArea) {
        //qui c'è IndividuaStruttura, Taglia e IsStruttura
        try {
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            eng.putVariableAsync("dimArea", dimArea);
            eng.putVariableAsync("a", i);
            eng.putVariableAsync("b", j);
            eng.putVariableAsync("dimStruttura", dimStruttura);
            eng.putVariableAsync("dimBox", dimBox);
            String pathRisultatiEdgeOttimizzati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIEDGEOTTIMIZZATI);
            eng.putVariableAsync("pathRisultatiEdgeOttimizzati", pathRisultatiEdgeOttimizzati.toCharArray());
            String pathRisultatiSoloStrutture = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATISOLOSTRUTTURE);
            eng.putVariableAsync("pathRisultatiSoloStrutture", pathRisultatiSoloStrutture.toCharArray());
            String pathRisultati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATI);
            eng.putVariableAsync("pathRisultati", pathRisultati.toCharArray());
            String pathML = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
            pathML = pathML + "\\ImmagineMultilookTemporale.tiff";
            eng.putVariableAsync("pathML", pathML.toCharArray());
            ottimizza();
        } catch (InterruptedException | ExecutionException | IllegalStateException ex) {
            Applicazione.getInstance().getVistaPrincipale().visualizzaErrori("Errore inaspettato");
        }
    }

    @Override
    public void eseguiIndividuaPalazzo(double percentuale) {
        logger.info("dentro isStruttura");
        try {
            logger.info("dentro try");
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            percentuale = percentuale / 100;
            eng.putVariableAsync("percentuale", percentuale);
            logger.info("prima di eval" + percentuale);
            String pathRisultatiFiltrati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIFILTRATI);
            eng.putVariableAsync("pathRisultatiFiltrati", pathRisultatiFiltrati.toCharArray());
            String pathRisultatiSoloStrutture = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATISOLOSTRUTTURE);
            eng.putVariableAsync("pathRisultatiSoloStrutture", pathRisultatiSoloStrutture.toCharArray());
            eseguiIsStruttura();
        } catch (InterruptedException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("ie");
        } catch (ExecutionException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("exe");
        }
        logger.info("prima di uscire");
    }

    @Override
    public List<Double> getContrasto(int i, int j, int dimArea) {
        double cr = 0.0;
        List<Double> listaCr = new ArrayList<>();
        try {
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            int tmp = 0;
            int numeroStrutturetmp = 1;
            String pathRisultati = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATI);
            int numeroStrutture = new File(pathRisultati).listFiles().length;
            logger.info("numeroStrutture DAOMatlab getContrasto prima del while " + numeroStrutture);
            while (numeroStrutture != numeroStrutturetmp) {
                numeroStrutturetmp = numeroStrutture;
                Thread.sleep(1000);//3500
                numeroStrutture = new File(pathRisultati).listFiles().length;
            }
            logger.info("numeroStrutture" + numeroStrutture);
            logger.info("tmp prima del while DAOMatlab " + tmp);
            eng.putVariableAsync("pathRisultati", pathRisultati.toCharArray());
            String pathML = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
            pathML = pathML + "\\ImmagineMultilookTemporale.tiff";
            eng.putVariableAsync("pathML", pathML.toCharArray());
            while (numeroStrutture != tmp) {
                eng.putVariableAsync("c", tmp + 1);
                eng.putVariableAsync("m", i);
                eng.putVariableAsync("n", j);
                eng.putVariableAsync("dimArea", dimArea);
                getContrastoSingolo();
                Future<Double> future = eng.getVariableAsync("cr");
                while (!future.isDone()) {
                    Thread.sleep(100);
                }
                cr = future.get();
                listaCr.add(cr);
                tmp++;
            }
        } catch (InterruptedException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("ie");
        } catch (MatlabSyntaxException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("mse");
        } catch (CancellationException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("ce");
        } catch (EngineException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("ee");
        } catch (ExecutionException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("exe");
        }
        logger.info("numeroContrasti DAOMatlab " + listaCr.size());
        return listaCr;
    }

    @Override
    public void calcolaParametri() {
        calcolaParametriArea();
        calcoloParametriPosizione();
        calcolaParametriAltri();
    }

    private void calcolaParametriArea() {
        double area = 0.0;
        List<Double> listaAree = new ArrayList<>();
        try {
            logger.info("dentro try");
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            int tmp = 0;
            int numeroStrutturetmp = 1;
            boolean isStruttura = (boolean) Applicazione.getInstance().getModello().getBean(Costanti.IS_STRUTTURA);
            String path = "";
            if (isStruttura) {
                path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIFILTRATI);
            } else {
                path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATISOLOSTRUTTURE);
            }
            int numeroStrutture = new File(path).listFiles().length;
            logger.info("numeroStrutture DAOMatlab prima del while " + numeroStrutture);
            while (numeroStrutture != numeroStrutturetmp) {
                numeroStrutturetmp = numeroStrutture;
                Thread.sleep(1000);//3500
                numeroStrutture = new File(path).listFiles().length;
                logger.info("daomatlab numeroStrutture" + numeroStrutture);
            }
            logger.info("tmp prima del while DAOMatlab calcolaParametriPrimo " + tmp);
            File cartella = new File(path);
            File[] listaFile = cartella.listFiles();
            Arrays.sort(listaFile, new ComparatoreFile());
            logger.info("numeroFile DAOMatlab calcolaParametriPrimo" + listaFile.length);
            while (tmp != numeroStrutture) {
                String pathNome = path + "\\" + listaFile[tmp].getName();
                eng.putVariableAsync("pathNome", pathNome.toCharArray());
                String edgeStrutt = listaFile[tmp].getName().replace("francobolloML", "");
                String pathEdgeStrutt = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIEDGEOTTIMIZZATI);
                pathEdgeStrutt = pathEdgeStrutt + "\\" + edgeStrutt;
                eng.putVariableAsync("pathEdgeStrutt", pathEdgeStrutt.toCharArray());
                calcoloArea();
                Future<Double> future = eng.getVariableAsync("area");
                while (!future.isDone()) {
                    Thread.sleep(100);//100
                }
                area = future.get();
                listaAree.add(area);
                tmp++;
            }
        } catch (InterruptedException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("ie");
        } catch (ExecutionException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("exe");
        }
        logger.info("daomatlab.listaAree" + listaAree.size());
        Applicazione.getInstance().getModello().putBean(Costanti.LISTA_AREE, listaAree);
    }

    private void calcolaParametriAltri() {
        double lunghezzaMedia = 0.0;
        double probabilitaErrore = 0.0;
        List<Double> listaLunghezzaMedia = new ArrayList<>();
        List<Double> listaProbabilitaErrore = new ArrayList<>();
        try {
            logger.info("dentro try");
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            int tmp = 0;
            boolean isStruttura = (boolean) Applicazione.getInstance().getModello().getBean(Costanti.IS_STRUTTURA);
            String path = "";
            if (isStruttura) {
                path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIFILTRATI);
            } else {
                path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATISOLOSTRUTTURE);
            }
            File cartella = new File(path);
            File[] listaFile = cartella.listFiles();
            Arrays.sort(listaFile, new ComparatoreFile());
            int numeroStrutture = new File(path).listFiles().length;
            logger.info("numeroStrutture" + numeroStrutture);
            logger.info("tmp prima del while DAOMatlab " + tmp);
            while (tmp != numeroStrutture) {
                String pathNome = path + "\\" + listaFile[tmp].getName();
                eng.putVariableAsync("pathNome", pathNome.toCharArray());
                String edgeStrutt = listaFile[tmp].getName().replace("francobolloML", "");
                String pathEdgeStrutt = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATIEDGEOTTIMIZZATI);
                pathEdgeStrutt = pathEdgeStrutt + "\\" + edgeStrutt;
                eng.putVariableAsync("pathEdgeStrutt", pathEdgeStrutt.toCharArray());
                calcoloLunghezzaMediaProbabilitaErrore();
                Future<Double> future = eng.getVariableAsync("lunghezzaMediaReale");
                while (!future.isDone()) {
                    Thread.sleep(100);//100
                }
                lunghezzaMedia = future.get();
                listaLunghezzaMedia.add(lunghezzaMedia);
                future = eng.getVariableAsync("probabilitaErrore");
                while (!future.isDone()) {
                    Thread.sleep(100);//100
                }
                probabilitaErrore = future.get();
                listaProbabilitaErrore.add(probabilitaErrore);
                tmp++;
            }
        } catch (InterruptedException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("ie");
        } catch (ExecutionException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("exe");
        }
        Applicazione.getInstance().getModello().putBean(Costanti.LISTA_LUNGHEZZA_MEDIA, listaLunghezzaMedia);
        Applicazione.getInstance().getModello().putBean(Costanti.LISTA_PROBABILITA_ERRORE, listaProbabilitaErrore);
    }

    private void calcoloParametriPosizione() {
        int mini = 0;
        int minj = 0;
        int maxi = 0;
        int maxj = 0;
        List<Posizione> listaPosizioni = new ArrayList<>();
        try {
            logger.info("dentro try");
            inizializzaSessioneMatlab();
            eng = engFuture.get();
            int tmp = 0;
            String path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_RISULTATI);
            int numeroStrutture = new File(path).listFiles().length;
            logger.info("numeroStruttur posizione" + numeroStrutture);
            logger.info("tmp prima del while DAOMatlab posizione" + tmp);
            String pathML = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_LAVORO);
            pathML = pathML + "\\ImmagineMultilookTemporale.tiff";
            eng.putVariableAsync("pathML", pathML.toCharArray());
            String pathRisultati = path + "\\";
            eng.putVariableAsync("pathRisultati", pathRisultati.toCharArray());
            while (tmp != numeroStrutture) {
                eng.putVariableAsync("k", tmp + 1);
                eng.evalAsync(
                        "I = imread(pathML);\n"
                        + "    Struttura = imread(strcat(strcat(pathRisultati,num2str(k)),'.tiff')); \n"
                        + "    [righe,colonne]= find(Struttura);\n"
                        + "    mini = min(righe);\n"
                        + "    minj = min(colonne);\n"
                        + "    maxi = max(righe);\n"
                        + "    maxj = max(colonne);\n");
                Future<Double> future = eng.getVariableAsync("mini");
                while (!future.isDone()) {
                    Thread.sleep(100);//100
                }
                mini = future.get().intValue();
                future = eng.getVariableAsync("maxi");
                while (!future.isDone()) {
                    Thread.sleep(100);//100
                }
                maxi = future.get().intValue();
                future = eng.getVariableAsync("minj");
                while (!future.isDone()) {
                    Thread.sleep(100);//100
                }
                minj = future.get().intValue();
                future = eng.getVariableAsync("maxj");
                while (!future.isDone()) {
                    Thread.sleep(100);//100
                }
                maxj = future.get().intValue();
                Posizione posizione = new Posizione();
                posizione.setMini(mini);;
                posizione.setMinj(minj);;
                posizione.setMaxi(maxi);;
                posizione.setMaxj(maxj);;
                listaPosizioni.add(posizione);
                tmp++;
            }
        } catch (InterruptedException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("ie");
        } catch (ExecutionException ex) {
            Applicazione.getInstance().getVistaIndividuaStruttura().visualizzaErrori("exe");
        }
        Applicazione.getInstance().getModello().putBean(Costanti.LISTA_POSIZIONI, listaPosizioni);
    }

    private void eseguiIndividuaStrutture() {
        eng.evalAsync(
                "cd (pathRisultati);\n"
                + "    delete *.tiff\n"
                + "    I = imread(pathSobel);\n"
                //                + "    a = 1615;\n"
                //                + "    b = 1875;\n"
                //                + "    dimBox = 3;\n"
                //                + "    dimStruttura = 50;\n"
                //                + "    dimArea = 550;\n"
                + "    SCUT = I(a-round(dimArea/2):a+round(dimArea/2),b-round(dimArea/2):b+round(dimArea/2));\n"
                + "    imwrite(SCUT, pathSCUT);\n"
                + "    import java.util.LinkedList\n"
                + "    coda = LinkedList();\n"
                + "    numerostruttura = 0;\n"
                + "    for m = dimBox*2:dimArea-(dimBox*2)\n"
                + "        for n = dimBox*2:dimArea-(dimBox*2)\n"
                + "            if(SCUT(m,n) == 1)\n"
                + "                spi = m;\n"
                + "                spj = n;\n"
                + "                Struttura = zeros(dimArea,dimArea);\n"
                + "                continua = true;\n"
                + "                while continua \n"
                + "                    if spi > dimBox*2 && spi < dimArea-(dimBox*2) && spj >dimBox*2 && spj < dimArea-(dimBox*2) \n"
                + "                        Box = SCUT(spi-dimBox:spi+dimBox,spj-dimBox:spj+dimBox);\n"
                + "                        for a = 1:(2*dimBox+1)\n"
                + "                            for b = 1:(2*dimBox+1)\n"
                + "                                if Box(a,b) == 1\n"
                + "                                        coda.add([spi+a-(dimBox+1),spj+b-(dimBox+1)]);\n"
                + "                                    SCUT(spi+a-(dimBox+1),spj+b-(dimBox+1)) = 0;\n"
                + "                                    Struttura(spi+a-(dimBox+1),spj+b-(dimBox+1)) = 1;\n"
                + "                                end \n"
                + "                            end\n"
                + "                        end\n"
                + "                    end\n"
                + "                    if coda.size() == 0\n"
                + "                        continua = false;\n"
                + "                    else\n"
                + "                        coordinata = coda.remove();\n"
                + "                        spi = coordinata(1);\n"
                + "                        spj = coordinata(2);\n"
                + "                    end\n"
                + "                end\n"
                + "                codastr = LinkedList();\n"
                + "                if sum(sum(Struttura)) > dimStruttura\n"
                + "                    codastr.add(Struttura);\n"
                + "                    numerostruttura = numerostruttura +1;\n"
                + "                    imwrite(Struttura, strcat(strcat(strcat(pathRisultati,'\\'),num2str(numerostruttura)),'.tiff'));\n"
                + "                end\n"
                + "            end\n"
                + "        end\n"
                + "    end");
    }

    private void ottimizza() {
        //francobolla
        eng.evalAsync(
                "cd (pathRisultatiEdgeOttimizzati);\n"
                + "delete *.tiff\n"
                + "cd (pathRisultatiSoloStrutture);\n"
                + "delete *.tiff\n"
                + "I = imread(pathML);\n"
                + "D = dir([pathRisultati, '\\*.tiff']);\n"
                + "num = length(D(not([D.isdir])));\n"
                //                + "m = 1600;%1615;\n"
                //                + "n = 1974;%1875;\n"
                //                + "dimArea = 550;\n"
                + "for k = 1: num\n"
                + "    Struttura = imread(strcat(strcat(strcat(pathRisultati,'\\'),num2str(k)),'.tiff')); \n"
                + "    [righe,colonne]= find(Struttura);\n"
                + "    mini = min(righe);\n"
                + "    minj = min(colonne);\n"
                + "    maxi = max(righe);\n"
                + "    maxj = max(colonne);\n"
                + "    Strutt = I(a-round(dimArea/2)+mini:a-round(dimArea/2)+maxi,b-round(dimArea/2)+minj:b-round(dimArea/2)+maxj);\n"
                + "    SobelStrutt = Struttura(mini:maxi,minj:maxj);\n"//serve in calcolo area
                + "    imwrite(SobelStrutt, strcat(strcat(strcat(pathRisultatiEdgeOttimizzati,'\\'),num2str(k)),'.tiff'));\n"//serve in calcolo area
                + "    imwrite(Strutt, strcat(strcat(strcat(pathRisultatiSoloStrutture,'\\francobolloML'),num2str(k)),'.tiff'));\n"
                + "end");
    }

    private void eseguiIsStruttura() {
        eng.evalAsync(
                "cd (pathRisultatiFiltrati);\n"
                + "delete *.tiff\n"
                + "soglia = 70;\n"
                + "D = dir([pathRisultatiSoloStrutture, '\\*.tiff']);\n"
                + "num = length(D(not([D.isdir])));\n"
                + "for c = 1:num\n"
                + "    I = imread(strcat(strcat(strcat(pathRisultatiSoloStrutture,'\\francobolloML'),num2str(c)),'.tiff'));\n"
                + "    numPuntiScuri = 0;\n"
                + "    numPuntiTotali = 0;\n"
                + "    for l = 1:size(I,1)\n"
                + "        for k = 1:size(I,2)\n"
                + "            numPuntiTotali = numPuntiTotali + 1;\n"
                + "            if I(l,k) < soglia\n"
                + "                numPuntiScuri = numPuntiScuri +1;\n"
                + "            end\n"
                + "        end\n"
                + "    end\n"
                + "    risultato = (numPuntiScuri)/numPuntiTotali;\n"
                + "    if risultato > percentuale\n"
                + "        imwrite(I, strcat(strcat(strcat(pathRisultatiFiltrati,'\\francobolloML'),num2str(c)),'.tiff'));\n"
                + "    end\n"
                + "end");
    }

    private void getContrastoSingolo() {
        eng.evalAsync(
                //                        "m = 1597;\n"
                //                        + "n = 1975;\n"
                //                        + "dimArea = 550; \n"+
                "cornice = 5;\n"
                //                        + "soglia = 20;\n"
                + "I = imread(pathML);"
                + "    Struttura = imread(strcat(strcat(strcat(pathRisultati,'\\'),num2str(c)),'.tiff'));\n"
                + "    sumxin = 0;\n"
                + "    sumxout = 0;\n"
                + "    [righe,colonne]= find(Struttura);\n"
                + "    mini = min(righe);\n"
                + "    minj = min(colonne);\n"
                + "    maxi = max(righe);\n"
                + "    maxj = max(colonne);\n"
                + "    SobelStrutt = I(m-round(dimArea/2)+mini:m-round(dimArea/2)+maxi,n-round(dimArea/2)+minj:n-round(dimArea/2)+maxj);\n"
                + "    mini = min(righe)-cornice;\n"
                + "    minj = min(colonne)-cornice;\n"
                + "    maxi = max(righe)+cornice;\n"
                + "    maxj = max(colonne)+cornice;\n"
                + "    SobelStruttCornice = I(m-round(dimArea/2)+mini:m-round(dimArea/2)+maxi,n-round(dimArea/2)+minj:n-round(dimArea/2)+maxj);\n"
                + "    m_in = size(SobelStrutt,1)*size(SobelStrutt,2);\n"
                + "    m_out = size(SobelStruttCornice,1)*size(SobelStruttCornice,2);\n"
                + "    NormSobelStrutt = mat2gray(SobelStrutt);\n"
                + "    NormSobelStruttCornice = mat2gray(SobelStruttCornice);\n"
                + "    for a = 1: size(NormSobelStrutt,1) \n"
                + "        for b = 1: size(NormSobelStrutt,2) \n"
                + "            xin = NormSobelStrutt(a,b);\n"
                + "            sumxin = sumxin + xin;\n"
                + "        end\n"
                + "    end\n"
                + "    for a = 1: size(NormSobelStruttCornice,1) \n"
                + "        for b = 1: size(NormSobelStruttCornice,2) \n"
                + "            xout = NormSobelStruttCornice(a,b);\n"
                + "            sumxout = sumxout + (1-xout);\n"
                + "        end\n"
                + "    end\n"
                + "    cr = ((1/m_in)*sumxin)*((1/m_out)*sumxout);");
    }

    private void calcoloArea() {
        eng.evalAsync("I = imread(pathNome);\n"
                + "PrimaMaschera = zeros(size(I,1),size(I,2));"
                + "Area = zeros(size(I,1),size(I,2));\n"
                + "dimStruttureReali = 1.5*1.5;\n"
                + "soglia = 150;\n"
                + "dimbox = 2;%2\n"
                + "numeroPuntiMax = 5;%2- valori migliori 5,6(cancella anche alcune cose buone)\n"
                + "import java.util.LinkedList\n"
                + "coda = LinkedList();\n"
                + "for i = 1:size(I,1)\n"
                + "    for j = 1:size(I,2)\n"
                + "        if I(i,j) >= soglia\n"
                + "            Area(i,j) = 1;\n"
                + "        end\n"
                + "    end\n"
                + "end\n"
                + "AreaIniziale = Area;\n"
                + "for i = dimbox+1:size(I,1)-dimbox\n"
                + "    for j = dimbox+1:size(I,2)-dimbox\n"
                + "        if Area(i,j)~=0\n"
                + "            Box = Area(i-dimbox:i+dimbox,j-dimbox:j+dimbox);\n"
                + "            numeroPunti = 0;\n"
                + "            for m = 1:(2*dimbox+1)\n"
                + "                for n = 1:(2*dimbox+1)\n"
                + "                    if Box(m,n) ==1 && m ~= 2 && n ~= 2\n"
                + "                        Area(m+i-(dimbox+1),n+j-(dimbox+1)) = 1;\n"
                + "                    end \n"
                + "                end\n"
                + "            end\n"
                + "        end\n"
                + "    end\n"
                + "end\n"
                + "for i = dimbox+1:size(I,1)-dimbox\n"
                + "    for j = dimbox+1:size(I,2)-dimbox\n"
                + "        if PrimaMaschera(i,j)~=0\n"
                + "            Box = Area(i-dimbox:i+dimbox,j-dimbox:j+dimbox);\n"
                + "            numeroPunti = 0;\n"
                + "            for m = 1:(2*dimbox+1)\n"
                + "                for n = 1:(2*dimbox+1)\n"
                + "                    if Box(m,n) ==1 && m ~= 2 && n ~= 2\n"
                + "                        numeroPunti = numeroPunti + 1;\n"
                + "                    end \n"
                + "                end\n"
                + "            end\n"
                + "            if numeroPunti <= numeroPuntiMax\n"
                + "                coda.add([i,j]);\n"
                + "            end\n"
                + "        end\n"
                + "    end\n"
                + "end\n"
                + "while ~coda.isEmpty()\n"
                + "    coordinata = coda.remove();\n"
                + "    Area(coordinata(1),coordinata(2)) = 0;\n"
                + "end\n"
                //devi careicare gli edge
                + "EdgeStrutt = imread(pathEdgeStrutt);\n"
                + "BW = bwmorph(EdgeStrutt,'thicken', 5);\n"
                + "Area = zeros(size(I,1),size(I,2));\n"
                + "for m = dimbox+1:size(I,1)-dimbox\n"
                + "    for n = dimbox+1:size(I,2)-dimbox\n"
                + "        if(PrimaMaschera(m,n)~=0 ||BW(m,n) ~=0)\n"
                + "            Area(m,n) = 1;\n"
                + "        end\n"
                + "    end\n"
                + "end\n"
                + "Area = bwmorph(Area,'bridge',Inf);\n"
                + "Area = bwmorph(Area,'close',Inf);\n"
                + "Area = Area.*AreaIniziale;\n"
                + "dimBox = 5;\n"
                + "numeroPuntiMax = 11;%6\n"
                + "import java.util.LinkedList\n"
                + "coda = LinkedList();\n"
                + "for a = dimBox+1:size(Area,1)-dimBox\n"
                + "    for b = dimBox+1:size(Area,2)-dimBox\n"
                + "        if Area(a,b)~= 0\n"
                + "            Box = Area(a-dimBox:a+dimBox,b-dimBox:b+dimBox);\n"
                + "            numeroPunti = 0;\n"
                + "            for m = 1:(2*dimBox+1)\n"
                + "                for n = 1:(2*dimBox+1)\n"
                + "                    if Box(m,n) ~= 0 && m ~= round(dimBox/2)+1 && n ~= round(dimBox/2)+1\n"
                + "                        numeroPunti = numeroPunti + 1;\n"
                + "                    end \n"
                + "                end\n"
                + "            end\n"
                + "            if numeroPunti <= numeroPuntiMax\n"
                + "                coda.add([a,b]);\n"
                + "            end\n"
                + "        end\n"
                + "    end\n"
                + "end\n"
                + "%while ~coda.isEmpty()\n"
                + "for k = 1:coda.size()\n"
                + "    coordinata = coda.remove();\n"
                + "    Area(coordinata(1),coordinata(2)) = 0;   \n"
                + "end\n"
                + "Area = bwmorph(Area,'clean', Inf);\n"
                + "area = sum(sum(Area))*dimStruttureReali;"
                + "%end");
    }

    private void calcoloLunghezzaMediaProbabilitaErrore() {
        eng.evalAsync("ML = imread(pathNome);\n"
                + "Edgestrutt = imread(pathEdgeStrutt);\n"
                + "[H,theta,rho] = hough(Edgestrutt);%francobollo di edge\n"
                + "P = houghpeaks(H,100,'threshold',ceil(0.3*max(H(:))));%mappa dei picchi, dice dove sono%H,100\n"
                + "lines = houghlines(Edgestrutt,theta,rho,P,'FillGap',3,'MinLength',10);%BW francobollo di edge%MinLength,5,30\n"
                + "max_len = 0;\n"
                + "lunghezzaMedia = 0;\n"
                + "numeroLati = 0;\n"
                + "MatriceLinee = zeros(550,550);\n"
                + "dimensioneRealePixel = 3;%sarebbe la dimesnione del pixel in termini reali. Qui vale 3 m\n"
                + "for k = 1:length(lines)\n"
                + "   xy = [lines(k).point1; lines(k).point2];\n"
                + "   len = norm(lines(k).point1 - lines(k).point2);%lunghezza del segmento visualizzato, da usare per perimetro\n"
                + "   lunghezzaMedia = lunghezzaMedia + len;\n"
                + "   numeroLati = numeroLati + 1;\n"
                + "   if ( len > max_len)%segmento in rosso\n"
                + "      max_len = len;\n"
                + "      xy_long = xy;\n"
                + "   end\n"
                + "   x1 = lines(k).point1(1);\n"
                + "   y1 = lines(k).point1(2);\n"
                + "   x2 = lines(k).point2(1);\n"
                + "   y2 = lines(k).point2(2);\n"
                + "   xinizio = x1;\n"
                + "   xfine = x2;\n"
                + "   %yInizio = y1;\n"
                + "   if x1 > x2\n"
                + "       xinizio = x2; \n"
                + "       xfine = x1;\n"
                + "   end\n"
                + "   %if y1 > y2\n"
                + "   %    y = y2;\n"
                + "   %end\n"
                + "   for x = xinizio : xfine-1\n"
                + "        y = round(y1 + ((y2-y1)/(x2-x1))*(x-x1));\n"
                + "        MatriceLinee(x,y) = 255;\n"
                + "   end\n"
                + "end\n"
                + "lunghezzaMedia = lunghezzaMedia/numeroLati;\n"
                + "lunghezzaMediaReale = lunghezzaMedia * dimensioneRealePixel;\n"
                + "probabilitaErrore = (sum(sum(MatriceLinee))/255)/(sum(sum(Edgestrutt))/255);");
    }

}
