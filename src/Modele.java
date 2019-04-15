import java.util.ArrayList;

public class Modele {
    private Fantome ghost;

    private ArrayList<Obstacle> obstacles =new ArrayList<>();

    public Modele(int width, int height){
        this.ghost = new Fantome(width/2, height/2, 30, 120, 0, 500);
    }

    public double[] updatePosition(double deltaTime, int height){
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


    public void creerObstacle(){

        double rayon= Math.floor(Math.random()*35)+10; // regarder si la valeer est ok

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
                    obstacles.add(obstacle);
                    break;

            //obstacle sinus
            case 2: obstacle= new ObstacleSin(x,y,rayon,0,10,50); // omega=10 à tester
                    obstacles.add(obstacle);
                    break;

            //obstacle quantique
            case 3: obstacle= new ObstacleQuant(x,y,rayon,0,0); // à remplir
                    obstacles.add(obstacle);
                    break;
        }

    }

}
