/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package givemeproject;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;

/**
 *
 * @author HP
 */
public class Main extends Application{
    
     public static void main(String[] args) {
         launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
      ViewManager.getInstance();
    }
}
