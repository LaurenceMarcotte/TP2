public class Modele {
    private Fantome ghost;

    public Modele(int width, int height){
        this.ghost = new Fantome(width/2, height/2, 30, 120, 0, 500);
    }

    public double[] updatePosition(double deltaTime, int width, int height){
        ghost.update(deltaTime, width, height);
        double[] positions = new double[3];
        positions[0] = ghost.getX();
        positions[1] = ghost.getY();
        positions[2] = ghost.getVx()*deltaTime;
        return positions;
    }
}
