import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Vue extends Application {

    public static final int WIDTH = 640, HEIGHT = 400;

    private Controleur controleur;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root, WIDTH, HEIGHT+40);
        Canvas canvas = new Canvas(WIDTH,HEIGHT);

        Image ghost = new Image("file:ghost.png");

        GraphicsContext context = canvas.getGraphicsContext2D();
        Image background = new Image("file:bg.png");

        context.drawImage(background, 0, 0);
        /*System.out.println(background.getWidth());
        context.drawImage(background, 320, 0,
                background.getWidth()/2, background.getHeight(),
                0,0,320,400);*/

        context.drawImage(ghost, WIDTH/2-ghost.getWidth()/2, HEIGHT/2-ghost.getHeight()/2);

        HBox barre = new HBox(20);
        Button pause = new Button("pause");
        CheckBox modeDebug = new CheckBox("Mode debug");
        Text score = new Text("Score: 0");
        barre.setAlignment(Pos.CENTER);

        barre.getChildren().addAll(pause, modeDebug, score);
        root.getChildren().addAll(canvas, barre);

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) * 1e-9;
                context.clearRect(0, 0, WIDTH, HEIGHT);

                controleur.update(deltaTime);


            }
        };

        stage.setTitle("Flappy Ghost");
        stage.getIcons().add(ghost);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
