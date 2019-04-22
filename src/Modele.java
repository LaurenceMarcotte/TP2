import java.util.HashMap;

public class Modele {

    private Fantome ghost;

    private double dtObstacle; //garde l'intervalle de temps afin de vérifier s'il est temps d'ajouter un obstacle

    private int numeroObstacle; //numéro du dernier obstacle initialisé afin que les nouveaux aient un numéro différent

    private int score;

    private int obstacleEvite; //nombre d'osbtacle évité, si on en évite 2, on réinitialise ce int

    private HashMap<Integer, Obstacle> obstacles = new HashMap<>(); //Contient les obstacles en jeu

    /**
     * Création du modèle/jeu. Commence par créé le fantôme ainsi que le premier obstacle
     *
     * @param width largeur du jeu
     * @param height hauteur du jeu
     */
    public Modele(int width, int height){
        this.ghost = new Fantome(width/2, height/2, 30, 120, 0, 500);
        creerObstacle(); //Création du premier obstacle
    }

    /**
     * Sert à mettre à jour la position du fantôme.
     *
     * @param deltaTime
     * @param height
     * @return
     */
    public double[] updateGhost(double deltaTime, int height){
        ghost.update(deltaTime, height);
        double[] positions = new double[3];
        positions[0] = ghost.getX();
        positions[1] = ghost.getY();
        positions[2] = ghost.getVx()*deltaTime;

        return positions;
    }

    public void vitesseGhost(){
        ghost.setVy(-300);
    }

    public HashMap<Integer, double[]> updateObstacle(double dt, int width, int height){
        dtObstacle+=dt;
        if(dtObstacle>=3){
            creerObstacle();
            dtObstacle=0;
        }

        HashMap<Integer, double[]> o = new HashMap<>();

        for (Obstacle obs: obstacles.values()) {
            obs.update(dt);
            if(!obs.getDepasse()){
                obs.setDepasse(ghost);
                if(obs.getDepasse()){
                    score+=5;
                    obstacleEvite++;
                    ghost.setVx(ghost.getVx()+15);
                    if(obstacleEvite == 2){
                        ghost.setAy(ghost.getAy() + 15);
                        obstacleEvite=0;
                    }
                }
            }

            //obstacle est rendu hors de la fenêtre à gauche
            if(obs.getDepasse() && obs.getX() + obs.getR() - ghost.getX() < - width/2){
                obstacles.remove(obs);
            }

        }

        for (Integer i: obstacles.keySet()) {
            double[] pos = new double[3];
            Obstacle obs = obstacles.get(i);
            pos[0] = obs.getX();
            pos[1] = obs.getY();
            pos[2] = obs.getR();
            o.put(i, pos);
        }

        return o;
    }

    public void creerObstacle(){

        double rayon= Math.floor(Math.random()*35)+10; // regarder si la valeur est ok

        //on va utiliser un switch pour l'analyse du type d'objet

        int objet= (int)Math.ceil(Math.random()*3); // le 3 représente le nb de types d'obstacles. à changer

        double hauteur= Math.random()*(400-2*rayon)+rayon;

        Obstacle obstacle;

        double x= ghost.getX()+320+rayon;
        double y= hauteur;

        //la vitesse n'est pas set ici. on la set en fonction du déroulement du jeu

        switch(objet){

            //obstacle simple
            case 1: obstacle=new ObstacleSimple(x,y,rayon,0,0);
                    obstacles.put(numeroObstacle, obstacle);
                    numeroObstacle++;
                    break;

            //obstacle sinus
            case 2: obstacle= new ObstacleSin(x,y,rayon,0,5,50); // omega=10 à tester
                    obstacles.put(numeroObstacle, obstacle);
                    numeroObstacle++;
                    break;

            //obstacle quantique
            case 3: obstacle= new ObstacleQuant(x,y,rayon,0,0); // à remplir
                    obstacles.put(numeroObstacle, obstacle);
                    numeroObstacle++;
                    break;
        }

    }

    public int getScore(){
        return score;
    }

    public HashMap<Integer, Boolean> testCollision(){
        HashMap<Integer, Boolean> collision = new HashMap();
        for (Integer i: obstacles.keySet()) {
            collision.put(i, ghost.intersects(obstacles.get(i)));
        }
        return collision;
    }

}
