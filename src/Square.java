import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Square extends BasicGizmo implements Serializable {

	private Color color = new Color(255, 123, 156);
	private int width;
	private GameController gameController;
	private int weight = 100;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Square(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.width=100;
	}

	public void draw(Graphics2D g)
	{
		Rectangle2D ab = new Rectangle2D.Double(x,y,width,width);
		g.setColor(color);
		g.fill(ab);
	}

	public boolean xcollide(Ball ball){

		if(ball.getX()+ball.r>=this.x&&ball.getX()+ball.r<this.x+10&&ball.getY()>=this.y-ball.r&&ball.getY()<=this.y+width+ball.r){
			ball.setX(this.x-ball.r);
			return true;
		}
		if(ball.getX()-ball.r>=this.x+width-10&&ball.getX()-ball.r<=this.x+width&&ball.getY()>=this.y-ball.r&&ball.getY()<=this.y+width+ball.r){
			ball.setX(this.x+width+ball.r);
			return true;
		}

		return false;
	}


	public boolean ycollide(Ball ball){

		if(ball.getY()+ball.r>=this.y&&ball.getY()+ball.r<=this.y+10&&ball.getX()>=this.x-ball.r&&ball.getX()<=this.x+width+ball.r){
			ball.setY(this.y-ball.r);
			return true;
		}
		if(ball.getY()-ball.r>=this.y+width-10&&ball.getY()-ball.r<=this.y+width&&ball.getX()>=this.x-ball.r&&ball.getX()<=this.x+width+ball.r)
		{
			ball.setY(this.y+this.width+ball.r);
			return true;
		}
		return false;
	}

}