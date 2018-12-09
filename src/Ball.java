import javafx.scene.shape.Ellipse;
import org.omg.CORBA.MARSHAL;
import physics.Circle;
import physics.Vect;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class Ball implements Serializable {

	private double a = 0.2;
	private double direction;
	private double velocityLoss;
	private double x = 20;
	private double y = 20;
	private double velocityX = 2;
	private double velocityY = 0;
	protected double r = 20;
	private Circle ballCircle;
	private Vect velocity;
	private GameController gameController;
	private Color color = new Color(80, 76, 79);

	public Ball(GameController gameController){
		this.gameController = gameController;
		this.velocityLoss = 0.2;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public Circle getBallCircle() {
		return ballCircle;
	}

	public void setBallCircle(Circle ballCircle) {
		this.ballCircle = ballCircle;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelocityLoss() {
		return velocityLoss;
	}

	public void setVelocityLoss(double velocityLoss) {
		this.velocityLoss = velocityLoss;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public void isAbsorbed(){
		this.setR(0);
		this.gameController.setMode(false);
		this.gameController.setGameOver(true);
	}

	public void startMoving(){

		x = x + velocityX ;
		if (x <= r) {
			x = r;
			velocityX = -velocityX + ( (velocityX > 0)? velocityLoss*velocityX : -velocityLoss*velocityX );
		}
		if (x >= gameController.getWidth() - r) {
			x = gameController.getWidth() - r;
			velocityX = -velocityX + ( (velocityX > 0)? velocityLoss*velocityX : -velocityLoss*velocityX );
		}
		for (int i=0; i<gameController.getCircleCount(); i++){
			if (gameController.getCircle(i).collide(this)){
				double theta=Math.atan((this.y-gameController.getCircle(i).getY())/(this.x-gameController.getCircle(i).getX()));
				velocityX = -velocityY*Math.sin(theta)*Math.cos(theta)-velocityX*Math.cos(theta)*Math.cos(theta)-velocityY*Math.cos(theta)*Math.sin(theta)+velocityX*Math.sin(theta)*Math.sin(theta);
			}
		}
		for(int j=0; j<gameController.getSquareCount(); j++){
			if(gameController.getSquare(j).xcollide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss*velocityX : -velocityLoss*velocityX );
			}
		}
		for(int j=0; j<gameController.getTriangleCount(); j++){
//			if(gameController.getTriangle(j).xcollide(this)||gameController.getTriangle(j).bevelcollide(this)){
//				velocityX=-velocityY;
//			}
			gameController.getTriangle(j).handlecollide(this);
		}
		for(int j=0; j<gameController.getTrapeziumCount(); j++){
			gameController.getTrapezium(j).handlecollide(this);

		}
		for(int j=0; j<gameController.getFlipperCount(); j++){
			if(gameController.getFlipper(j).getLeftRotate())
			{

				if(x>=gameController.getFlipper(j).getX()-r&&x<=gameController.getFlipper(j).getX()+gameController.getFlipper(j).getLength()&&y>=gameController.getFlipper(j).getY()-gameController.getFlipper(j).getLength()&&y<=gameController.getFlipper(j).getY()-r)
				{
					setX(gameController.getFlipper(j).getX()-r);

				}

			}
			if(gameController.getFlipper(j).collide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss*velocityX : -velocityLoss*velocityX );
			}

		}
		for(int j=0; j<gameController.getAbsorberCount(); j++){
			if(gameController.getAbsorber(j).xcollide(this)){
				this.isAbsorbed();
			}
		}

		y = y + velocityY + a/2;
		velocityY = velocityY + a ;
		if (y <= r) {
			y = r;
			velocityY = -velocityY + ( (velocityY > 0)? velocityLoss*velocityY : -velocityLoss*velocityY );
		}
		if (y >= gameController.getHeight() - r) {
			y = gameController.getHeight() - r;
			velocityY = -velocityY + ( (velocityY > 0)? velocityLoss*velocityY : -velocityLoss*velocityY );
		}
		for (int i=0; i<gameController.getCircleCount(); i++){
			if (gameController.getCircle(i).collide(this)){
				double theta=Math.atan((this.y-gameController.getCircle(i).getY())/(this.x-gameController.getCircle(i).getX()));
				velocityY = velocityY*Math.cos(theta)*Math.cos(theta)-velocityX*Math.sin(theta)*Math.cos(theta)-velocityY*Math.sin(theta)*Math.sin(theta)-velocityX*Math.sin(theta)*Math.cos(theta);
			}
		}
		for(int j=0; j<gameController.getSquareCount(); j++){
			if(gameController.getSquare(j).ycollide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss*velocityY : -velocityLoss*velocityY );
			}
		}
//		for(int j=0; j<gameController.getTriangleCount(); j++){
//			if(gameController.getTriangle(j).ycollide(this)||gameController.getTriangle(j).bevelcollide(this)){
//				velocityY=-velocityX;
//			}
//		}
		for(int j=0; j<gameController.getTrapeziumCount(); j++){
			if(gameController.getTrapezium(j).collide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss*velocityY : -velocityLoss*velocityY );
			}
		}
		for(int j=0; j<gameController.getFlipperCount(); j++){
			if(gameController.getFlipper(j).getRightRotate())
			{
				if(y>=gameController.getFlipper(j).getY()-gameController.getFlipper(j).getLength()&&y<=gameController.getFlipper(j).getY()-r&&x>=gameController.getFlipper(j).getX()+r&&x<=gameController.getFlipper(j).getX()+gameController.getFlipper(j).getLength())
				{
					setY(gameController.getFlipper(j).getY()+r);

				}
			}
			if(gameController.getFlipper(j).collide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss*velocityY : -velocityLoss*velocityY );
			}
		}
		for(int j=0; j<gameController.getAbsorberCount(); j++){
			if(gameController.getAbsorber(j).ycollide(this)){
				this.isAbsorbed();
			}
		}

		for(int j=0; j<gameController.getTrackCount(); j++){              //轨道内为真空环境，加速度为0
			if(this.x>=gameController.getTrack(j).getX()
					&&this.x<=gameController.getTrack(j).getX()+gameController.getTrack(j).getWidth()
					&&this.y>=gameController.getTrack(j).getY()
					&&this.y<=gameController.getTrack(j).getY()+gameController.getTrack(j).getWidth()
					||this.x>=gameController.getTrack(j).getX()
					&&this.x<=gameController.getTrack(j).getX()+gameController.getTrack(j).getWidth()
					&&this.y>=gameController.getTrack(j).getY()+gameController.getTrack(j).getWidth()
					&&this.y<=gameController.getTrack(j).getY()+gameController.getTrack(j).getWidth()+gameController.getTrack(j).getWidth()
					||this.x>=gameController.getTrack(j).getX()+gameController.getTrack(j).getWidth()
					&&this.x<=gameController.getTrack(j).getX()+gameController.getTrack(j).getWidth()+gameController.getTrack(j).getWidth()
					&&this.y>=gameController.getTrack(j).getY()+gameController.getTrack(j).getWidth()
					&&this.y<=gameController.getTrack(j).getY()+gameController.getTrack(j).getWidth()+gameController.getTrack(j).getWidth()){
				a = 0;
			}else{
				a = 0.02;
			}
			if(gameController.getTrack(j).ycollide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss*velocityY : -velocityLoss*velocityY );
			}
			if(gameController.getTrack(j).yEnterTrack(this)){
				velocityX = 0;
				velocityY = 4;
			}
			if(gameController.getTrack(j).xcollide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss*velocityX : -velocityLoss*velocityX );
			}
			if(gameController.getTrack(j).xEnterTrack(this)){
				velocityY = 0;
				velocityX = -4;
			}
			if(gameController.getTrack(j).inTrackGoUp(this)){
				velocityX = 0;
				velocityY = -4;
			}
			if(gameController.getTrack(j).inTrackGoRight(this)){
				velocityX = 4;
				velocityY = 0;
			}
		}

	}

	public void draw(Graphics2D g){
		Ellipse2D ab = new Ellipse2D.Double(x-r,y-r,2*r,2*r);
		ballCircle=new Circle(x,y,r);
		velocity=new Vect(velocityX,velocityY);
		g.setColor(color);
		g.fill(ab);
	}

	public Vect getVelocity() {
		return velocity;
	}

	public void setVelocity(Vect velocity) {
		this.velocity = velocity;
		this.velocityX=velocity.x();
		this.velocityY=velocity.y();
	}

	public Rectangle boundingBox() {
		return new Rectangle(0, 0, 1000, 1000);
	}

}