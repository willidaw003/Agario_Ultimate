import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by weavechr000 on 2/10/2017.
 */
public class Game extends JPanel implements ActionListener, MouseMotionListener, MouseListener  {

    Timer timer;
    ArrayList<Entity> blobs;
    int mouseX, mouseY;

    public Game() {

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Agario");
        setPreferredSize(new Dimension(1400,750));
        frame.setResizable(false);
        setBackground(Color.WHITE);
//        ImageIcon pic = new ImageIcon("shapes.png");
//        Image icon = pic.getImage();
//        frame.setIconImage(icon);

        frame.add(this);
        frame.addMouseMotionListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.init();
        game.run();

    }

    public void init() {

        blobs = new ArrayList<>();
        blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                10,10,1,1, Color.GREEN, "player"));
        for(int i = 0; i < 15; i++)
            blobs.add(new Trap(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    4,4,.2,.2,Color.RED));

        for(int i = 0; i < 40; i++)
            blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    8,8,0,0, Color.BLUE, "food"));

        for(int i = 0; i < 6; i++)
            blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    10,10,1,1, Color.CYAN, "enemy"));


    }

    public void run() {

        timer = new Timer(1000/60,this);
        timer.start();

    }

    public void collision(Entity e, Entity other) {

        if(getBounds().intersects(other.getBounds())) {

            if(other instanceof Trap);
            else if(other instanceof Circle) {
                if(other.getType().equals("food")) {
                    e.setWidth(e.getWidth() + e.getWidth() / 1000 + 2);
                    e.setHeight(e.getHeight() + e.getHeight() / 1000 + 2);
                    e.setDx(e.getDx() + .01);
                    e.setDy(e.getDy() + .01);
                }
                else if(e.getType().equals("player"))
//                else if(other.getType().equals("player")) {
//                    if(e.getX() < other.getX()) e.setDx(e.getDx() * -1);
//                    if(e.getY() < other.getY()) e.setDy(e.getDy() * -1);
//                }
            }

        }
    }

//    public void split() {
//
//
//
//    }

    public void paint(Graphics g) {

        super.paint(g);
        for(Entity obj : blobs) {
            if(obj.getType().equals("food")) {
                obj.setColor(new Color((int)(Math.random() * 180) + 75, (int)(Math.random() * 180) + 75, (int)(Math.random() * 180) + 75));
            }
            obj.paint(g);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i = 0; i < blobs.size(); i++) {
            for(int j = 1; j < blobs.size(); j++) {

                blobs.get(i).move();
                if(blobs.get(i).collides(blobs.get(j))) {
                    if(blobs.get(i).getType().equals("player") || blobs.get(i).getType().equals("enemy")) {
                        collision(blobs.get(i), blobs.get(j));
                    }
                    else collision(blobs.get(j), blobs.get(i));

                    if(blobs.get(i).getType().equals("food"))
                        blobs.remove(i);
                    if(blobs.get(j).getType().equals("food"))
                        blobs.remove(j);
                }

            }
        }

        repaint();



//        blobs.get(0).playerMove(mouseX, mouseY);
//        for(int i = 1; i < blobs.size(); i++) {
//            blobs.get(i).move();
//            if(blobs.get(0).collides(blobs.get(i))) {
//                collision(blobs.get(i));
//                if(blobs.get(i).getType().equals("food"))
//                    blobs.remove(i);
//            }
//        }

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mouseX = e.getX() - 3;
        mouseY = e.getY() - 25;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
