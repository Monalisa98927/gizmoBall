import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.geom.Line2D;

public class Wall {

	private int height;
	private boolean isHit;
	private int width;
	private int x;
	private int y;
	private GameController gameController;

	public Wall(GameController gameController){
		this.gameController = gameController;
	}

	public boolean isHit() {
		return isHit;
	}

	public boolean collide(Ball ball){
		return false;
	}

	public void setPosition(int x, int y){

	}
	public void draw(Graphics2D g)
	{
		for(int i=50;i<=2000;i=i+50){
			Line2D filledLine1=new Line2D.Float(i,0,i,2000);
			Line2D filledLine2=new Line2D.Float(0,i,2000,i);
			g.setColor(Color.gray);
			g.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
			g.draw(filledLine1);
			g.draw(filledLine2);
		}
	}

}