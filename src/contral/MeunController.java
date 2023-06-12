/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contral;

import static contral.GameController.st;
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
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import view.ViewManager;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MeunController implements Initializable {

    @FXML
    private FlowPane boi;
    @FXML
    private Button start;
    @FXML
    private Button option;
    @FXML
    private Button exit;
    @FXML
    private Button highScroeBTN;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\curser.png"));  
            boi.setCursor(new ImageCursor(image));
            AudioBackground.getInstance().makeSong(0.2, "Plante");
        } catch (IOException ex) {
            Logger.getLogger(MeunController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void StartGame(ActionEvent event) {
        ViewManager.instance.changeTOChoose();
    }

    @FXML
    private void OptionsMeun(ActionEvent event) {
        ViewManager.instance.changeToOption();
    }

    @FXML
    private void ExitDaGame(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void highScore(ActionEvent event) throws FileNotFoundException, IOException {
        FXMLLoader load3 = new FXMLLoader(getClass().getResource("/view/HighScore.fxml"));
        Scene s = new Scene(load3.load());
        Image image = new Image(new FileInputStream("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\curser.png"));  //pass in the image path
        s.setCursor(new ImageCursor(image));
        st = new Stage();
        st.setScene(s);
        st.setFullScreen(true);
        st.show();
    }

}
