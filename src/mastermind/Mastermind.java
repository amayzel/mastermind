package mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mastermind extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final int ROWS = 10;
	private static final int COLS = 4;
	
	private JButton[][] mainButtons = new JButton[ROWS][COLS];
	private JButton[][] sideButtons = new JButton[ROWS*2][COLS/2];
	
	private Color PINK = new Color(239,89,123);
	private Color ORANGE = new Color(255,109,49);
	private Color GREEN = new Color(115,182,107);
	private Color YELLOW = new Color(255,203,24);
	private Color BLUE = new Color(41,162,198);
	private Color WHITE = Color.WHITE;

	private Random r = new Random();
	private int[] key = new int[4];
	private int[] guess = new int[4];
	
	private int turn = 0;
	
	
	@SuppressWarnings("deprecation")
	public Mastermind() {
		setSize(400, 600);
		setTitle("Mastermind");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		Container board = getContentPane();
		board.setLayout(new BorderLayout());
		board.setBackground(Color.BLUE);
		Container container = new Container();
		GridLayout grid = new GridLayout(ROWS, COLS);
		container.setLayout(grid);
		board.add(container, BorderLayout.CENTER);
		
		Container container2 = new JPanel();
		container2.setLayout(new GridLayout(20,2));
		board.add(container2, BorderLayout.EAST);
		
		for(int i = 0; i < key.length; i++){
			key[i] = r.nextInt(5);
		}
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < ROWS; i++) {
					for (int j = 0; j < COLS; j++) {
						if (event.getSource().equals(mainButtons[i][j])) {
							if(mainButtons[i][j].getBackground().equals(Color.LIGHT_GRAY)){
								mainButtons[i][j].setBackground(PINK);
							}
							else if(mainButtons[i][j].getBackground().equals(PINK)){
								mainButtons[i][j].setBackground(ORANGE);
							}
							else if(mainButtons[i][j].getBackground().equals(ORANGE)){
								mainButtons[i][j].setBackground(YELLOW);
							}
							else if(mainButtons[i][j].getBackground().equals(YELLOW)){
								mainButtons[i][j].setBackground(GREEN);
							}
							else if(mainButtons[i][j].getBackground().equals(GREEN)){
								mainButtons[i][j].setBackground(BLUE);
							}
							else if(mainButtons[i][j].getBackground().equals(BLUE)){
								mainButtons[i][j].setBackground(WHITE);
							}
							else if(mainButtons[i][j].getBackground().equals(WHITE)){
								mainButtons[i][j].setBackground(PINK);
							}
						}
					}
				}
			}
		};
		
		ActionListener listener2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < sideButtons.length; i++) {
					for (int j = 0; j < sideButtons[i].length; j++) {
						if (event.getSource().equals(sideButtons[i][j])) {
							if(sideButtons[i][j].getBackground().equals(Color.DARK_GRAY)){
								sideButtons[i][j].setBackground(PINK);
							}
							
						}
					}
				}
			}
		};
		
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				
				mainButtons[i][j] = new JButton();
				mainButtons[i][j].setBackground(Color.LIGHT_GRAY);
				mainButtons[i][j].addActionListener(listener);
				container.add(mainButtons[i][j]);
			}
		}
		
		for(int i = 0; i < sideButtons.length; i++){
			for(int j = 0; j < sideButtons[i].length; j++){
				
				sideButtons[i][j] = new JButton();
				sideButtons[i][j].setBackground(Color.DARK_GRAY);
				sideButtons[i][j].addActionListener(listener);
				container2.add(sideButtons[i][j]);
			}
		}
		
		mainButtons[0][0].setEnabled(false);
		
		
		
	}
	
	public void enableNextRow(){
		
		for(int i = 0; i < 4; i++){
			
		}
	}
	
	public boolean enableSideButtons(){
		if(mainButtons[turn][0].getBackground().equals(Color.LIGHT_GRAY)
			&&	mainButtons[turn][1].getBackground().equals(Color.LIGHT_GRAY)
			&&	mainButtons[turn][2].getBackground().equals(Color.LIGHT_GRAY)
			&&	mainButtons[turn][3].getBackground().equals(Color.LIGHT_GRAY)){
			 
			
		}
		return false;
	}
	
	public void checkGuess(){
		
		ArrayList<Color> answerPegs = new ArrayList<Color>();
		
		for(int i = 0; i < guess.length; i++){
			if(mainButtons[turn][i].getBackground().equals(PINK)){
				guess[i] = 0;
			}
			else if(mainButtons[turn][i].getBackground().equals(ORANGE)){
				guess[i] = 1;
			}
			else if(mainButtons[turn][i].getBackground().equals(YELLOW)){
				guess[i] = 2;
			}
			else if(mainButtons[turn][i].getBackground().equals(GREEN)){
				guess[i] = 3;
			}
			else if(mainButtons[turn][i].getBackground().equals(BLUE)){
				guess[i] = 4;
			}
			else if(mainButtons[turn][i].getBackground().equals(WHITE)){
				guess[i] = 5;
			}
		}
		
		
		for(int i = 0; i < guess.length; i++){
			for(int j = 0; j < key.length; j++){
				if(guess[i] == key[j]){
					if(i == j){
						answerPegs.add(Color.BLACK);
					}
					else{
						answerPegs.add(Color.WHITE);
					}
				}
			}
		}
	}
	
	public static void main(String [] args){
		Mastermind mm = new Mastermind();
		mm.setVisible(true);
	}

}