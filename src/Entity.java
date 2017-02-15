import java.awt.*;

/**
 * Created by weavechr000 on 2/10/2017.
 */
public abstract class Entity {
//test in html window
    Game game;
    private double x, y, width, height;
    private double dx, dy;
    private Color color;

    public Entity(Game game, double x, double y, double width, double height, double dx, double dy, Color color) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    public void move() {

        if(this instanceof Trap) {
            double shift = (System.currentTimeMillis() % 16)/10;

            dx = Math.random()*3-1.5;
            dy = Math.random()*3-1.5;

            if(dx < 0) dx -= shift;
            else dx -= shift;
            if(dy < 0) dy -= shift;
            else dy -= shift;
        }

        double nextTop = y + dy;
        double nextRight = x + dx + width;
        double nextBottom = y + dy + height;
        double nextLeft = x + dx;

        if(nextLeft < 0 || nextRight + width > game.getWidth()) dx *= -1;
        if(nextTop < 0 || nextBottom + height > game.getHeight()) dy *= -1;

        x+=dx;
        y+=dy;


    }

    public void collision() {

    }

    public abstract void paint(Graphics g);


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
