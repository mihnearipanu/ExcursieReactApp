package Controller;

import autogara.domain.User;
import Service.IService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordField;

    private IService service;
    Stage dialog;
    private String username;
    private ExcursiiController excursiiController;

    @FXML
    private void initialize(){}

    public void setService(IService service, ExcursiiController excursiiController, Stage dialog){
        this.dialog = dialog;
        this.service = service;
        this.excursiiController = excursiiController;
        textFieldUsername.setText("admin");
        passwordField.setText("admin");
        AnchorPane pane = new AnchorPane();
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        textFieldUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                pane.requestFocus();
                firstTime.setValue(false);
            }
        });
    }

    @FXML
    public void handleLogin(){
        String textUsername = textFieldUsername.getText();
        String textPassword = passwordField.getText();
        if (!textUsername.equals("") && !textPassword.equals("")){
            try{
                service.login(new User(null, textUsername, textPassword), excursiiController);
                System.out.println("Utilizator Logat!");
                username = textUsername;
                dialog.close();

                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("excursii.fxml"));
                AnchorPane layout = loader.load();
                primaryStage.setScene(new Scene(layout));
                this.excursiiController = loader.getController();
                excursiiController.setService(service, primaryStage, this);
                primaryStage.show();
            }catch (Exception e){
                e.printStackTrace();
                Alert mesaj = new Alert(Alert.AlertType.ERROR);
                mesaj.setTitle("Eroare!");
                mesaj.setContentText("User sau parola gresita!");
                mesaj.showAndWait();
            }
        }else{
            Alert mesaj = new Alert(Alert.AlertType.ERROR);
            mesaj.setTitle("Eroare!");
            mesaj.setContentText("Casutele nu pot fi goale!");
            mesaj.showAndWait();
        }
//        if(!service.verifica(textUsername, textPassword)){
//            Alert mesaj = new Alert(Alert.AlertType.ERROR);
//            mesaj.setTitle("Eroare!");
//            mesaj.setContentText("User sau parola gresita!");
//            mesaj.showAndWait();
//        }else{
//            try {
//                username = textUsername;
//                IService servExcursie = new ServiceProxy("localhost", 55555);
//                Stage primaryStage = new Stage();
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getClassLoader().getResource("excursii.fxml"));
//                AnchorPane layout = loader.load();
//                primaryStage.setScene(new Scene(layout));
//
//                ExcursiiController excursiiController = loader.getController();
//                excursiiController.setService(servExcursie, primaryStage);
//                dialog.close();
//                primaryStage.show();
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//        }
    }

    public String getUsername() {
        return username;
    }
}
