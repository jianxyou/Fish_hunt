import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.nio.file.FileAlreadyExistsException;

public class Ball extends Entity {

    public Ball(double x, double y, double w, double h, double windowWidth, double windowHeight){
        super(x,y,w,h,windowWidth,windowHeight);
        this.x = x;
    }

    @Override
    public void update(GraphicsContext context, double dt) throws InterruptedException {

        // newY est la seule chose à rénover.
        double newY = y + vy * dt * 1e-9;
        y = newY;
        context.setFill(Color.rgb(0,0,255,0.4));
        context.fillOval(x - w / 2, y - h / 2, w, h);
    }

    public void setVy(double vy){
        this.vy = vy;
    }

}
