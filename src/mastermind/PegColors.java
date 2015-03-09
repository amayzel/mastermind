package mastermind;

import java.awt.Color;

public enum PegColors {


	PINK(new Color(239,89,123)),
	ORANGE(new Color(255,109,49)),
	GREEN(new Color(115,182,107)),
	YELLOW(new Color(255,203,24)),
	BLUE(new Color(41,162,198)),
	WHITE(Color.WHITE);

	Color c;

	PegColors(Color c){
		this.c = c;
	}

	public Color getColor(){
		return this.getColor();
	}

	public PegColors getColor(int colorNum){
		switch(colorNum) {
		case 0:
			return PINK;
		case 1:
			return ORANGE;
		case 2:
			return GREEN;
		case 3:
			return YELLOW;
		case 4:
			return BLUE;
		case 5:
			return WHITE;
		}
		return null;
	}

}




