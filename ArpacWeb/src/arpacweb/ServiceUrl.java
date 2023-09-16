/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arpacweb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author aless
 */
class ServiceUrl extends Service<ObservableList<Modello>>{

    private final String url;
    private final int max;

    public ServiceUrl(String url, int max) {
        this.url = url;
        this.max = max;
    }
    
    @Override
    protected Task<ObservableList<Modello>> createTask() {
        return new Task<ObservableList<Modello>>(){
            @Override
            protected ObservableList<Modello> call() throws Exception {
                ObservableList<Modello> list = FXCollections.observableArrayList();
                
                Modello m;
                
                try(BufferedReader i = new BufferedReader(new InputStreamReader(new URL(url).openStream()))){
                    i.readLine();
                    Scanner in = new Scanner(i);
                    in.useLocale(Locale.US);
                    in.useDelimiter(",|\n");
                    
                    int count = 1;
                    while(in.hasNext() && count<=max){
                        m = new Modello();
                        m.setStazione(in.next());
                        m.setDescrizione(in.next());
                        m.setSensore(in.next());
                        m.setUm(in.next());
                        m.setData(LocalDateTime.parse(in.next(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssx")));
                        m.setValore(in.nextDouble());
                        
                        if (m.getSensore().equalsIgnoreCase("Temperatura aria") || m.getSensore().equalsIgnoreCase("Precipitazione")){
                            list.add(m);
                            this.updateProgress(count, max);
                            count++;
                        }
                        m = null;
                    }
                    if(count-1!=max)
                        this.updateProgress(max, max);
                }
                return list;
            }
            
        };
    }
    
}
