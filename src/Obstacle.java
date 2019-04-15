public class Obstacle extends Sphere {

    private boolean depasse = false;


    public Obstacle(double x, double y, double r, double vx, double vy, double ay) {
        super(x, y, r, vx, vy, ay);
    }

    public void update(double dt) {

        setVy(getVy() + getAy() * dt);


        setX(getX() + getVx() * dt);
        setY(getY() + getVy() * dt);

    }

    public boolean getDepasse() {
        return depasse;
    }

    private void setDepasse(Fantome boo) {

        if ((boo.getX()-boo.getR()) > (this.getX()+this.getR())) {
            depasse = true;
        }

    }


}

