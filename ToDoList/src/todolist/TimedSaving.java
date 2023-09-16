/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todolist;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author clari
 */
public class TimedSaving implements Runnable {

    ObservableList<Evento> eventi;

    public TimedSaving(ObservableList<Evento> eventi) {
        this.eventi = eventi;
    }

    @Override
    public void run() {
        
        while (!Thread.currentThread().isInterrupted()) {
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimedSaving.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            synchronized (eventi) {
                
                try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("saved.dat")))) {
                    oos.writeObject(new ArrayList<Evento>(eventi));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TimedSaving.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    
                }
            }
        }
    }
    
}