/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi;

import it.unibas.dbtesi.controllo.ControlloFrame;
import it.unibas.dbtesi.controllo.ControlloIndividuaStruttura;
import it.unibas.dbtesi.controllo.ControlloPrincipale;
import it.unibas.dbtesi.modello.Modello;
import it.unibas.dbtesi.modello.Posizione;
import it.unibas.dbtesi.modello.Struttura;
import it.unibas.dbtesi.persistenza.DAOCarica;
import it.unibas.dbtesi.persistenza.DAOMatlab;
import it.unibas.dbtesi.persistenza.DAOPosizione;
import it.unibas.dbtesi.persistenza.DAOStruttura;
import it.unibas.dbtesi.persistenza.IDAOCarica;
import it.unibas.dbtesi.persistenza.IDAOMatlab;
import it.unibas.dbtesi.persistenza.IDAOPosizione;
import it.unibas.dbtesi.persistenza.IDAOStruttura;
import it.unibas.dbtesi.vista.Frame;
import it.unibas.dbtesi.vista.VistaIndividuaStruttura;

import it.unibas.dbtesi.vista.VistaPrincipale;
import javax.swing.SwingUtilities;

/**
 *
 * @author Francesco
 */
public class Applicazione {

    private static Applicazione singleton = new Applicazione();

    private Applicazione() {
    }

    public static Applicazione getInstance() {
        return singleton;
    }

    private Modello modello;
    private IDAOCarica daoCarica;
    private IDAOMatlab daoMatlab;
    private IDAOStruttura daoStruttura;
    private IDAOPosizione daoPosizione;
    private Frame vistaFrame;
    private VistaPrincipale vistaPrincipale;
    private VistaIndividuaStruttura vistaIndividuaStruttura;
    private ControlloFrame controlloFrame;
    private ControlloPrincipale controlloPrincipale;
    private ControlloIndividuaStruttura controlloIndividuaStruttura;

    public void inizializza() {
        modello = new Modello();
        daoStruttura = new DAOStruttura(Struttura.class);
        daoMatlab = new DAOMatlab();
        daoPosizione = new DAOPosizione(Posizione.class);
        daoCarica = new DAOCarica();
        vistaFrame = new Frame();
        vistaPrincipale = new VistaPrincipale();
        vistaIndividuaStruttura = new VistaIndividuaStruttura(vistaFrame);
        controlloFrame = new ControlloFrame();
        controlloPrincipale = new ControlloPrincipale();
        controlloIndividuaStruttura = new ControlloIndividuaStruttura();

        vistaPrincipale.inizializza();
        vistaIndividuaStruttura.inizializza();
        vistaFrame.inizializza();
        daoMatlab.inizializzaSessioneMatlab();
    }

    public Modello getModello() {
        return modello;
    }

    public IDAOCarica getDaoCarica() {
        return daoCarica;
    }

    public IDAOMatlab getDaoMatlab() {
        return daoMatlab;
    }

    public IDAOStruttura getDaoStruttura() {
        return daoStruttura;
    }

    public IDAOPosizione getDaoPosizione() {
        return daoPosizione;
    }

    public Frame getVistaFrame() {
        return vistaFrame;
    }

    public VistaPrincipale getVistaPrincipale() {
        return vistaPrincipale;
    }

    public ControlloFrame getControlloFrame() {
        return controlloFrame;
    }

    public ControlloPrincipale getControlloPrincipale() {
        return controlloPrincipale;
    }

    public VistaIndividuaStruttura getVistaIndividuaStruttura() {
        return vistaIndividuaStruttura;
    }

    public ControlloIndividuaStruttura getControlloIndividuaStruttura() {
        return controlloIndividuaStruttura;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Applicazione.getInstance().inizializza();
            }
        });
    }
}
