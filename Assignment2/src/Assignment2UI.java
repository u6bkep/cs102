
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisMatch;
import TennisDatabase.TennisPlayer;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
		//create tennis database
		TennisDatabase database = new TennisDatabase();
		//print out software versions
		System.out.println("java.version = " + System.getProperty("java.version"));
		System.out.println("javafx.version  = " + System.getProperty("javafx.version"));
		System.out.println("javafx.runtime.version  = " + System.getProperty("javafx.runtime.version"));
		
		// setup a basic windows
		Scene scene = new Scene( new Group(), Color.ALICEBLUE );
		
		//Setup ui contents
//		Label labelTest = new Label( "TEST" );
//		Button buttonTest = new Button( "TEST" );
//		VBox vboxStage = new VBox();
//		vboxStage.setSpacing(5);
//		vboxStage.setPadding(new Insets( 10, 20, 30, 40 ) ); // top, right, bottom, left
//		vboxStage.getChildren().addAll(labelTest, buttonTest);
		
		//player table columns
		
		TableColumn<TennisPlayer, String> IDColumn = new TableColumn<>("Id");
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
		
		TableColumn<TennisPlayer, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		TableColumn<TennisPlayer, Integer> yobColumn = new TableColumn<>("YOB");
		yobColumn.setCellValueFactory(new PropertyValueFactory<>("BirthYear"));
		
		TableColumn<TennisPlayer, String> countryColumn = new TableColumn<>("Country");
		countryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
		
		TableColumn<TennisPlayer, String> wlColumn = new TableColumn<>("W/L");
		wlColumn.setCellValueFactory(new PropertyValueFactory<>("WinLoss"));
		
		//match table columns
		
		TableColumn<TennisMatch, LocalDate> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
		
		TableColumn<TennisMatch, String> player1Column = new TableColumn<>("Player1");
		player1Column.setCellValueFactory(new PropertyValueFactory<>("IdPlayer1"));
		
		TableColumn<TennisMatch, String> player2Column = new TableColumn<>("Player2");
		player2Column.setCellValueFactory(new PropertyValueFactory<>("IdPlayer2"));

		TableColumn<TennisMatch, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("Tournament"));
		
		TableColumn<TennisMatch, String> scoreColumn = new TableColumn<>("Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("MatchScore"));
		
		//element definitions
		
//		AnchorPane paneRigh = new AnchorPane();
//		AnchorPane paneLeft = new AnchorPane();
		VBox controlsPane = new VBox();
		SplitPane splitPaneStage = new SplitPane();
		SplitPane tableSplit = new SplitPane();
		SplitPane controlsPlit = new SplitPane();
		TableView<TennisPlayer> playerTable = new TableView<TennisPlayer>();
		TableView<TennisMatch> matchTable = new TableView<TennisMatch>();
		HBox addPlayerBox = new HBox();
		HBox addMatchBox = new HBox();
		
		
//		paneRigh.prefHeightProperty().bind(splitPaneStage.heightProperty());
//		paneLeft.prefHeightProperty().bind(splitPaneStage.heightProperty());
		
		//user controls
		
		TextField fileLocation = new TextField("/home/ben/Documents/school/CS102/Assignment1/cs102__assignment_01__example_data_file.txt");
		
		Button showAll = new Button("show all");
		showAll.setOnAction((event) ->
		{
			playerTable.getItems().clear();
			matchTable.getItems().clear();
			
			try
			{
			playerTable.getItems().addAll(FXCollections.observableArrayList(database.getAllPlayers()));
			matchTable.getItems().addAll(FXCollections.observableArrayList(database.getAllMatches()));
			}
			catch(NullPointerException e) {};

			playerTable.refresh();
			matchTable.refresh();
		});
		
		Button loadButton = new Button("load");
		loadButton.setOnAction((event) ->
		{
			try {
				database.loadFromFile(fileLocation.getText());
			} catch (TennisDatabaseRuntimeException e) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
				e.printStackTrace();
				alert.show();
			} catch (TennisDatabaseException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
				e.printStackTrace();
				alert.show();
			}
			
			showAll.fire();
			
//			playerTable.getItems().clear();
//			matchTable.getItems().clear();
//			
//			playerTable.getItems().addAll(FXCollections.observableArrayList(database.getAllPlayers()));
//			matchTable.getItems().addAll(FXCollections.observableArrayList(database.getAllMatches()));
//			
//			playerTable.refresh();
//			matchTable.refresh();
		});
		
		
		
		Button clearButon = new Button("clear");
		clearButon.setOnAction((event) ->
		{
			database.reset();
			playerTable.getItems().clear();
			matchTable.getItems().clear();
		});
		
		Button MatchesOfPlayer = new Button("show matches of player");
		MatchesOfPlayer.setOnAction((event) ->
		{
			TennisMatch[] matches;
			try
			{
				String playerId = playerTable.getSelectionModel().getSelectedItem().getId();
				try {
					matches = database.getMatchesOfPlayer(playerId);
					matchTable.getItems().clear();
					matchTable.getItems().addAll(FXCollections.observableArrayList(matches));
				} catch (TennisDatabaseRuntimeException | TennisDatabaseException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
					e.printStackTrace();
					alert.show();
				}
			}
			catch(NullPointerException e) {};
		});
		
		TextField id = new TextField("id");
		TextField firstName = new TextField("First Name");
		TextField lastName = new TextField("Last Name");
		TextField birthYear = new TextField("Birth Year");
		TextField country = new TextField("Country");
		
		Button addPlayerButton = new Button("add player");
		addPlayerButton.setOnAction((event) ->
		{
			try {
				database.insertPlayer(id.getText(), firstName.getText(), lastName.getText(), Integer.decode(birthYear.getText()), country.getText());
			} catch (NumberFormatException | TennisDatabaseException | TennisDatabaseRuntimeException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
				e.printStackTrace();
				alert.show();
			}
			showAll.fire();
		});
		
		TextField idPlayer1 = new TextField("idPlayer1");
		TextField idPlayer2 = new TextField("idPlayer2");
		TextField date = new TextField("date");
		TextField location = new TextField("location");
		TextField score = new TextField("score");
		
		Button addMatchButton = new Button("add Match");
		addMatchButton.setOnAction((event) ->
		{
			try {
				database.insertMatch(idPlayer1.getText(), idPlayer2.getText(), LocalDate.parse(date.getText(), DateTimeFormatter.ofPattern("yyyyMMdd")), location.getText(), score.getText());
			} catch (TennisDatabaseException | TennisDatabaseRuntimeException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
				e.printStackTrace();
				alert.show();
			}
			catch(DateTimeParseException e)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR, "error parsing date. must be in the form:\nyyyyMMdd", ButtonType.OK);
				e.printStackTrace();
				alert.show();
			}
			showAll.fire();
		});
		
		Button deletePlayer = new Button("delete player");
		deletePlayer.setOnAction((event) ->
		{
			TennisMatch[] matches;
			try
			{
				String playerId = playerTable.getSelectionModel().getSelectedItem().getId();
				try {
					database.deletePlayer(playerId);
					
				} catch (TennisDatabaseRuntimeException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
					e.printStackTrace();
					alert.show();
				}
				showAll.fire();
			}
			catch(NullPointerException e) {};
		});
		
		TextField saveFileLocation = new TextField("save/file/location");
		
		Button saveFileButton = new Button("save to file");
		saveFileButton.setOnAction((event) ->
		{
			try {
				database.saveToFile(saveFileLocation.getText());
			} catch (TennisDatabaseRuntimeException e) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
				e.printStackTrace();
				alert.show();
			} catch (TennisDatabaseException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
				e.printStackTrace();
				alert.show();
			}
			catch(NullPointerException e)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR, "no data to save", ButtonType.OK);
				e.printStackTrace();
				alert.show();
			}
			
			showAll.fire();
		});
		
		// attach columns to tables
		playerTable.getColumns().add(IDColumn);
		playerTable.getColumns().add(nameColumn);
		playerTable.getColumns().add(yobColumn);
		playerTable.getColumns().add(countryColumn);
		playerTable.getColumns().add(wlColumn);
