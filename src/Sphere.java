public class Sphere {
    private double x, y;
    private double r;

    private double vx = 500, vy = 190;

    // Gravité
    private double ay = 200;

    public Sphere(double x, double y, double r, double vx, double vy, double ay) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public void update(double dt, int width, int height) {

        vx += dt;
        vy += dt * ay;

        x += dt * vx;
        y += dt * vy;

        if (x + r > width || x - r < 0) {
            vx *= -0.9;
        }
        if (y + r > height || y - r < 0) {
            vy *= -0.9;
        }

        x = Math.min(x, width - r);
        x = Math.max(x, r);

        y = Math.min(y, height - r);
        y = Math.max(y, r);
    }

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

    /**
     * Interchange les vitesses s'il y a collision entre les deux balles
     *
     * @param other l'autre balle
     */
    public void testCollision(Sphere other) {
        if (this.intersects(other)) {
            double vx = this.vx;
            double vy = this.vy;

            this.vx = other.vx;
            this.vy = other.vy;

            other.vx = vx;
            other.vy = vy;

            // Calculer la quantité qui overlap en X, same en Y
            // Déplacer les deux de ces quantités/2
            double dx = other.x - this.x;
            double dy = other.y - this.y;
            double d2 = dx * dx + dy * dy;
            double d = Math.sqrt(d2);

            // Overlap en pixels
            double overlap = d - (this.r + other.r);

            // Direction dans laquelle se déplacer (normalisée)
            double directionX = dx / d;
            double directionY = dy / d;

            double deplacementX = directionX * overlap / 2;
            double deplacementY = directionY * overlap / 2;

            this.x += deplacementX;
            this.y += deplacementY;
            other.x -= deplacementX;
            other.y -= deplacementY;
        }
    }
}
