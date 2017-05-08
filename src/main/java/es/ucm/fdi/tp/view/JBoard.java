package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public abstract class JBoard extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4518722262994516431L;
	
	
	private pieza seleccionada;
	private int fila;
	private int colu;
	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;
	private int _SEPARATOR = -2;
	
	public JBoard() {
		initGUI();
	}

	private void initGUI() {
		setBorder(BorderFactory.createRaisedBevelBorder());

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				JBoard.this.keyTyped(e.getExtendedKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JBoard.this.requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int col = (e.getX() / _CELL_WIDTH);
				int row = (e.getY() / _CELL_HEIGHT);

				int mouseButton = 0;

				if (SwingUtilities.isLeftMouseButton(e))
					mouseButton = 1;
				else if (SwingUtilities.isMiddleMouseButton(e))
					mouseButton = 2;
				else if (SwingUtilities.isRightMouseButton(e))
					mouseButton = 3;

				if (mouseButton == 0)
					return; // Unknown button, don't know if it is possible!
				
				pieza nuevaPieza=getPieza(row,col);
				if(seleccionada!=null && nuevaPieza==seleccionada){
					seleccionar(fila,colu);
					seleccionada=null;
					repaint();
				}
				else if(seleccionada==null){
					seleccionada=nuevaPieza;
					if(seleccionada!=null){
					fila=row;
					colu=col;
					seleccionar(row,col);
					repaint();
					}
				}
				else{
					seleccionar(fila,colu);
					seleccionada=nuevaPieza;
					if(seleccionada!=null){
					fila=row;
					colu=col;
					seleccionar(row,col);
					repaint();
					}
				}
				
				JBoard.this.mouseClicked(row, col, e.getClickCount(), mouseButton);
			}
		});
		
		_SEPARATOR = getSepPixels();
		if ( _SEPARATOR < 0 ) _SEPARATOR = 0;

		this.setPreferredSize(new Dimension(400, 400));
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fillBoard(g);
	}

	private void fillBoard(Graphics g) {

		int numCols = getNumCols();
		int numRows = getNumRows();

		if (numCols <= 0 || numRows <= 0) {
			g.setColor(Color.red);
			g.drawString("Waiting for game to start!", 20, this.getHeight() / 2);
			return;
		}

		_CELL_WIDTH = this.getWidth() / numCols;
		_CELL_HEIGHT = this.getHeight() / numRows;

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++)
				drawCell(i, j, g);
	}
	
	private BufferedImage loadImage(String iconName) {
		try {
			return ImageIO.read(getClass().getResource("/" + iconName + ".png"));
		} catch (IOException ioe) {
			System.err.println("NO ICON for " + iconName);
			return null;
		}
	}
	
	private BufferedImage load2(String name){
		try {
		return ImageIO.read(new File("src/main/resources/" + name + ".png"));
	} catch (IOException ioe) {
		System.err.println("NO ICON for " + name);
		return null;
	}
	}

	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor( getBackground(row, col));
		g.fillRect(x + _SEPARATOR, y + _SEPARATOR, _CELL_WIDTH - 2*_SEPARATOR, _CELL_HEIGHT - 2*_SEPARATOR);

		pieza p = getPieza(row, col);

		if (p != null) {
			Color c=getColor(p);
			Shape s = p.getShape();
			if(s != Shape.CIRCLE && s != Shape.RECTANGLE){
				g.setColor(c);
				if(p.getSelected()) g.setColor(Color.WHITE);
				g.fillOval(x+4, y+4, _CELL_WIDTH -8, _CELL_HEIGHT - 8);
				g.setColor(Color.black);
				g.drawOval(x+4, y+4, _CELL_WIDTH -8, _CELL_HEIGHT - 8);
			}
			if(!p.getSoloCirculo()){
			switch (s) {
			case CIRCLE:
				g.setColor(c);
				if(p.getSelected())g.setColor(Color.WHITE);
				g.fillOval(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				g.setColor(Color.black);
				g.drawOval(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				break;
			case RECTANGLE:
				g.setColor(c);
				if(p.getSelected())g.setColor(Color.WHITE);
				g.fillRect(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				g.setColor(Color.black);
				g.drawRect(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				break;
			case WPAWN:
				g.drawImage(loadImage("WhitePawn"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case BPAWN:
				g.drawImage(loadImage("BlackPawn"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case BKNIGHT:
				//g.drawImage(loadImage("BlackKnight"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("BlackKnight"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case WKNIGHT:
				//g.drawImage(loadImage("WhiteKnight"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("WhiteKnight"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case WBISHOP:
				//g.drawImage(loadImage("WhiteBishop"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("WhiteBishop"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case BBISHOP:
				//g.drawImage(loadImage("BlackBishop"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("BlackBishop"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case WROOK:
				//g.drawImage(loadImage("WhiteRook"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("WhiteRook"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case BROOK:
				//g.drawImage(loadImage("BlackRook"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("BlackRook"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case WQUEEN:
				//g.drawImage(loadImage("WhiteQueen"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("WhiteQueen"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case BQUEEN:
				//g.drawImage(loadImage("BlackQueen"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("BlackQueen"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case WKING:
				//g.drawImage(loadImage("WhiteKing"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("WhiteKing"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;
			case BKING:
				//g.drawImage(loadImage("BlackKing"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				g.drawImage(load2("BlackKing"), x, y, _CELL_WIDTH + 2, _CELL_HEIGHT + 2, null);
				break;

			default:
				break;
				}
			}
		}

	}

	protected abstract void seleccionar(int x, int y);

	protected abstract void keyTyped(int keyCode);

	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);

	protected abstract Shape getShape(int player);

	protected abstract Color getColor(pieza p);

	protected abstract pieza getPieza(int row, int col);

	protected abstract Color getBackground(int row, int col);

	protected abstract int getNumRows();

	protected abstract int getNumCols();

	protected int getSepPixels() {
		return 2;
	}


}
