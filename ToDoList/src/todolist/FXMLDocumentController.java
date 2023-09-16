/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todolist;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 *
 * @author clari
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField eventField;
    @FXML
    private DatePicker datePick;
    @FXML
    private TableView<Evento> mainTab;
    @FXML
    private TableColumn<Evento, LocalDate> dateClm;
    @FXML
    private TableColumn<Evento, String> eventClm;
    
    ObservableList<Evento> eventi;
    @FXML
    private Button addButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private MenuItem rmMenu;
    @FXML
    private MenuItem expMenu;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        eventi = FXCollections.observableArrayList();
        
        caricaEventi();
        
        datePick.setValue(LocalDate.now());
        
        dateClm.setCellValueFactory(new PropertyValueFactory<Evento, LocalDate>("data"));
        eventClm.setCellValueFactory(new PropertyValueFactory<Evento, String>("descrizione"));
        
        eventClm.setCellFactory(TextFieldTableCell.forTableColumn());
        
        mainTab.setItems(eventi);
        
        addButton.disableProperty().bind(eventField.textProperty().isEmpty());
        
        SimpleListProperty spl = new SimpleListProperty(eventi);
        
        rmMenu.disableProperty().bind(spl.emptyProperty());
        
        expMenu.disableProperty().bind(spl.emptyProperty());

        
        Thread t = new Thread(new TimedSaving(eventi));
        
        t.setDaemon(true);
        
        t.start();

    }

    @FXML
    private void addEvent(ActionEvent event) {
        eventi.add(new Evento(datePick.getValue(), eventField.getText()));
        refresh();

    }

    @FXML
    private void delEvent(ActionEvent event) {
        Evento e = mainTab.getSelectionModel().getSelectedItem();
        
        eventi.remove(e);
    }
    
    private void refresh() {
        
        datePick.setValue(LocalDate.now());
        eventField.clear();
        eventi.sort(null);
        
    }
    
    private void caricaEventi() {
        
        ArrayList<Evento> list = null;
        
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("saved.dat")))) {
            
            list = (ArrayList<Evento>) ois.readObject();
            
            eventi.addAll(list);
            
            Evento.setEventcount(eventi.size());
            
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Going to create a new one.");
        } catch (IOException  ex) {
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void importCSV(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Apri File...");
        File file = fc.showOpenDialog(rootPane.getScene().getWindow());
        if (file != null) {
            try (Scanner i = new Scanner(new BufferedReader(new FileReader(file)))) {
                
                i.useLocale(Locale.ITALY);
                i.useDelimiter(";|\n");
                
                eventi.clear();
                while (i.hasNext()) {
                    
                    String data = i.next();
                    String descrizione = i.next().replaceAll("\\|", ";");
                    
                    Evento e = new Evento(LocalDate.parse(data), descrizione);
                    
                    eventi.add(e);

                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } 

        }
    }

    @FXML
    private void exportCSV(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.setTitle("Salva come...");
        File file = fc.showSaveDialog(rootPane.getScene().getWindow());
        if (file != null) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                
                for (Evento e : eventi) {
                    
                    String replaced = e.getDescrizione().replaceAll(";", "|");
                    
                    bw.write(e.getData().toString() + ";" + replaced + '\n');
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void updateEvent(TableColumn.CellEditEvent<Evento, String> event) {
        
        Evento e = mainTab.getSelectionModel().getSelectedItem();
        
        e.setDescrizione(event.getNewValue());
        
    }
}