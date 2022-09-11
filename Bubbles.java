import javafx.scene.canvas.GraphicsContext;

public class Bubbles extends Entity{

    private GroupDeBall[] groups;
    private double bubbleTime;
    public Bubbles(double x, double y, double w, double h, double windowWidth, double windowHeight) {
        super(x,y,w,h,windowWidth,windowHeight);
        groups = new GroupDeBall[3];
        initialize();

        // bubbleTime sert à comptr le temps, chaque trois seconds, on réinitialiser les attributs.
        bubbleTime = 0;

    }

    public void initialize(){
        for (int i = 0; i<3;i ++){
            double randomX = Math.random()*windowWidth;
            groups[i] = new GroupDeBall(randomX,y,w,h,windowWidth,windowHeight);
        }

    }

    @Override
    public void update(GraphicsContext context, double  dt) throws InterruptedException {

        // on initialize les bubbles à chaque trois seconds.
        bubbleTime += dt*1e-9;
        if (bubbleTime >3){
            initialize();
            bubbleTime = 0;
        }
        for (GroupDeBall group : groups ){
            group.update(context,dt);
        }
    }
}
