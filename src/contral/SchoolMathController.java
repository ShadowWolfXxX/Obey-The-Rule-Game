/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contral;

import static contral.GameController.st;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.ViewManager;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class SchoolMathController implements Initializable {

    @FXML
    private Label number1;
    @FXML
    private Label opreation;
    @FXML
    private Label number2;
    @FXML
    private TextField answer;
    @FXML
    private Button submit;
    @FXML
    private Button delete;
    @FXML
    private ImageView person;
    @FXML
    private Button back;
    @FXML
    private Label score;
    @FXML
    private Label timer;
    @FXML
    private VBox HighScoreScreen;
    @FXML
    private TextField HighScoreName;
    @FXML
    private Button submitNameBTN;
    String wholetext = "";
    int scoreNum = 0;
    int randnum1 = 0;
    int randnum2 = 0;
    int opreationNumber = 0;
    int personTexuter = 2;
    Media correctSound;
    Media failedSound;
    boolean noSpam = true;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            HighScoreScreen.setVisible(false);
            AudioBackground.getInstance().makeSong(0.2, "Mathbackground");
            correctSound = new Media(new File(getClass().getResource("/audio/curret.wav").getPath()).toURI().toString());
            failedSound = new Media(new File(getClass().getResource("/audio/Wrong.wav").getPath()).toURI().toString());
            Image img = new Image(new FileInputStream("src\\image\\Need.png"));
            person.setImage(img);
        } catch (IOException ex) {
            Logger.getLogger(SchoolMathController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void doSubmit(ActionEvent event) {
        wholetext = ""; //restat the text for new quetion
        checkanswer();
    }

    @FXML
    private void doDelete(ActionEvent event) {
        if (answer.getText().equals("") || answer.getText().equals("press yes") || answer.getText().matches("^[a-z]+$")) {
            answer.requestFocus();
            answer.end();
        } else {
            answer.setText(answer.getText().substring(0, answer.getText().length() - 1));//get ride of last chacter
            if (!answer.getText().matches("^[a-z]+$")) {
                wholetext = wholetext.substring(0, wholetext.length() - 1);
            }
            answer.requestFocus();
            answer.end();
        }
    }

    @FXML
    private void type(KeyEvent event) {
        //only 5 latter can take
        answer.setTextFormatter(new TextFormatter<String>((Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 5) {
                return null;
            } else {
                return change;
            }
        }));

        answer.setOnKeyReleased((key) -> {
            if(answer.getText().equals("press yes")){
                
            }else{
            if (key.getText().matches("^[0-9]+$") || key.getText().equals("-")) {
                if (wholetext.length() < 5) {//cuz the format can't take more than 5
                    wholetext += key.getText();
                }
            } else {

            }
            answer.clear();
            answer.setText(wholetext);
            answer.requestFocus();
            answer.end();
        }
        });
        
    }

    public void question() {
        number1.setText("");
        number2.setText("");
        opreation.setText("");
        answer.setText("");

        String[] op = {"+", "-", "×", "÷"};

        int randop = (int) Math.floor(Math.random() * 4);
        opreationNumber = randop;

        if (randop == 1 || randop == 0) {
            randnum1 = (int) Math.floor(Math.random() * 99);
            randnum2 = (int) Math.floor(Math.random() * 99);
        } else {
            randnum1 = (int) Math.floor(Math.random() * 99);
            randnum2 = (int) Math.floor(Math.random() * 9);
            while (randnum2 == 0) {
                randnum2 = (int) Math.floor(Math.random() * 9);
            }
        }

        number1.setText(randnum1 + "");
        number2.setText(randnum2 + "");
        opreation.setText(op[randop]);
        answer.requestFocus();
        answer.end();
    }

    public void checkanswer() {
        if (noSpam) {
            noSpam = false;
            int finalAnswer = 0;

            Thread thread2 = new Thread() {
                @Override
                public void run() {
                    int mint = 4, sec = 00;
                    while (mint >= 0 && sec >= 0) {
                        try {
                            Thread.sleep(1000);
                            changeLabel(timer, mint, sec);
                            if (sec == 0) {
                                mint--;
                                sec = 60;
                            } else {
                                sec--;
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            if (opreationNumber == 0) {
                finalAnswer = randnum1 + randnum2;
            } else if (opreationNumber == 1) {
                finalAnswer = randnum1 - randnum2;
            } else if (opreationNumber == 2) {
                finalAnswer = randnum1 * randnum2;
            } else {
                finalAnswer = randnum1 / randnum2;
            }

            if (answer.getText().equals(finalAnswer + "")) {
                MediaPlayer clickyS1 = new MediaPlayer(correctSound);
                clickyS1.play();
                clickyS1.setVolume(0.8);
                scoreNum += 5;
            } else if (answer.getText().equals("press yes")) {
                thread2.start();
                answer.setEditable(true);
            } else {
                MediaPlayer clickyS2 = new MediaPlayer(failedSound);
                clickyS2.play();
                clickyS2.setVolume(0.2);
                scoreNum -= 5;
            }
            score.setText(scoreNum + "");

            try {
                Image img = new Image(new FileInputStream(textuer()));
                person.setImage(img);
                PauseTransition wait = new PauseTransition(Duration.seconds(2));
                wait.setOnFinished((e) -> {
                    try {
                        Image img2 = new Image(new FileInputStream(textuer()));
                        person.setImage(img2);
                        question();
                        noSpam = true;
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                wait.play();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void changeLabel(final Label label, final int min, final int sec) {

        Platform.runLater(() -> {
            //if timer 30 secand left
            if (min == 0 && sec == 30) {
                try {
                    AudioBackground.getInstance().makeSong(1, "HorroMathBackground");
                    personTexuter *= -1;
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //if timer is done
            if (min == 0 && sec == 0) {
                HighScoreScreen.setVisible(true);
            }
            label.setText(min + ":" + sec);
        });
    }

    public String textuer() {
        String url = "";

        if (personTexuter == 1) {
            url = "src\\image\\Need.png";
            personTexuter = 2;
        } else if (personTexuter == 2) {
            url = "src\\image\\Feed.png";
            personTexuter = 1;
        } else if (personTexuter == -1) {
            url = "src\\image\\HorroNeed.png";
            personTexuter = -2;
        } else if (personTexuter == -2) {
            url = "src\\image\\HorroFeed.png";
            personTexuter = -1;
        }

        return url;
    }

    @FXML
    private void SubmitHighScore(ActionEvent event) {
        try {
            ChooseController.game.close();
            ViewManager.getInstance().changeTOmeun();
            File savedScore = new File("src\\saveFile\\Score.txt");
            if (!savedScore.exists()) {
                savedScore.createNewFile();
            }
            FileWriter fw = new FileWriter(savedScore, true);
            fw.append(HighScoreName.getText() + " " + scoreNum + "\n");
            fw.close();
            FXMLLoader load3 = new FXMLLoader(getClass().getResource("/view/HighScore.fxml"));
            Scene s = new Scene(load3.load());
            Image image = new Image(new FileInputStream("src\\image\\curser.png"));  //pass in the image path
            s.setCursor(new ImageCursor(image));
            st = new Stage();
            st.setScene(s);
            st.setFullScreen(true);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        AudioBackground.getInstance().makeSong(0.2, "Plante");
        ChooseController.game.close();
    }

}
