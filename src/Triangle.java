import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

public class Triangle extends BasicGizmo {

	private int rotate;
	private Color color = new Color(255, 232, 96);
	private GameController gameController;
	private int width;


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Triangle(GameController gameController,int x,int y){
		this.gameController=gameController;
		this.x=x;
		this.y=y;
		this.width = 50;
	}

	public int getRotate() {
		return rotate;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

	public void draw(java.awt.Graphics2D g)
	{
		Polygon filledPolygon=new Polygon();
		filledPolygon.addPoint(x,y);
		filledPolygon.addPoint(x + width,y);
		filledPolygon.addPoint(x,y - width );
		g.setColor(color);
		g.fillPolygon(filledPolygon);
		g.drawPolygon(filledPolygon);
	}

	public boolean collide(Ball ball){

		double squX = (ball.getX() - this.x) * (ball.getX() - this.x);
		double squY = (ball.getY() - this.y) * (ball.getY() - this.y);
		double squR = ball.r + this.width;

		if(squX + squY > squR * squR){
			return false;
		}
		else {
			return true;
		}
	}
}