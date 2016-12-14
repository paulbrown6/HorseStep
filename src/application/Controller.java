package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logistic.Logistic;
import score.Score;

import java.util.ArrayList;

public class Controller {

    public static Stage primaryStage;
    private Logistic log = new Logistic();
    private Boolean boolClickSound = true;
    private ArrayList<ImageView> soundImageList = new ArrayList<ImageView>();
    private Boolean startgame = false;
    private Integer os = 0;
    private ArrayList<Integer> oldStep = new ArrayList<Integer>();
    private ArrayList<Integer> nextStep;
    private Integer numberScore;
    private String name = "Unnamed";

    @FXML
    private ImageView sound;
    @FXML
    private ImageView soundg;
    @FXML
    private ImageView sounds;
    @FXML
    private ImageView soundi;
    @FXML
    private ImageView config;
    @FXML
    private BorderPane home;
    @FXML
    private BorderPane game;
    @FXML
    private BorderPane score;
    @FXML
    private BorderPane information;
    @FXML
    private BorderPane configurate;
    @FXML
    private Rectangle effectConfig;
    @FXML
    private GridPane gamepane;
    @FXML
    private Text textfail;
    @FXML
    private Text textscore;
    @FXML
    private ScrollPane scorePane;
    @FXML
    private Label scoreLabel;
    @FXML
    private TextField nameArea;
    @FXML
    private Button playButton;

    //Initialize

    public void initialize(){
        soundImageList.clear();
        oldStep.clear();
        startgame = false;
        numberScore = 0;
        os = 0;
        scoreLabel.setText("Score: 0");
        imageInitialize();
        gamePanelInitialize();
        scoreInitialize();
        System.out.println("Инициализация завершена!");
    }

    private void imageInitialize(){
        soundImageList.add(sound);
        soundImageList.add(soundg);
        soundImageList.add(soundi);
        soundImageList.add(sounds);
        for (ImageView img: soundImageList){
            img.setImage(new Image("file:resources/images/soundon.png"));
        }
        config.setImage(new Image("file:resources/images/configure.png"));
    }

