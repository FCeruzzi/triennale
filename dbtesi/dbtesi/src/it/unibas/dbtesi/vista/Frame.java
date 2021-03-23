/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.vista;

import it.unibas.dbtesi.Applicazione;
import it.unibas.dbtesi.Costanti;
import it.unibas.dbtesi.persistenza.DAOMatlab;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class Frame extends javax.swing.JFrame {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Frame.class);

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void inizializza() {
        initComponents();
        inizializzaAzioni();
//        JScrollPane scrollPane = new JScrollPane(Applicazione.getInstance().getVistaPrincipale());
//        setLayout(new BorderLayout());//
        this.setContentPane(Applicazione.getInstance().getVistaPrincipale());
        this.pack();
        setLocationRelativeTo(null);
        setVisible(true);
        inizializzaPath();
        cancellaCartelleSeEsce();
    }

    private void inizializzaAzioni() {
        this.jMenuCaricaImmagine.setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneCaricaImmagine());
        this.jMenuSobel.setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneMatlabSobel());
        this.jMenuIndividuaStruttura.setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneMatlabIndividuaStruttura());
        this.jMenuEsci.setAction(Applicazione.getInstance().getControlloFrame().getAzioneEsci());
    }

    public void visualizzaErrori(String errori) {
        JOptionPane.showMessageDialog(this, errori, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }

    public void visualizzaMessaggio(String mess) {
        JOptionPane.showMessageDialog(this, mess, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
        javax.swing.JMenu jMenuFile = new javax.swing.JMenu();
        jMenuCaricaImmagine = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuEsci = new javax.swing.JMenuItem();
        javax.swing.JMenu jMenu1 = new javax.swing.JMenu();
        jMenuSobel = new javax.swing.JMenuItem();
        jMenuIndividuaStruttura = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuFile.setText("File");

        jMenuCaricaImmagine.setText("jMenuItem1");
        jMenuFile.add(jMenuCaricaImmagine);
        jMenuFile.add(jSeparator1);

        jMenuEsci.setText("jMenuItem2");
        jMenuFile.add(jMenuEsci);

        jMenuBar1.add(jMenuFile);

        jMenu1.setText("Strumenti");

        jMenuSobel.setText("jMenuItem1");
        jMenu1.add(jMenuSobel);

        jMenuIndividuaStruttura.setText("jMenuItem1");
        jMenu1.add(jMenuIndividuaStruttura);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuCaricaImmagine;
    private javax.swing.JMenuItem jMenuEsci;
    private javax.swing.JMenuItem jMenuIndividuaStruttura;
    private javax.swing.JMenuItem jMenuSobel;
    // End of variables declaration//GEN-END:variables

    public void attivaSobel() {
        this.jMenuSobel.setEnabled(true);
    }

    public void attivaIndividuaStruttura() {
        this.jMenuIndividuaStruttura.setEnabled(true);
    }



    private void inizializzaPath() {
        String separatore = File.separator;
        String directoryCorrente = "C:\\Users\\Ceruz\\Desktop\\Francesco";
        String lavoro = directoryCorrente + separatore + "_lavoro" + separatore;
        String risultati = lavoro + "risultati";
        String risultatiSoloStrutture = lavoro + "risultatiSoloStrutture";
        String risultatiFiltrati = lavoro + "risultatiFiltrati";
        String risultatiEdgeOttimizzati = lavoro + "risultatiEdgeOttimizzati";
        try {
            new File(lavoro).mkdir();
            new File(risultati).mkdir();
            new File(risultatiSoloStrutture).mkdir();
            new File(risultatiFiltrati).mkdir();
            new File(risultatiEdgeOttimizzati).mkdir();
            logger.info("cartelle create");
        } catch (Exception e) {
            visualizzaErrori("Errore nell'inizializzazione delle directory");
        }
//        logger.info("curdir" + directoryCorrente);
//        logger.info("lavoro" + lavoro);
//        logger.info("risultati" + risultati);
//        logger.info("risultatiSoloStrutture" + risultatiSoloStrutture);
//        logger.info("risultatiFiltrati" + risultatiFiltrati);
//        logger.info("risultatiEdgeOttimizzati" + risultatiEdgeOttimizzati);
        Applicazione.getInstance().getModello().putBean(Costanti.PATH_LAVORO, lavoro);
        Applicazione.getInstance().getModello().putBean(Costanti.PATH_RISULTATI, risultati);
        Applicazione.getInstance().getModello().putBean(Costanti.PATH_RISULTATISOLOSTRUTTURE, risultatiSoloStrutture);
        Applicazione.getInstance().getModello().putBean(Costanti.PATH_RISULTATIFILTRATI, risultatiFiltrati);
        Applicazione.getInstance().getModello().putBean(Costanti.PATH_RISULTATIEDGEOTTIMIZZATI, risultatiEdgeOttimizzati);
    }

    private void cancellaCartelleSeEsce() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                logger.info("cancellaCartelle");
                Applicazione.getInstance().getDaoCarica().cancellaCartelle();
                System.exit(0);
            }
        });
    }

}
