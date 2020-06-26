package application;

//import javafx scenes
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;










public class startMenu extends Application {
	
	
	  static void main(String[] args) {
	        launch(args);
	    }
	 
	  private GameMenu gameMenu;
	  
	
    @Override
    public void start(Stage window) throws Exception {
    
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
    
    Scene scene = new Scene(root);
    
    //putting my name in the title
    window.setTitle("Aaron Duthcer's Memory Game");
    window.setScene(scene);
    window.show();
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
    		
    		//flipping the colors around when your mouse enters the button
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
}
    
    
    
    
    

