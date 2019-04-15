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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Vue extends Application {

    private static final int WIDTH = 640, HEIGHT = 400;

    private Stage stage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext context;

    private Image ghost = new Image("file:ghost.png");
    private Image background = new Image("file:bg.png");

    //les 27 images des obstacles
    private ArrayList<Image> imageObstacles = new ArrayList<>();

    //contient les images des obstacles qui sont actuellement dans le jeu
    private HashMap<Integer, Image> obstacles = new HashMap<>();

    //contient les positions des obstacles sur la fenêtre
    private HashMap<Integer, int[]> posObstacles = new HashMap<>();

    //position du fantôme par rapport au background
    private double positionBg = 0;

    //Est-ce que l'animation a été mise sur pause
    private boolean isPause = false;

    private CheckBox modeDebug;

    private double r = 30;
    private Controleur controleur;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(5);
        this.stage = stage;
        scene = new Scene(root, WIDTH, HEIGHT+40);
        canvas = new Canvas(WIDTH,HEIGHT);
        controleur = new Controleur(this);

        context = canvas.getGraphicsContext2D();

        HBox barre = new HBox(20); //20 est le spacing entre les éléments
        Button pause = new Button("Pause");
        modeDebug = new CheckBox("Mode debug");

        Text score = new Text("Score: 0");
        barre.setAlignment(Pos.CENTER);

        barre.getChildren().addAll(pause, new Separator(Orientation.VERTICAL), modeDebug,
                new Separator(Orientation.VERTICAL), score);
        root.getChildren().addAll(canvas, barre);

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

        scene.setOnKeyPressed((keyEvent -> {
            if(keyEvent.getCode() == KeyCode.SPACE){
                controleur.vitesseGhost();
            }
            else if(keyEvent.getCode() == KeyCode.ESCAPE){
                Platform.exit();
            }
        }));

        for (int i = 0; i < 27; i++) {
            imageObstacles.add(i, new Image("file:obstacle/"+i+".png"));
        }

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;


            @Override
            public void handle(long now) {
                if (lastTime == 0 || isPause) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) * 1e-9;
                context.clearRect(0, 0, WIDTH, HEIGHT);

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

    public void update(double posXGhost, double posYGhost, double deltaXGhost, HashMap<Integer, double[]> obs){

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

        if(modeDebug.isSelected()){
            context.fillOval(WIDTH/2 - r, posYGhost - r, 2*r,2*r);
        }
        else {
            //Dessin du fantôme
            context.drawImage(ghost, WIDTH / 2 - r / 2, posYGhost - r / 2, 2*r,2*r);
        }
        for (Integer i:obs.keySet()) {
            if(!obstacles.containsKey(i)){
                obstacles.put(i,imageObstacles.get((int)(Math.random()*27)));
            }
            double[] pos = obs.get(i);
            double posX = pos[0]-posXGhost-pos[2]+WIDTH/2;
            context.drawImage(obstacles.get(i), posX, pos[1]-pos[2], 2*pos[2], 2*pos[2]);
            continue;
        }
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
}
