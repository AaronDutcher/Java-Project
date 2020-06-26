/*
 * Aaron Dutcher
 * Into Java class 
 * 06/22/2020
 */



package application;

//Importing all my utils for my program
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;




public class MemoryTester extends Application {

    
	private static final int numPairs = 28;
    private static final int numRows = 7;
    
    private Tile picked = null;
    private int counter = 2;

    
    
    private Parent game() {
        Pane root = new Pane();
        root.setPrefSize(355, 405);

        char c = 'A' ;
        List<Tile> tiles = new ArrayList<>();
        for (int j = 0; j < numPairs; j++) {
            tiles.add(new Tile(String.valueOf(c)));
            tiles.add(new Tile(String.valueOf(c)));
            c++;
        }

        Collections.shuffle(tiles);

        for (int j = 0; j < tiles.size(); j++) {
            Tile tile = tiles.get(j);
            tile.setTranslateX(50 * (j % numRows));
            tile.setTranslateY(50 * (j / numRows));
            root.getChildren().add(tile);
        }

        return root;
    }

    @Override
    public void start(Stage Stage) throws Exception {
        Stage.setScene(new Scene(game()));
        Stage.show();
     
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile(String value) {
            Rectangle border = new Rectangle(50, 50);
            border.setFill(null);
            border.setStroke(Color.BLUE);

            text.setText(value);
            text.setFont(Font.font(30));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(this::handleMouseClick);
            close();
        }

        public void handleMouseClick(MouseEvent event) {
            if (isOpen() || counter == 0)
                return;

            counter--;

            if (picked == null) {
                picked = this;
                open(() -> {});
            }
            else {
                open(() -> {
                    if (!hasSameValue(picked)) {
                        picked.close();
                        this.close();
                    }

                    picked = null;
                    counter = 2;
                });
            }
        }

        public boolean isOpen() {
            return text.getOpacity() == 1;
        }

        public void open(Runnable action) {
        	FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), text);
            fadeTransition.setToValue(1);
            fadeTransition.setOnFinished(e -> action.run());
            fadeTransition.play();
        }

        public void close() {
        	FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), text);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }

        public boolean hasSameValue(Tile other) {
            return text.getText().equals(other.text.getText());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
