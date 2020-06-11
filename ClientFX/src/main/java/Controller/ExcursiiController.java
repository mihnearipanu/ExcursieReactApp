package Controller;

import autogara.domain.Excursie;
import ObjectProtocol.ServiceProxy;
import Observer.Observer;
import Service.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class ExcursiiController implements Observer {

    ObservableList<Excursie> model = FXCollections.observableArrayList();

    @FXML
    TableView<Excursie> tableView;
    @FXML
    private TableColumn<Excursie, String> tableColumnObiectiv;
    @FXML
    private TableColumn<Excursie, String> tableColumnComp;
    @FXML
    private TableColumn<Excursie, String> tableColumnOra;
    @FXML
    private TableColumn<Excursie, String> tableColumnPret;
    @FXML
    private TableColumn<Excursie, String> tableColumnNrLocuri;
    @FXML
    private TextField textFieldObiectiv;
    @FXML
    private TextField textFieldOraPlecare1;
    @FXML
    private TextField textFieldOraPlecare2;
    @FXML
    private TextField textFieldMinPlecare1;
    @FXML
    private TextField textFieldMinPlecare2;

    IService serv;
    Stage dialog;
    LoginController loginController;

    @FXML
    private void initialize(){}

    public void setService(IService serv, Stage dialog, LoginController loginController){
        this.loginController = loginController;
        this.dialog = dialog;
        this.serv = serv;
        initModel();
        setTable();
        setFields();
    }

    @FXML
    private void setTable(){
        tableColumnObiectiv.setCellValueFactory(new PropertyValueFactory<Excursie, String>("obiectiv"));
        tableColumnComp.setCellValueFactory(new PropertyValueFactory<Excursie, String>("numeCompTrans"));
        tableColumnOra.setCellValueFactory(new PropertyValueFactory<Excursie, String>("oraPlecarii"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<Excursie, String>("pret"));
        tableColumnNrLocuri.setCellValueFactory(new PropertyValueFactory<Excursie, String>("nrLocuri"));
        tableView.setRowFactory(table -> new TableRow<Excursie>() {
            @Override
            public void updateItem(Excursie item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getNrLocuri() == 0) {
                    setStyle("-fx-background-color: red;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void initModel(){
        try {
            ArrayList<Excursie> excursii = serv.getAll();
            model.setAll(excursii);
            tableView.setItems(model);
            tableView.refresh();
            tableView.getSelectionModel().select(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initModel(String obiectiv, LocalTime ora1, LocalTime ora2){
        try {
            ArrayList<Excursie> excursii = serv.getFiltrare(obiectiv, ora1, ora2);
            model.setAll(excursii);
            tableView.setItems(model);
            tableView.refresh();
            tableView.getSelectionModel().select(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setFields(){
        textFieldObiectiv.setText("Piatra Neamt");
        textFieldOraPlecare1.setText("10");
        textFieldOraPlecare2.setText("17");
        textFieldMinPlecare1.setText("00");
        textFieldMinPlecare2.setText("00");
    }

    public void handleCautare(){
        try{
            String obiectiv = textFieldObiectiv.getText();
            LocalTime ora1 = LocalTime.of(Integer.parseInt(textFieldOraPlecare1.getText()), Integer.parseInt(textFieldMinPlecare1.getText()));
            LocalTime ora2 = LocalTime.of(Integer.parseInt(textFieldOraPlecare2.getText()), Integer.parseInt(textFieldMinPlecare2.getText()));
            if(obiectiv.equals("")){
                Alert mesaj = new Alert(Alert.AlertType.ERROR);
                mesaj.setTitle("Eroare!");
                mesaj.setContentText("Numele obiectivului nu poate fi gol!");
                mesaj.showAndWait();
            }else{
                initModel(obiectiv, ora1, ora2);
            }
        }catch(NumberFormatException ex){
            Alert mesaj = new Alert(Alert.AlertType.ERROR);
            mesaj.setTitle("Eroare!");
            mesaj.setContentText("Nu ati introdus ce trebuie in casutele de ora si minut!");
            mesaj.showAndWait();
        }
    }

    public void handleRezervare(){
        Excursie excursie = tableView.getSelectionModel().getSelectedItem();
        if(excursie != null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("bilet.fxml"));
                AnchorPane rad = loader.load();
                Stage dialog = new Stage();
                dialog.setTitle("Rezervare");
                dialog.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(rad);
                dialog.setScene(scene);

                BiletController biletController = loader.getController();
                biletController.setService(new ServiceProxy("localhost", 55555), dialog, excursie);

                dialog.show();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            Alert mesaj = new Alert(Alert.AlertType.ERROR);
            mesaj.setTitle("Eroare!");
            mesaj.setContentText("Va rugam selectati o excursie!");
            mesaj.showAndWait();
        }
        initModel();
    }

    public void handleRefresh(){
        initModel();
    }

    public void handleLogout() {
        try {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("login.fxml"));
            AnchorPane layout = loader.load();
            primaryStage.setScene(new Scene(layout));

            LoginController loginController = loader.getController();
            loginController.setService(new ServiceProxy("localhost", 55555), this, primaryStage);

            dialog.close();
            primaryStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateExcursii(ArrayList<Excursie> excursii) {
        tableView.getItems().clear();
        model.setAll(excursii);
        tableView.setItems(model);
        tableView.refresh();
        tableView.getSelectionModel().select(null);
    }
}
