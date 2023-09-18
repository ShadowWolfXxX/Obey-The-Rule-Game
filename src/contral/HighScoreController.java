/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contral;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class HighScoreController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label score;
    @FXML
    private Button doneBTN;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {        
          File savedScore = new File("src\\saveFile\\Score.txt");
            Scanner sc = new Scanner(savedScore);
            int count = 0;
            while (sc.hasNext()) {
                if (count % 2 == 0) {
                    name.setText(name.getText() + "\n" + sc.next());
                } else {
                    score.setText(score.getText() + "\n" + sc.next());
                }
                count++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HighScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void done(ActionEvent event) {
        try {
            GameController.st.close();
            AudioBackground.getInstance().makeSong(0.2, "Plante");
        } catch (IOException ex) {
            Logger.getLogger(HighScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
