
import java.io.IOException;

import TennisDatabase.TennisMatch;
import TennisDatabase.TennisPlayer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Assignment2UI extends Application {
	
	//private Stage primaryStage;
    //private BorderPane rootLayout;
    
	//private ObservableList<String> table1data = FXCollections.observableArrayList();
    
    //@FXML
    //private TableView<String> playerTable;
    //@FXML
    //private TableColumn<String,String> idColumn;

	@Override
	public void start(Stage primaryStage) 
	{
		//print out software versions
		System.out.println("java.version = " + System.getProperty("java.version"));
		System.out.println("javafx.version  = " + System.getProperty("javafx.version"));
		System.out.println("javafx.runtime.version  = " + System.getProperty("javafx.runtime.version"));
		
		// setup a basic windows
		Scene scene = new Scene( new Group(), Color.ALICEBLUE );
		
		//Setup ui contents
		Label labelTest = new Label( "TEST" );
		Button buttonTest = new Button( "TEST" );
		VBox vboxStage = new VBox();
		vboxStage.setSpacing(5);
		vboxStage.setPadding(new Insets( 10, 20, 30, 40 ) ); // top, right, bottom, left
		vboxStage.getChildren().addAll(labelTest, buttonTest);
		
		//table columns
		
		TableColumn<TennisPlayer, String> IDColumn = new TableColumn<>("ID");
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
		
		AnchorPane paneRigh = new AnchorPane();
		AnchorPane paneLeft = new AnchorPane();
		SplitPane splitPaneStage = new SplitPane();
		SplitPane tableSplit = new SplitPane();
		TableView<TennisPlayer> playerTable = new TableView();
		TableView<TennisMatch> matchTable = new TableView();
		
		paneRigh.prefHeightProperty().bind(splitPaneStage.heightProperty());
		paneLeft.prefHeightProperty().bind(splitPaneStage.heightProperty());
		
		playerTable.getColumns().add(IDColumn);
		
		splitPaneStage.getItems().addAll(tableSplit, paneRigh);
		splitPaneStage.prefWidthProperty().bind(scene.widthProperty());
		splitPaneStage.prefHeightProperty().bind(scene.heightProperty());
		
		

		
		//attach window contents to window
		( (Group) scene.getRoot() ).getChildren().addAll( splitPaneStage );
		
		
		
		//show window, returns on window close
        primaryStage.setTitle("My Title");
        primaryStage.setScene(scene);
        primaryStage.show();
/*
        initRootLayout();
        showPersonOverview();
        */
       // this.idColumn.setCellValueFactory(cellData -> cellData.getValue());
        }
	/*
	public String getTableValues()
	{
		return "test";
	}
	

	
	 
     //Initializes the root layout.
     
	 public void initRootLayout() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Assignment2UI.class.getResource("RootLayout.fxml"));
	            this.rootLayout = (BorderPane) loader.load();
	            
	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 
	     // Shows the overview inside the root layout.
	     
	    public void showPersonOverview() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Assignment2UI.class.getResource("Assignment2UI.fxml"));
	            AnchorPane Assignment2UI = (AnchorPane) loader.load();
	            
	            // Set person overview into the center of root layout.
	            rootLayout.setCenter(Assignment2UI);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    // Returns the main stage.
	     // @return
	     
	    public Stage getPrimaryStage() {
	        return primaryStage;
	    }
*/
	public static void main(String[] args) {
		launch(args);
	}
}
