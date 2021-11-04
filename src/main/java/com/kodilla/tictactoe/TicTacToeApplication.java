package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.scene.layout.BorderWidths.AUTO;


public class TicTacToeApplication extends Application {
    private final Image bgImage = new Image("file:src/main/resources/background.jpg");
    private final Image x = new Image("file:src/main/resources/x2.jpg");
    private final Image o = new Image("file:src/main/resources/o2.jpg");
    private final ImageView xView = new ImageView(x);
    private final ImageView oView = new ImageView(o);
    private boolean isX = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(1600, 900, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setBackground(background);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(480);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(500);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPrefWidth(520);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPrefWidth(520);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPrefWidth(300);
        ColumnConstraints column6 = new ColumnConstraints();
        column6.setPrefWidth(300);
        grid.getColumnConstraints().addAll(column1,column2,column3,column4,column5, column6);
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(200);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(400);
        RowConstraints row3 = new RowConstraints();
        row3.setPrefHeight(400);
        RowConstraints row4 = new RowConstraints();
        row4.setPrefHeight(400);
        RowConstraints row5 = new RowConstraints();
        row5.setPrefHeight(200);
        grid.getRowConstraints().addAll(row1,row2,row3,row4,row5);

        xView.setFitHeight(200);
        xView.setFitWidth(200);
        oView.setFitHeight(200);
        oView.setFitWidth(200);

        for(int i = 0; i < 3; i ++){
            for (int j = 0; j <3; j++) {
                Button button = new Button();
                button.setStyle("-fx-background-color: transparent;");
                button.setPrefSize(450,450);
                button.setOnAction(event -> {
                    if(isX){
                        button.setGraphic(xView);
                    } else {
                        button.setGraphic(oView);
                    }
                    isX = !isX;
                    button.setDisable(true);
                    if(grid.getCellBounds(2,2).equals(xView) && grid.getCellBounds(2,3).equals(xView) && grid.getCellBounds(2,4).equals(xView) ||
                    grid.getCellBounds(3,2).equals(xView) && grid.getCellBounds(3,3).equals(xView) && grid.getCellBounds(3,4).equals(xView) ||
                            grid.getCellBounds(4,2).equals(xView) && grid.getCellBounds(4,3).equals(xView) && grid.getCellBounds(4,4).equals(xView) ||
                            grid.getCellBounds(2,2).equals(oView) && grid.getCellBounds(2,3).equals(oView) && grid.getCellBounds(2,4).equals(oView) ||
                            grid.getCellBounds(3,2).equals(oView) && grid.getCellBounds(3,3).equals(oView) && grid.getCellBounds(3,4).equals(oView) ||
                            grid.getCellBounds(4,2).equals(oView) && grid.getCellBounds(4,3).equals(oView) && grid.getCellBounds(4,4).equals(oView) ||
                            grid.getCellBounds(2,2).equals(oView) && grid.getCellBounds(3,3).equals(oView) && grid.getCellBounds(4,4).equals(oView) ||
                            grid.getCellBounds(2,4).equals(oView) && grid.getCellBounds(3,3).equals(oView) && grid.getCellBounds(4,2).equals(oView) ||
                            grid.getCellBounds(2,2).equals(xView) && grid.getCellBounds(3,3).equals(xView) && grid.getCellBounds(4,4).equals(xView) ||
                            grid.getCellBounds(2,4).equals(xView) && grid.getCellBounds(3,3).equals(xView) && grid.getCellBounds(4,2).equals(xView)) {
                        System.out.println("You won");
                    }

                });
                grid.add(button, i+1, j+1);
            }
        }

        grid.setGridLinesVisible(true);

        Scene scene = new Scene(grid, 1600, 900, Color.BLACK);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
