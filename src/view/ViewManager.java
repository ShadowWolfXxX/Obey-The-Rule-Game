/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author HP
 */
public class ViewManager extends Stage {

    private final Scene meun;
    private final Scene option;
    private final Scene Choose;
    public static ViewManager instance;

    public static ViewManager getInstance() throws IOException {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }
    
    private ViewManager() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/meun.fxml"));
        meun = new Scene(load.load());

        FXMLLoader load2 = new FXMLLoader(getClass().getResource("/view/Choose.fxml"));
        Choose = new Scene(load2.load());

        FXMLLoader load3 = new FXMLLoader(getClass().getResource("/view/Option.fxml"));
        option = new Scene(load3.load());
        
        this.setScene(meun);
        this.initStyle(StageStyle.UNDECORATED);
        this.show();
    }

    

    public void changeTOChoose() {
        this.setScene(Choose);
    }

    public void changeTOmeun() {
        this.setScene(meun);
    }

    public void changeToOption() {
        this.setScene(option);
    }
}
