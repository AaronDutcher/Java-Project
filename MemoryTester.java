/*
 * Aaron Dutcher
 * Into Java class 
 * 06/22/2020
 */



package application;

//Importing all the util for my game
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

	public static void main(String[] args) {
        launch(args);
    }
	
    //getting the number of pairs to match with the number of rows
	private  int numPairs = 28;
    private  int numRows = 7;
    
    private Tile picked = null;
    private int counter = 2;

    
    //building the bordersize
    private Parent game() {
        Pane root = new Pane();
        root.setPrefSize(355, 405);

        //getting the letters in my name to match
        char c = 'A' ;
        List<Tile> tiles = new ArrayList<>();
        for (int j = 0; j < numPairs; j++) {
            tiles.add(new Tile(String.valueOf(c)));
            tiles.add(new Tile(String.valueOf(c)));
            c++;
        }

        Collections.shuffle(tiles);
        
        // making sure the same number of rows and letters are equally split
        for (int j = 0; j < tiles.size(); j++) {
            Tile tile = tiles.get(j);
            tile.setTranslateX(50 * (j % numRows));
            tile.setTranslateY(50 * (j / numRows));
            root.getChildren().add(tile);
        }

        return root;
    }

    
    //setting my stage to start my game
    @Override
    public void start(Stage Stage) throws Exception {
        Stage.setScene(new Scene(game()));
        Stage.show();
     
    }
    //this is the border in the game with the font size 
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
        /*
         * how the game responds to the mouse click and to make sure 
         * it has 2 clicks per turn. If the letters are the same then
         * they stay on the screen if not then they disappear.
         */
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
        
        //A fade transition for when the letters open 
        public void open(Runnable action) {
        	FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), text);
            fadeTransition.setToValue(1);
            fadeTransition.setOnFinished(e -> action.run());
            fadeTransition.play();
        }
        
        //A fade transition for when the letters close
        public void close() {
        	FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), text);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }
        
        //boolean to see if the tiles are the same
        public boolean hasSameValue(Tile other) {
            return text.getText().equals(other.text.getText());
        }
    }
}
