public class ObstacleQuant extends Obstacle {

    private double origine;

    public ObstacleQuant(double origine, double y, double r, double vx, double ay) {
        super(origine, y, r, vx, 0, ay);

        this.origine=origine;
    }


    private void quantique(boolean ordre) {

        double aleatoire= Math.random()*60 -30;

        if (ordre){

            setX(origine+aleatoire+Math.signum(aleatoire)*getR());

        }else{ setY(origine+aleatoire+Math.signum(aleatoire)*getR());}

    }

    public void update(){
        //pt un compteur pour faire le quantum ?

    }


}