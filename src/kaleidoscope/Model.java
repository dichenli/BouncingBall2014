package kaleidoscope;

import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the Model class for a kaleidoscope. It is an Observable,
 * which means that it can notifyObservers that something in the
 * model has changed, and they should take appropriate actions.
 * 
 * @author David Matuszek
 * @author Dichen Li
 * @author Monica Ionescu
 */
public class Model extends Observable {
	public final int MODEL_SIZE = new Random().nextInt(50) + 50;  //size of object is set randomly
	private int xPosition = 0; //initial position is (0, 0)
	private int yPosition = 0;
	private int xLimit, yLimit;
	private int xDelta = 6; //initial speed
	private int yDelta = 4;
	private Timer timer;

	/**
	 * Sets the "walls" that the ball should bounce off from.
	 * 
	 * @param xLimit The position (in pixels) of the wall on the right.
	 * @param yLimit The position (in pixels) of the floor.
	 */
	public void setLimits(int xLimit, int yLimit) {
		this.xLimit = xLimit;
		this.yLimit = yLimit;
		xPosition = Math.min(xPosition, xLimit);
		yPosition = Math.min(yPosition, yLimit);
	}
	/**
	 * Tells the ball to advance one step in the direction that it is moving.
	 * If it hits a wall, its direction of movement changes.
	 */
	public void makeOneStep() {
		// Do the work
		xPosition += xDelta;
		if (xPosition < - xLimit / 2 || xPosition > xLimit / 2 - MODEL_SIZE ) {
			xDelta = -xDelta;
			xPosition += xDelta;
		}
		yPosition += yDelta;
		if (yPosition < -yLimit / 2 || yPosition > yLimit / 2 - MODEL_SIZE ) {
			yDelta = -yDelta;
			yPosition += yDelta;
		}
		// Notify observers
		setChanged();
		notifyObservers();
	}
	/**
	 * @return The balls X position.
	 */
	public int getX() {
		return xPosition;
	}

	/**
	 * @return The balls Y position.
	 */
	public int getY() {
		return yPosition;
	}

	/**
	 * changes value of speed in x direction
	 */
    public void setxDelta(int xDelta) {
		this.xDelta = xDelta;
	}
    
    /**
	 * changes value of speed in y direction
	 */
	public void setyDelta(int yDelta) {
		this.yDelta = yDelta;
	}
	
	/**
	 * changes value of speed of the model
	 */
	public void setSpeed(int xDelta, int yDelta) {
		setxDelta(xDelta);
		setyDelta(yDelta);
	}
	
	/**
	 * Tells the ball to start moving. This is done by starting a Timer
	 * that periodically executes a TimerTask. The TimerTask then tells
	 * the ball to make one "step."
	 */
	public void start() {
		Random rand = new Random();
		xDelta = rand.nextInt(10) + 2;
		yDelta = rand.nextInt(10) + 2;
		int deltaSize = rand.nextInt(200);
		timer = new Timer(true);
		timer.schedule(new Strobe(), 0, 40); // 25 times a second        
	}

	/**
	 * Tells the ball to stop where it is.
	 */
	public void pause() {
		timer.cancel();
	}



	/**
	 * Tells the model to advance one "step."
	 */
	private class Strobe extends TimerTask {
		@Override
		public void run() {
			makeOneStep();
		}
	}
}