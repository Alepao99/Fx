/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memooriginal;

import java.io.Serializable;

/**
 *
 * @author aless
 */
public class MemoClass implements Serializable{
    private final String titolo;
    private String testo;

    public MemoClass(String titolo, String testo) {
        this.titolo = titolo;
        this.testo = testo;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public String toString() {
        return " " + titolo;
    }
    
    
}
