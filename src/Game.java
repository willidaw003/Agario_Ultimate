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
        setBackground(Color.BLACK);
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
        blobs.add(new Circle(this,695,375,10,10,3.5,3.5,50,Color.GREEN, "player"));
        for(int i = 0; i < 15; i++) {
            blobs.add(new Trap(this, (int) (25 + (getWidth() - 100) * Math.random()), (int) (25 + (getHeight() - 100) * Math.random()),
                    4, 4, .02, .02, 0, Color.RED));
            blobs.get(i+1).speed();
        }

        for(int i = 0; i < 50; i++)
            blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    6,6,0,0, 0,Color.YELLOW, "food"));

        for(int i = 0; i < 8; i++)
            blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    10,10,.045,.045, 0,Color.CYAN, "enemy"));

    }

    public void run() {

        timer = new Timer(1000/60,this);
        timer.start();

    }

    public int collision(Entity e, Entity other, boolean isBattle) {

        if(e.getBounds().intersects(other.getBounds())) {

            if(other instanceof Trap && e.getType().equals("player")) {
                die();
            }
            else if(other instanceof Circle) {
                if(other.getType().equals("food")) {
                    e.setWidth(e.getWidth() + 1);
                    e.setHeight(e.getHeight() + 1);
                    e.setDx(e.getDx() < 0 ? e.getDx() + .0005 : e.getDx() - .0005);
                    e.setDy(e.getDy() < 0 ? e.getDy() + .0005 : e.getDy() - .0005);
                    e.setSlowDown(e.getSlowDown() + 2);
                    System.out.println(e.getX());
                }
                
            }
            if(isBattle) {
                if(e.getWidth() > other.getWidth() * 1.2) {
                    e.setWidth(e.getWidth() + other.getWidth() * .75);
                    e.setHeight(e.getHeight() + other.getHeight() * .75);
                    e.setDx(e.getDx() < 0 ? e.getDx() + other.getWidth()*.00007 : e.getDx() - other.getWidth()*.00007);
                    e.setDy(e.getDy() < 0 ? e.getDy() + other.getWidth()*.00007 : e.getDy() - other.getWidth()*.00007);
                    e.setSlowDown(e.getSlowDown() + other.getWidth() / 3);
                    return 1;
                }
                else if(other.getWidth() > e.getWidth() * 1.2) {
                    other.setWidth(other.getWidth() + e.getWidth() * .75);
                    other.setHeight(other.getHeight() + e.getHeight() * .75);
                    other.setDx(other.getDx() < 0 ? other.getDx() + e.getWidth()*.00007 : other.getDx() - e.getWidth()*.00007);
                    other.setDy(other.getDy() < 0 ? other.getDy() + e.getWidth()*.00007 : other.getDy() - e.getWidth()*.00007);
                    other.setSlowDown(other.getSlowDown() + e.getWidth() / 3);
                    return 2;
                }
                System.out.println("battle");
            }

        }
        
        return -1;
    }

    public void respawn() {

        if(System.currentTimeMillis() % 131 == 0) {
            blobs.add(new Trap(this, (int) (25 + (getWidth() - 100) * Math.random()), (int) (25 + (getHeight() - 100) * Math.random()),
                    4, 4, .02, .02, 0, Color.RED));
            blobs.get(blobs.size()-1).speed();
            for(int i = 0; i < 8; i++)
                blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                        6,6,0,0, 0,Color.YELLOW, "food"));
            double r = blobs.get(0).getWidth() * Math.random();
            blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    r, r,.075,.075, 0, Color.CYAN, "enemy"));
        }

    }


    public void paint(Graphics g) {

        super.paint(g);
        for(Entity obj : blobs) {
            obj.paint(g);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        blobs.get(0).playerMove(mouseX, mouseY);
        boolean iBlob = false, jBlob = false;

        for(int i = 0; i < blobs.size(); i++) {
            for(int j = 1; j < blobs.size(); j++) {

                if(blobs.get(i).getType().equals("player") || blobs.get(i).getType().equals("enemy")) iBlob = true;
                if(blobs.get(j).getType().equals("player") || blobs.get(j).getType().equals("enemy")) jBlob = true;

                blobs.get(j).move();
                if(blobs.get(i).collides(blobs.get(j)) && i != j) {

                    if(iBlob && !jBlob) {
                        collision(blobs.get(i), blobs.get(j), false);
                        if(blobs.get(j).getType().equals("food")) {
                            blobs.remove(j);
                            if(i > 1)
                                i--;
                            if(j > 1)
                                j--;
                        }
                    }
                    else if(jBlob && !iBlob) {
                        collision(blobs.get(j), blobs.get(i), false);
                        if(blobs.get(i).getType().equals("food")) {
                            blobs.remove(i);
                            if(i > 1)
                                i--;
                            if(j > 1)
                                j--;
                        }
                    }
                    else if(iBlob && jBlob) {
                        int delete = collision(blobs.get(j), blobs.get(i), true);
                        if(delete == 1) {
                            if(blobs.get(i).getType().equals("player")) die();
                            blobs.remove(i);
                        }
                        else if(delete == 2) {
                            if(blobs.get(j).getType().equals("player")) die();
                            blobs.remove(j);
                        }
                    }
                }

                iBlob = false;
                jBlob = false;

            }

        }

        respawn();
        repaint();

    }

    public void die() {
        JOptionPane.showMessageDialog(this, "You died!");
        System.exit(0);
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
