import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; //how big the objects are in the game
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //how many units allowed on per thing 
	static final int DELAY = 75; //how slow the game will be
	final int x[]= new int[GAME_UNITS]; //max length in x
	final int y[] = new int[GAME_UNITS]; //max length in y
	int bodyParts = 6; //starting length
	int applesEaten = 0; //initially zero
	int appleX; //location of apple x
	int appleY; //location of apple y
	char direction = 'R'; //snake going right at beginning of the game
	boolean running = false; //initially game isn't running
	Timer timer; //timer to execute task repeatedly over interval (milliseconds)
	Random random; //random

	GamePanel(){ 
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); //setting the dimensions of the screen
		this.setBackground(Color.black); //background is blue
		this.setFocusable(true); //allows for keyboard input events
		this.addKeyListener(new MyKeyAdapter()); //Adds the newly created MyKeyAdapter instance as a key listener using the addKeyListener() This means that the MyKeyAdapter class's method keyPressed(), will be called whenever a key event occurs within the GamePanel. 
		startGame();
		
	}
	
	public void startGame() {
		newApple(); 
		running = true; //game is running now
		timer = new Timer(DELAY,this); //timer is in control of the game happening. Each delay it updates. 
		timer.start();
		
	}
	
	//to draw the stuff. Each time snake moves it redraws
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) { //adding graphics
		if(running) {
			g.setColor(Color.red); //sets the apple colour to red
			g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE); //makes the apple an oval and first two are positions, last two are the size
		
		
			for(int i =0; i<bodyParts; i++) {
				if(i==0) {//for the head
					g.setColor(Color.green);
					g.fillRect(x[i],y[i],UNIT_SIZE, UNIT_SIZE);
				}
				else //for the body
					g.setColor(new Color(100, 150, 80));
					g.fillRect(x[i],y[i],UNIT_SIZE, UNIT_SIZE);
					}
			
			//Score
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
			
			}
		
		else {
			gameOver(g);
		}
		}
	
	
	
	public void newApple() { //adding a new apple
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //(SCREEN_WIDTH/UNIT_SIZE) is to find a number between 0 and number of grids. The multiply that by the size of a grid to make it the pixel size. 
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; 
	}
	
	public void move() { //to move the snake
		for(int i = bodyParts; i>0; i--) { //to shift all body parts (head is done separately)
			x[i] = x[i-1]; //shift all coordinates in array by one spot (takes the position ahead)
			y[i] = y[i-1]; //same thing but for y-coordinates
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
			
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
			
		case 'R':
			x[0]= x[0] + UNIT_SIZE;
			break;
			
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
	}
	
	//if eating apple, we get a new one
	public void checkApple() { 
		if((x[0]==appleX)&& (y[0]==appleY)) {
			bodyParts++;
			applesEaten++;
			newApple(); 
			
			
		}
	}
	
	public void checkCollisions() {
		//if the head hits the body
		for(int i=bodyParts; i>0; i--) { 
			if((x[0]== x[i]) && (y[0]== y[i])) {
				running =false;
			}
		}
		
		//if head touches left border
		if(x[0] <0) {
			running =false;
		}
		
		  //if head touches right border
	    if (x[0] >= SCREEN_WIDTH) {
	        running = false;
	    }
	    
	    //if head touches top border
	    if (y[0] < 0) {
	        running = false;
	    }
	    
	    //if head touches bottom border
	    if (y[0] >= SCREEN_HEIGHT) {
	        running = false;
	    }
	    
	    //Stop the timer if the game is over
	    if (!running) {
	        timer.stop();
	    }
		
	}
	
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{ //inherits methods such as keyTyped, keyPressed, keyReleased
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				if(direction != 'R') { //cant turn left its already right (cant do 180)
					direction ='L';
					break;
				}
			
			 case KeyEvent.VK_RIGHT:
			 case KeyEvent.VK_D:
			      if (direction != 'L') { //cant turn right if already left
			          direction = 'R'; 
			        }
			        break;
			   
			 case KeyEvent.VK_UP:
			 case KeyEvent.VK_W:
			        if (direction != 'D') { 
			            direction = 'U'; 
			        }
			        break;
			    case KeyEvent.VK_DOWN:
			    case KeyEvent.VK_S:
			        if (direction != 'U') {
			            direction = 'D'; 
			        }
			        break;
			   
			}
			}
		}
		
	

	
}
