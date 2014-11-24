/**
 * This is an example of the basic "Bouncing Ball" animation, making
 * use of the Model-View-Controller design pattern and the Timer and
 * Observer/Observable classes.
 */
package kaleidoscope;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;
/**
 * The Controller sets up the GUI and handles all the controls (buttons,
 * menu items, etc.)
 * 
 * @author David Matuszek
 * @author <Dichen Li>
 * @author <Monica Ionescu>
 * 
 */


public class Controller extends JFrame {
	JPanel buttonPanel = new JPanel();
	JButton runButton = new JButton("Run");
	JButton stopButton = new JButton("Stop");
	JButton colorButton = new JButton("Color");
	JButton speedButton = new JButton("Speed");
	Timer timer;

	/** The Model is the object that does all the computations. It is
	 * completely independent of the Controller and View objects. */
	Model modelBall, modelRectangle, modelTriangle;

	/** The View object displays what is happening in the Model. */
	View view;

	/**
	 * Runs the kaleidoscope program.
	 * @param args Ignored.
	 */
	public static void main(String[] args) {
		Controller c = new Controller();
		c.init();
		c.display();
	}

	/**
	 * Sets up communication between the components.
	 */
	private void init() {
		modelBall = new Model();     // The models are independent from the other classes
		modelRectangle = new Model();
		modelTriangle = new Model(); //three models for triangle, ball, and rectangle, respectively
		view = new View(modelBall, modelRectangle, modelTriangle);  // The view observes all the three models and draws all of them 
		modelBall.addObserver(view); // The models need to give permission to be observed
		modelRectangle.addObserver(view);
		modelTriangle.addObserver(view);
	}

	/**
	 * Displays the GUI.
	 */
	private void display() {
		layOutComponents();
		attachListenersToComponents();
		setSize(1000, 1000); 
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Arranges the various components in the GUI.
	 */
	private void layOutComponents() {
		setLayout(new BorderLayout());
		this.add(BorderLayout.SOUTH, buttonPanel);
		buttonPanel.add(runButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(colorButton);
		buttonPanel.add(speedButton);
		stopButton.setEnabled(false);
		this.add(BorderLayout.CENTER, view);
	}

	/**
	 * Attaches listeners to the components, and schedules a Timer.
	 */
	private void attachListenersToComponents() {
		// The Run button tells the Model to start
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				modelBall.start();
				modelRectangle.start();
				modelTriangle.start();
			}
		});
		// The Color button tells the View to change color
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				view.setColor();
			}
		});

		//The Speed button tells the View to change speed
		speedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Random rand = new Random();	
				int x = rand.nextInt(10) + 2;
				int y = rand.nextInt(10) + 2; // speed is a random number between 2 to 12
				modelBall.setSpeed(x, y);
				x = rand.nextInt(10) + 2; //every model is assigned different random speed
				y = rand.nextInt(10) + 2;
				modelRectangle.setSpeed(x, y);
				x = rand.nextInt(10) + 2;
				y = rand.nextInt(10) + 2;
				modelTriangle.setSpeed(x, y);
			}
		});

		// The Stop button tells the Model to pause
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				
				modelBall.pause();
				modelRectangle.pause();
				modelTriangle.pause();
			}
		});
		
		// When the window is resized, the Model is given the new limits
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				
				modelBall.setLimits(view.getWidth(), view.getHeight());
				modelRectangle.setLimits(view.getWidth(), view.getHeight());
				modelTriangle.setLimits(view.getWidth(), view.getHeight());
			}
		});
	}


}