package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener,  ActionListener {
    private final int WIDTH = 1000;
    private final int HEIGHT = 500;

    private Timer timer;
    public int PlayerX = 500;
    public int player_width = 100;
    public int player_height = 10;
    private int ballX = 300;
    private int ballY = 100;
    private int total_Bricks = 21;
    private MapGenerator map;
    private int score;
    private boolean running = false;

    public int xDire = 1;
    public int yDire = 1;

    public Game() {
        canvasSetup();
        this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        map = new MapGenerator(3, 7);
        Window window = new Window("Brick Breaker", this);
        timer = new Timer(0, this);
        timer.start();
    }

    private void canvasSetup() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
//        draw Map
        map.draw((Graphics2D) g);

//        draw ball
        g.setColor(Color.yellow);
        g.fillOval(ballX, ballY, 20, 20);

//       draw score
         g.setColor(Color.white);
         g.setFont(new Font("Roboto",Font.BOLD,25));
         g.drawString(""+score,590,30);

         if (total_Bricks<=0){
             running=false;
             xDire=0;
             yDire=0;
             g.setColor(Color.green);
             g.setFont(new Font("Serif",Font.BOLD,25));
             g.drawString("GAME OVER, Score:",190,300);

             g.setFont(new Font("Serif",Font.BOLD,20));
             g.drawString("You Won",210,350);
         }

         if (ballY>570){
             running=false;
             xDire=0;
             yDire=0;
             g.setColor(Color.red);
             g.setFont(new Font("Serif",Font.BOLD,25));
             g.drawString("GAME OVER, Score:",190,300);

             g.setFont(new Font("Serif",Font.BOLD,20));
             g.drawString("Press Enter to Restart",210,350);
         }

//        draw the player
        g.setColor(Color.green);
        g.fillRect(PlayerX, 460, player_width, player_height);

        g.dispose();
    }

    public void moveRight() {
        running = true;
        PlayerX += 20;
    }

    public void moveLeft() {
        running = true;
        PlayerX -= 20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            ballX += xDire;
            ballY += yDire;
            if (ballX < 0) {
                xDire = -xDire;
            }
            if (ballX > 900) {
                xDire = -xDire;
            }
            if (ballY < 0) {
                yDire = -yDire;
            }

            if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(PlayerX, 460, player_width, player_height))) {
                yDire = -yDire;
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 200;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            total_Bricks--;
                            score += 5;


                            if (ballX + 19 <= brickRect.x || ballY + 1 >= brickRect.x + brickRect.width) {
                                xDire = -xDire;
                            } else
                                yDire = -yDire;
                            break A;
                        }
                    }
                }
                timer.start();

            }
            repaint();
        }
    }

        @Override
        public void keyTyped (KeyEvent e){

        }

        @Override
        public void keyPressed (KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) {
                if (PlayerX >= 890)
                    PlayerX = 890;
                else
                    moveRight();
            }

            if (key == KeyEvent.VK_LEFT) {
                if (PlayerX < 10)
                    PlayerX = 10;
                else
                    moveLeft();
            }
            if (key==KeyEvent.VK_ENTER){
                if (!running){
                    running=true;
                    ballX=120;
                    ballY=350;
                    xDire=-1;
                    yDire=-2;
                    PlayerX=310;
                    score=0;
                    total_Bricks=21;
                    map=new MapGenerator(3,7);

                    repaint();
                }
            }
        }

        @Override
        public void keyReleased (KeyEvent e){

        }
    }

