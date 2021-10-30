package de.leonheuer.pwgen;

import de.leonheuer.pwgen.controller.HomeController;
import de.leonheuer.pwgen.controller.TrayController;
import de.leonheuer.pwgen.manager.DataManager;
import de.leonheuer.pwgen.util.Util;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PasswordGen extends Application {

    private static PasswordGen instance;
    private DataManager dm;
    private HomeController ct;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        registerClasses();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("home.fxml"));
        Parent root = loader.load();
        ct = loader.getController();

        Platform.setImplicitExit(false);
        SwingUtilities.invokeLater(this::showTray);

        stage = primaryStage;
        stage.setTitle("PasswordGen");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon_large.png")));
        stage.show();

        ct.onStartup();
    }

    private void registerClasses() {
        instance = this;
        dm = new DataManager();
    }

    private void showTray() {
        try {
            new TrayController(this);
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static PasswordGen getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dm;
    }

    public HomeController getHomeController() {
        return ct;
    }

    public Stage getStage() {
        return stage;
    }

}
