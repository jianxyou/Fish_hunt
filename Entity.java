import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;

public abstract class Entity {
    protected double x,y;
    protected double w,h;
    protected double vx,vy;
    protected double windowWidth, windowHeight;
    protected Image image;

    public Entity(double x, double y, double w, double h , double windowWidth , double windowHeight) {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.windowWidth=windowWidth;
        this.windowHeight=windowHeight;
    }



    public abstract void update(GraphicsContext context, double dt) throws InterruptedException;

}
