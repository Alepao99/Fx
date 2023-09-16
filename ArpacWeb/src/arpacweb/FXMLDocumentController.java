/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arpacweb;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author aless
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private DatePicker datePick;
    @FXML
    private TextField limitFld;
    @FXML
    private ProgressIndicator progressIndi;
    @FXML
    private TableColumn<Modello, String> luogo;
    @FXML
    private TableColumn<Modello, String> sensore;
    @FXML
    private TableColumn<Modello, String> ora;
    @FXML
    private TableColumn<Modello, String> valore;
    @FXML
    private TableColumn<Modello, String> unitaDiMisura;
    @FXML
    private TableView<Modello> mainTab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        luogo.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        sensore.setCellValueFactory(new PropertyValueFactory<>("sensore"));
        ora.setCellValueFactory(new PropertyValueFactory<>("ora"));
        valore.setCellValueFactory(new PropertyValueFactory<>("valore"));
        unitaDiMisura.setCellValueFactory(new PropertyValueFactory<>("um"));
        
        datePick.setValue(LocalDate.now());
        limitFld.setText("100");
    }    

    @FXML
    private void getDati(ActionEvent event) {
        String data = datePick.getValue().format(DateTimeFormatter.BASIC_ISO_DATE);
        String url = "http://cemec.arpacampania.it/meteoambientecampania/prodotti/meteo/arpac_dati_centraline_" + data + "_meteo.csv";
        
        ServiceUrl service = new ServiceUrl(url,Integer.parseInt(limitFld.getText()));
        
        progressIndi.progressProperty().bind(service.progressProperty());
        mainTab.itemsProperty().bind(service.valueProperty());
        service.start();
        
    }
    
}
