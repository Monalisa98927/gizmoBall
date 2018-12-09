import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class Flipper extends Gizmo implements Serializable {

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	protected int length;
	protected boolean isRotate;
	protected int width;
	protected double theta;
	private int x2;
	private int y2;
	private boolean rightRotate;
	private boolean leftRotate;
	private GameController gameController;
	private Color color = new Color(255, 12, 26);

	public Flipper(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.length = 100;
		this.theta=0;
		this.leftRotate=false;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public double getTheta()
	{
		return  this.theta;
	}
	public void setTheta(double theta)
	{
		this.theta=theta;
	}

	public void move(){

	}


	public boolean isRotate() {
		return isRotate;
	}

	public void setRightRotate(boolean rotate) {
		rightRotate = rotate;
	}
	public boolean getRightRotate()
	{
		return this.rightRotate;
	}
	public  void setLeftRotate(boolean rotate){
		leftRotate=rotate;
	}
	public  boolean getLeftRotate()
	{
		return this.leftRotate;
	}

	public void draw(Graphics2D g)
	{
		double a=Math.toRadians(theta);
		System.out.println(theta+" "+Math.cos(a));
		x2=x+length*(int)Math.cos(a);
		y2=y+length*(int)Math.sin(a);
		System.out.println(x2+" dadsa "+x);
		Line2D filledLine=new Line2D.Float(x,y,x2,y2);
		g.setColor(color);
		g.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
//		AffineTransform transform = new AffineTransform();
//		transform.rotate(theta,x,y);
//		g.setTransform(transform);
		g.draw(filledLine);
	}

	@Override
	public boolean collide(Ball ball){
		if(y2==y)
		{

			if (ball.getX() >= Math.min(x,x2) && ball.getX() <=Math.max(x,x2)&&ball.getY()>=y-ball.r&&ball.getY()<=y+ball.r) {
				if( Math.abs(ball.getY() - (y + ball.r + 5)) >= Math.abs(ball.getY() - (y - ball.r - 5))) {
					ball.setY(y - ball.r - 5);
				}
				else {
					ball.setY(y + ball.r + 5);
				}
				return true;
			}


		}
		else if(x2==x) {
			if(ball.getY()>=Math.min(y,y2)&&ball.getY()<=Math.max(y,y2)&&ball.getX()>=x-ball.r&&ball.getX()<=x+ball.r) {
				if (Math.abs(ball.getX() - (x + ball.r + 5)) >= Math.abs(ball.getX() - (x - ball.r - 5)))
					ball.setX(x - ball.r - 5);
				else
					ball.setX(x + ball.r + 5);
				return true;
			}

		}
		return false;
	}


}
