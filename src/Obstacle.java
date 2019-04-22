public class Obstacle extends Sphere {

    private boolean depasse = false; //indique si l'obstacle a été dépassé

    /**
     * Création d'un obstacle vu comme une sphère
     *
     * @param x position initiale en x
     * @param y position initiale en y
     * @param r rayon de l'obstacle
     * @param vx vitesse en x initiale
     * @param vy vitesse en y initiale
     * @param ay accélération en y
     */
    public Obstacle(double x, double y, double r, double vx, double vy, double ay) {
        super(x, y, r, vx, vy, ay);
    }

    /**
     * Mise à jour de la position d'un obstacle
     * @param dt temps écoulé depuis la dernière mise à jour
     */
    public void update(double dt) {

        setVy(getVy() + getAy() * dt);


        setX(getX() + getVx() * dt);
        setY(getY() + getVy() * dt);
    }

    /**
     * Getter de l'attribut depasse
     * @return boolean depasse, si vrai obstacle a été dépassé, faux sinon
     */
    public boolean getDepasse() {
        return depasse;
    }

    /**
     * Setter de depasse
     * @param boo fantôme qui peut dépasser l'obstacle
     */
    public void setDepasse(Fantome boo) {
        //vérifie si le fantôme est plus loin que l'obstacle
        if ((boo.getX()-boo.getR()) > (this.getX()+this.getR())) {
            depasse = true;
        }

    }


}