    private void gamePanelInitialize(){
        int k = 0;
        for (int i = 0; i < 8; i++){
            for (int n = 0; n < 8; n++){
                Button b = new Button("");
                b.setPrefWidth(63.0);
                b.setPrefHeight(60.0);
                k++;
//                b.setText(k + "");
                gamepane.add(b, n, i);
                b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (startgame) {
                            for(Integer next: nextStep) {
                                if (gamepane.getChildren().indexOf(b)== next) {
                                    if(nextStep.size() != 0){
                                        for(Integer a : nextStep){
                                                ColorAdjust eff = new ColorAdjust();
                                                eff.setBrightness(0);
                                                gamepane.getChildren().get(a).setEffect(eff);
                                        }
                                    }
                                    System.out.println(gamepane.getChildren().indexOf(b));
                                    os = gamepane.getChildren().indexOf(b);
                                    oldStep.add(os);
                                    System.out.println("OldStep : " + oldStep);
                                    nextStep = new ArrayList<Integer>(log.nextStep(gamepane.getChildren().indexOf(b), oldStep));
                                    System.out.println("NextStep : " + nextStep);
                                    b.setVisible(false);
                                    numberScore += 5;
                                    scoreLabel.setText("Score: " + numberScore);
                                    if(nextStep.size() != 0){
                                        for(Integer a : nextStep){
                                            if (next == a){
                                            }
                                            else {
                                                ColorAdjust eff = new ColorAdjust();
                                                eff.setBrightness(-0.40);
                                                gamepane.getChildren().get(a).setEffect(eff);
                                            }
                                        }
                                    }
                                    if (nextStep.isEmpty()){
                                        textfail.setVisible(true);
                                        textfail.setDisable(false);
                                        textscore.setVisible(true);
                                        textscore.setDisable(false);
                                        textscore.setText("Score: " + numberScore);
                                        log.scoreAdd(new Score(name, numberScore));
                                        effectConfig.setVisible(true);
                                        effectConfig.setDisable(false);
                                        effectConfig.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                            @Override
                                            public void handle(MouseEvent event) {
                                                game.setVisible(false);
                                                game.setDisable(true);
                                                textfail.setVisible(false);
                                                textfail.setDisable(true);
                                                textscore.setVisible(false);
                                                textscore.setDisable(true);
                                                effectConfig.setVisible(false);
                                                effectConfig.setDisable(true);
                                                home.setVisible(true);
                                                home.setDisable(false);
                                                playButton.setText("Play");
                                                gamepane.getChildren().remove(0, 64);
                                                log = new Logistic();
                                                initialize();
                                            }
                                        });
                                    }
                                }
                            }
                        }else {
                            startgame = true;
                            os = gamepane.getChildren().indexOf(b);
                            oldStep.add(os);
                            System.out.println(gamepane.getChildren().indexOf(b));
                            nextStep = new ArrayList<Integer>(log.nextStep(gamepane.getChildren().indexOf(b), oldStep));
                            b.setVisible(false);
                            numberScore += 5;
                            scoreLabel.setText("Score: " + numberScore);
                            if(nextStep.size() != 0){
                                for(Integer a : nextStep){
                                        ColorAdjust eff = new ColorAdjust();
                                        eff.setBrightness(-0.40);
                                        gamepane.getChildren().get(a).setEffect(eff);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private void scoreInitialize(){
        ArrayList<Score> sc = log.scores();
//        VBox vb = new VBox();
//        HBox hb;
        TableView<Score> scoretable = new TableView<>();
        scoretable.setPrefHeight(530.0);
        scoretable.setPrefWidth(750.0);
        TableColumn<Score, String> scoretablename = new TableColumn<>();
        scoretablename.setPrefWidth(500.0);
        TableColumn<Score, Integer> scoretableresult = new TableColumn<>();
        scoretableresult.setPrefWidth(235.0);
        scoretable.getColumns().add(scoretablename);
        scoretable.getColumns().add(scoretableresult);
        scoretablename.setCellValueFactory(new PropertyValueFactory<Score, String>("Name"));
        scoretableresult.setCellValueFactory(new PropertyValueFactory<Score, Integer>("Score"));
        ObservableList<Score> oblist = FXCollections.observableArrayList();
        for (Score s : sc) {
//            hb = new HBox(530);
//            hb.setPrefWidth(750.0);
            Text name = new Text();
            name.setText(s.getName());
            name.setFont(new Font("Arial Bold", 30));
//            hb.setHgrow(name, Priority.ALWAYS);
//            hb.setMargin(name, new Insets(0,10,0,0));
//            hb.getChildren().add(name);
            Text scor = new Text();
            scor.setText(s.getScore().toString());
            scor.setFont(new Font("Arial Bold", 30));
//            hb.setHgrow(scor, Priority.ALWAYS);
//            hb.setMargin(name, new Insets(0,0,0,10));
//
//            hb.getChildren().add(scor);
//            hb.setAlignment(Pos.CENTER_LEFT);
//            vb.getChildren().add(hb);
            oblist.add(s);
        }
        scoretable.setItems(oblist);
//        scorePane.setContent(vb);
        scorePane.setContent(scoretable);
    }

    //Functions



    //Pane

    public void play(ActionEvent actionEvent) {
        home.setVisible(false);
        home.setDisable(true);
        game.setVisible(true);
        game.setDisable(false);
    }

    public void score(ActionEvent actionEvent) {
        home.setVisible(false);
        home.setDisable(true);
        score.setVisible(true);
        score.setDisable(false);
    }

    public void information(ActionEvent actionEvent) {
        home.setVisible(false);
        home.setDisable(true);
        information.setVisible(true);
        information.setDisable(false);
    }

    public void exit(ActionEvent actionEvent) {
        primaryStage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        if (score.isVisible()){
            score.setVisible(false);
            score.setDisable(true);
        }
        else {
            if (information.isVisible()) {
                information.setVisible(false);
                information.setDisable(true);
            }
            else {
                if (game.isDisable()){
                    game.setVisible(false);
                    game.setDisable(true);
                    configurate.setVisible(false);
                    configurate.setDisable(true);
                    effectConfig.setVisible(false);
                    effectConfig.setDisable(true);
                    playButton.setText("Continue");
                }
            }
        }
        home.setVisible(true);
        home.setDisable(false);
    }

    public void cancelConfig(ActionEvent actionEvent) {
        configurate.setVisible(false);
        configurate.setDisable(true);
        game.setDisable(false);
        effectConfig.setVisible(false);
        effectConfig.setDisable(true);
    }

    public void clickSound(MouseEvent mouseEvent) {
        if (boolClickSound) {
            for (ImageView img: soundImageList){
                img.setImage(new Image("file:resources/images/soundoff.png"));
            }
            boolClickSound = false;
        }
        else {
            for (ImageView img: soundImageList){
                img.setImage(new Image("file:resources/images/soundon.png"));
            }
            boolClickSound = true;
        }
    }

    public void clickConfig(MouseEvent mouseEvent) {
        configurate.setVisible(true);
        configurate.setDisable(false);
        game.setDisable(true);
        effectConfig.setVisible(true);
        effectConfig.setDisable(false);
    }

    public void rename(ActionEvent actionEvent) {
        name = nameArea.getText();
        System.out.println(name);
    }
}
