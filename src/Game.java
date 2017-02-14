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
        for(int i = 0; i < 25; i++)
            blobs.add(new Trap(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getWidth()-100) * Math.random()),
                    15,15,2,2,Color.RED));

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

//        blobs.get(0).playerMove();
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
