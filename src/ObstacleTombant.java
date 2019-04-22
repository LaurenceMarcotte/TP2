public class ObstacleTombant extends Obstacle{

    private double grossissement=40;

    public ObstacleTombant(double x, double y, double r, double vx, double vy, double ay){
        super(x,y,r,vx,vy,ay);
    }

    @Override
    public void update(double dt){
        double r = getR()+grossissement*dt;
        if(r>=45 || r<=10){
            grossissement*=-1;
            setR(getR()+grossissement*dt);
        }
        else{
            setR(r);
        }

        super.update(dt);

    }
}
