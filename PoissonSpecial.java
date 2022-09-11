import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

// Fichier :     SpecialFish.java
// Création:     2022.04.16
// Auteur :      Jianxin You ,Yehao Yan
// Objet :       The class SpecialFish contains all the attributes and
//               methodes of the special fish in this game.

public class PoissonSpecial extends Entity {

    // If the specialFish is Crabe,boolean isCrabe is true
    // boolean isStar is false
    // vice versa
    private boolean isCrabe;
    private boolean isStar;

    // Whether the fish was eaten by a shark
    private boolean isFed;

    // A integer determines what kind of fish it is
    // If int whichFish = 0 ,it means that the fish is a crabe
    // If int whichFish = 1 ,it means that the fish is a seestar
    private int whichFish;

    // Time is a number that records the time of the fish movement
    double time = 0;

    //The seeStar oscillates vertically with an amplitude of 50px/s.
    double amplitude = 50;

    private boolean partieDeDroite;

    private Recorder recorder;

    private double specialFishTime = 0;

    private boolean attends;

    private double moveTime;


    // Constructer
    public PoissonSpecial(double x, double y, double w, double h, double windowWidth, double windowHeight) {
        super(x, y, w, h, windowWidth, windowHeight);
    }
    // Set recorder
    public void setRecorder(Recorder recorder) {
        this.recorder = recorder;
    }

    // Set the horizontal speed
    public double setVx(int level) {
        double vitesse = 100 * Math.pow(level, 1 / 3) + 200;
        if (isCrabe) {
            if (x == 0) {
                return vx = 1.3 * vitesse;
            } else {
                return vx = -1.3 * 380;
            }
        }
        else {
            if (x == 0) {
                return vx = vitesse;
            } else {
                return vx = -vitesse;
            }
        }
    }

    public void update(GraphicsContext context, double dt) throws InterruptedException {


        time += dt * 1e-9;
        // chaque trois seconde, on initialize une nouvelle poisson normale.
        // et si on est en train d'afficher le level up, on ne dessine pas la poisson aussi.
        if (time > 5 && recorder.getLevelUpAffichageDone() && recorder.getLevel() >= 2){
            initialize();
            time = 0;
        }

        // si la poisson est cliqué( ou est fed)
        // ou on est entrain d'attendre l'apparaitre de la prochaine poisson, on ne dessine rien
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

    // This method update is used for the animation;
    public void updatePosition(double dt) throws InterruptedException {
            //The seeStar oscillates vertically with an amplitude of 50px/s.
        if (isStar) {
            moveTime += dt * 1e-9;
            x += vx * dt * 1e-9;

            if (moveTime < 0.3) y += amplitude * dt * 1e-9;
            if (moveTime> 0.3 && moveTime < 0.6) y -= amplitude * dt * 1e-9;
            if (moveTime > 0.6) moveTime= 0;

        }
        else if (isCrabe) {
            moveTime += dt * 1e-9;
            // it moves forward for 0.5s, then backwards for 0.25s
            // then moves forward for 0.5s
            // until it has passed the other side of the screen
            if (moveTime< 0.5) x += vx * dt * 1e-9;
            if (moveTime > 0.5 && moveTime < 0.75) x -= vx * dt * 1e-9;
            if (moveTime > 0.75) moveTime= 0;

        }

    }

    //Set the initial abscissa of the special fish
    public double setX() {
        double[] positionX = new double[]{0,640.0};
        int i = (int) (Math.random() * 2);
        if (i == 0) {
            partieDeDroite = false;
        } else {
            partieDeDroite  = true;
        }
        return positionX[i];
    }

    // Set the initial ordinate of the special fish
    public double setY() {
        int min = 96;
        int max = 384;
        return (Math.random() * (max - min)) + min;
    }

    // Initialize the speed ,the position
    // the status of the fish,the species
    // and the image of the fish.
    public void initialize() {
        whichFish = (int) (Math.random() * 2);
        image = randomFish();
        x = setX();
        y = setY();
        vx = setVx(recorder.getLevel());
        isFed = false;
        attends = false;
        moveTime = 0;
    }

    // Randomly determine the species of the fish
    public Image randomFish() {
        if (whichFish == 0) {
            isCrabe = true;
            isStar = false;
            return new Image("/crabe.png");
        } else {
            isCrabe = false;
            isStar = true;
            return new Image("/star.png");
        }
    }

    public boolean isFishOutOfScope() {
        if (partieDeDroite) {
            return x < 0 - w;
        }
        else{
            return x > windowWidth;
        }


    }


    // This methode drawImage is used for display the image of the fish
    public void drawImage(GraphicsContext context) {
        context.drawImage(image, x, y, 130, 130);
    }


    //Set the status of the fish (be eaten or not)
    //If it is eaten,set the position of the fish out of the screen
    public void feedFish(){
        // la poisson peut être fed seulement si il n'y a pas level Up affichage.
        if (recorder.getLevelUpAffichageDone() && !isFed) {
            recorder.incrementPoint();
            isFed = true;
        }
    }


    //Check the position between the mouse and the fish
    public boolean contains(Point2D p) {
        Point2D centre = new Point2D(x + w / 2, y + h / 2);

        return (p.getX() > centre.getX() && p.getX() < x + w && p.getY() < centre.getY() && p.getY() > y) ||
                (p.getX() > x && p.getX() < centre.getX() && p.getY() < centre.getY() && p.getY() > y) ||
                (p.getX() > x && p.getX() < centre.getX() && p.getY() < y + h && p.getY() > centre.getY()) ||
                (p.getX() > centre.getX() && p.getX() < x + w && p.getY() < y + h && p.getY() > centre.getY());

    }

}








