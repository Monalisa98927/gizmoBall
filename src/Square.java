import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Square extends BasicGizmo {

	private Color color = new Color(255, 123, 156);
	private int width;
	private GameController gameController;

	public int getWidth() {
		return width;
	}

	public Square(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.width=50;
	}

	public void draw(java.awt.Graphics2D g)
	{
		Rectangle2D ab = new Rectangle2D.Double(x,y,width,width);
		g.setColor(color);
		g.fill(ab);
	}

	public boolean collide(Ball ball){

		double squX = (ball.getX() - this.x) * (ball.getX() - this.x);
		double squY = (ball.getY() - this.y) * (ball.getY() - this.y);
		double squR = ball.r + this.getWidth() ;

		if(squX + squY > squR * squR){
			return false;
		}
		else {
			return true;
		}
	}
}