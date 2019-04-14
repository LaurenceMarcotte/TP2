import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Vue extends Application {

    private static final int WIDTH = 640, HEIGHT = 400;

    private Stage stage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext context;

    private Image ghost = new Image("file:ghost.png");
    private Image background = new Image("file:bg.png");

    private double positionBg = 0;


    private boolean isPause = false;

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

        context.drawImage(background, positionBg, 0);

        context.drawImage(ghost, WIDTH/2-ghost.getWidth()/2, HEIGHT/2-ghost.getHeight()/2);

        HBox barre = new HBox(20); //20 est le spacing entre les éléments
        Button pause = new Button("Pause");
        CheckBox modeDebug = new CheckBox("Mode debug");
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

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                if(isPause){
                    lastTime = now;
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

    public void update(double posXGhost, double posYGhost, double deltaXGhost){
        positionBg += deltaXGhost;
        if(positionBg>=WIDTH){
            positionBg = positionBg-WIDTH;
        }
        if(positionBg==0){
            context.drawImage(background, 0,0);
        }
        context.drawImage(background, positionBg, 0, background.getWidth()-positionBg, background.getHeight(),
                0,0,background.getWidth()-positionBg, HEIGHT);
        context.drawImage(background, 0, 0, positionBg, background.getHeight(),
                background.getWidth()-positionBg, 0, positionBg, HEIGHT);

        context.drawImage(ghost, WIDTH/2-ghost.getWidth()/2, posYGhost-ghost.getHeight()/2);
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
}
