public class Fantome extends Sphere {

    public Fantome(double x, double y, double r, double vx, double vy, double ay){
        super( x, y, r, vx, vy, ay);
    }

    /**
     * Update la position et la vitesse du fantome.
     *
     * @param dt intervalle de temps écoulé depuis la dernière update
     * @param width largeur de la fenêtre de jeu
     * @param height hauteur de la fenêtre de jeu
     */
    public void update(double dt, int width, int height) {
        setVy(getVy()+dt*getAy());
        double vx = getVx();
        double vy = getVy();

        setX(getX() + vx*dt);
        setY(getY() + vy*dt);

        double x = getX();
        double y = getY();
        double r = getR();

/*        if (x + getR() > width ||  - r < 0) {
            setVx(getVx()*(-0.9));
        }*/
        if (y + r > height || y - r < 0) {
            setVy(getVy()*(-0.9));
        }

/*        setX(Math.min(x, width - r));
        setX(Math.max(x, r));*/

        setY(Math.min(y, height - r));
        setY(Math.max(y, r));
    }
}
