import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import javax.sound.sampled.Line;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Triangle extends BasicGizmo implements Serializable {

	private int rotate;
	private Color color = new Color(255, 232, 96);
	private GameController gameController;
	private int width;
	private List<LineSegment> lineSegments=new ArrayList<LineSegment>();
	private double collisiontime;
	private List<Circle> triangleCircles=new ArrayList<Circle>();
	private Vect newVelocity;
	private double theta;
	private int weight = 100;

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
		this.width = 100;
		this.collisiontime=0.01;
		theta=0;
		newVelocity=new Vect(0,0);
	}

	public double getRotate() {
		return theta;
	}

	public void setRotate(double addedtheta ) {
		this.theta+=addedtheta;
	}

	public List<LineSegment> getLineSegments()
	{
		return this.lineSegments;
	}

	public void setLineSegments(List<LineSegment> lineSegments) {
		LineSegment a=new LineSegment(x,y,x+width*(int)Math.cos(theta),y+width*(int)Math.sin(theta));
		LineSegment b=new LineSegment(x,y,x+width*(int)Math.sin(theta),y - width*(int)Math.cos(theta));
		LineSegment c=new LineSegment(x+width*(int)Math.cos(theta),y+width*(int)Math.sin(theta),x+width*(int)Math.sin(theta),y - width*(int)Math.cos(theta));
		lineSegments.add(a);
		lineSegments.add(b);
		lineSegments.add(c);
		this.lineSegments = lineSegments;
	}

	public List<Circle> getTriangleCircles() {

		return triangleCircles;
	}

	public void setTriangleCircles(List<Circle> triangleCircles) {
		Circle circle0=new Circle(x,y,0);
		Circle circle1=new Circle(x+width*(int)Math.cos(theta),y+width*(int)Math.sin(theta),0);
		Circle circle2=new Circle(x+width*(int)Math.sin(theta),y - width*(int)Math.cos(theta),0);
		triangleCircles.add(circle0);
		triangleCircles.add(circle1);
		triangleCircles.add(circle2);
		this.triangleCircles = triangleCircles;
	}

	public void draw(java.awt.Graphics2D g)
	{
		Polygon filledPolygon=new Polygon();
		filledPolygon.addPoint(x,y);
		filledPolygon.addPoint(x + width*(int)Math.cos(theta),y+width*(int)Math.sin(theta));
		filledPolygon.addPoint(x+width*(int)Math.sin(theta),y - width*(int)Math.cos(theta));
		g.setColor(color);
		g.fillPolygon(filledPolygon);
		g.drawPolygon(filledPolygon);
		setLineSegments(lineSegments);
		setTriangleCircles(triangleCircles);
	}

	public  void handlecollide(Ball ball)
	{
		for(int i=getLineSegments().size()-1;i>=getLineSegments().size()-3;i--)
		{
			if(Geometry.timeUntilWallCollision(getLineSegments().get(i),ball.getBallCircle(),ball.getVelocity())<=collisiontime)
			{
				newVelocity=Geometry.reflectWall(getLineSegments().get(i),ball.getVelocity(),0.8);
				ball.setVelocity(newVelocity);
			}
		}
		for(int i=getTriangleCircles().size()-1;i>=getTriangleCircles().size()-3;i--)
		{
			if(Geometry.timeUntilCircleCollision(getTriangleCircles().get(i),ball.getBallCircle(),ball.getVelocity())<=collisiontime)
			{
				newVelocity= Geometry.reflectCircle(triangleCircles.get(i).getCenter(),ball.getBallCircle().getCenter(),ball.getVelocity(),0.9);
				ball.setVelocity(newVelocity);
			}
		}

	}

	public boolean xcollide(Ball ball){

		if(ball.getX()+ball.r>=this.x&&ball.getX()+ball.r<=this.x+10&&ball.getY()>=this.y-this.width-ball.r&&ball.getY()<=this.y+ball.r) {
			ball.setX(this.x - ball.r);
			return true;
		}
		return false;
	}
	public boolean ycollide(Ball ball){

		if(ball.getY()-ball.r>=this.y-10&&ball.getY()-ball.r<=this.y&&ball.getX()+ball.r>=this.x&&ball.getX()<=this.x+this.width+ball.r) {
			ball.setY(this.y+ball.r);
			return true;
		}
		return false;
	}

	public boolean bevelcollide(Ball ball)
	{
		if(ball.getX()>=this.x&&ball.getX()<=this.x+width+Math.sqrt(2)*ball.r&&ball.getY()>=this.y-width-Math.sqrt(2)*ball.r&&ball.getY()<=this.y&&this.x- ball.getX()+ball.getY()>=this.y-width-Math.sqrt(2)*ball.r&&this.x-ball.getX()+ball.getY()<=this.y-width)
		{
			ball.setX((this.x-this.y+ball.getX()+ball.getY()+width+Math.sqrt(2)*ball.r)/2);
			ball.setY((this.x+this.y-ball.getX()+ball.getY()-width-Math.sqrt(2)*ball.r)/2);
			return true;
		}
		return false;
	}

}