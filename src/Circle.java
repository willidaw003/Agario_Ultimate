import java.awt.*;

/**
 * Created by weavechr000 on 2/10/2017.
 */
public class Circle extends Entity {


    public Circle(Game game, int x, int y, int width, int height, double dx, double dy, Color color) {
        super(game, x, y, width, height, dx, dy, color);
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(getColor());
        g.fillOval(getX(),getY(),getWidth(),getHeight());

    }
}
