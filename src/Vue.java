import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Vue extends Application {

    //Hauteur et largeur du canvas
    private static final int WIDTH = 640, HEIGHT = 400;

    private Stage stage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext context;

    //Image du fantôme et du background
    private Image ghost = new Image("file:ghost.png");
    private Image background = new Image("file:bg.png");

    //les 27 images des obstacles
    private ArrayList<Image> imageObstacles = new ArrayList<>();

    //contient les images des obstacles qui sont actuellement dans le jeu
    private HashMap<Integer, Image> obstacles = new HashMap<>();

    //position du fantôme par rapport au background
    private double positionBg = 0;

    //Est-ce que l'animation a été mise sur pause
    private boolean isPause = false;

    private CheckBox modeDebug;

    private Text score;

    private Controleur controleur;

    private double r; //Rayon du fantôme

    private String codeSecret="";

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Interface graphique
     * @param stage fenêtre de l'interface
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(5);

        this.stage = stage;
        scene = new Scene(root, WIDTH, HEIGHT+40);
        canvas = new Canvas(WIDTH,HEIGHT);
        controleur = new Controleur(this);
        context = canvas.getGraphicsContext2D();
        r = controleur.rayon();

        HBox barre = new HBox(20); //20 est le spacing entre les éléments
        Button pause = new Button("Pause");
        modeDebug = new CheckBox("Mode debug");
        score = new Text("Score: 0");
        barre.setAlignment(Pos.CENTER);

        barre.getChildren().addAll(pause, new Separator(Orientation.VERTICAL), modeDebug,
                new Separator(Orientation.VERTICAL), score);
        root.getChildren().addAll(canvas, barre);

        //Ce qui se passe quand on clique sur pause ou sur le checkbox
        pause.setOnAction((actionEvent -> {
            isPause = !isPause;
            if(isPause){
                pause.setText("Resume");
            }
            else{
                pause.setText("Pause");
                /* Après l’exécution de la fonction, le focus va automatiquement au canvas */
                Platform.runLater(() -> {
                    canvas.requestFocus();
                });
            }
        }));

        modeDebug.setOnAction((actionEvent -> {
            Platform.runLater(() -> {
                canvas.requestFocus();
            });
        }));

        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.SPACE){
                controleur.vitesseGhost();
            }
            else if(keyEvent.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }


            if(keyEvent.getCode()==KeyCode.T){
              codeSecret+="t";

            }
            if(keyEvent.getCode()==KeyCode.W){
                codeSecret+="w";

            }
            if(keyEvent.getCode()==KeyCode.A){
                codeSecret+="a";

            }
            if(keyEvent.getCode()==KeyCode.D){
                codeSecret+="d";

            }
            if(keyEvent.getCode()==KeyCode.O){
                codeSecret+="o";

                if(codeSecret.equals("twado")){
                    canvas.setRotate(canvas.getRotate()+180);
                    codeSecret="";
                }else{
                    codeSecret="";
                }
            }
        });

        //On va chercher toutes les images des obstacles
        for (int i = 0; i < 27; i++) {
            imageObstacles.add(i, new Image("file:obstacle/"+i+".png"));
        }

        //Animation ici
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                //Si le bouton pause a été enfoncé, on met le deltaTime toujours à 0
                if (lastTime == 0 || isPause) {
                    lastTime = now;
                }

                double deltaTime = (now - lastTime) * 1e-9;
                context.clearRect(0, 0, WIDTH, HEIGHT);

                //On demande au contrôleur de mettre à jour l'affichage
                controleur.update(deltaTime);
                lastTime = now;
            }
        };
        timer.start();

        /* Après l’exécution de la fonction, le focus va automatiquement au canvas */
        Platform.runLater(() -> {
            canvas.requestFocus();
        });

        /* Lorsqu’on clique ailleurs sur la scène, le focus retourne sur le canvas */
        scene.setOnMouseClicked((event) -> {
            canvas.requestFocus();
        });

        stage.setTitle("Flappy Ghost");
        stage.getIcons().add(ghost);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Mise à jour de l'affichage graphique.
     *
     * @param posXGhost position en x du fantôme
     * @param posYGhost position en y du fantôme
     * @param deltaXGhost déplacement en x du fantôme
     * @param obs HashMap qui contient les nouvelles positions des obstacles
     * @param score le score du joueur
     * @param collision HashMap qui dit si un obstacle est entré en collision avec le fantôme
     */
    public void update(double posXGhost, double posYGhost, double deltaXGhost, HashMap<Integer, double[]> obs,
                       int score, HashMap<Integer, Boolean> collision, boolean reinitialise){

        //on avance la position de la variable pour trouver où est le fantôme par rapport au background
        positionBg += deltaXGhost;

        /*Dans le cas où on passe au travers de l'image du background, la variable qui suit la position
        *dans le background en x retourne au début de l'image.
        */
        if(positionBg>=WIDTH){
            positionBg = positionBg-WIDTH;
        }

        /*On coupe le background en 2 images, la première est positionnée à gauche de la fenêtre, soit à la
        * position (0,0) et on ne prend que la partie sur laquelle le fantôme n'est pas encore allée. La
        * deuxième est la partie coupée qui a été coupée à la première image que l'on positionne à droite
        * dans la fenêtre.*/
        context.drawImage(background, positionBg, 0, background.getWidth()-positionBg, background.getHeight(),
                0,0,background.getWidth()-positionBg, HEIGHT);
        context.drawImage(background, 0, 0, positionBg, background.getHeight(),
                background.getWidth()-positionBg, 0, positionBg, HEIGHT);

        //Mise à jour de l'image du fantôme
        if(modeDebug.isSelected()){
            context.setFill(Color.BLACK);
            context.fillOval(WIDTH/2 - r, posYGhost - r, 2*r,2*r);
        }
        else {
            //Dessin du fantôme
            context.drawImage(ghost, WIDTH / 2 - r / 2, posYGhost - r / 2, 2*r,2*r);
        }

        if (reinitialise) {
            obstacles = new HashMap<>();
        }

        //Mise à jour des images des obstacles
        for (Integer i:obs.keySet()) {

            //Dans le cas où on a un nouvel obstacle dans le jeu, on lui associe une image
            if(!obstacles.containsKey(i)){
                obstacles.put(i,imageObstacles.get((int)(Math.random()*27)));
            }

            double[] pos = obs.get(i);
            double posX = pos[0]-posXGhost-pos[2]+WIDTH/2.0;

            if(modeDebug.isSelected()){
                if(collision.get(i)){
                    context.setFill(Color.RED);
                }
                else{
                    context.setFill(Color.YELLOW);
                }
                context.fillOval(posX, pos[1] - pos[2], 2*pos[2], 2*pos[2]);
            }
            else{
                context.drawImage(obstacles.get(i), posX, pos[1]-pos[2], 2*pos[2], 2*pos[2]);
            }
        }

        //Mise à jour du score
        this.score.setText("Score: " + score);

    }

    /**
     * Getter de la largeur de la fenêtre
     * @return largeur de la fenêtre
     */
    public static int getWIDTH() {
        return WIDTH;
    }

    /**
     * Getter de la hauteur du canvas
     * @return hauteur du canvas
     */
    public static int getHEIGHT() {
        return HEIGHT;
    }

    /**
     * Dit si le checkbox est sélectionné ou pas
     * @return boolean du checkbox, si vrai on est dans le mode Debug, sinon faux.
     */
    public boolean modeDebug(){
        return modeDebug.isSelected();
    }

}
