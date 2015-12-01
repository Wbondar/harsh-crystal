package pl.chelm.pwsz.harsh_crystal;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public final class HarshCrystal extends Application {
	public static void main (String arguments[]) throws Exception {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BoardBuilder boardBuilder = new BoardBuilder();
		boardBuilder
			.setWidth(100)
			.setHeight(100)
			.setQuantityOfActors(40)
			.setQuantityOfActorTypes(2)
		;
		SimulationBuilder simulationBuilder = SimulationBuilders.getInstance(SchellingSegregationModel.class, boardBuilder)
				.setProperty("tolerance", 1.0 / 3.0);
		Simulation simulation = simulationBuilder.build();
		
		Executor simulationExecutor = Executors.newSingleThreadExecutor();
		
		primaryStage.setTitle("Harsh Crystal.");
        Button run = new Button();
        run.setText("Run.");
        run.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                simulationExecutor.execute(simulation);
            }
        });
        Button pause = new Button();
        pause.setText("Pause.");
        pause.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                try {
					simulationExecutor.wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(run);
        root.getChildren().add(pause);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
	}
}
