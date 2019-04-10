import javafx.application.Application;
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

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root, 640, 440);
        Canvas canvas = new Canvas(640,400);
        HBox barre = new HBox();

        Image ghost = new Image("file:ghost.png");

        GraphicsContext context = canvas.getGraphicsContext2D();
        Image background = new Image("file:bg.png");
        context.drawImage(background, 0,0);

        Button pause = new Button("pause");
        CheckBox modeDebug = new CheckBox("Mode debug");
        Text score = new Text("Score: 0");
        barre.getChildren().addAll(pause, modeDebug, score);
        root.getChildren().addAll(canvas, barre);

        stage.setTitle("Flappy Ghost");
        stage.getIcons().add(ghost);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
