public class Fantome extends Sphere {

    public Fantome(double x, double y, double r, double vx, double vy, double ay){
        super( x, y, r, vx, vy, ay);
    }

    //collision avec le plafond ou le sol
    public boolean testCollision(int height){
        if (this.getX()+this.getR()>height || this.getX() - this.getR()<0) {
            setVx(getVx()*(-0.9));
            setVy(getVy()*(-0.9));
        }
        return true;
    }
}
