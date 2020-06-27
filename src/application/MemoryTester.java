/*
 * Aaron Dutcher
 * Into Java class 
 * 06/22/2020
 */




package application;

//import javafx scenes
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;






public class MemoryTester extends Application {

	
	Scene menu;
	Scene game;
	Stage primaryStage;
	 
	
	static void main(String[] args) {
	        launch(args);
	    }
	 
	  private GameMenu gameMenu;
	  
	  
    @Override
    public void start(Stage mainWindow) throws Exception {
    
    primaryStage = mainWindow;
    	
    	//the size of the window
    Pane root = new Pane();
    root.setPrefSize(355, 405);
    
    
    //importing the picture for the  menu 
	InputStream iStream = Files.newInputStream(Paths.get("res/images/Thinking.png"));
    Image pic = new Image(iStream);
    iStream.close();
    
    //making the image fit to the size i have for my menu
    ImageView IView = new ImageView(pic);
    IView.setFitWidth(355);
    IView.setFitHeight(405);
    
    //combining the picture and game menu
    gameMenu = new GameMenu();
    root.getChildren().addAll(IView, gameMenu);
    
    menu = new Scene(root);
    game = new Scene(game());
    
    
    //putting my name in the title
    mainWindow.setTitle("Aaron Duthcer's Memory Game");
    mainWindow.setScene(menu);
    mainWindow.show();
    }
    
    //this is creating the game menu buttons and naming them.
    private class GameMenu extends Parent {
        public GameMenu() {
            VBox menu = new VBox(20);
            
            //location of the buttons           
            menu.setTranslateX(100);
            menu.setTranslateY(200);
                    
            
            //start button
            MenuButton start = new MenuButton("START");
            start.setOnMouseClicked(event -> {
                primaryStage.setScene(game);
            });
               
            //exit button
            MenuButton exit = new MenuButton("EXIT");
            exit.setOnMouseClicked(event -> {
                System.exit(0);
            });
           
            //so both buttons will display on the menu
            menu.getChildren().addAll(start, exit);
            
            getChildren().addAll(menu);
            
            };
        
    }
    //but the effects on the buttons
    private static class MenuButton extends StackPane {
    	private Text text;
    	
    	//making the color red for the text
    	public MenuButton(String name) {
    		text = new Text(name);
    		text.getFont();
			text.setFont(Font.font(20));
    		text.setFill(Color.RED);
    		
    		//making the background blue of the button
    		Rectangle bg = new Rectangle(150, 40);
    		bg.setOpacity(0.6);
    		bg.setFill(Color.BLUE);
    		 
    		//alignment of the letters in the button
    		setAlignment(Pos.CENTER_LEFT);
    		setRotate(-0.5);
    		getChildren().addAll(bg, text);
    		
    		//fliping  the colors around when your mouse enters the button
    		setOnMouseEntered(event -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.RED);
                text.setFill(Color.BLUE);
            });

    		//the colors going back to normal after your mouse leaves the button
            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLUE);
                text.setFill(Color.RED);
            });
    		
            
        }
    	
    }
    
  //getting the number of pairs to match with the number of rows
  	private  int numPairs = 28;
      private  int numRows = 7;
      
      private Tile picked = null;
      private int counter = 2;

      
      //building the border size
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
          /**
           *
           * how the game responds to the mouse click and to make sure 
           * it has 2 clicks per turn. If the letters are the same then
           * they stay on the screen if not then they disappear.
           * 
           * @param event handles the mouse click
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

    
    
    
    
    

