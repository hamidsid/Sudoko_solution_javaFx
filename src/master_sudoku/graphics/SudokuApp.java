package master_sudoku.graphics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import master_sudoku.logic.Board;

public class SudokuApp extends Application {
	static Paint value0 = Paint.valueOf("FF9d5c");
	GridPane grid = new GridPane();
	static final int TILESIZE = 40;
	Board board = new Board();
	OneLetterTextField[][] fields;

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) {
		Alert unsolvableAlert = new Alert(AlertType.INFORMATION);
		unsolvableAlert.setTitle("Error");
		unsolvableAlert.setHeaderText("Sukoku is unsolvable");
		unsolvableAlert.setContentText("Please try again with different inputs");

        //Setup all buttons
		Button btnSolve = new Button("Solve");
		Button btnReset = new Button("Reset");
		Button btnExit = new Button("Exit");
		VBox vBox = new VBox();
		HBox hBox = new HBox(20);
		btnSolve.setPrefSize(200, 60);
		btnReset.setPrefSize(200, 60);
		btnExit.setPrefSize(200, 60);
		
		//calls method that creates all the OneLetterTextFields[][] and fixes their alignment
		setupFields();

		//if button "solve" is pressed
		btnSolve.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//sends information about user input to Board class
				for (int i = 0; i < fields.length; i++) {
					for (int j = 0; j < fields[0].length; j++) {
						if (!fields[i][j].getText().trim().isEmpty()) {
							board.put(i, j, fields[i][j].getText());
						}
					}
				}
				//gets information about solution from the Board class to the fields matrix
				if (board.solve()) {
					for (int i = 0; i < fields.length; i++) {
						for (int j = 0; j < fields[0].length; j++) {
							fields[i][j].setText(board.get(i, j));
						}
					}
				} else {
					unsolvableAlert.showAndWait();
					board.reset();
				}
			}
		});
		//if button "reset" is pressed
		btnReset.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clear(fields);
			}
		});
		//if button "exit" is pressed
		btnExit.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});

		hBox.getChildren().addAll(btnSolve, btnReset, btnExit);
		hBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(grid, hBox);
		Scene scene = new Scene(vBox, TILESIZE * 9, TILESIZE * 10);
		stage.setTitle("Sudoku Solver");
		stage.setScene(scene);
		stage.show();
	}
	//creates all the OneLetterTextFields[][] and fixes their alignment
	private void setupFields() {
		fields = new OneLetterTextField[9][9];
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[0].length; j++) {
				fields[i][j] = new OneLetterTextField();
				fields[i][j].setPrefSize(TILESIZE, TILESIZE);
				if (((i / 3 != 1) && (j / 3 != 1)) || (i / 3 == 1 && j / 3 == 1)) {
					fields[i][j].setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
				}
				fields[i][j].setAlignment(Pos.CENTER);
				fields[i][j].setFont(Font.font("Verdana", FontWeight.BOLD, 16));
			}
		}

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				grid.add(fields[i][j], j, i, 1, 1);
			}
		}
	}
	//resets the TextField matrix and the board
	private TextField[][] clear(TextField[][] fields) {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[0].length; j++) {
				fields[i][j].clear();

			}
		}
		board.reset();
		return fields;
	}

}
