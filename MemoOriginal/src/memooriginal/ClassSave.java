/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memooriginal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author aless
 */
public class ClassSave implements Runnable {
    private ObservableList<MemoClass> list;
    private String file;
    private CountDownLatch latch;

    public ClassSave(ObservableList<MemoClass> list, String file, CountDownLatch latch) {
        this.list = list;
        this.file = file;
        this.latch = latch;
    }
        
    @Override
    public void run() {
        try{
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            ArrayList<MemoClass> memo = new ArrayList<>();
            for(MemoClass x : list)
                memo.add(x);
            out.writeObject(memo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }finally{
            latch.countDown();
        }
    }
    
}
