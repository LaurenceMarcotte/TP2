public class Obstacle extends Sphere {


    public Obstacle(double x, double y, double r, double vx, double vy, double ay){ super(x,y,r,vx,vy,ay); }

    public void update(double dt){

        setVy(getVy()+getAy()*dt);


        setX(getX()+getVx()*dt);
        setY(getY()+getVy()*dt);

    }



}
