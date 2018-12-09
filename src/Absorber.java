import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Absorber extends Gizmo implements Serializable {

	private Color color = new Color(70, 75, 255);
	private int height;
	private boolean isHit;
	private int width;
	private GameController gameController;

	public Absorber(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.width=200;
		this.height=50;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHit() {
		return isHit;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean xcollide(Ball ball){				//水平方向发生碰撞

		if(ball.getX()+ball.r>=this.x&&ball.getX()+ball.r<this.x+10&&ball.getY()>=this.y-ball.r&&ball.getY()<=this.y+height+ball.r){
			ball.setX(this.x-ball.r);
			return true;
		}
		if(ball.getX()-ball.r>=this.x+height-10&&ball.getX()-ball.r<=this.x+height&&ball.getY()>=this.y-ball.r&&ball.getY()<=this.y+height+ball.r){
			ball.setX(this.x+width+ball.r);
			return true;
		}

		return false;
	}

	public boolean ycollide(Ball ball){				//竖直方向发生碰撞

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

	public void draw(Graphics2D g)		//画吸收器
	{
		Rectangle2D ab = new Rectangle2D.Double(x,y,width,height);
		g.setColor(color);
		g.fill(ab);
	}

}

