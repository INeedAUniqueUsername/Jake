import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;




@SuppressWarnings("serial")
public class SoundExample extends JFrame implements MouseListener{

	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	Clip clip;
	Clip bgSound;
	
	public SoundExample(){
		
		super("SoundExample");
		
		setSize(WIDTH, HEIGHT);
		
		JPanel gamePanel = new JPanel();
		gamePanel.setFocusable(true);
		
		// start listening for mouse events
		gamePanel.addMouseListener(this);
		
		getContentPane().add(gamePanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		music("relax.wav");
		
		
	}
	
	public void playSound(String sndFile) {
		// load sound files
		try {
			// Open an audio input stream.
			java.net.URL url = this.getClass().getClassLoader()
					.getResource(sndFile);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	
	public void music(String sndFile){
		// load sound files
				try {
					// Open an audio input stream.
					java.net.URL url = this.getClass().getClassLoader()
							.getResource(sndFile);
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
					// Get a sound clip resource.
					bgSound = AudioSystem.getClip();
					// Open audio clip and load samples from the audio input stream.
					bgSound.open(audioIn);
					bgSound.loop(100);

				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
		}
	
	
	public static void main(String[] args) {

		@SuppressWarnings("unused")
		SoundExample run = new SoundExample();

	}



	@Override
	public void mouseClicked(MouseEvent e) {
		playSound("boing_x.wav");
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
