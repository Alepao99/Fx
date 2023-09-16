/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memooriginal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author aless
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField titoloFld;
    @FXML
    private TextField testoFld;
    @FXML
    private Button creaMemo;
    @FXML
    private Button pulisci;
    @FXML
    private Button confirmModify;
    @FXML
    private Button annullaModifiche;
    @FXML
    private ListView<MemoClass> listView;
    private ObservableList<MemoClass> list;
    @FXML
    private Button modificaMemo;
    @FXML
    private Button cancellaMemo;
    @FXML
    private Button salvaSuFile;
    private String support;
    private final String file = "Backup";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.observableArrayList();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<MemoClass> memo = new ArrayList<>();
            memo = (ArrayList<MemoClass>) in.readObject();
            for (MemoClass x : memo) {
                list.add(x);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File non trovato");
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Errore in file");
        }
        
        listView.setItems(list);
        confirmModify.setDisable(true);
        annullaModifiche.setDisable(true);
        
        ScheduledService<Void> serviceSave = new ScheduledService<Void>(){
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>(){
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new ClassSave(list,file,latch));
                        latch.await();
                        return null;
                    }
                    
                };
            }
            
        }; 
        serviceSave.setPeriod(Duration.seconds(300));
        serviceSave.start();
        
}    

    @FXML
    private void creaMemoAct(ActionEvent event) {
        if(titoloFld.getText().equalsIgnoreCase("")){
            Alert a = new Alert(Alert.AlertType.ERROR,"Titolo vuoto -> inserisci titolo!",ButtonType.OK);
            a.showAndWait();
        }else{
            list.add(new MemoClass(titoloFld.getText(),testoFld.getText()));
        }
        this.pulisciAct(event);
            
    }

    @FXML
    private void pulisciAct(ActionEvent event) {
        
        titoloFld.setText("");
        testoFld.setText("");
                    titoloFld.setDisable(false);
            testoFld.setDisable(false);
            creaMemo.setDisable(false);
    }

    @FXML
    private void confirmModifyAct(ActionEvent event) {
        for(MemoClass x : list)
            if(x.getTitolo().equalsIgnoreCase(titoloFld.getText())){
                x.setTesto(testoFld.getText());
                break;
            }
            creaMemo.setDisable(false);
            cancellaMemo.setDisable(false);
            pulisci.setDisable(false);
            modificaMemo.setDisable(false);
            confirmModify.setDisable(true);
            annullaModifiche.setDisable(true);
            titoloFld.setDisable(false);
             titoloFld.setDisable(true);
            testoFld.setDisable(true);
    }

    @FXML
    private void annullaModificheAct(ActionEvent event) {
            testoFld.setText(support);
            creaMemo.setDisable(false);
            cancellaMemo.setDisable(false);
            pulisci.setDisable(false);
            modificaMemo.setDisable(false);
            confirmModify.setDisable(true);
            annullaModifiche.setDisable(true);
            titoloFld.setDisable(false);
             titoloFld.setDisable(true);
            testoFld.setDisable(true);
            
    }
    
    @FXML
    private void modificaMemoAct(ActionEvent event) {
        testoFld.setDisable(false);
        MemoClass c;
        if((c = listView.getSelectionModel().getSelectedItem()) != null){
            support = c.getTesto();
            creaMemo.setDisable(true);
            cancellaMemo.setDisable(true);
            pulisci.setDisable(true);
            modificaMemo.setDisable(true);
            confirmModify.setDisable(false);
            annullaModifiche.setDisable(false);
            titoloFld.setDisable(true);
        }
    }

    @FXML
    private void cancellaMemoAct(ActionEvent event) {
        this.pulisciAct(event);
        list.remove(listView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void salvaSuFileAct(ActionEvent event) {
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
        
    }

    @FXML
    private void setTittleText(MouseEvent event) {
        MemoClass c;
        if((c=listView.getSelectionModel().getSelectedItem())!=null){
            titoloFld.setText(c.getTitolo());
            testoFld.setText(c.getTesto());
            titoloFld.setDisable(true);
            testoFld.setDisable(true);
            creaMemo.setDisable(true);
        }
    }
    
}
