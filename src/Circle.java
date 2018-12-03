import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.geom.Ellipse2D;
import java.io.File;

public class Circle extends BasicGizmo {

	private int r;
	private GameController gameController;
	private Color color = new Color(102, 255, 134);

	public int getR() {
		return r;
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
		double sin = (ball.getX() - this.x) / Math.sqrt(squX + squY);
		double cos = (ball.getY() - this.y) / Math.sqrt(squX + squY);

		if(squX + squY > squR){
			return false;
		}
		else {
			double ball_x = this.x + (ball.r + this.r) * sin;
			double ball_y = this.y + (ball.r + this.r) * cos;
//			System.out.println(sin+" "+cos+" "+(sin*sin+cos*cos));
//			System.out.println(ball.getX()+" "+ball.getY());

			ball.setX(ball_x);
			ball.setY(ball_y);

//			System.out.println(ball_x+" "+ball_y);
//			System.out.println();

			double squX1 = (ball_x - this.x) * (ball_x - this.x);
			double squY1 = (ball_y - this.y) * (ball_y - this.y);
			//System.out.println((squX+squY-squR)+"  "+(squX1+squY1-squR));

			return true;
		}
	}

	public void draw(java.awt.Graphics2D g)
	{
		Ellipse2D ab = new Ellipse2D.Double(x,y,r,r);
		g.setColor(color);
		g.fill(ab);
	}
}