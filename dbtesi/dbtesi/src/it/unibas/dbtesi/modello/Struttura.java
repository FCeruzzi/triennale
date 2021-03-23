/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.modello;

import javax.persistence.CascadeType;
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
public class Struttura {

    private Long id;
    private Posizione posizione;
    private Immagine immagine;
    private double area;
    private double lunghezzaMedia;
    private double contrasto;
    private double falseDetectionProbability;
    private int anno;

    public Struttura() {
    }

    public Struttura(Posizione posizione, Immagine immagine, double area, double lunghezzaMedia, double contrasto, double falseDetectionProbability, int anno) {
        this.posizione = posizione;
        this.immagine = immagine;
        this.area = area;
        this.lunghezzaMedia = lunghezzaMedia;
        this.contrasto = contrasto;
        this.falseDetectionProbability = falseDetectionProbability;
        this.anno = anno;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Posizione getPosizione() {
        return posizione;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Immagine getImmagine() {
        return immagine;
    }

    public double getArea() {
        return area;
    }

    public double getLunghezzaMedia() {
        return lunghezzaMedia;
    }

    public double getContrasto() {
        return contrasto;
    }

    public double getFalseDetectionProbability() {
        return falseDetectionProbability;
    }

    public void setImmagine(Immagine immagine) {
        this.immagine = immagine;
    }

    public void setContrasto(double contrasto) {
        this.contrasto = contrasto;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setLunghezzaMedia(double lunghezzaMedia) {
        this.lunghezzaMedia = lunghezzaMedia;
    }

    public void setFalseDetectionProbability(double falseDetectionProbability) {
        this.falseDetectionProbability = falseDetectionProbability;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
