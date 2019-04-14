public class Fantome extends Sphere {

    public Fantome(double x, double y, double r, double vx, double vy, double ay){
        super( x, y, r, vx, vy, ay);
    }

    /**
     * Update la position et la vitesse du fantome.
     *
     * @param dt intervalle de temps écoulé depuis la dernière update
     * @param height hauteur de la fenêtre de jeu
     */
    public void update(double dt, int height) {
        setVy(getVy()+dt*getAy());
        double vx = getVx();
        double vy = getVy();

        setX(getX() + vx*dt);
        setY(getY() + vy*dt);

        double y = getY();
        double r = getR();

        if (y + r > height || y - r < 0) {
            setVy(getVy()*(-0.9));
        }

        setY(Math.min(y, height - r));
        setY(Math.max(y, r));
    }

    @Override
    public void setVy(double vy){
        if(vy<300){
            super.setVy(vy);
        }
        else{
            super.setVy(300);
        }
    }
}
