import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.util.*;
import javax.naming.ldap.Control;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;

public class Controller {

    private Entity entities[];
    private Cursor cursor;
    private Recorder recorder;    // recorder sert à stocker les inforamtion de nos jeux
    private Score scores;        // scores sert à update les scores.

    private double windowWidth;
    private double windowHeight;

    public Controller(double windowWidth, double windowHeight, GraphicsContext context) {

        this.entities = new Entity[3];
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        PoissonNormal normalFish = new PoissonNormal(0, 0, 130, 130, windowWidth, windowHeight);
        Bubbles bubbles = new Bubbles(0, 0, 130, 130, windowWidth, windowHeight);
        PoissonSpecial specialFish = new PoissonSpecial(0, 0, 130, 130, windowWidth, windowHeight);

        this.recorder = new Recorder();
        this.scores = new Score();
        normalFish.setRecorder(recorder);
        specialFish.setRecorder(recorder);

        entities[0] = normalFish;
        entities[1] = specialFish;
        entities[2] = bubbles;

        cursor = new Cursor(0, 0, 0, 0, windowWidth, windowHeight);

    }

    public void setPoint(Point2D p) {
        cursor.setPos(p);
    }



    // si on n'a plus de live, et on affiche game over plus de 3 seconds, le jeu est finit.
    public boolean gameOver() {
        return recorder.getLife() == 0 && recorder.getGameOverAffichageFinis();
    }


    public ArrayList<String> updateScore(String nom){
        return scores.updateScore(recorder.getPoint(), nom);
    }

    public void update(GraphicsContext context, double dt) throws InterruptedException {

        // on dessine la cible


        context.clearRect(0, 0, windowWidth, windowHeight);
        context.setFill(Color.rgb(0, 0, 139));
        context.fillRect(0, 0, windowWidth, windowHeight);

        PoissonNormal normalFish = (PoissonNormal) entities[0];
        PoissonSpecial specialFish = (PoissonSpecial) entities[1];
        Bubbles bubbles = (Bubbles) entities[2];

        normalFish.update(context, dt);
        specialFish.update(context, dt);
        bubbles.update(context, dt);
        recorder.update(context, dt);
        cursor.update(context,dt);

    }


    // si on recommence une nouvelle partie, on doit réinitialiser les attributes.
    public void initializeJeu() {

        PoissonNormal normalFish = new PoissonNormal(0, 0, 130, 130, windowWidth, windowHeight);
        Bubbles bubbles = new Bubbles(0, 0, 130, 130, windowWidth, windowHeight);
        PoissonSpecial specialFish = new PoissonSpecial(0, 0, 130, 130, windowWidth, windowHeight);

        recorder.initialize();

        normalFish.setRecorder(recorder);
        specialFish.setRecorder(recorder);

        entities[0] = normalFish;
        entities[1] = specialFish;
        entities[2] = bubbles;
    }



    public void keyPress(String value) {
        switch (value) {
            case "k" -> recorder.levelUp();
            case "j" -> recorder.incrementPoint();
            case "h" -> recorder.addLife();
            case "l" -> recorder.setLife(0);
        }

    }

    // fonction pour nous aider de savoir si  on clique la souris ou non.
    public void lancerBall(GraphicsContext context, Point2D p) {
        cursor.initialize(p.getX(), p.getY());
        cursor.setEstLancee(true);
    }



    // cette fonction vérifie si notres poissons est clqué,
    // s'ils sont cliquées, alors, la poisson est fed.
    public void checkAndRemoveImage(Point2D p) {

        PoissonNormal normalFish = (PoissonNormal) entities[0];
        PoissonSpecial specialFish = (PoissonSpecial) entities[1];
        if (normalFish.contains(p)) {
            normalFish.feedFish();
        }

        if (specialFish.contains(p)) {
            specialFish.feedFish();
        }

    }

    public void writeScore(){
        scores.writeRecords();
    }
    public int getPoint(){
        return recorder.getPoint();
    }


}
