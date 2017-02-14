/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatefileoperation;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author Yzaaaa
 */
public class StartMenu extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLの読み込み
        URL fxmlLocation = getClass().getResource("StartMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader( fxmlLocation );
        
        //シーンの作成
        Pane root = (Pane)fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("File Operation");
        
        //ウィンドウの表示
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
