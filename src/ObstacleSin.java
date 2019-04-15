public class ObstacleSin extends Obstacle {

    private double omega;
    private double amplitude;
    private double origine;
    private double tempsEcoule;



    public ObstacleSin(double x, double origine ,double r, double vx, double omega,double amp){
        super(x,origine,r,vx,1.0,0);
        this.omega=omega;
        amplitude=amp;
        this.origine=origine;
    }

    @Override
    public void update(double dt){
        tempsEcoule+=dt;
        setX(getX()+dt*getVx());
        setY(origine+amplitude*Math.sin(omega*tempsEcoule));

    }

    public double getOmega(){
        return omega;
    }

    public void setOmega(double u){
        omega=u;
    }

    public double getAmplitude(){
        return amplitude;
    }

    public void setAmplitude(double u){
        amplitude=u;
    }
}
