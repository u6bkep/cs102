
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Assignment2UI extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    
	private ObservableList<String> table1data = FXCollections.observableArrayList();
    
    @FXML
    private TableView<String> playerTable;
    @FXML
    private TableColumn<String,String> idColumn;

	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
        
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue());
        }
	
	public String getTableValues()
	{
		return "test";
	}
	

	
	 /**
     * Initializes the root layout.
     */
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
	 
	 /**
	     * Shows the overview inside the root layout.
	     */
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
	    
	    /**
	     * Returns the main stage.
	     * @return
	     */
	    public Stage getPrimaryStage() {
	        return primaryStage;
	    }

	public static void main(String[] args) {
		launch(args);
	}
}
