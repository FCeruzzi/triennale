/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.vista;

import com.sun.java.swing.plaf.windows.WindowsProgressBarUI;
import it.unibas.dbtesi.Applicazione;
import java.awt.Color;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ProgressBarUI;

/**
 *
 * @author Francesco
 */
public class VistaIndividuaStruttura extends javax.swing.JDialog {

    /**
     * Creates new form VistaOperazioni
     */
    public VistaIndividuaStruttura(Frame vistaFrame) {
        super(vistaFrame, false); //Per eseguire le istruzioni successive all'apertura, la finestra deve essere NON modale. Ci può essere un problema con le sessioni nidificate di Hibernate.
    }

    public void inizializza() {
        initComponents();
        inizializzaAzioni();
        inizializzaCampi();
        pack();
        setLocationRelativeTo(getParent());
        setTitle("Individua Struttura");
        jSliderContrasto.setEnabled(false);
        jSliderSogliaStruttura.setEnabled(false);
    }

    public void visualizza() {
        pulisciCampi();
        visualizzaProgressBar();
        setVisible(true);
    }

    public void nascondi() {
        setVisible(false);
    }

    private void inizializzaAzioni() {
        jButtonCalcola.setAction(Applicazione.getInstance().getControlloIndividuaStruttura().getAzioneCalcola());
        jButtonEsci.setAction(Applicazione.getInstance().getControlloIndividuaStruttura().getAzioneEsci());
    }

    public void inizializzaCampi() {
        inizializzaLabel();
        inizializzaRadioButton();
        inizializzaSlider();
        inizializzaProgressBar();
    }

    private void inizializzaProgressBar() {
        ProgressBarUI ui = new WindowsProgressBarUI() {
            protected Color getSelectionBackground() {
                return Color.black;
            }

            protected Color getSelectionForeground() {
                return Color.white;
            }
        };
        jProgressBar.setUI(ui);
    }

