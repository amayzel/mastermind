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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Mastermind extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final int ROWS = 10;
	private static final int COLS = 4;
	
	private int numPegs = 4;

	private JButton[][] mainButtons = new JButton[ROWS][COLS];
	private JButton[][] sideButtons = new JButton[ROWS*2][COLS/2];
	private JButton[] keyButtons = new JButton[numPegs];
	private JButton doneButton = new JButton();


	private Color PINK = new Color(239,89,123);
	private Color ORANGE = new Color(255,109,49);
	private Color GREEN = new Color(115,182,107);
	private Color YELLOW = new Color(255,203,24);
	private Color BLUE = new Color(41,162,198);
	private Color WHITE = Color.WHITE;

	private Random r = new Random();
	private int[] key = new int[numPegs];
	private int[] guess = new int[numPegs];
	private int turn = 0;

	private int whitePegs = 0;
	private int blackPegs = 0;
	private int temp;

	public Mastermind() {
		setSize(400, 600);
		setTitle("Mastermind");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		Container board = getContentPane();
		Container center = new Container();
		Container centerCenter = new Container();
		Container centerEast = new Container();
		Container southCenter = new Container();
		Container southWest = new Container();
		Container east   = new Container();
		Container west   = new Container();
		Container north  = new Container();
		Container south  = new Container();

		board.setLayout(new BorderLayout());
		board.setBackground(Color.WHITE);

		center.setLayout(new BorderLayout());
		south.setLayout(new BorderLayout());

		centerCenter.setLayout(new GridLayout(ROWS, COLS));
		centerEast.setLayout(new GridLayout(ROWS*2, COLS/2));
		southCenter.setLayout(new GridLayout(1,4));
		southWest.setLayout(new GridLayout(2,2));

		center.add(centerCenter, BorderLayout.CENTER);
		center.add(centerEast, BorderLayout.EAST);

		south.add(southCenter, BorderLayout.CENTER);
		south.add(southWest, BorderLayout.WEST);

		board.add(center, BorderLayout.CENTER);
		board.add(east, BorderLayout.EAST);
		board.add(west, BorderLayout.WEST);
		board.add(north, BorderLayout.NORTH);
		board.add(south, BorderLayout.SOUTH);		

		
		ActionListener mainPegListener = new ActionListener() {
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

				enableDoneButton();
			}
		};

		ActionListener doneListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (event.getSource().equals(doneButton)) {
					checkGuess();
					temp = blackPegs;
					paintSideButtons();
					turn++;
					for (int i = 0; i < ROWS; i++) {
						for (int j = 0; j < COLS; j++) {
							if (i == turn) {
								mainButtons[i][j].setEnabled(true);
							} else {
								mainButtons[i][j].setEnabled(false);
							}
						}
					}
					
					if(won() || temp == 4){
						disableAll();
						showKey();
						JOptionPane.showMessageDialog(null, "YOU WON!!!");
					}
					else if(lost() || turn == 10){
						showKey();
						JOptionPane.showMessageDialog(null, "YOU LOST!!!");
					}					
					else{
						doneButton.setEnabled(false);
					}
				}

				
			}
		};

		north.setLayout(new GridLayout());
		doneButton = new JButton("Check");
		doneButton.addActionListener(doneListener);
		doneButton.setEnabled(false);
		north.add(doneButton);
		
		for(int i = 0; i < sideButtons.length; i++){
			for(int j = 0; j < sideButtons[i].length; j++){
				sideButtons[i][j] = new JButton();
				sideButtons[i][j].setBackground(Color.LIGHT_GRAY);
				sideButtons[i][j].setEnabled(false);
				sideButtons[i][j].addActionListener(mainPegListener);
				centerEast.add(sideButtons[i][j]);
			}
		}

		
		

		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				mainButtons[i][j] = new JButton();
				mainButtons[i][j].setBackground(Color.LIGHT_GRAY);
				mainButtons[i][j].addActionListener(mainPegListener);
				if (i == turn) {
					mainButtons[i][j].setEnabled(true);
				} else {
					mainButtons[i][j].setEnabled(false);
				}
				centerCenter.add(mainButtons[i][j]);
			}
		}
		
		generateKey();
		
		for(int i = 0; i < keyButtons.length; i++){
			
			keyButtons[i] = new JButton();
			keyButtons[i].setBackground(Color.LIGHT_GRAY);
			keyButtons[i].setEnabled(false);
			southCenter.add(keyButtons[i]);
			if(i == keyButtons.length-1)
				printOutKey();
		}
		//southWest.setForeground(Color.WHITE);
		southWest.add(new JLabel("ANSWER KEY: "));
		

		enableNextRow();

	}
	
	
	public void generateKey(){
		for(int i = 0; i < key.length; i++){
			key[i] = r.nextInt(5);
		}
		//take this out and call when game is over
		//showKey();
	}
	
	public void showKey(){
		for(int i = 0; i < key.length; i++){
			keyButtons[i].setBackground(numToColor(key[i])); 
		}
	}
	
	public void printOutKey(){
		showKey();
		System.out.println("This is the answer: ");
		for(int i = 0; i < key.length; i++){
			if(keyButtons[i].getBackground().equals(PINK)){
				System.out.println("PINK"); 
			}
			if(keyButtons[i].getBackground().equals(ORANGE)){
				System.out.println("ORANGE"); 
			}
			if(keyButtons[i].getBackground().equals(YELLOW)){
				System.out.println("YELLOW"); 
			}
			if(keyButtons[i].getBackground().equals(GREEN)){
				System.out.println("GREEN"); 
			}
			if(keyButtons[i].getBackground().equals(BLUE)){
				System.out.println("BLUE"); 
			}
			if(keyButtons[i].getBackground().equals(WHITE)){
				System.out.println("WHITE"); 
			}
			keyButtons[i].setBackground(Color.LIGHT_GRAY);
		}
	}
	
	public Color numToColor(int num){
		if(num == 0){
			return PINK; 
		}
		else if(num == 1){
			return ORANGE; 
		}
		else if(num == 2){
			return YELLOW; 
		}
		else if(num == 3){
			return GREEN; 
		}
		else if(num == 4){
			return BLUE; 
		}
		else if(num == 5){
			return WHITE; 
		}
		return null;
		
	}
	
	public int colorToNum(Color c){
		if(c.equals(PINK)){
			return 0; 
		}
		else if(c.equals(ORANGE)){
			return 1; 
		}
		else if(c.equals(YELLOW)){
			return 2; 
		}
		else if(c.equals(GREEN)){
			return 3; 
		}
		else if(c.equals(BLUE)){
			return 4; 
		}
		else if(c.equals(WHITE)){
			return 5; 
		}
		return 6;
		
	}
	
	public void enableNextRow(){
		for(int i = 0; i < mainButtons[turn].length; i++){
			mainButtons[turn][i].setEnabled(true);
		}
		for(int i = 0; i < mainButtons.length; i++){
			if(i != turn){
				for(int j = 0; j < mainButtons[i].length; j++){
					mainButtons[i][j].setEnabled(false);
				}
			}

		}
	}

	public void enableDoneButton(){
		if(!mainButtons[turn][0].getBackground().equals(Color.LIGHT_GRAY)
				&&	!mainButtons[turn][1].getBackground().equals(Color.LIGHT_GRAY)
				&&	!mainButtons[turn][2].getBackground().equals(Color.LIGHT_GRAY)
				&&	!mainButtons[turn][3].getBackground().equals(Color.LIGHT_GRAY)){

			doneButton.setEnabled(true);
		}
		
	}
	
	public void disableAll(){
		for(int i = 0; i < mainButtons.length; i++){
			for(int j = 0; j < mainButtons[i].length; j++){
				mainButtons[i][j].setEnabled(false);
			}
		}
		for(int i = 0; i < sideButtons.length; i++){
			for(int j = 0; j < sideButtons[i].length; j++){
				sideButtons[i][j].setEnabled(false);
			}
		}
		
		doneButton.setEnabled(false);
		
	}
	
	public void checkGuess(){

		blackPegs = 0;
		whitePegs = 0;

		for(int i = 0; i < guess.length; i++){
			guess[i] = colorToNum(mainButtons[turn][i].getBackground());
		}

		for (int i = 0; i < 4; i++){
			if (guess[i] == key[i]){
				blackPegs++;
			}				
		}
			
		ArrayList<Integer> alreadyProcessed = new ArrayList<Integer>();
		
		for (int color : key){
			for (int j = 0; j < 4; j++){
				if (color == guess[j] && !alreadyProcessed.contains(j)) {
					alreadyProcessed.add(j);
					whitePegs++;
					break;
				}
			}
		}
		whitePegs -= blackPegs;
	}
	
	public boolean won(){
		if(temp == 4){
			return true;
		}
		return false;
	}

	public boolean lost(){
		if(turn == 10){
			return true;
		}
		return false;
	}
	
	public void paintSideButtons(){	
		
		int[] o = new int[2];
		o[0] = turn*2;
		o[1] = turn*2+1;
		
		/*paintingSpots = new int[4][2];
		paintingSpots[0][0] = 0; 
		paintingSpots[0][1] = turn*2;
		paintingSpots[1][0] = 1; 
		paintingSpots[1][1] = turn*2;
		paintingSpots[2][0] = 0; 
		paintingSpots[2][1] = turn*2+1;
		paintingSpots[3][0] = 1; 
		paintingSpots[3][1] = turn*2+1;*/
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 2; j++){
				if(blackPegs != 0){
					sideButtons[o[i]][j].setBackground(Color.BLACK);
					blackPegs--;
				}
				else if(whitePegs != 0){
					sideButtons[o[i]][j].setBackground(Color.WHITE);
					whitePegs--;
				}
			}			
		}
	}
	
	public static void main(String [] args){
		Mastermind mm = new Mastermind();
		mm.setVisible(true);
	}

}