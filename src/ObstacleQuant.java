public class ObstacleQuant extends Obstacle {

    private int compteur;

    private double origine;

    public ObstacleQuant(double origine, double y, double r, double vx, double ay) {
        super(origine, y, r, vx, 0, ay);

        this.origine=origine;
    }


    private void quantique(boolean ordre) {

        double aleatoireX= Math.random()*60 -30;
        double aleatoireY=Math.random()*60-30;

        if (ordre){

            setX(origine+aleatoireX+Math.signum(aleatoireX)*getR());
            setY(origine+aleatoireY+Math.signum(aleatoireY)*getR());

        }

    }


    @Override
    public void update(double dt){
        super.update(dt);
        compteur++;
        quantique(compteur%3==0);

    }


}