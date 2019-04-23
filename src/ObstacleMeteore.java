public class ObstacleMeteore extends Obstacle{

    private double grossissement=40; //ce par quoi le rayon augmente

    /**
     * Génère un obstacle météore qui va descendre du haut jusqu'au bas avec la même accélération que le fantôme.
     * Le rayon de l'obstacle va aussi osciller.
     *
     * @param x position initiale en x
     * @param y position initiale en y
     * @param r rayon initial
     * @param vx vitesse initiale en x
     * @param vy vitesse initiale en y
     * @param ay accélération initiale
     */
    public ObstacleMeteore(double x, double y, double r, double vx, double vy, double ay){
        super(x,y,r,vx,vy,ay);
    }

    /**
     * Met à jour la position et le rayon de l'obstacle
     *
     * @param dt temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(double dt){
        double r = getR()+grossissement*dt;
        if(r>=45 || r<=10){
            grossissement*=-1;
            setR(getR()+grossissement*dt);
        }
        else{
            setR(r);
        }

        super.update(dt);

    }
}
