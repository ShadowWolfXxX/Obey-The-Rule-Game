/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contral;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ViewManager;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class GameController implements Initializable {

    @FXML
    private ImageView person;
    @FXML
    private ImageView food1;
    @FXML
    private ImageView food2;
    @FXML
    private ImageView food3;
    boolean noSpam = true;
    boolean noSpam2 = true;
    ArrayList<String> resultList;
    private Label wantitLAB;
    @FXML
    private Label score;
    @FXML
    private Label timer;
    @FXML
    private Label wantit;
    @FXML
    private VBox HighScoreScreen;
    @FXML
    private TextField HighScoreName;
    @FXML
    private Button back;
    @FXML
    private Button submitNameBTN;

    String order;
    int currectAnswer = -1;
    int scoreNum = 0;
    int personTexuter = 2;
    static Stage st;
    File savedScore;
    Media eatingSound;
    Media correctSound;
    Media failedSound;

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
            AudioBackground.getInstance().makeSong(1, "FoodBackground");
            File foodAllImage = new File("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\food");
            savedScore = new File("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\saveFile\\Score.txt");
            correctSound = new Media(new File(getClass().getResource("/audio/curret.wav").getPath()).toURI().toString());
            eatingSound = new Media(new File(getClass().getResource("/audio/eating.wav").getPath()).toURI().toString());
            failedSound = new Media(new File(getClass().getResource("/audio/Wrong.wav").getPath()).toURI().toString());

            resultList = new ArrayList<>();
            File[] imageArray = foodAllImage.listFiles();
            for (File file : imageArray) {
                resultList.add(file.getCanonicalPath());
            }
            Image img = new Image(new FileInputStream("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\Need.png"));
            person.setImage(img);
            Image startCandy = new Image(new FileInputStream("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\Start.png"));
            food2.setImage(startCandy);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeLabel(final Label label, final int min, final int sec) {

        Platform.runLater(() -> {
            //time turn 30 secand left
            if (min == 0 && sec == 30) {
                try {
                    AudioBackground.getInstance().makeSong(1, "HorroFoodBackground");
                    personTexuter *= -1;
                    resultList.clear();
                    File foodAllImage = new File("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\HorroFood");
                    File[] imageArray = foodAllImage.listFiles();
                    for (File file : imageArray) {
                        resultList.add(file.getCanonicalPath());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //time done
            if (min == 0 && sec == 0) {
                HighScoreScreen.setVisible(true);
            }

            //Change label text
            label.setText(min + ":" + sec);
        });
    }

    public void scorePoint(int btn) {
        if (noSpam) {
            //timer
            Thread thread2 = new Thread() {
                @Override
                public void run() {
                    int mint = 2 , sec = 30;
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

            if ((currectAnswer == 0 && btn == 0) || (currectAnswer == 1 && btn == 1) || (currectAnswer == 2 && btn == 2)) {
                MediaPlayer clickyS1 = new MediaPlayer(correctSound);
                clickyS1.play();
                clickyS1.setVolume(0.3);
                scoreNum += 5;
            } else if (currectAnswer == -1) {
                thread2.start();
            } else {
                MediaPlayer clickyS2 = new MediaPlayer(failedSound);
                clickyS2.play();
                clickyS2.setVolume(0.2);
                scoreNum -= 5;

            }
            score.setText("" + scoreNum);
        }
    }

    public String textuer() {
        String url = "";

        if (personTexuter == 1) {
            url = "C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\Need.png";
            personTexuter = 2;
        } else if (personTexuter == 2) {
            url = "C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\Feed.png";
            personTexuter = 1;
        } else if (personTexuter == -1) {
            url = "C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\HorroNeed.png";
            personTexuter = -2;
        } else if (personTexuter == -2) {
            url = "C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\HorroFeed.png";
            personTexuter = -1;
        }

        return url;
    }

    public void eat() {
        if (noSpam) {
            noSpam = false;
            try {
                Image img = new Image(new FileInputStream(textuer()));
                person.setImage(img);
                MediaPlayer clickyS = new MediaPlayer(eatingSound);
                clickyS.play();
                clickyS.setVolume(0.4);
                PauseTransition wait = new PauseTransition(Duration.seconds(2));
                wait.setOnFinished((e) -> {
                    try {
                        Image img2 = new Image(new FileInputStream(textuer()));
                        person.setImage(img2);
                        setFood();
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

    public void setFood() throws FileNotFoundException {
      
            String[] want = new String[3];            
            PauseTransition wait = new PauseTransition(Duration.seconds(0));
            wait.setOnFinished((e) -> {
                Image img[] = new Image[3];
                String noRepeat = "";
                for (int i = 0; i < 3; i++) {
                    int x = (int) Math.floor(Math.random() * resultList.size());
                    if (!noRepeat.contains("" + x)) {
                        try {
                            img[i] = new Image(new FileInputStream(resultList.get(x)));
                            String neededfoodName = resultList.get(x).substring(resultList.get(x).lastIndexOf("\\") + 1, resultList.get(x).lastIndexOf("."));
                            want[i] = neededfoodName;
                            noRepeat += x + " ";
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        i--;
                    }
                }
                int y = (int) Math.floor(Math.random() * 3);
                wantit.setText(want[y]);
                currectAnswer = y;
                food1.setImage(img[0]);
                food2.setImage(img[1]);
                food3.setImage(img[2]);
            });
            wait.play();
        
    }

    @FXML
    private void SubmitHighScore(javafx.event.ActionEvent event) {
        try {
            ChooseController.game.close();
            ViewManager.getInstance().changeTOmeun();
            FileWriter fw = new FileWriter(savedScore, true);
            fw.append(HighScoreName.getText() + " " + scoreNum + "\n");
            fw.close();
            FXMLLoader load3 = new FXMLLoader(getClass().getResource("/view/HighScore.fxml"));
            Scene s = new Scene(load3.load());
            Image image = new Image(new FileInputStream("C:\\Users\\HP\\Documents\\NetBeansProjects\\givemeproject\\src\\image\\curser.png"));  
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
    private void foodD1(MouseEvent event) {

        food1.setOnMouseDragged(e -> {
            if (noSpam) {
                food1.setX(e.getX() - 150);
                food1.setY(e.getY() - 100);
            }
        });

        food1.setOnMouseReleased(e -> {
            if (noSpam) {
                food1.setX(0);
                food1.setY(0);
                food1.setImage(null);
                scorePoint(0);
                eat();
            }
        });

    }

    @FXML
    private void foodD2(MouseEvent event) {

        food2.setOnMouseDragged(e -> {
            if (noSpam) {
                food2.setX(e.getX() - 150);
                food2.setY(e.getY() - 100);
            }
        });

        food2.setOnMouseReleased(e -> {
            if (noSpam) {
                food2.setX(0);
                food2.setY(0);
                food2.setImage(null);
                scorePoint(1);
                eat();
            }
        });

    }

    @FXML
    private void foodD3(MouseEvent event) {

        food3.setOnMouseDragged(e -> {
            if (noSpam) {
                food3.setX(e.getX() - 150);
                food3.setY(e.getY() - 100);
            }
        });

        food3.setOnMouseReleased(e -> {
            if (noSpam) {
                food3.setX(0);
                food3.setY(0);
                food3.setImage(null);
                scorePoint(2);
                eat();
            }
        });

    }

    @FXML
    private synchronized void goBack(javafx.event.ActionEvent event) throws IOException {
        AudioBackground.getInstance().makeSong(0.2, "Plante");
        ChooseController.game.close();
    }

}
