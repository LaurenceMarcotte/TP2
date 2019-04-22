public class Fantome extends Sphere {

    /**
     * Création d'un fantôme vu comme une sphère
     *
     * @param x position initiale en x
     * @param y position initiale en y
     * @param r rayon du fantôme
     * @param vx vitesse initiale en x
     * @param vy vitesse initiale en y
     * @param ay accélération initiale en y
     */
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
        setY(Math.max(getY(), r));
    }

    /**
     * Ajustement de la vitesse en y. Ne peut pas dépasser 300 px/s
     * @param vy nouvelle vitesse en y
     */
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
