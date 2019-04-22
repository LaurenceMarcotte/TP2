public class ObstacleSin extends Obstacle {

    private double omega; //vitesse angulaire
    private double amplitude; //amplitude du sinus
    private double origine; //position initiale en y, centre du sinus
    private double tempsEcoule; //temps écoulé depuis la création de l'obstacle


    /**
     * Génère un obstacle sinusoïdal
     *
     * @param x position initiale en x
     * @param origine position initiale en y, centre du sinus
     * @param r rayon de l'obstacle
     * @param vx vitesse initiale en x
     * @param omega vitesse angulaire du sinus
     * @param amp amplitude du sinus
     */
    public ObstacleSin(double x, double origine ,double r, double vx, double omega,double amp){
        super(x,origine,r,vx,omega,0);
        this.omega=omega;
        amplitude=amp;
        this.origine=origine;
    }

    /**
     * Mise à jour de la position du l'obstacle sinus
     * @param dt temps écoulé depuis la dernière mise à jour
     */
    @Override
    public void update(double dt){
        tempsEcoule+=dt;
        setX(getX()+dt*getVx());
        setY(origine+amplitude*Math.sin(omega*tempsEcoule));

    }

    /**
     * Getter de la vitesse angulaire
     * @return vitesse angulaire
     */
    public double getOmega(){
        return omega;
    }

    /**
     * Setter de la vitesse angulaire
     * @param u nouvelle vitesse angulaire
     */
    public void setOmega(double u){
        omega=u;
    }

    /**
     * Getter de l'amplitude du sinus
     * @return amplitude du sinus
     */
    public double getAmplitude(){
        return amplitude;
    }

    /**
     * Setter de l'amplitude du sinus
     * @param u nouvelle amplitude du sinus
     */
    public void setAmplitude(double u){
        amplitude=u;
    }
}
