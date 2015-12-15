package pl.chelm.pwsz.harsh_crystal;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public final class HarshCrystal extends Frame {
	public HarshCrystal(Simulation simulation) {
		setTitle(simulation.getClass().getSimpleName());
		add(new BoardCanvas(simulation.getBoard()));
		addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		        setVisible(false);
		        dispose();
		        System.exit(0);
		      }
		    });
		pack();
	}
	
	public static void main (String arguments[]) throws Exception {
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
			.build()
		;
		
		(new HarshCrystal(simulation)).setVisible(true);
	}
}
