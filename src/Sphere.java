public class Sphere {
    private double x, y;
    private double r;

    private double vx, vy;

    // Gravité, seulement en y
    private double ay;

    /**
     * Initialise un objet sphère qui se déplace dans l'espace et qui peut entrer en collision avec d'autres objets
     * @param x position x initiale
     * @param y position y initiale
     * @param r rayon de la sphère
     * @param vx vitesse en x initiale
     * @param vy vitesse en y initiale
     * @param ay accélération en y initiale
     */
    public Sphere(double x, double y, double r, double vx, double vy, double ay) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
        this.ay = ay;
    }

    /**
     * Indique s'il y a intersection entre les deux balles
     *
     * @param other autre sphère avec laquelle on peut entre en collision
     * @return true s'il y a intersection
     */
    public boolean intersects(Sphere other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double d2 = dx * dx + dy * dy;

        return d2 < (this.r + other.r) * (this.r + other.r);
    }

    /**
     * Getter de de la position x
     * @return position x
     */
    public double getX() {
        return x;
    }

    /**
     * Getter de la position en y
     * @return position en y
     */
    public double getY() {
        return y;
    }

    /**
     * Getter du rayon de la sphère
     * @return rayon de la sphère
     */
    public double getR() {
        return r;
    }

    /**
     * Getter de la vitesse en x
     * @return vitesse en x
     */
    public double getVx() {
        return vx;
    }

    /**
     * Getter de la vitesse en y
     * @return vitesse en y
     */
    public double getVy() {
        return vy;
    }

    /**
     * Getter de l'accélération en y
     * @return accélération en y
     */
    public double getAy() {
        return ay;
    }

    /**
     * Setter de la position en x
     * @param x nouvelle position en x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter de la position en y
     * @param y nouvelle position en y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Setter du rayon de la sphère
     * @param r nouveau rayon de la sphère
     */
    public void setR(double r) {
        this.r = r;
    }

    /**
     * Setter de la vitesse en x
     * @param vx nouvelle vitesse en x
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Setter de la vitesse en y
     * @param vy nouvelle vitesse en y
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * Setter de l'accélération en y
     * @param ay nouvelle accélération en y
     */
    public void setAy(double ay) {
        this.ay = ay;
    }
}
