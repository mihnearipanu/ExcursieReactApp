import Controller.ExcursiiController;
import Controller.LoginController;
import ObjectProtocol.ServiceProxy;
import Service.IService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        IService service = new ServiceProxy("localhost", 55555);
        primaryStage.setTitle("Login");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("login.fxml"));
        AnchorPane layout = loader.load();
        primaryStage.setScene(new Scene(layout));

        FXMLLoader loaderExcursii = new FXMLLoader();
        loaderExcursii.setLocation(getClass().getClassLoader().getResource("excursii.fxml"));
        AnchorPane layoutExcursii = loaderExcursii.load();
        ExcursiiController excursiiController = loaderExcursii.getController();

        LoginController loginController = loader.getController();
        loginController.setService(service, excursiiController, primaryStage);

        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                service.logout(loginController.getUsername());
            }
        });
    }

    public static void main(String[] args){
        launch(args);
    }
}