//		
		matchTable.getColumns().add(dateColumn);
		matchTable.getColumns().add(player1Column);
		matchTable.getColumns().add(player2Column);
		matchTable.getColumns().add(locationColumn);
		matchTable.getColumns().add(scoreColumn);
		
		// 
		
		splitPaneStage.getItems().addAll(tableSplit, controlsPlit);
		splitPaneStage.prefWidthProperty().bind(scene.widthProperty());
		splitPaneStage.prefHeightProperty().bind(scene.heightProperty());
		
		tableSplit.getItems().addAll(playerTable, matchTable);
		tableSplit.setOrientation(Orientation.VERTICAL);
		
//		loadButton.setLayoutX(14);
//		loadButton.setLayoutY(14);
		
//		controlsPane.getChildren().add(loadButton);
		controlsPane.getChildren().addAll(fileLocation, loadButton, showAll, MatchesOfPlayer, clearButon, addPlayerBox, addMatchBox, deletePlayer, saveFileLocation, saveFileButton);
		addPlayerBox.getChildren().addAll(id, firstName, lastName, birthYear, country, addPlayerButton);
		addMatchBox.getChildren().addAll(idPlayer1, idPlayer2, date, location, score, addMatchButton);
		controlsPlit.getItems().addAll(controlsPane);

		
		//attach window contents to window
		( (Group) scene.getRoot() ).getChildren().addAll( splitPaneStage );
		
	
		
		
		//show window, returns on window close
        primaryStage.setTitle("My Title");
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(900);
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
