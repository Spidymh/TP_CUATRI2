package es.ucm.fdi.tp.mvc;

import java.awt.Color;

public class ColorChangeEvent {
	int row;
	Color NuevoColor;
	
	public ColorChangeEvent(int row, Color c) {
		this.row=row;
		this.NuevoColor=c;
	}

	public int getRow(){
		return this.row;
	}
	
	public Color getNuevoColor(){
		return this.NuevoColor;
	}
}
