

public class ObstacleQuant extends Obstacle {

    //private int compteur;
    //private double compteur;

    double origineX;
    private long temps= System.currentTimeMillis();

    public ObstacleQuant(double x, double y, double r, double vx, double ay) {
        super(x, y, r, vx, 0, ay);

        origineX=x;

    }


/*    private void quantique(boolean ordre) {

        double aleatoireX= Math.random()*60 -30;
        double aleatoireY=Math.random()*60-30;

        if (ordre){

            setX(origine+aleatoireX+Math.signum(aleatoireX)*getR());
            setY(origine+aleatoireY+Math.signum(aleatoireY)*getR());

        }

    }*/

        private void quantique(){
            double aleatoireX = Math.random()*60 - 30;
            double aleatoireY = Math.random()*60 - 30;


            setX(origineX+aleatoireX+Math.signum(aleatoireX)*getR());
            setY(getY()+aleatoireY+Math.signum(aleatoireY)*getR());

        }


    @Override
    public void update(double dt){
        /*super.update(dt);
        compteur++;
        quantique(compteur%3==0);*/
        //compteur++;
        if((System.currentTimeMillis()-temps)%200==0) {// le temps est à vérif
            quantique();
        }




    }


}