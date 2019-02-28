package no.games.samples;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ASimpleWindow extends Application{


	

	    Button button;

	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        primaryStage.setTitle("Title of the Window");
	        final SwingNode swingNode = new SwingNode();
//	        createSwingContent(swingNode); 
	        button = new Button();
	        button.setText("Don't Click me");
	        StackPane layout = new StackPane();
	        layout.getChildren().add(button);
	        layout.getChildren().add(swingNode);
	        Scene scene = new Scene(layout, 300, 250);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
	    private void createSwingContent(final SwingNode swingNode) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                swingNode.setContent(new JButton("Click me!"));
	               
	            }
	        });
	    }

	}

