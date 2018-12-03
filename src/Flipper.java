import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Flipper extends Gizmo {

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	protected int height;
	protected boolean isRotate;
	protected int width;
	protected int x;
	protected int y;
	private GameController gameController;
	private Color color = new Color(255, 12, 26);

	public Flipper(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.height = 50;
	}

	public void move(){

	}

	public void setPosition(int x, int y){

	}

	public boolean isRotate() {
		return isRotate;
	}

	public void setRotate(boolean rotate) {
		isRotate = rotate;
	}

	public void draw(java.awt.Graphics2D g)
	{
		Line2D filledLine=new Line2D.Float(x,y,x+height,y);
		g.setColor(color);
		g.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		g.draw(filledLine);
	}

	public boolean collide(Ball ball){

		double squX = (ball.getX() - this.x - this.height/2) * (ball.getX() - this.x - this.height/2);
		double squY = (ball.getY() - this.y) * (ball.getY() - this.y);
		double squR = ball.r + this.height/2;

		if(squX + squY > squR * squR){
			return false;
		}
		else {
			return true;
		}
	}
}