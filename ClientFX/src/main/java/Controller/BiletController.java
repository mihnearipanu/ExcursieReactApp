package Controller;

import autogara.domain.Bilet;
import autogara.domain.Excursie;
import Service.IService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BiletController {
    @FXML
    private TextField textFieldIdExcursie;
    @FXML
    private TextField textFieldNumeClient;
    @FXML
    private TextField textFieldNrTel;
    @FXML
    private TextField textFieldNrLocuri;

    private IService serv;
    Stage dialog;
    Excursie e;

    public void setService(IService serv, Stage dialog, Excursie e){
        this.serv = serv;
        this.dialog = dialog;
        this.e = e;
        setFields(e);
    }

    private void setFields(Excursie e){
        textFieldIdExcursie.setText(e.getId());
        textFieldIdExcursie.setEditable(false);
        textFieldNumeClient.setText("");
        textFieldNrTel.setText("");
        textFieldNrLocuri.setText("");
    }

    @FXML
    public void handleRezerva(){
        try {
            String id = textFieldNrTel.getText();
            String idExcursie = textFieldIdExcursie.getText();
            String numeClient = textFieldNumeClient.getText();
            Integer nrTel = Integer.parseInt(textFieldNrTel.getText());
            Integer nrLocuri = Integer.parseInt(textFieldNrLocuri.getText());
            Bilet b = new Bilet(id, idExcursie, numeClient, nrTel, nrLocuri);
            if(e.getNrLocuri() < b.getNrLocuri()){
                Alert mesaj = new Alert(Alert.AlertType.ERROR);
                mesaj.setTitle("Eroare!");
                mesaj.setContentText("Mai sunt doar "+ e.getNrLocuri() +" locuri diponibile!");
                mesaj.showAndWait();
            }
            else{
                serv.rezervare(b);
                Alert mesaj = new Alert(Alert.AlertType.INFORMATION);
                mesaj.setTitle("Status");
                mesaj.setContentText("Rezervare efectuata!");
                mesaj.showAndWait();
                dialog.close();
            }
        }catch(NumberFormatException e){
            Alert mesaj = new Alert(Alert.AlertType.ERROR);
            mesaj.setTitle("Eroare!");
            mesaj.setContentText("Doar numere sunt permise in campurile Numar Telefon si Nrumar Locuri!");
            mesaj.showAndWait();
        }
    }

    @FXML
    public void handleCancel(){
        dialog.close();
    }
}
