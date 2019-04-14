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
        double[] positions = jeu.updatePosition(deltaTime, vue.getHEIGHT());
        System.out.println(positions[0]);
        vue.update(positions[0], positions[1], positions[2]);
    }

    public void vitesseGhost(){
        jeu.vitesseGhost();
    }
}
