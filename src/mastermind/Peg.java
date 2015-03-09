package mastermind;

import javax.swing.JButton;

public class Peg extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PegColors pc;

	public Peg(){
		pc = null;
	}
	
	public void nextColor(){
		int numColor = this.pc.ordinal();
		if(numColor == 5){
			numColor = 0;
		}
		else{
			numColor++;
		}
		this.pc = pc.getColor(numColor);
	}
	
}
