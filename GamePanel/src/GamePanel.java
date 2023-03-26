import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // we are going to have some sort of matrix like how big do we want the objects
    // our game
    static final int UNIT_SIZE = 25;
    // to calculate how many objects can be fitted on the screen
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    // the higher the number of the delay the slower the game is.
    static final int DELAY = 75;
    // creating two array these arrays will hold all
    // the body parts coordination of our snake including the head.
    // since the snake can't be bigger than the game itself our array size will be
    // game units.
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    // initializing the body parts that we want for the snake
    int bodyParts = 6;
    int applesEaten;
    // this going to be the coordination of apples that is located in the x
    // and appear randomly every time the apples are eaten.
    int appleX;
    int appleY;
    // we will have our snake going right when we start the game
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;




    // constructor
    GamePanel(){
       random = new Random();
       // dimension class uses the parameters of height and width
       this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
       this.setBackground(Color.black);
       this.setFocusable(true);
       //The listener interface for receiving keyboard
        // events (keystrokes). The class that is interested in
        // processing a keyboard event either implements this
        // interface (and all the methods it contains) or extends
        // the abstract KeyAdapter class (overriding only the methods
        // of interest).
       this.addKeyListener(new MyKeyAdapter());
       startGame();
    }
    // we made a method called startGame and its void because it won't return any value
    public void startGame(){
        // populating apples so that game can start
       newApple();
       // the game should be running
       running = true;
       // this will dictate how fast the game will run
       timer = new Timer(DELAY,this);
       timer.start();
    }
    // this method is for creating painted components with using graphics
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {
        // this loop is for drawing lines across our game panel ,so it becomes
        // some sort of grid.(with only unit size 25 pixels it was hard to see)
        if (running) {
          /*  for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                // we are going to draw lines across the x-axis and y-axis
                // starting and ending coordinates
                // for x-axis
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                // for y-axis
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }*/
            g.setColor(Color.red);
            //this is how large the apple is going to be
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            // again a for loop to iterate through all the body parts
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    // for a different shade of green we typed the RGB value
                    g.setColor(new Color(45, 180, 0));
                    // to get random snake colors
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            // font metrics is useful for aligning up text in the center of the screen
            FontMetrics metrics = getFontMetrics(g.getFont());
            // this is going to put the text at the very center of the screen width
            g.drawString("Score: " + applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,g.getFont().getSize());

        }else{
            gameOver(g);
        }
    }
    // to be able to populate apple after it's eaten
    public void newApple(){
        // we are going to have this appear along some place at the x-axis
     appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
     // so that we can get one of the positions that we made with grid
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
        //loop for iterating all the body parts of the snake
        // we are actually shifting our bodyparts
     for(int i = bodyParts; i>0; i--){
        x[i] = x[i-1];
         y[i] = y[i-1];
     }
     //creating a switch that will change where our snake is headed.
        switch(direction){
            case 'U' :
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D' :
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L' :
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R' :
                x[0] = x[0] + UNIT_SIZE;
                break;

        }
    }
    // to check whether the snake ate the apple.
    public void checkApple(){
        // we are going to examine coordinates of the snake and the coordinates of the apple
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            // this will function as score.
            newApple();
            // to generate new apple

        }


    }
    // to check whether the snake collided with itself
    public void checkCollisions() {
        // to check if our snake's head collides with its body we are going to
        // iterate through all the body parts with a loop
        // checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                // x[0] = head of our snake y[0] = body
                // if it's true then the head has collided with the body
                // we are basically asking if their coordinates are the same
                running = false;
                // and the snake will stop moving and this will trigger game over
            }
        }
        // checks if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        // checks if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // checks if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        // checks if head touches bottom order
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if(!running){
            timer.stop();
        }
        // The Graphics class is the abstract
        // base class for all graphics contexts that
        // allow an application to draw onto components
        // that are realized on various devices, as well
        // as onto off-screen images.
    }
    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        // font metrics is useful for aligning up text in the center of the screen
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        // this is going to put the text at the very center of the screen width
        g.drawString("Score: " + applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        // font metrics is useful for aligning up text in the center of the screen
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        // this is going to put the text at the very center of the screen width
        g.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2,SCREEN_HEIGHT / 2);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // to be able to see the snake's body we need to call the move method in here
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        // if the game is not running we are going to call the repaint method
        // that method is used for refreshing the new appearance of the part on the graphical UI
        repaint();



    }
    // creating an inner class
    public class MyKeyAdapter extends KeyAdapter{
        //An event which indicates that a keystroke
        // occurred in a component. This low-level
        // event is generated by a component object
        // (such as a text field) when a key is pressed, released, or typed.
        // we need to use this part to be able to control the snake

        @Override
        public void keyPressed(KeyEvent e){

            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    // to avoid users going 180 degree we are going to limit it within 90
                    // so that they won't collide into themselves and the game won't be over
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP :
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN :
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}
