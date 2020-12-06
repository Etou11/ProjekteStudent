package acamo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CompletableFuture;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import acamo.ActiveAircrafts;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jsonstream.PlaneDataServer;
import messer.BasicAircraft;
import messer.*;
import senser.Senser;
import de.saring.leafletmap.*;
import de.saring.leafletmap.events.MapClickEventListener;

public class Acamo extends Application implements Observer
{
	private ActiveAircrafts activeAircrafts;
    private TableView<BasicAircraft> table = new TableView<BasicAircraft>();
    private ObservableList<BasicAircraft> aircraftList = FXCollections.observableArrayList();
    private ArrayList<String> fields;
    
    //Labels for GUI values
    Label selectedAiracftLabelValue = new Label("Aircraft");
    Label selectedIcaoValue = new Label("");
    Label selectedOperatorValue = new Label("");
    Label selectedPosTimeValue = new Label("");
    Label selectedCoordinateValue = new Label("");
    Label selectedSpeedValue = new Label("");
    Label selectedTrakValue = new Label("");
    
    //Text fields for GUI
    TextField latitudeText;
    TextField longitudeText;
    
    //Create hash map for markers
    HashMap<String, Marker> markerHashMap = new HashMap<String, Marker>();
    //Initialise loadState 
    CompletableFuture<Worker.State> loadState;
    //Initialise LeaftlyMap
    LeafletMapView mapView = new LeafletMapView();
    //Initialise map layer config
    List<MapLayer> config = new LinkedList<>();
    //Initialise home Marker
    Marker homeMarker;
    
    //Value for selected row
    int selectedRow = -1;
 
    private int distance = 100;
    private double latitude = 48.7433425;
    private double longitude = 9.3201122;
    private static boolean haveConnection = true;
    
    public static void main(String[] args) {
		launch(args);
    }
    

