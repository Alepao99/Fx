/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arpacweb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author aless
 */
public class Modello {
    private String stazione;
    private String descrizione;
    private String sensore;
    private String um;
    private LocalDateTime data;
    private Double valore;

    public void setStazione(String stazione) {
        this.stazione = stazione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setSensore(String sensore) {
        this.sensore = sensore;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setValore(double valore) {
        this.valore = valore;
    }

    public String getStazione() {
        return stazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getSensore() {
        return sensore;
    }

    public String getUm() {
        return um;
    }

    public LocalDateTime getData() {
        return data;
    }

    public double getValore() {
        return valore;
    }
    
    public String getOra(){
        return data.format(DateTimeFormatter.ISO_TIME);
    }

    @Override
    public String toString() {
        return "stazione= " + stazione + ", descrizione= " + descrizione + ", sensore= " + sensore + ", um= " + um + ", data=" + data + ", valore= " + valore;
    }
    
    
}