    private void inizializzaSlider() {
        jSliderContrasto.setMinorTickSpacing(10);
        jSliderContrasto.setPaintTicks(true);
        jSliderContrasto.setPaintLabels(true);
        jSliderSogliaStruttura.setMinorTickSpacing(10);
        jSliderSogliaStruttura.setPaintTicks(true);
        jSliderSogliaStruttura.setPaintLabels(true);
        Hashtable position = new Hashtable();
        position.put(0, new JLabel("0"));
        position.put(50, new JLabel("50%"));
        position.put(100, new JLabel("100%"));
        jSliderContrasto.setLabelTable(position);
        jSliderContrasto.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                jLabelContrasto.setText("Contrasto: " + getValoreSliderContrasto() + " %");
            }
        });
        jSliderSogliaStruttura.setLabelTable(position);
        jSliderSogliaStruttura.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                jLabelSogliaStruttura.setText("Estensione ombra: " + getValoreSliderSogliaStruttura() + " %");
            }
        });
    }

    private void inizializzaLabel() {
        jLabelDimbox.setText("Dimensione Box");
        jLabelDimensioneStruttura.setText("Dimensione struttura");
        jLabelDimArea.setText("Dimensione area");
        jLabelContrasto.setText("Contrasto: ");
        jLabelSogliaStruttura.setText("Estensione ombra: ");
    }

    private void inizializzaRadioButton() {

        jCheckBoxIsStruttura.setText("Solo palazzi");
        jCheckBoxIsStruttura.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jCheckBoxIsStruttura.isSelected()) {
                    jSliderContrasto.setEnabled(true);
                    jSliderSogliaStruttura.setEnabled(true);
                } else {
                    jSliderContrasto.setEnabled(false);
                    jSliderSogliaStruttura.setEnabled(false);
                }
            }
        });
    }

    public int getValoreSliderContrasto() {
        return jSliderContrasto.getValue();
    }

    public int getValoreSliderSogliaStruttura() {
        return jSliderSogliaStruttura.getValue();
    }

    public boolean isStruttura() {
        return jCheckBoxIsStruttura.isSelected();
    }

    public void pulisciCampi() {
        jLabelX.setText("Azimuth: ");
        jLabelY.setText("Range: ");
        jTextDimBox.setText("");
        jTextDimStruttura.setText("");
        jTextDimArea.setText("");
    }

    public String getDimBox() {
        return jTextDimBox.getText();
    }

    public String getDimStruttura() {
        return jTextDimStruttura.getText();
    }

    public String getDimArea() {
        return jTextDimArea.getText();
    }

    public void setDimArea(int dimArea) {
        String jString = dimArea + "";
        jLabelDimArea.setText(jString);
    }

    public void sety(int j) {
        String jString = "Range: " + j;
        jLabelY.setText(jString);
    }

    public void setX(int i) {
        String iString = "Azimuth: " + i;
        jLabelX.setText(iString);
    }

    public void visualizzaErrori(String errori) {
        JOptionPane.showMessageDialog(this, errori, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }

    public void visualizzaMessaggio(String mess) {
        JOptionPane.showMessageDialog(this, mess, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void visualizzaProgressBar() {
        jProgressBar.setString("Pronto");
        jProgressBar.setStringPainted(true);
        pack();
        jProgressBar.setStringPainted(false);
        jProgressBar.setIndeterminate(false);

    }

    public void aggiornaBar(String testo, boolean painted) {
        jProgressBar.setStringPainted(painted);
        jProgressBar.setIndeterminate(true);
        jProgressBar.setString(testo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabelX = new javax.swing.JLabel();
        jLabelY = new javax.swing.JLabel();
        jLabelDimbox = new javax.swing.JLabel();
        jLabelDimensioneStruttura = new javax.swing.JLabel();
        jButtonCalcola = new javax.swing.JButton();
        jButtonEsci = new javax.swing.JButton();
        jTextDimBox = new javax.swing.JTextField();
        jTextDimStruttura = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabelDimArea = new javax.swing.JLabel();
        jTextDimArea = new javax.swing.JTextField();
        jPanelSlider = new javax.swing.JPanel();
        jLabelContrasto = new javax.swing.JLabel();
        jSliderContrasto = new javax.swing.JSlider();
        jLabelSogliaStruttura = new javax.swing.JLabel();
        jSliderSogliaStruttura = new javax.swing.JSlider();
        jCheckBoxIsStruttura = new javax.swing.JCheckBox();
        jProgressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelX.setText("jLabel4");

        jLabelY.setText("jLabel2");

        jLabelDimbox.setText("jLabel2");

        jLabelDimensioneStruttura.setText("jLabel2");

        jButtonCalcola.setText("jButton1");

        jButtonEsci.setText("jButton2");

        jTextDimBox.setText("jTextField1");
        jTextDimBox.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jTextDimStruttura.setText("jTextField1");

        jLabel1.setText("Pixel selezionato ");

        jLabelDimArea.setText("jLabel2");

        jTextDimArea.setText("jTextField1");

        jPanelSlider.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelContrasto.setText("jLabel2");

        jLabelSogliaStruttura.setText("jLabel2");

        javax.swing.GroupLayout jPanelSliderLayout = new javax.swing.GroupLayout(jPanelSlider);
        jPanelSlider.setLayout(jPanelSliderLayout);
        jPanelSliderLayout.setHorizontalGroup(
            jPanelSliderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSliderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSliderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderContrasto, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelContrasto)
                    .addComponent(jLabelSogliaStruttura)
                    .addComponent(jSliderSogliaStruttura, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelSliderLayout.setVerticalGroup(
            jPanelSliderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSliderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelContrasto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderContrasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSogliaStruttura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderSogliaStruttura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBoxIsStruttura.setText("jCheckBox1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDimbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextDimBox, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDimensioneStruttura)
                            .addComponent(jLabelY)
                            .addComponent(jLabelX)
                            .addComponent(jLabel1)
                            .addComponent(jLabelDimArea)
                            .addComponent(jCheckBoxIsStruttura))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextDimStruttura, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(jTextDimArea)))
                    .addComponent(jPanelSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonCalcola)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonEsci)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDimbox)
                    .addComponent(jTextDimBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDimensioneStruttura)
                    .addComponent(jTextDimStruttura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextDimArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDimArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxIsStruttura)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEsci)
                    .addComponent(jButtonCalcola))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCalcola;
    private javax.swing.JButton jButtonEsci;
    private javax.swing.JCheckBox jCheckBoxIsStruttura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelContrasto;
    private javax.swing.JLabel jLabelDimArea;
    private javax.swing.JLabel jLabelDimbox;
    private javax.swing.JLabel jLabelDimensioneStruttura;
    private javax.swing.JLabel jLabelSogliaStruttura;
    private javax.swing.JLabel jLabelX;
    private javax.swing.JLabel jLabelY;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelSlider;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSliderContrasto;
    private javax.swing.JSlider jSliderSogliaStruttura;
    private javax.swing.JTextField jTextDimArea;
    private javax.swing.JTextField jTextDimBox;
    private javax.swing.JTextField jTextDimStruttura;
    // End of variables declaration//GEN-END:variables

}
