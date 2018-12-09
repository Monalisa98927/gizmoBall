import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.Serializable;

public class Circle extends BasicGizmo implements Serializable {

	private int r;
	private GameController gameController;
	private Color color = new Color(102, 255, 134);
	private int weight = 100;

	public int getR() {
		return r;
	}
	public void setR(int r)
	{
		this.r=r;
	}

	public Circle(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.r = 50;

	}

	public boolean collide(Ball ball){

		double squX = (ball.getX() - this.x) * (ball.getX() - this.x);
		double squY = (ball.getY() - this.y) * (ball.getY() - this.y);
		double squR = (ball.r + this.r) * (ball.r + this.r);
		System.out.println(this.x + " "+ this.y );
		System.out.println(ball.getX()+" "+ball.getY());
		System.out.println(squX+" "+squY+" "+squR);
		double sin = (ball.getX() - this.x) / Math.sqrt(squX + squY);
		double cos = (ball.getY() - this.y) / Math.sqrt(squX + squY);

		if(squX + squY > squR){
			return false;
		}
		else {
			double ball_x = this.x + (ball.r + this.r) * sin;
			double ball_y = this.y + (ball.r + this.r) * cos;

			System.out.println("last status :"+ball_x+" "+ball_y);
			ball.setX(ball_x);
			ball.setY(ball_y);

			double squX1 = (ball_x - this.x) * (ball_x - this.x);
			double squY1 = (ball_y - this.y) * (ball_y - this.y);

			return true;
		}
	}

	public void draw(Graphics2D g)
	{
		Ellipse2D ab = new Ellipse2D.Double(x-r,y-r,2*r,2*r);
		g.setColor(color);
		g.fill(ab);
	}
}