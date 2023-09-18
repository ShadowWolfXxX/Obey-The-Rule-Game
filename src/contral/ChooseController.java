/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contral;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ViewManager;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ChooseController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button restFood;
    @FXML
    private Button mathRoom;

    public static Stage game;

    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //just custom the curser
            File cur = new File("src\\image\\curser.png");
            Image image = new Image(cur.toURI().toString()); 
            root.setCursor(new ImageCursor(image));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChooseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        ViewManager.getInstance().changeTOmeun();
    }

    @FXML
    private void toRestFood(ActionEvent event) throws IOException {
        FXMLLoader load2 = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        Scene s = new Scene(load2.load());
        Image image = new Image(new FileInputStream("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\curser.png"));  
        s.setCursor(new ImageCursor(image));
        game = new Stage();
        game.setScene(s);
        game.setFullScreen(true);
        game.show();
    }

    @FXML
    private void toMathRoom(ActionEvent event) throws IOException {
        FXMLLoader load2 = new FXMLLoader(getClass().getResource("/view/SchoolMath.fxml"));
        Scene s = new Scene(load2.load());
         File cur = new File("src\\image\\curser.png");
        Image image = new Image(cur.toURI().toString());  
        s.setCursor(new ImageCursor(image));
        game = new Stage();
        game.setScene(s);
        game.setFullScreen(true);
        game.show();
    }

}
