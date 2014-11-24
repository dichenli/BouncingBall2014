package kaleidoscope;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JPanel;

/**
 * The View "observes" and displays what is going on in the Model.
 * In this example, the Model contains only a single kaleidoscope ball.
 * 
 * @author David Matuszek
 * @author Monica Ionescu
 * @author Dichen Li
 */
public class View extends JPanel implements Observer {

	/** The view will be observing the models of the ball,
	 *  the rectangle and the triangle. 
	 */
	Model modelBall, modelRectangle, modelTriangle;
	Color colorBall = Color.RED;
	Color colorRectangle = Color.BLUE;
	Color colorTriangle = Color.GREEN;

	/**
	 * Constructor.
	 * @param model The Models whose working are to be displayed.
	 */
	View(Model modelBall, Model modelRectangle, Model modelTriangle) {
		this.modelBall = modelBall;
		this.modelRectangle =modelRectangle;
		this.modelTriangle = modelTriangle;
	}

	/**
	 * Displays what is going on in the Models. Note: This method should
	 * NEVER be called directly; call repaint() instead.
	 * 
	 * @param g The Graphics on which to paint things.
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(colorBall);
		drawBall(g);
		g.setColor(colorRectangle);
		fillRectangle(g);
		g.setColor(colorTriangle);
		fillTriangle(g);
	}

	/**
	 * draw the ball on the screen, reflected 8 times
	 */
	private void drawBall(Graphics g) {
		int x = modelBall.getX();
		int y = modelBall.getY();
		int[] positionX = new int[] {x, -x, x, -x, y, -y, y, -y};// the X coordinate of ball for each reflection
		int[] positionY = new int[] {y, y, -y, -y, x, x, -x, -x};// the Y coordinate of ball for each reflection
		for(int i = 0; i < 8; i++) {
			//draw the ball at each coordinate specified above
			g.fillOval(moveX(positionX[i]), moveY(positionY[i]), modelBall.MODEL_SIZE, modelBall.MODEL_SIZE);
		}
	}
	
	/**
	 * draw the rectangle on the screen, reflected 8 times
	 */
	private void fillRectangle(Graphics g) {
		int x = modelRectangle.getX();
		int y = modelRectangle.getY();
		int[] positionX = new int[] {x, -x, x, -x, y, -y, y, -y};
		int[] positionY = new int[] {y, y, -y, -y, x, x, -x, -x};
		for(int i = 0; i < 8; i++) {
			g.fillRect(moveX(positionX[i]), moveY(positionY[i]), modelRectangle.MODEL_SIZE, modelRectangle.MODEL_SIZE);
		}
	}

	/**
	 * draw the triangle on the screen, reflected 8 times
	 */
	private void fillTriangle(Graphics g) {
		int x = modelTriangle.getX();
		int y = modelTriangle.getY();
		int[] positionX = new int[] {x, -x, x, -x, y, -y, y, -y};
		int[] positionY = new int[] {y, y, -y, -y, x, x, -x, -x};
		for(int i = 0; i < 8; i++) {
			//Draw the triangle using the coordinates of the three points defined below
			g.fillPolygon(trianglePointsX (moveX(positionX[i])), trianglePointsY (moveY(positionY[i])), 3);
		}
	}
	
	/**
	 *
	 * @param x: x coordinate of the top left point of the triangle
	 * @return the X coordinates of all three points of the triangle
	 */
	private int[] trianglePointsX (int x) {
		return new int[] {x, x + modelTriangle.MODEL_SIZE, x};
		
	}
	/**
	 *
	 * @param y: y coordinate of the top left point of the triangle
	 * @return the Y coordinates of all three points of the triangle
	 */	
	private int[] trianglePointsY (int y) {
		return new int[] {y, y, y +  + modelTriangle.MODEL_SIZE};
		
	}
	
	/**
	 * @param originalX: the X coordinates given by the model
	 * @return the actual x coordinate used on the view
	 */
	private int moveX (int originalX) {
		return originalX + getWidth() / 2;
	}
	/**
	 * @param originalY: the Y coordinates given by the model
	 * @return the actual y coordinate used on the view
	 */
	private int moveY (int originalY) {
		return originalY + getHeight() / 2;
	}

	/**
	 * @return a random color
	 */
	private Color genRandColor() {
		Random rand = new Random();	
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		return new Color(r, g, b);
	}
	
	/**
	 * sets different random colors to each model
	 */
	public void setColor() {
		this.colorBall = genRandColor();
		this.colorRectangle = genRandColor();
		this.colorTriangle = genRandColor();
	}

	/**
	 * When an Observer notifies Observers (and this View is an Observer),
	 * this is the method that gets called.
	 * 
	 * @param obs Holds a reference to the object being observed.
	 * @param arg If notifyObservers is given a parameter, it is received here.
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object arg) {
		repaint();
	}
}