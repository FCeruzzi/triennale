/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.vista;

import it.unibas.dbtesi.Applicazione;
import it.unibas.dbtesi.Costanti;
import it.unibas.dbtesi.modello.ComparatoreData;
import it.unibas.dbtesi.modello.Struttura;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JViewport;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francesco
 */
public class VistaPrincipale extends javax.swing.JPanel {

    private final static Logger logger = LoggerFactory.getLogger(VistaPrincipale.class);

    private Point point;

    public void inizializza() {
        initComponents();
        inizializzaComponenti();
        inizializzaAzioni();
        aggiornaTabella();
        disattivaBottoneCaricaStrutturaDatabase();
        disattivaBottoneScelgiStruttura();
    }

    private void inizializzaAzioni() {
        this.jButtonCarica.setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneCaricaImmagine());
        this.jButtonMostraStrutturaSelezionata.setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneMostraImmagineSelezionata());
        this.jButtonCaricaStrutturaDatabase.setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneCaricaStrutturaSingolaDatabase());
        this.jButtonScaricaDatabase.setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneScaricaDatabase());
    }

    public void getNuovaImmagineDialog(BufferedImage image) {
        try {
            File f = (File) Applicazione.getInstance().getModello().getBean(Costanti.FILE_SCELTO);
            JPanel pane = new JPanel(new BorderLayout());
            JLabel jLabelTiff = new JLabel();
            JScrollPane scrollPane = new JScrollPane(pane);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
            pane.add(jLabelTiff);
            if (image.getHeight() < jTabbedPanel.getHeight() && image.getWidth() < jTabbedPanel.getWidth()) {
                logger.info("da scalare");
                Image img = getScaledImage(image, jTabbedPanel.getWidth() - 20, jTabbedPanel.getHeight() - 45);
                image = (BufferedImage) img;
            }
            ImageIcon imageIcon = new ImageIcon(image);
            jLabelTiff.setIcon(imageIcon);
            pane.remove(jLabelTiff);
            pane.add(jLabelTiff);
            if (jTabbedPanel.getName() != "eliminami") {
                jLabelTiff.setAutoscrolls(true);
                jLabelTiff.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            point = new Point(e.getPoint());
                        } else {
                            PointerInfo a = MouseInfo.getPointerInfo();
                            point = new Point(a.getLocation());
                            SwingUtilities.convertPointFromScreen(point, e.getComponent());
                            int x = (int) point.getX();
                            int y = (int) point.getY();
                            jLabelXY.setText("Azimuth: " + x + " Range: " + y);
                            Applicazione.getInstance().getModello().putBean(Costanti.PIXEL_SELEZIONATO_X, x);
                            Applicazione.getInstance().getModello().putBean(Costanti.PIXEL_SELEZIONATO_Y, y);
                            Applicazione.getInstance().getVistaIndividuaStruttura().setX(x);
                            Applicazione.getInstance().getVistaIndividuaStruttura().sety(y);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        PointerInfo a = MouseInfo.getPointerInfo();
                        Point point = new Point(a.getLocation());
                        SwingUtilities.convertPointFromScreen(point, e.getComponent());
                        int x = (int) point.getX();
                        int y = (int) point.getY();
                        jLabelXY.setText("Azimuth: " + x + " Range: " + y);
                    }

                    @Override
                    public void mouseExited(MouseEvent event) {
                        jLabelXY.setText("Azimuth: " + " Range: ");
                    }

                });
                jLabelTiff.addMouseMotionListener(new MouseMotionListener() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        PointerInfo a = MouseInfo.getPointerInfo();
                        point = new Point(a.getLocation());
                        SwingUtilities.convertPointFromScreen(point, e.getComponent());
                        int x = (int) point.getX();
                        int y = (int) point.getY();
                        jLabelXY.setText("Azimuth: " + x + " Range: " + y);
                    }

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        PointerInfo a = MouseInfo.getPointerInfo();
                        point = new Point(a.getLocation());
                        SwingUtilities.convertPointFromScreen(point, e.getComponent());
                        int x = (int) point.getX();
                        int y = (int) point.getY();
                        jLabelXY.setText("Azimuth: " + x + " Range: " + y);
                        if (point != null) {
                            JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, jLabelTiff);
                            if (viewPort != null) {
                                int deltaX = point.x - e.getX();
                                int deltaY = point.y - e.getY();
                                Rectangle view = viewPort.getViewRect();
                                view.x += deltaX;
                                view.y += deltaY;
                                jLabelTiff.scrollRectToVisible(view);
                            }
                        }
                    }
                });
            }
            if (jTabbedPanel.getComponent(0).getName() == "eliminami") {
                jTabbedPanel.remove(0);
            }
            int indice = jTabbedPanel.getComponentCount();
            jTabbedPanel.addTab(f.getName(), scrollPane);
            jTabbedPanel.setSelectedIndex(indice);
        } catch (NullPointerException npe) {

        }

    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        jButtonCarica = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStrutture = new javax.swing.JTable();
        jButtonMostraStrutturaSelezionata = new javax.swing.JButton();
        jButtonCaricaStrutturaDatabase = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableFootpriintsDatabase = new javax.swing.JTable();
        jButtonScaricaDatabase = new javax.swing.JButton();
        jTextQuery = new javax.swing.JTextField();
        jTabbedPanel = new javax.swing.JTabbedPane();
        jLabelXY = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Strutture"));
        jPanel1.setName(""); // NOI18N

        jButtonCarica.setText("jButton1");

        jTableStrutture.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableStrutture);

        jButtonMostraStrutturaSelezionata.setText("jButton1");

        jButtonCaricaStrutturaDatabase.setText("jButton1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCarica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonMostraStrutturaSelezionata)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCaricaStrutturaDatabase)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCarica)
                    .addComponent(jButtonMostraStrutturaSelezionata)
                    .addComponent(jButtonCaricaStrutturaDatabase))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Footprint Database"));

        jTableFootpriintsDatabase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableFootpriintsDatabase);

        jButtonScaricaDatabase.setText("jButton1");

        jTextQuery.setText("jTextField1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonScaricaDatabase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextQuery))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(374, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonScaricaDatabase)
                    .addComponent(jTextQuery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addGap(42, 42, 42)))
        );

        jTabbedPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelXY.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelXY.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPanel)
                    .addComponent(jLabelXY, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelXY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPanel))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void aggiornaTabella() {
        List<Struttura> listaStrutture = (List<Struttura>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_STRUTTURE);
        ModelloTabellaStrutture modelloTabella = new ModelloTabellaStrutture(listaStrutture);
        this.jTableStrutture.setModel(modelloTabella);
        List<Struttura> listaStruttureFD = (List<Struttura>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_STRUTTURE_DF);
        ModelloTabellaFootprintsDatabase modelloTabellaFD = new ModelloTabellaFootprintsDatabase(listaStruttureFD);
        this.jTableFootpriintsDatabase.setModel(modelloTabellaFD);

    }

    public int getRigaSelezionata() {
        return this.jTableStrutture.getSelectedRow();
    }

    public void visualizzaErrori(String errori) {
        JOptionPane.showMessageDialog(this, errori, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }

    public void visualizzaMessaggio(String mess) {
        JOptionPane.showMessageDialog(this, mess, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void attivaBottoneScelgiStruttura() {
        this.jButtonMostraStrutturaSelezionata.setEnabled(true);
    }

    public void attivaBottoneCaricaStrutturaDatabase() {
        this.jButtonCaricaStrutturaDatabase.setEnabled(true);
    }

    public void disattivaBottoneCaricaStrutturaDatabase() {
        this.jButtonCaricaStrutturaDatabase.setEnabled(false);
    }

    public void disattivaBottoneScelgiStruttura() {
        this.jButtonMostraStrutturaSelezionata.setEnabled(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCarica;
    private javax.swing.JButton jButtonCaricaStrutturaDatabase;
    private javax.swing.JButton jButtonMostraStrutturaSelezionata;
    private javax.swing.JButton jButtonScaricaDatabase;
    private javax.swing.JLabel jLabelXY;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPanel;
    private javax.swing.JTable jTableFootpriintsDatabase;
    private javax.swing.JTable jTableStrutture;
    private javax.swing.JTextField jTextQuery;
    // End of variables declaration//GEN-END:variables

    private void inizializzaComponenti() {
        jLabelXY.setText("Azimuth: Range: ");
        JPanel jp = new JPanel();
        jp.setName("eliminami");
        jTabbedPanel.add(jp, "");
        jTextQuery.setText("");
        jTableStrutture.getTableHeader().addMouseListener(new TableHeaderMouseListener(jTableStrutture));
        jTableFootpriintsDatabase.getTableHeader().addMouseListener(new TableHeaderMouseListener(jTableFootpriintsDatabase));
//        inizializzaCombo();
    }

    public void setQuery(String campo) {
        jTextQuery.setText(campo);
    }

    public int getAnno() {
        int annoInt = 0;
        while (annoInt == 0) {
            annoInt = cercaAnno();
        }
        return annoInt;
    }

    private int cercaAnno() throws HeadlessException {
        SpinnerNumberModel sModel = new SpinnerNumberModel(2017, 1980, 2017, 1);
        JSpinner spinner = new JSpinner(sModel);
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
        int option = JOptionPane.showOptionDialog(null, spinner, "Anno immagine di riferimento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        int anno = 0;
        if (option == JOptionPane.CANCEL_OPTION) {
            // user hit cancel
            logger.info("value" + spinner.getValue());
            anno = -1;
        } else if (option == JOptionPane.OK_OPTION) {
            // user entered a number
            logger.info("value" + spinner.getValue());
            anno = (int) spinner.getValue();
        }

        return anno;
    }

//    private void inizializzaCombo() {
//        jComboQuery.removeAllItems();
//        jComboQuery.addItem("");
//        jComboQuery.addItem("Nome");
//        jComboQuery.addItem("Anno");
//        jComboQuery.addItem("Area");
//        jComboQuery.addItem("Posizione");
//        jComboQuery.addItem("Contrasto");
//        jComboQuery.addItem("Lunghezza media");
//        jComboQuery.addItem("False detection probability");
//    }
//    
//    public int getIndiceCombo(){
//        return jComboQuery.getSelectedIndex();
//    }
    public String getQuery() {
        return jTextQuery.getText();
    }

}
