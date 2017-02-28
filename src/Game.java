import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by weavechr000 on 2/10/2017.
 */
public class Game extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    Timer timer;
    ArrayList<Entity> blobs;
    int mouseX, mouseY;
    Toolkit tk = Toolkit.getDefaultToolkit();
    boolean start = false;
    int message = 1;

    public Game() {

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Agario");
        setPreferredSize(new Dimension((int)(tk.getScreenSize().getWidth()-20),(int)(tk.getScreenSize().getHeight())-90));
        frame.setResizable(false);
        setBackground(Color.BLACK);
//        ImageIcon pic = new ImageIcon("shapes.png");
//        Image icon = pic.getImage();
//        frame.setIconImage(icon);

        frame.add(this);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);
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

        for(int i = 0; i < 70; i++)
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
                    e.setWidth(e.getWidth() + .5);
                    e.setHeight(e.getHeight() + .5);
                    e.setDx(e.getDx() < 0 ? e.getDx() + .0005 : e.getDx() - .0005);
                    e.setDy(e.getDy() < 0 ? e.getDy() + .0005 : e.getDy() - .0005);
                    e.setSlowDown(e.getSlowDown() + 2);
                    e.setMass(e.getMass() + 1);
                }

            }
            if(isBattle) {
                if(e.getWidth() > other.getWidth() * 1.2) {
                    e.setWidth(e.getWidth() + other.getWidth() * .25);
                    e.setHeight(e.getHeight() + other.getHeight() * .25);
                    e.setDx(e.getDx() < 0 ? e.getDx() + other.getWidth()*.00007 : e.getDx() - other.getWidth()*.00007);
                    e.setDy(e.getDy() < 0 ? e.getDy() + other.getWidth()*.00007 : e.getDy() - other.getWidth()*.00007);
                    e.setSlowDown(e.getSlowDown() + other.getWidth() / 5);
                    e.setMass(e.getMass() + other.getMass() / 4);
                    return 1;
                }
                else if(other.getWidth() > e.getWidth() * 1.2) {
                    other.setWidth(other.getWidth() + e.getWidth() * .25);
                    other.setHeight(other.getHeight() + e.getHeight() * .25);
                    other.setDx(other.getDx() < 0 ? other.getDx() + e.getWidth()*.00007 : other.getDx() - e.getWidth()*.00007);
                    other.setDy(other.getDy() < 0 ? other.getDy() + e.getWidth()*.00007 : other.getDy() - e.getWidth()*.00007);
                    other.setSlowDown(other.getSlowDown() + e.getWidth() / 5);
                    other.setMass(other.getMass() + e.getMass() / 4);
                    return 2;
                }

            }

        }

        return -1;
    }

    public void respawn() {

        if(System.currentTimeMillis() % 753 == 0) {
            for(int i = 0; i < 25; i++)
                blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                        6,6,0,0, 0,Color.YELLOW, "food"));

            for(int i = 0; i < 3; i++) {
                double r = blobs.get(0).getWidth() * Math.random() + 5;
                blobs.add(new Circle(this,(int)(25 + (getWidth()-100) * Math.random()),(int)(25 + (getHeight()-100) * Math.random()),
                        r, r,.075,.075, 0, Color.CYAN, "enemy"));
            }

        }
        if(System.currentTimeMillis() % 9523 == 0) {
            blobs.add(new Trap(this, (int) (25 + (getWidth() - 100) * Math.random()), (int) (25 + (getHeight() - 100) * Math.random()),
                    4, 4, .02, .02, 0, Color.RED));
            blobs.get(blobs.size()-1).speed();
        }

    }


    public void paint(Graphics g) {

        super.paint(g);

        if(GameState.isPlay) {

            for(Entity obj : blobs) {
                obj.paint(g);
            }
            g.setFont(new Font("Serif", Font.BOLD, 14));
            g.setColor(Color.WHITE);
            printSimpleString(Integer.toString(blobs.get(0).getMass()),0,(int)(blobs.get(0).getX() + blobs.get(0).getWidth()/2),
                    (int)(blobs.get(0).getY() + blobs.get(0).getWidth()/2), g);

        }
        else if(GameState.isMenu) {

            g.setFont(new Font("Serif", Font.BOLD, 32));
            g.setColor(Color.WHITE);
            printSimpleString("Agario" + message, getWidth(), 0, getHeight()/6, g);
            printSimpleString("Play", getWidth(), 0, getHeight()/6*13/5, g);
            g.drawRect(getWidth()/3, getHeight()/6*2, getWidth()/3, getHeight()/6);

            printSimpleString("How to Play", getWidth(), 0, getHeight()/6*4, g);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            switch(message) {
                case 1: printSimpleString("Use the mouse to move the green ball. The farther the away the mouse is, the faster the ball goes,",
                            getWidth(), 0, getHeight()/6*5, g);
                        printSimpleString("however the closer it is, the more precise you can move.",
                            getWidth(), 0, getHeight()/6*5 + 25, g); break;
                case 2: printSimpleString("Eat the cyan blobs to grow in size, but avoid blobs that are bigger than you as well as the red traps.",
                        getWidth(), 0, getHeight()/6*5, g); break;
                case 3: printSimpleString("Over time, more blobs and food will spawn. The speed will also speed up and slow down.",
                        getWidth(), 0, getHeight()/6*5, g); break;
                case 4: printSimpleString("Right click to pause the game. Use this if you need to go to the bathroom..  Definitely not for cheating..",
                        getWidth(), 0, getHeight()/6*5, g); break;
            }


        }
        else if(GameState.isPause) {

            printSimpleString("Right Click to Resume", getWidth(), 0, getHeight()/2, g);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(GameState.isPlay) {
            blobs.get(0).playerMove(mouseX, mouseY);
            entityMovement();
            repaint();
        }

    }

    public void entityMovement() {

        boolean iBlob = false, jBlob = false;

        for(int i = 0; i < blobs.size(); i++) {
            for(int j = 1; j < blobs.size(); j++) {

                if(i >= blobs.size() || j >= blobs.size()) break;

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
    }

    public void die() {
        JOptionPane.showMessageDialog(this, "You died!");
        System.exit(0);
    }

    private void printSimpleString(String s, int width, int XPos, int YPos, Graphics g2d) {

        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width/2 - stringLen/2;
        g2d.drawString(s,start + XPos, YPos);

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

        if(GameState.isMenu) {
            if(e.getX() > getWidth()/3 && e.getX() < getWidth()/3*2 && e.getY() > getHeight()/6*2 && e.getY() < getHeight()/6*3) {
                start = true;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(start) {
            if(e.getX() > getWidth()/3 && e.getX() < getWidth()/3*2 && e.getY() > getHeight()/6*2 && e.getY() < getHeight()/6*3) {
                GameState.setIsPlay(true);
                GameState.setIsMenu(false);
                start = false;
            }
        }

        if(GameState.isPlay || GameState.isPause) {
            if(e.getButton() == 3) {
                GameState.togglePlay();
                GameState.togglePause();
                if(GameState.isPause)
                    timer.restart();
            }
        }

        if(GameState.isMenu) {
            if(e.getButton() == 1) {
                if(e.getY() > getHeight()/2) {
                    System.out.println(message);
                    if(message == 4) message = 1;
                    else message += 1;
                }
            }
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
