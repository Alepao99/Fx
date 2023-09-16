/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Magnitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
/**
 *
 * @author clari
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<EQEvent> mainTab;
    @FXML
    private TableColumn<EQEvent, LocalDateTime> dateCln;
    @FXML
    private TableColumn<EQEvent, Double> magnitudeCln;
    @FXML
    private TableColumn<EQEvent, String> locationCln;
    @FXML
    private ProgressIndicator indicator;

    private ObservableList<EQEvent> events;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        events = FXCollections.observableArrayList();
        dateCln.setCellValueFactory(new PropertyValueFactory<EQEvent, LocalDateTime>("time"));
        magnitudeCln.setCellValueFactory(new PropertyValueFactory<EQEvent, Double>("magnitude"));
        locationCln.setCellValueFactory(new PropertyValueFactory<EQEvent, String>("eventLocationName"));

        mainTab.setItems(events);
        
    }

    @FXML
    private void loadData(ActionEvent event) throws Exception {

        URLRead service = new URLRead();
        service.setUrl("http://webservices.ingv.it/fdsnws/event/1/query?starttime=2020-11-18T00%3A00%3A00&endtime=2020-11-25T23%3A59%3A59&minmag=2&maxmag=10&mindepth=-10&maxdepth=1000&minlat=-90&maxlat=90&minlon=-180&maxlon=180&minversion=100&orderby=time-asc&format=text&limit=10000");
        Task task = service.createTask();
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        indicator.visibleProperty().bind(task.runningProperty());
        //new Thread(task).start();
        
    }

    public class URLRead extends Service<ObservableList> {

        private String url;

        public final void setUrl(String value) {
            this.url = value;
        }

        public final String getUrl() {
            return this.url;
        }

        @Override
        protected Task<ObservableList> createTask() {
            return new Task<ObservableList>() {

                String s = "";

                @Override
                protected ObservableList call() throws IOException, MalformedURLException {
                    URL u = new URL(url);
                    BufferedReader buf = new BufferedReader(new InputStreamReader(u.openStream()));
                    String line;
                    line = buf.readLine();
                    while ((line = buf.readLine()) != null) {
                        s += line + "|";
                    }
                        
                    //ObservableList<EQEvent> list = FXCollections.observableArrayList();
                    try (Scanner i = new Scanner(new BufferedReader(new StringReader(s)))) {

                        EQEvent e;
                        i.useLocale(Locale.US);
                        i.useDelimiter("\\||\\n"); // espressione regolare con OR logico dei caratteri pipe e fine linea

                        while (i.hasNext()) {
                            e = new EQEvent();
                            e.setEventID(i.next());
                            e.setTime(LocalDateTime.parse(i.next()));
                            e.setLatitude(i.nextDouble());
                            e.setLongitude(i.nextDouble());
                            e.setDepthkm(i.nextDouble());
                            e.setAuthor(i.next());
                            e.setCatalog(i.next());
                            e.setContributor(i.next());
                            e.setContributorID(i.next());
                            e.setMagType(i.next());
                            e.setMagnitude(i.nextDouble());
                            e.setMagAuthor(i.next());
                            e.setEventLocationName(i.next());
                            events.add(e);
                        }
                    }
                    return events;
                }
                
            };
        }
    }
}
