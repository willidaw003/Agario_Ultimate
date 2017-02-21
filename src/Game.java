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
        blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                10,10,1,1, Color.GREEN, "player"));
        for(int i = 0; i < 10; i++)
            blobs.add(new Trap(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    4,4,.2,.2,Color.RED));

        for(int i = 0; i < 20; i++)
            blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                    4,4,0,0, Color.BLUE, "food"));

    }

    public void run() {

        timer = new Timer(1000/60,this);
        timer.start();

    }

    public void paint(Graphics g) {

        super.paint(g);
        for(Entity obj : blobs) {
            obj.paint(g);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        collision();

        blobs.get(0).playerMove(mouseX, mouseY);
        for(int i = 1; i < blobs.size(); i++) {
            blobs.get(i).move();
        }
        repaint();
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
