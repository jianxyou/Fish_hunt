import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
// Fichier :     NormalFish.java
// Création:     2022.04.15
// Auteur :      Jianxin You 20134401,Yehao Yan 20179475
// Objet :       This class FishHunt is the core of these programs
//               It mainly includes four scenes,Welcome Screen,game interface,Score ranking interface,
//               and the score input interface.
//               It takes on the task of vue in the MVC architecture,which is mainly controlled by
//               an another important class Controller in this project.

public class FishHunt extends Application {

    private Controller controller;


    @Override
    public void start(Stage primaryStage) throws Exception {

        //set interface size
        double WINDOW_WIDTH = 640.0;
        double WINDOW_HEIGHT = 480.0;



        //Welcome Screen
        Pane root = new Pane();
        Scene sceneInitial = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        //Score ranking interface
        VBox rootScore = new VBox();
        Scene sceneScore = new Scene(rootScore,WINDOW_WIDTH,WINDOW_HEIGHT);

        //game interface
        Pane game = new Pane();
        Scene sceneGame = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);

        //score input interface
        VBox rootScoreFini = new VBox();
        Scene sceneJeuFinis = new Scene(rootScoreFini,WINDOW_WIDTH, WINDOW_HEIGHT);

        HBox newScore = new HBox();


        // Texts to display
        Text text = new Text("Meilleurs scores");
        Text text2 = new Text("Meilleurs scores");
        TextField writeName = new TextField();


        //Buttons to display
        Button menu = new Button("Menu");
        Button menu2 = new Button("Menu");
        Button ajouter = new Button("Ajouter!");


        Button nouvellePartie= new Button("Nouvelle Partie!");
        nouvellePartie.setLayoutX(260);
        nouvellePartie.setLayoutY(320);
        Button meilleurScore = new Button("Meilleur Score");
        meilleurScore.setLayoutX(270);
        meilleurScore.setLayoutY(390);

        //Style
        text.setFont(Font.font( "serif",25));
        rootScore.setAlignment(Pos.CENTER);
        rootScoreFini.setAlignment(Pos.CENTER);
        newScore.setAlignment(Pos.CENTER);

        //Background image of welcome screen
        Image logo = new Image("logo.png");
        ImageView view = new ImageView(logo);
        view.setFitHeight(480);
        view.setFitWidth(640);

        ListView<String> list = new ListView<>();
        ListView<String> list2 = new ListView<>();

        //Set elements for the HBox
        Text votreNom = new Text("Your name :");
        Text votrePoints = new Text("");

        newScore.getChildren().add(votreNom);
        newScore.getChildren().add(writeName);
        newScore.getChildren().add(list);
        newScore.getChildren().add(votrePoints);
        newScore.getChildren().add(ajouter);


        //Set elements for the Score ranking interface
        rootScore.getChildren().add(text2);
        rootScore.getChildren().add(list2);
        rootScore.getChildren().add(menu2);


        //Set elements for the score input interface
        rootScoreFini.getChildren().add(text);
        rootScoreFini.getChildren().add(list);
        rootScoreFini.getChildren().add(newScore);
        rootScoreFini.getChildren().add(menu);

        //Set elements for the Welcome Screen
        root.getChildren().add(view);
        root.getChildren().add(nouvellePartie);
        root.getChildren().add(meilleurScore);

        //Set canvas
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext context = canvas.getGraphicsContext2D();
        game.getChildren().add(canvas);

        //All the control about the screen is done by the controller
        this.controller = new Controller(WINDOW_WIDTH,WINDOW_HEIGHT,context);

        // nouvelleParite  est une animation associé à notre jeu
        nouvellePartie.setOnAction(event -> {
            controller.initializeJeu();
            primaryStage.setScene(sceneGame);
            new AnimationTimer() {
                private long lastTime = 0;

                @Override
                public void handle(long now) {
                    if (lastTime == 0) {
                        lastTime = now;
                        return;
                    }

                    long deltaT = now - lastTime;
                    lastTime = now;
                    try {
                        controller.update(context,deltaT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (controller.gameOver()){
                        primaryStage.setScene(sceneJeuFinis);
                        votrePoints.setText(" has done " + controller.getPoint() + " points");
                        stop();
                    }
                }
            }.start();
        });


        //Set the function of the button ajouter
        ajouter.setOnAction(event -> {
            String nameWrite = writeName.getText();

            // si on entre pas le nom, on ne ajout pas dans nos scores
            if (nameWrite.trim().equals("")){
                primaryStage.setScene(sceneInitial);
            }

            else {
                ArrayList<String> scoresWithNameWithRank = controller.updateScore(nameWrite);
                list.getItems().setAll(scoresWithNameWithRank);
                list2.getItems().setAll(scoresWithNameWithRank);
                primaryStage.setScene(sceneInitial);
                controller.writeScore();
            }
        });



        meilleurScore.setOnAction(event -> {
            primaryStage.setScene(sceneScore);
        });

        menu.setOnAction(event -> {
            primaryStage.setScene(sceneInitial);
        });

        menu2.setOnAction(event -> {
            primaryStage.setScene(sceneInitial);
        });

        // Set how to interact with the interface
        canvas.setOnMouseClicked((event) -> {
            Point2D p = new Point2D(event.getX(), event.getY());

            // si la poisson est cliqué, on doit le retirer
            controller.checkAndRemoveImage(p);

            // lancerBall sert à afficher l'animation de ball.
            controller.lancerBall(context,p);
        });

        sceneGame.setOnKeyPressed((event)-> {
            System.out.println(event.getText());
            controller.keyPress(event.getText());
        });

        canvas.setOnMouseMoved((event) -> {
            Point2D cursorPos= new Point2D(event.getX(),event.getY());
            controller.setPoint(cursorPos);
        });


        primaryStage.setScene(sceneInitial);
        primaryStage.setTitle("Fish Hunt");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

