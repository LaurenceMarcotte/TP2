public class Sphere {
    private double x, y;
    private double r;

    private double vx, vy;

    // Gravité, seulement en y
    private double ay;


    public Sphere(double x, double y, double r, double vx, double vy, double ay) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
        this.ay = ay;
    }

    //update pour les obstacles
/*    public void update(double dt) {
        vy += dt * ay;

        x += dt * vx;
        y += dt * vy;
    }*/

    /**
     * Indique s'il y a intersection entre les deux balles
     *
     * @param other
     * @return true s'il y a intersection
     */
    public boolean intersects(Sphere other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double d2 = dx * dx + dy * dy;

        return d2 < (this.r + other.r) * (this.r + other.r);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getAy() {
        return ay;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }
}
