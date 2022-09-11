import com.sun.javafx.application.PlatformImpl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.nio.file.FileAlreadyExistsException;


// classe Recorder sert à stocker les données importants de notre jeux
public class Recorder {

    // Dans l'état initial, le joueur a trois chances, à partir de zéro point et au niveau un
    private int life;
    private int level;
    private int point;

    private double time;

    private boolean gameOverAffichage;
    private boolean levelUpAffichageDone;  // level one est un peux special, donc
    private boolean gameOverAffichageFinis;


    public Recorder(){
        initialize();
    }

    // Initialize recorder
    public void initialize(){
        levelUpAffichageDone = false;
        gameOverAffichageFinis = false;
        gameOverAffichage = false;

        time = 0;
        life = 3;
        level = 1;
        point = 0;

    }


    // Pour l'affichage d'animations
    public void update(GraphicsContext context, double dt) {

        drawInformation(context);
        if (!levelUpAffichageDone) {
            time += dt * 1e-9;
            drawLevelUpTexte(context);
            if (time > 3) {
                levelUpAffichageDone = true;
                time = 0;
            }
        }
        if (gameOverAffichage) {
            time += dt * 1e-9;
            if (time > 3){
                gameOverAffichageFinis = true;
            }
        }

    }

    public boolean getGameOverAffichageFinis(){
        return gameOverAffichageFinis;
    }



    // Afficher les scores et les chances restantes
    public void drawInformation(GraphicsContext context){

        context.setFill(Color.WHITE);
        context.setFont(Font.font(50));
        context.fillText(String.valueOf(point),300,70);

        if (life == 3){
            context.drawImage(new Image("/00.png"),290,96,50,50);
            context.drawImage(new Image("/00.png"),230,96,50,50);
            context.drawImage(new Image("/00.png"),350,96,50,50);
        }

        else if(life == 2){
            context.drawImage(new Image("/00.png"),290,96,50,50);
            context.drawImage(new Image("/00.png"),230,96,50,50);
        }

        else if(life == 1){
            context.drawImage(new Image("/00.png"),230,96,50,50);
        }

        else if(life == 0){
            context.setFill(Color.RED);
            context.setFont(Font.font(100));
            context.fillText("Game Over",50,240);
        }

    }



    // on afficher le nouveau level lorsque level up
    public void drawLevelUpTexte(GraphicsContext context) {
        context.setFill(Color.RED);
        context.setFont(Font.font(100));
        context.fillText("Level" + String.valueOf(level), 175, 240);
    }


    public boolean getLevelUpAffichageDone(){
        return this.levelUpAffichageDone;
    }



    // Un point pour cinq poissons sont
    public void incrementPoint(){
        this.point += 1;
        if(point % 5 == 0 && point != 0){
            levelUp();
        }
    }


    public int getLife(){
        return this.life;
    }


    public void levelUp(){
        this.level += 1;
        levelUpAffichageDone = false;
        time = 0;
    }

    public void decrementLife(){
        this.life -= 1;
        if (life == 0){
            levelUpAffichageDone = true;
            gameOverAffichage = true;
            time = 0;

        }

    }

    public void addLife(){
        if (life == 3){
            return ;
        }
        this.life += 1;
    }
    public void setLife(int life){

        levelUpAffichageDone = true;
        gameOverAffichage = true;
        time = 0;
        this.life = life;
    }

    public int getLevel(){
        return this.level;
    }



    public int getPoint(){
        return this.point;
    }

}

