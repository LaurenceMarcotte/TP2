import java.util.HashMap;

public class Controleur {

    private Vue vue;

    private Modele jeu;

    private boolean reinitialise = false;

    /**
     * Crée le Contrôleur du jeu
     * @param vue Interface graphique du jeu
     */
    public Controleur(Vue vue){
        this.jeu = new Modele(vue.getWIDTH(), vue.getHEIGHT());
        this.vue = vue;

    }

    /**
     * update la position du background, du fantome et des obstacles dans la vue.
     *
     * @param deltaTime temps écoulé depuis la dernière update.
     */
    public void update(double deltaTime){
        //positionGhost a comme configuration {positionXFantome, positionYFantome, déplacementEnX}
        double[] positionGhost = jeu.updateGhost(deltaTime, vue.getHEIGHT());

        /*obstacles est un dictionnaire qui associe un tableau des positions en x et y de l'osbtacle avec son
        * numéro. Ainsi, la vue pourra savoir quel numéro a telle nouvelle position, sans savoir quel obstacle
        * s'est. collision informe la vue sur si l'obstacle ayant tel numéro est entré en collision avec le
        * fantôme.*/
        HashMap<Integer, double[]> obstacles = jeu.updateObstacle(deltaTime, vue.getWIDTH(), vue.getHEIGHT());
        HashMap<Integer, Boolean> collision = jeu.testCollision();

        /*Si on n'est pas dans le mode debug et que le fantôme est entré en collision avec un obstacle, on
        * réinitialise le jeu*/
        if (collision.containsValue(true) && !vue.modeDebug()){
            jeu = new Modele(vue.getWIDTH(), vue.getHEIGHT());
            obstacles = new HashMap<>();
            collision = new HashMap<>();
            reinitialise = true;
        }

        vue.update(positionGhost[0], positionGhost[1], positionGhost[2], obstacles, jeu.getScore(), collision,
                reinitialise);
        reinitialise = false;
    }
    public boolean testSecret(){// c un secret ;)
        boolean u=false;
        if(vue.getCodeSecret().equals("twado")){

            u=true;
        }
        vue.setCodeSecret("");
        return u;
}

    /**
     * Dit au modèle de modifier la vitesse du fantôme. Ne devrait être appelé que lorsqu'on appuie sur espace.
     */
    public void vitesseGhost(){
        jeu.vitesseGhost();
    }

    public double rayon(){
        return jeu.getRayon();
    }
}
