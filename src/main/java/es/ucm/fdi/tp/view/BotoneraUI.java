package es.ucm.fdi.tp.view;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.tp.mvc.ModeListener;

@SuppressWarnings("unused")
public class BotoneraUI extends JPanel{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	private JButton random;
	private JButton smart;
	private JButton reiniciar;
	private JButton apagar;
	private ButtonsUIListener listener;
	
	private ModoJugador modo;
	
	
	public void setPuedeInteractuar(boolean x){
		listener.setPuedeInteractuar(x);
	}
	
	private ImageIcon loadIcon(String iconName) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getResource("/" + iconName + ".png")));
		} catch (IOException ioe) {
			System.err.println("NO ICON for " + iconName);
			return null;
		}
	}
	
	BotoneraUI(ButtonsUIListener listen){
		super();
		//String filePath=new File("").getAbsolutePath();
		this.setVisible(true);
		this.listener=listen;

		//random.setIcon(loadIcon("dice"));
		//smart.setIcon(loadIcon("nerd"));
		//reiniciar.setIcon(loadIcon("restart"));
		//apagar.setIcon(loadIcon("exit"));
		this.random=new JButton("Random");
		random.setIcon(new ImageIcon("src/main/resources/dice.png"));
		this.smart=new JButton("Smart");	
		smart.setIcon(new ImageIcon("src/main/resources/nerd.png"));
		this.reiniciar=new JButton("Reiniciar");
		reiniciar.setIcon(new ImageIcon("src/main/resources/restart.png"));
		this.apagar=new JButton("Apagar");
		apagar.setIcon(new ImageIcon("src/main/resources/exit.png"));


		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.modo=new ModoJugador();
		
		random.addActionListener(listener);
		smart.addActionListener(listener);
		reiniciar.addActionListener(listener);
		apagar.addActionListener(listener);
		this.add(random);		
		this.add(smart);
		this.add(reiniciar);
		this.add(apagar);
		this.add(modo);
		
	}
	
	public void AddModeObserver(ModeListener o){
		this.modo.addModeObserver(o);
	}
	
	public static void main(String args[]){
		JFrame ventana=new JFrame();
		ventana.setSize(600,600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contenido=new JPanel();
		//contenido.setVisible(true);
		contenido.add(new BotoneraUI(new ButtonsUIListener(){
			@Override
			public void generateRandom() {	}
			@Override
			public void generateSmart() {	}
			@Override
			public void reset() {}
			@Override
			public void apagar() {
				System.exit(0);
			}
		}));
		ventana.setContentPane(contenido);
		ventana.setVisible(true);
		}
}

