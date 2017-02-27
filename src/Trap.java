import java.awt.*;

/**
 * Created by weavechr000 on 2/10/2017.
 */
public class Trap extends Entity {


    public Trap(Game game, double x, double y, double width, double height, double dx, double dy, double slowDown, Color color) {
        super(game, x, y, width, height, dx, dy, slowDown, color, "");
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(getColor());
        g.fillRect((int)getX(),(int)getY(),(int)getWidth(),(int)getHeight());




    }



}
