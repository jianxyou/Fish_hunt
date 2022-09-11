import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import java.awt.desktop.AboutEvent;

public class Cursor extends Entity{

    private double x;
    private double y;
    private double rayon;

    // l'attribut estLancee est sert à savoir si on finit l'animation de cliquer la souris ou non.
    private boolean estLancee;
    private GraphicsContext context;
    private Image cible;

    public Cursor(double x, double y, double w, double h , double windowWidth , double windowHeight){
        super(x, y, w, h, windowWidth, windowHeight);
        cible = ImageHelpers.colorize(
                new Image("cible.png"), Color.rgb(0,0,0));
    }


    public void initialize(double x,double y){
        rayon = 50;
        this.x = x;
        this.y = y;
    }

    public void setPos(Point2D p){
        x =  p.getX();
        y =  p.getY();
    }

    public void drawImage(GraphicsContext context){
        context.drawImage(cible,x-25,y-25,50,50);
    }


    public void setContext(GraphicsContext context){
        this.context = context;
    }


    public void update(GraphicsContext context, double dt) {
        drawImage(context);
        if (estLancee) {
            rayon -= dt * 300 * 1e-9;
            if (rayon < 0) {
                estLancee = false;
            }
            context.setFill(Color.rgb(0, 0, 0));
            context.fillOval(x - rayon / 2, y - rayon / 2, rayon, rayon);
        }
    }

    public void setEstLancee(boolean lancee){
        estLancee = lancee;
    }


}
