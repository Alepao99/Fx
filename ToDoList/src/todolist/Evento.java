/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todolist;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author clari
 */
public class Evento implements Comparable<Evento>, Serializable {
    
    private int id;
    private LocalDate data;
    private String descrizione;
    private static int eventcount = 0;
    

    public Evento(LocalDate data, String descrizione) {
        this.data = data;
        this.descrizione = descrizione;
        this.id = eventcount;
        eventcount++;
    }
    
    public LocalDate getData() {
        return data;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }
    
    public String getDescrizione() {
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public int compareTo(Evento o) {
        if(this.data.equals(o.data)) return Integer.compare(this.id, o.id);
        
        return this.data.compareTo(o.data); 
    }

    public static int getEventcount() {
        return eventcount;
    }
    
    public static void setEventcount(int eventcount) {
        Evento.eventcount = eventcount;
    }
    
}