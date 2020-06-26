




package application;

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



public class mainMenu extends Application {
	
	
	  static void main(String[] args) {
	        launch(args);
	    }
	 
	  private GameMenu gameMenu;
	  
	
    @Override
    public void start(Stage window) throws Exception {
    
    Pane root = new Pane();
    root.setPrefSize(355, 405);
    
    
    
	InputStream iStream = Files.newInputStream(Paths.get("res/images/Thinking.png"));
    Image pic = new Image(iStream);
    iStream.close();
    
    ImageView IView = new ImageView(pic);
    IView.setFitWidth(355);
    IView.setFitHeight(405);
    
    gameMenu = new GameMenu();
    root.getChildren().addAll(IView, gameMenu);
    
    Scene scene = new Scene(root);
    
    window.setTitle("Aaron Duthcer's Memory Game");
    window.setScene(scene);
    window.show();
    }
    
    
    private class GameMenu extends Parent {
        public GameMenu() {
            VBox menu = new VBox(20);
            
            menu.setTranslateX(100);
            menu.setTranslateY(200);
                    
            
            
            MenuButton start = new MenuButton("START");
            start.setOnMouseClicked(event -> {
               
            });            

            MenuButton exit = new MenuButton("EXIT");
            exit.setOnMouseClicked(event -> {
                System.exit(0);
            });
           
            menu.getChildren().addAll(start, exit);
            
            getChildren().addAll(menu);
            
            };
        
    }
    
    private static class MenuButton extends StackPane {
    	private Text text;
    	
    	public MenuButton(String name) {
    		text = new Text(name);
    		text.getFont();
			text.setFont(Font.font(20));
    		text.setFill(Color.RED);
    		
    		Rectangle bg = new Rectangle(150, 40);
    		bg.setOpacity(0.6);
    		bg.setFill(Color.BLUE);
    		 
    		
    		setAlignment(Pos.CENTER_LEFT);
    		setRotate(-0.5);
    		getChildren().addAll(bg, text);
    		
    		setOnMouseEntered(event -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.RED);
                text.setFill(Color.BLUE);
            });

            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLUE);
                text.setFill(Color.RED);
            });
    		
            
        }   
    } 	
}
    
    
    
    
    