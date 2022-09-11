import javafx.scene.canvas.GraphicsContext;


// cette classe crée un group de balls;
public class GroupDeBall extends Entity{


    private Ball[] balls;

    public GroupDeBall(double x, double y, double w, double h, double windowWidth, double windowHeight) {
        super(x,y,w,h,windowWidth,windowHeight);

        balls = new Ball[5];

        for (int i=0; i<5;i++){
            // on intialise la position et la vitesse de chaque ball
            double bias = -20 + Math.random() * 40;
            double vitesse = 350 + Math.random() * 100;
            double xBalls = this.x + bias;
            double size = 10 + Math.random()*30;
            Ball ball = new Ball(xBalls,windowHeight+100,size,size,windowWidth,windowHeight);
            ball.setVy(-vitesse);
            balls[i] = ball;

        }
    }

    @Override
    public void update(GraphicsContext context, double  dt) throws InterruptedException {
        for (Ball ball : balls ){
            ball.update(context,dt);
        }
    }


}



