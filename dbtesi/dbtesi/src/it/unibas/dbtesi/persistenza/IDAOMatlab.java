/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.persistenza;

import com.mathworks.engine.EngineException;
import java.awt.Image;
import java.util.List;
import java.util.concurrent.Future;

/**
 *
 * @author Francesco
 */
public interface IDAOMatlab {
 
    public void inizializzaSessioneMatlab();
    
    public void chiudiSessioneMatlab();
    
//    public Future<Image> caricaImmagineTiff();
//    
//    public void salvaImmagineTiff();
    
//    public void eseguiFunction();

    public void eseguiSobel();

//    public void eseguiMatlabIndividuaStruttura(int i,int j,int dimbox,int dimstruttura);
    
    public void eseguiMatlabIndividuaStruttura(int i, int j, int dimbox, int dimstruttura, int dimArea);
    
    public void ottimizza(int i, int j, int dimBox, int dimStruttura, int dimArea);
    
    public List<Double> getContrasto(int i, int j, int dimArea);

    public void eseguiIndividuaPalazzo(double percentuale);
    
    //Non funziona questa
//    public void isStruttura(int i, int j, int dimArea, int cornice, int soglia);

    public void calcolaParametri();
}
