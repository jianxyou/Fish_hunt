import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.lang.reflect.WildcardType;

public class PoissonNormal extends Entity {


    private Image image;
    private double gravity = 100;
    private boolean isFed;
    private boolean partieDeDroite;

    private double time;
    private boolean attends;  // un boolean pour nous aide à savoir la poisson peut bouger ou non.

    private Recorder recorder;

    public PoissonNormal(double x, double y, double w, double h, double windowWidth, double windowHeight) {
        super(x, y, w, h, windowWidth, windowHeight);
    }


    public void setRecorder(Recorder recorder){
        this.recorder = recorder;
    }


    public void initialize(){
        isFed = false;
        image = randomFish();
        x = setX();
        y = setY();
        vx = setVx(recorder.getLevel());
        vy = setVy();
        attends = false;
    }

    @Override
    public void update(GraphicsContext context, double dt) throws InterruptedException {


        time += dt * 1e-9;
        // chaque trois seconde, on initialize une nouvelle poisson normale.
        // et si on est en train d'afficher le level up, on ne dessine pas la poisson aussi.
        if (time > 3 && recorder.getLevelUpAffichageDone()){
            initialize();
            time = 0;
        }

        // si la poisson est cliqué( ou est fed) , on ne dessine rien, et attends l'apparaitre de la prochaine poisson
        if (isFed || attends || recorder.getLife() == 0){
            return;
        }

        // sinon, on repositionner la poisson, et la dessine.
        else{
            updatePosition(dt);
            drawImage(context);
        }

        if ( isFishOutOfScope() && !isFed && recorder.getLevelUpAffichageDone()){
            recorder.decrementLife();
            attends = true;
        }

    }

    public void feedFish(){
        // la poisson peut être fed seulement si il n'y a pas level Up affichage.
        if (recorder.getLevelUpAffichageDone() && !isFed) {
            recorder.incrementPoint();
            isFed = true;
        }
    }


    public void updatePosition(double dt){
        vy += gravity * dt * 1e-9;
        x += vx * dt * 1e-9;
        y += vy * dt * 1e-9;
    }


    public boolean isFishOutOfScope() {
        if (partieDeDroite) {
            return x < 0 - w;
        }
        else{
            return x > windowWidth;
        }
    }

    // on choisit un image de la poisson ramdomisé
    public Image randomFish(){
        int i = (int)(Math.random()*7);
        int r = (int)(Math.random()*255);
        int g = (int)(Math.random()*255);
        int b = (int)(Math.random()*255);
        return  ImageHelpers.colorize(
                new Image("/0"+String.valueOf(i)+".png"),
                Color.rgb(r,g,b));
    }



    public void drawImage(GraphicsContext context){
        if (vx >= 0) {
            context.drawImage(image,x,y,130,130);
        }else {
            context.drawImage(ImageHelpers.flop(image), x, y, 130, 130);
        }
    }




    public boolean contains(Point2D p) {
        Point2D centre = new Point2D(x + w/2,y + h/2);

        return  (p.getX() > centre.getX() && p.getX() < x + w && p.getY() < centre.getY() && p.getY() > y) ||
                (p.getX() > x && p.getX() < centre.getX() && p.getY() < centre.getY() && p.getY() > y) ||
                (p.getX() > x && p.getX() < centre.getX() && p.getY() < y + h && p.getY() > centre.getY()) ||
                (p.getX() > centre.getX() && p.getX() < x + w && p.getY() < y + h && p.getY() > centre.getY());

    }


    public double setY() {
        int min = 96;
        int max = 384;
        return (Math.random() * (max - min)) + min;
    }
    public int setX() {
        int[] positionX = new int[]{0,640};
        int i = (int)(Math.random()*2);
        if (i == 0){
            partieDeDroite = false;
        }
        else{
            partieDeDroite = true;
        }
        return positionX[i];
    }

    public double setVx(int level) {
        double vitesse = 100 * Math.pow(level, 1 / 3) + 200;
        if(!partieDeDroite) {
            return vx = vitesse;
        }else{
            return vx = - vitesse;
        }
    }

    public double setVy() {
        int min = 100;
        int max = 200;
        return vy = -((Math.random() * (max - min)) + min);
    }


}

