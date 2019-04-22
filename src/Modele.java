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
     * @param deltaTime Temps écoulé depuis la dernière mise à jour
     * @param height hauteur du jeu
     * @return un tableau de double qui a comme format {positionXFantome, positionYFantome, déplacementXFantome}
     */
    public double[] updateGhost(double deltaTime, int height){
        ghost.update(deltaTime, height);
        double[] positions = new double[3];
        positions[0] = ghost.getX();
        positions[1] = ghost.getY();
        positions[2] = ghost.getVx()*deltaTime;

        return positions;
    }

    /**
     * Ajuste la vitesse du fantome à 300 vers le haut, devrait être appelé quand on appuie sur espace
     */
    public void vitesseGhost(){
        ghost.setVy(-300);
    }

    /**
     * Met à jour les positions de tous les obstacles en jeu. Commence par crééer des obstacles si 3 secondes s'est
     * écoulé depuis la dernière création d'obstacle.
     *
     * @param dt temps écoulé depuis la dernière mise à jour
     * @param width largeur de la fenêtre de jeu
     * @param height hauteur de la fenêtre de jeu
     * @return nouvelles positions des obstacles dans un hashmap qui associe les positions avec le numéro de l'osbtacle
     */
    public HashMap<Integer, double[]> updateObstacle(double dt, int width, int height){
        dtObstacle+=dt;

        //On crée un nouvel obstacle à chaque 3 secondes
        if(dtObstacle>=3){
            creerObstacle();
            dtObstacle=0;
        }

        //mise à jour des positions des obstacles ici
        for (Obstacle obs: obstacles.values()) {
            obs.update(dt);
            obstacleDepasse(obs); //vérifie si l'osbtacle a été dépassé

            //obstacle est rendu hors de la fenêtre à gauche, on le retire/détruit
            if(obs.getDepasse() && obs.getX() + obs.getR() - ghost.getX() < - width/2){
                obstacles.remove(obs);
            }
        }

        //va contenir les nouvelles positions de chaque obstacle
        HashMap<Integer, double[]> o = new HashMap<>();

        //ajout des nouvelles positions des obstacles dans o
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

    /**
     * Création des nouveaux obstacles ici avec leur position initiale
     */
    public void creerObstacle(){

        double rayon= Math.floor(Math.random()*35)+10; // Définie le rayon de l'obstacle


        /* on va utiliser un switch pour l'analyse du type d'objet.
         * Le 3 représente le nb de types d'obstacles. Si c'est 0, on a un obstacle simple, si c'est 1, un obstacle
         * sinus, si c'est 3 un obstacle quantique.
         */
        int objet= (int)Math.ceil(Math.random()*3); //

        Obstacle obstacle;

        /*
         * Position initiale de l'osbtacle. On l'initialise en x juste à l'extérieur de la fenêtre.
         * En y, on le place de façon aléatoire.
         */
        double x = ghost.getX()+320+rayon;
        double y = Math.random()*(400-2*rayon)+rayon;

        switch(objet){

            //obstacle simple
            case 1: obstacle=new Obstacle(x,y,rayon,0,0,0);
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

    /**
     * Getter de l'attribut score qui contient le score du joueur
     * @return le score du joueur
     */
    public int getScore(){
        return score;
    }

    /**
     * Vérification des collisions entre le fantôme et les obstacles.
     *
     * @return un HashMap qui contient true or false dépendamment de si le fantôme est entré en collision avec un
     * obstacle.
     */
    public HashMap<Integer, Boolean> testCollision(){
        HashMap<Integer, Boolean> collision = new HashMap();

        for (Integer i: obstacles.keySet()) {
            collision.put(i, ghost.intersects(obstacles.get(i)));
        }

        return collision;
    }

    /**
     *
     * @param obs
     */
    public void obstacleDepasse(Obstacle obs){
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
    }

}
