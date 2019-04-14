public class Controleur {

    private Vue vue;

    private Modele jeu;

    /**
     * update la position du background, du fantome et des obstacles dans la vue.
     *
     * @param deltaTime temps écoulé depuis la dernière update.
     */
    public void update(double deltaTime){
        //TODO
        int[] positions = jeu.updatePosition(deltaTime, vue.getWIDTH(), vue.getHEIGHT());
        vue.update();
    }
}
