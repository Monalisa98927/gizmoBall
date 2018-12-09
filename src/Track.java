import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Track extends BasicGizmo implements Serializable {

    private Color color = new Color(177, 28, 249);
    private int width;
    private GameController gameController;
    private boolean xEnter = false;
    private boolean yEnter = false;

    public int getWidth() {
        return width;
    }

    public Track(GameController gameController,int x,int y){
        this.gameController = gameController;
        this.x = x;
        this.y = y;
        this.width=100;
    }

    public void draw(Graphics2D g)
    {
        Polygon filledPolygon=new Polygon();
        filledPolygon.addPoint(x,y);
        filledPolygon.addPoint(x + width,y);
        filledPolygon.addPoint(x + width, y + width);
        filledPolygon.addPoint(x + width + width,y + width);
        filledPolygon.addPoint(x + width + width,y + width + width);
        filledPolygon.addPoint(x,y + width + width);
        g.setColor(color);
        g.setStroke(new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
        //g.fillPolygon(filledPolygon);
        g.drawPolygon(filledPolygon);
    }

    public boolean xcollide(Ball ball){

        if(ball.getX()+ball.r>=this.x
                &&ball.getX()+ball.r<this.x+5
                &&ball.getY()>=this.y-ball.r
                &&ball.getY()<=this.y+width+width+ball.r){
            ball.setX(this.x-ball.r);
            return true;
        }
        if(ball.getX()-ball.r>=this.x+width-5
                &&ball.getX()-ball.r<=this.x+width
                &&ball.getY()>=this.y-ball.r
                &&ball.getY()<=this.y+width-ball.r){
            ball.setX(this.x+width+ball.r);
            return true;
        }

        return false;
    }

    public boolean ycollide(Ball ball){

        if(ball.getY()+ball.r>=this.y+width
                &&ball.getY()+ball.r<=this.y+width+5
                &&ball.getX()>=this.x+width-ball.r
                &&ball.getX()<=this.x+width+width+ball.r)
        {
            ball.setY(this.y+this.width-ball.r);
            return true;
        }
        if(ball.getY()-ball.r>=this.y+width+width-5
                &&ball.getY()-ball.r<=this.y+width+width
                &&ball.getX()>=this.x-ball.r
                &&ball.getX()<=this.x+width+width+ball.r)
        {
            ball.setY(this.y+this.width+width+ball.r);
            return true;
        }
        return false;
    }

    public boolean xEnterTrack(Ball ball){                   //判断小球进入下方入口
        if(ball.getX()-ball.r>=this.x+width+width-5
                &&ball.getX()-ball.r<=this.x+width+width
                &&ball.getY()>=this.y-ball.r+width
                &&ball.getY()<=this.y+width+width+ball.r
                &&ball.getVelocityX()<0){

            ball.setY(this.y+1.5*width);
            ball.setVelocityY(0);
            ball.setA(0);

            //System.out.println("xEnterTrack");
            this.xEnter = true;

            return true;
        }
        return false;
    }

    public boolean yEnterTrack(Ball ball){              //判断小球进入上方入口
        if(ball.getY()+ball.r>=this.y
                &&ball.getY()+ball.r<=this.y+5
                &&ball.getX()>=this.x-ball.r
                &&ball.getX()<=this.x+width+ball.r
                &&ball.getVelocityY()>0){

            ball.setX(this.x+0.5*width);
            ball.setVelocityX(0);
            ball.setA(0);

            //System.out.println("yEnterTrack");
            this.yEnter = true;

            return true;
        }
        return false;
    }

    public boolean inTrackGoRight(Ball ball){           //从下方出口出轨道

        if(ball.getX()-ball.r>=this.x+25
                &&ball.getX()+ball.r<=this.x+width-25
                &&ball.getY()-ball.r>=this.y+width+25
                &&ball.getY()+ball.r<=this.y+width+width-25
                &&ball.getVelocityX()==0
                &&yEnter){
            //System.out.println(Math.abs(ball.getVelocityX())+" "+Math.abs(ball.getVelocityY())+" inTrackGoRight");

            ball.setVelocityX(4);
            ball.setVelocityY(0);
            yEnter = false;
            return true;
        }
        return false;
    }

    public boolean inTrackGoUp(Ball ball){              //从上方出口出轨道

        if(ball.getX()-ball.r>=this.x+20
                &&ball.getX()+ball.r<=this.x+width-20
                &&ball.getY()-ball.r>=this.y
                &&xEnter){
            //System.out.println(ball.getA()+" "+Math.abs(ball.getVelocityX())+" "+Math.abs(ball.getVelocityY())+" inTrackGoUp");

            ball.setVelocityY(-4);
            ball.setVelocityX(0);
            xEnter = false;
            return true;
        }
        return false;
    }

}