    @Override
    public void start(Stage stage) 
    {
		String urlString = "https://opensky-network.org/api/states/all";
		PlaneDataServer server;
		
		if(haveConnection)
			server = new PlaneDataServer(urlString, latitude, longitude, distance);
		else
			server = new PlaneDataServer(latitude, longitude, distance);
		
		new Thread(server).start();

		Senser senser = new Senser(server);
		new Thread(senser).start();
		
		Messer messer = new Messer();
		senser.addObserver(messer);
		new Thread(messer).start();
		
		// TODO: create activeAircrafts
		activeAircrafts = new ActiveAircrafts();
		
		// TODO: activeAircrafts and Acamo needs to observe messer 
		messer.addObserver(this);
		messer.addObserver(activeAircrafts);
		
        fields = BasicAircraft.getAttributesNames();
       
        
        // TODO: Fill column header using the attribute names from BasicAircraft
		for(int i = 0;i < fields.size();i++) 
		{
			TableColumn<BasicAircraft, String> columns = new TableColumn<BasicAircraft, String>(fields.get(i));
			columns.setCellValueFactory(new PropertyValueFactory<>(fields.get(i)));
			table.getColumns().add(i, columns);
		}
		
		table.setItems(aircraftList);
		table.setEditable(false);
        table.autosize();
 
        // TODO: Create layout of table and pane for selected aircraft
        Label tableName = new Label("Active Aircrafts");
        tableName.setAlignment(Pos.TOP_CENTER);
        tableName.setMaxSize(100, 5);
        
        VBox selectedAircraftvBox = new VBox(7);
        selectedAircraftvBox.setSpacing(10);
        
        VBox selectedAircraftvBox2 = new VBox(7);
        selectedAircraftvBox2.setSpacing(10);
        
        
        //Labels for set names
        Label selectedAiracftLabel = new Label("Selected ");
        Label selectedIcao = new Label("Icao: ");
        Label selectedOperator = new Label("Operator: ");
        Label selectedPosTime = new Label("Pos Time: ");
        Label selectedCoordinate = new Label("Coordinate: ");
        Label selectedSpeed = new Label("Speed: ");
        Label selectedTrak = new Label("Trak: ");
        
        selectedAircraftvBox.getChildren().add(selectedAiracftLabel);
        selectedAircraftvBox.getChildren().add(selectedIcao);
        selectedAircraftvBox.getChildren().add(selectedOperator);
        selectedAircraftvBox.getChildren().add(selectedPosTime);
        selectedAircraftvBox.getChildren().add(selectedCoordinate);
        selectedAircraftvBox.getChildren().add(selectedSpeed);
        selectedAircraftvBox.getChildren().add(selectedTrak);
        
        selectedAircraftvBox2.getChildren().add(selectedAiracftLabelValue);
        selectedAircraftvBox2.getChildren().add(selectedIcaoValue);
        selectedAircraftvBox2.getChildren().add(selectedOperatorValue);
        selectedAircraftvBox2.getChildren().add(selectedPosTimeValue);
        selectedAircraftvBox2.getChildren().add(selectedCoordinateValue);
        selectedAircraftvBox2.getChildren().add(selectedSpeedValue);
        selectedAircraftvBox2.getChildren().add(selectedTrakValue);
        
        //Map
        mapView.setLayoutX(0);
        mapView.setLayoutY(0);
        mapView.setMaxWidth(640);
        config.add(MapLayer.MAPBOX);
        
        //Text field + button
        latitudeText = new TextField();
        longitudeText = new TextField();
        Label latitudeLabel = new Label("Latitude:");
        Label longitudeLabel = new Label("Longitude:");
        Button submitButton = new Button("Submit");
        VBox customInput = new VBox(5);
        customInput.getChildren().add(0, latitudeLabel);
        customInput.getChildren().add(1, latitudeText);
        customInput.getChildren().add(2, longitudeLabel);
        customInput.getChildren().add(3, longitudeText);
        customInput.getChildren().add(4, submitButton);
        
        //Grid
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        root.add(tableName, 1, 0);
        root.add(mapView, 0, 1);
        root.add(customInput, 0, 2);
        root.add(table, 1, 1);
        root.add(selectedAircraftvBox, 2, 1);
        root.add(selectedAircraftvBox2, 3, 1);

        //Scene
		Scene scene = new Scene(root, 1500, 800);
        stage.setScene(scene);
        stage.setTitle("Acamo");
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
        
        ////Add map display and basic map control
        loadState = mapView.displayMap(
        				new MapConfig(config,
        								new ZoomControlConfig(),
        								new ScaleControlConfig(),
        								new LatLong(latitude, longitude)));
        
        //Add markers to map
        loadState.whenComplete((state, throwable) -> {
        	
        	homeMarker = new Marker(new LatLong(latitude, longitude),
        					"HOME", "HOME", 0);
        	mapView.addCustomMarker("HOME", "icons/basestation.png");
        	mapView.addMarker(homeMarker);
        	latitudeText.setText(String.valueOf(latitude));
        	longitudeText.setText(String.valueOf(longitude));
        	
        	for(int i = 0; i <= 24; i++)
        	{
        		String number = String.format("%02d", i);
        		mapView.addCustomMarker("plane" + number,  "icons/plane" + number + ".png");
        	}
        });
        

        // TODO: Add event handler for selected aircraft
        //Selection event
        table.setOnMouseClicked((MouseEvent event) -> 
        {
        	
            if(event.getButton().equals(MouseButton.PRIMARY)){
                selectedRow = table.getSelectionModel().getSelectedIndex();
            	updateSelectedAircraft(table.getSelectionModel().getSelectedIndex());
            }
        });
        
        
        //Workaround for update, mouse has to be moved over table content to fire event
        table.setOnMouseMoved(new EventHandler<MouseEvent>()
		{
        	public void handle(MouseEvent event)
        	{
        		//System.out.println("Selected row:" + selectedRow);
        		if(selectedRow >= 0)
        		{
        			updateSelectedAircraft(selectedRow);
        		}
        	}
        });
        
       
        //Event clicked on map
        loadState.whenComplete((state, throwable) -> {
        	mapView.onMapClick(new MapClickEventListener() {
        	
				@Override
				public void onMapClick(LatLong latLong)
				{
					resetLocation(latLong.component1(), latLong.component2(), distance, server);
				}
				
			});
        });
        
        //Button event
        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
        	
        	@Override
        	public void handle(ActionEvent event)
        	{
        		resetLocation(Double.valueOf(latitudeText.getText()), Double.valueOf(longitudeText.getText()), distance, server);
        	}
        				});
        
        
    }
    

    private void updateSelectedAircraft(int selectedRowEvent)
    {
    	try
		{
    		BasicAircraft ba = table.getItems().get(selectedRowEvent);
    		
    		selectedIcaoValue.setText(ba.getIcao());
        	selectedOperatorValue.setText(ba.getOperator());
        	selectedPosTimeValue.setText(ba.getPosTime().toString());
        	selectedCoordinateValue.setText(ba.getCoordinate().toString());
        	selectedSpeedValue.setText(ba.getSpeed().toString());
        	selectedTrakValue.setText(ba.getTrak().toString());
		}
		catch (Exception e)
		{
			System.out.println("Error in updateSelectedAircraft: " + e);
			return;
		}
    }
    
    private void resetLocation(double lat, double lng, int distance, PlaneDataServer server)
    {
    	server.resetLocation(lat, lng, distance);
    	
    	for(Map.Entry<String, Marker> ele : markerHashMap.entrySet())
    	{
    		mapView.removeMarker(ele.getValue());
    	}
    	
    	markerHashMap.clear();
    	
    	homeMarker.move(new LatLong(lat, lng));
    	latitudeText.setText(String.valueOf(lat));
    	longitudeText.setText(String.valueOf(lng));
    	mapView.panTo(new LatLong(lat, lng));
    	
    	activeAircrafts.clear();
    	
    }

    
    // TODO: When messer updates Acamo (and activeAircrafts) the aircraftList must be updated as well
    @Override
    public void update(Observable o, Object arg) 
    {	
    	Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
		    	table.getItems().clear();
		    	table.getItems().addAll(aircraftList);
		    	table.setItems(aircraftList);
		    	
		    	try
				{
		    		activeAircrafts.update(o, arg);
		        	aircraftList.removeAll();
		        	aircraftList.addAll(activeAircrafts.values());
				}
				catch (Exception e)
				{
					System.out.println("Error when updating Acamo  e: " + e);
				}
		    	
				
		        //Update plane markers
		        for(int i = 0; i < activeAircrafts.values().size(); i++)
		        {
		        	int heading = activeAircrafts.values().get(i).getTrak().intValue();
		        	LatLong latlong = new LatLong(activeAircrafts.values().get(i).getCoordinate().getLatitude(), activeAircrafts.values().get(i).getCoordinate().getLongitude());
		        	String icao = activeAircrafts.values().get(i).getIcao();
		        	String planeIcon = (heading / 15) > 9 ?  ("plane" + heading / 15) : ("plane0" + heading / 15);
		        	

		        	if(markerHashMap.containsKey(icao))
		        	{
		        		Marker marker = markerHashMap.get(icao);
		        		marker.move(latlong);
		        		marker.changeIcon(planeIcon);
		        	}
		        	else 
		        	{
						loadState.whenComplete((state, throwable) ->
						{
							Marker marker = new Marker(latlong, icao, planeIcon, 0);
							mapView.addMarker(marker);
							markerHashMap.put(icao, marker);
						});
						
					}
		        }
			}
		});
    }
    	
}
