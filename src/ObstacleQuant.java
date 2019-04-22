public class ObstacleQuant extends Obstacle {

    private double tempsActuel;


    public ObstacleQuant(double x, double y, double r, double vx, double ay) {
        super(x, y, r, vx, 0, ay);

    }

    private void quantique(){
            double aleatoireX = Math.random()*60 - 30;
            double aleatoireY = Math.random()*60 - 30;


            setX(getX()+aleatoireX+Math.signum(aleatoireX)*getR());
            setY(getY()+aleatoireY+Math.signum(aleatoireY)*getR());

    }

    /**
     *
     * @param dt
     */
    @Override
    public void update(double dt){
        tempsActuel+=dt;

        /*On vérifie si ça fait plus de 0.2 secondes depuis la dernière mise à jour, si oui on met à jour la position
         * de l'obstacle
         */
        if(tempsActuel>=0.2) {
            quantique();
            tempsActuel=0;

        }




    }


}