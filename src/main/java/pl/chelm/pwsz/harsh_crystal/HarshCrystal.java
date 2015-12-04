package pl.chelm.pwsz.harsh_crystal;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public final class HarshCrystal extends Application {
	public static void main (String arguments[]) throws Exception {
		launch(arguments);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setHgap(10.0);
		gridPane.setVgap(10.0);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		primaryStage.setTitle("Harsh Crystal.");
		
		Scene scene = new Scene(gridPane, 1024, 1024);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Board board = new BoardBuilder()
			.setWidth(100)
			.setHeight(100)
			.setQuantityOfActors(40)
			.setQuantityOfActorTypes(2)
			.build()
		;
		final Simulation simulation = (new SchellingSegregationModelBuilder( ))
				.setProperty("tolerance", 1.0 / 3.0)
				.setBoard(board)
				.build();
		
		assert simulation.getBoard().getActorsStream().count() == 40L;
		
		simulation.getBoard()
		.getPositionsStream()
		.forEach(p -> this.display(gridPane, p));
	}

	private final void display(final GridPane gridPane, final Board.Position position) {
		if (position.isOccupied()) {
			gridPane.add(new Label(String.valueOf(position.getOccupant().getType().getId())), position.getX(), position.getY());
		} else {
			gridPane.add(new Label("O"), position.getX(), position.getY());
		}
	}
}
