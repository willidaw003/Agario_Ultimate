import java.awt.*;

/**
 * Created by weavechr000 on 2/10/2017.
 */
public class Trap extends Entity {


    public Trap(Game game, int x, int y, int width, int height, double dx, double dy, Color color) {
        super(game, x, y, width, height, dx, dy, color);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(getColor());
        g.fillRect(getX(),getY(),getWidth(),getHeight());
//        int[] xPoints = {getX(),getX()+20,getX()+10};
//        int[] yPoints = {getY(),getY(),getY()+20};
//        g.fillPolygon(xPoints,yPoints,3);



    }


}
