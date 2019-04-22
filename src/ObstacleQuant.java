public class ObstacleQuant extends Obstacle {

    private double tempsActuel; //temps écoulé depuis la dernière mise à jour

    /**
     * Génère un obstacle quantique
     *
     * @param x position initiale en x
     * @param y position initiale en y
     * @param r rayon de l'obstacle
     */
    public ObstacleQuant(double x, double y, double r) {
        super(x, y, r, 0, 0, 0);

    }

    /**
     * Ajuste la position de l'obstacle
     */
    private void quantique(){
            //Déplacement aléatoire en +-30 de la position
            double aleatoireX = Math.random()*60 - 30;
            double aleatoireY = Math.random()*60 - 30;

            //Nouvelle position
            setX(getX()+aleatoireX+Math.signum(aleatoireX)*getR());
            setY(getY()+aleatoireY+Math.signum(aleatoireY)*getR());

    }

    /**
     * Mise à jour de la position en x,y seulement 0.2 seconde a été écoulé
     * @param dt temps écoulé depuis le dernier appel de update
     */
    @Override
    public void update(double dt){
        tempsActuel+=dt;

        /*On vérifie si ça fait plus de 0.2 secondes depuis la dernière mise à jour, si oui, on met à jour la position
         * de l'obstacle
         */
        if(tempsActuel>=0.2) {
            quantique();
            tempsActuel=0;

        }




    }


}