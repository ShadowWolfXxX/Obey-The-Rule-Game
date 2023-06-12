/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contral;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import view.ViewManager;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class OptionController implements Initializable {

    @FXML
    private FlowPane boi;
    @FXML
    private Slider silderValue;
    @FXML
    private Button backBTN;
    @FXML
    private CheckBox muteBTN;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        silderValue.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            try {
                double value = (double) silderValue.getValue();
                muteBTN.setSelected(false);
                AudioBackground.getInstance().addVolume(value);
            } catch (IOException ex) {
                Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        ViewManager.getInstance().changeTOmeun();
    }

    @FXML
    private void mute(ActionEvent event) throws IOException {
        if (muteBTN.isSelected()) {
            AudioBackground.getInstance().mute();
        } else {
            AudioBackground.getInstance().addVolume(0);
        }
    }

}
