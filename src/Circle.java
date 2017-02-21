import java.awt.*;

/**
 * Created by weavechr000 on 2/10/2017.
 */
public class Circle extends Entity {


    public Circle(Game game, double x, double y, double width, double height, double dx, double dy, Color color, String type) {
        super(game, x, y, width, height, dx, dy, color, type);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(getColor());
        g.fillOval((int)getX(),(int)getY(),(int)getWidth(),(int)getHeight());

    }
}
