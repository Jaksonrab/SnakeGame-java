import javax.swing.JFrame;

public class GameFrame extends JFrame{

	GameFrame(){
		
//		GamePanel panel = new GamePanel();
//		this.add(panel);
		
		this.add(new GamePanel()); //game panel is the inner stuff
		this.setTitle("Snake"); //the title of the game frame is "Snake"
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when user closes window the application will close
		this.setResizable(false); //wont be able to resize the game
		this.pack(); //pack the components tightly 
		this.setVisible(true); //visible on the screen 
		this.setLocationRelativeTo(null); //make it appear in the middle of the computer
		
	
	}
}
