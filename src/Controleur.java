import java.util.HashMap;

public class Controleur {

    private Vue vue;

    private Modele jeu;

    public Controleur(Vue vue){
        this.vue = vue;
        this.jeu = new Modele(vue.getWIDTH(), vue.getHEIGHT());
    }

    /**
     * update la position du background, du fantome et des obstacles dans la vue.
     *
     * @param deltaTime temps écoulé depuis la dernière update.
     */
    public void update(double deltaTime){
        double[] positions = jeu.updateGhost(deltaTime, vue.getHEIGHT());
        HashMap<Integer, double[]> obstacles = jeu.updateObstacle(deltaTime, vue.getWIDTH(), vue.getHEIGHT());
        HashMap<Integer, Boolean> collision = jeu.testCollision();
        if (collision.containsValue(true) && !vue.modeDebug()){
            jeu = new Modele(vue.getWIDTH(), vue.getHEIGHT());
        }
        vue.update(positions[0], positions[1], positions[2], obstacles, jeu.getScore(), collision);
    }

    public void vitesseGhost(){
        jeu.vitesseGhost();
    }
}
