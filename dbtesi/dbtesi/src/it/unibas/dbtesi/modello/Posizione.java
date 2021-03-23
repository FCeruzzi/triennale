/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.modello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Francesco
 */
@Entity
public class Posizione {

    private Long id;
    private Struttura struttura;
    private int maxi;
    private int maxj;
    private int mini;
    private int minj;

    public Posizione() {
    }

    public Posizione(Struttura struttura, int maxi, int maxj, int mini, int minj) {
        this.struttura = struttura;
        this.maxi = maxi;
        this.maxj = maxj;
        this.mini = mini;
        this.minj = minj;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    @OneToOne(mappedBy = "posizione")
    public Struttura getStruttura() {
        return struttura;
    }

    public int getMaxi() {
        return maxi;
    }

    public void setMaxi(int maxi) {
        this.maxi = maxi;
    }

    public int getMaxj() {
        return maxj;
    }

    public void setMaxj(int maxj) {
        this.maxj = maxj;
    }

    public int getMini() {
        return mini;
    }

    public void setMini(int mini) {
        this.mini = mini;
    }

    public int getMinj() {
        return minj;
    }

    public void setMinj(int minj) {
        this.minj = minj;
    }

    public void setStruttura(Struttura struttura) {
        this.struttura = struttura;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    

}
