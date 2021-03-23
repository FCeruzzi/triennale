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
public class Immagine {

    private Long id;
    private Struttura struttura;
    private String nome;
    private byte[] bufferedImage;

    public Immagine() {
    }

    public Immagine(String nome) {
        this.nome = nome;
    }

    public Immagine(Struttura struttura, String nome, byte[] bufferedImage) {
        this.struttura = struttura;
        this.nome = nome;
        this.bufferedImage = bufferedImage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    @OneToOne(mappedBy = "immagine")
    public Struttura getStruttura() {
        return struttura;
    }

    public String getNome() {
        return nome;
    }
    
    public byte[] getBufferedImage() {
        return bufferedImage;
    }

    public void setStruttura(Struttura struttura) {
        this.struttura = struttura;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setBufferedImage(byte[] bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
    
    

}
