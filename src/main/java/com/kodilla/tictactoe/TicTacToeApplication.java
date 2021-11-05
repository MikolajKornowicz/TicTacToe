package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.BorderWidths.AUTO;


public class TicTacToeApplication extends Application {
    private final Image bgImage = new Image("file:src/main/resources/background.jpg");
    private final Image x = new Image("file:src/main/resources/pngX.png");
    private final Image o = new Image("file:src/main/resources/pngO.png");
    private boolean isX = true;
    private Label status = new Label();
    private List<Button> buttonList = new ArrayList<>();


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


        for(int i = 0; i < 3; i ++){
            for (int j = 0; j <3; j++) {
                Button button = new Button();
                button.setStyle("-fx-background-color: transparent;");
                button.setPrefSize(450,450);
                button.setOnAction(event -> {
                    if(isX){
                        ImageView xView = new ImageView(x);
                        xView.setFitHeight(200);
                        xView.setFitWidth(200);
                        button.setGraphic(xView);
                    } else {
                        ImageView oView = new ImageView(o);
                        oView.setFitHeight(200);
                        oView.setFitWidth(200);
                        button.setGraphic(oView);
                    }
                    isX = !isX;
                    button.setDisable(true);
                    checkWin();
                    checkLoose();


                });
                grid.add(button, i+1, j+1);
                buttonList.add(button);
            }
        }

        grid.setGridLinesVisible(false);

        Scene scene = new Scene(grid, 1600, 900, Color.BLACK);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void cpuMove (){

    }

    private void checkWin (){
        boolean isHorizontalWinner = checkHorizontal();
        boolean isVerticalWinner = checkVertical();
        boolean isDiagonalWinner = chekDiagonal();

        if(isHorizontalWinner || isVerticalWinner || isDiagonalWinner ){
            buttonList.forEach(button -> button.setDisable(true));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game won");
            alert.setHeaderText("Congratulation!");
            alert.setContentText("You won!");
            alert.showAndWait();
        }
    }
private void checkLoose () {
    boolean isHorizontalWinner = checkHorizontal();
    boolean isVerticalWinner = checkVertical();
    boolean isDiagonalWinner = chekDiagonal();
    boolean ifAllMarked = ifChecked();
        if (!isHorizontalWinner && !isVerticalWinner && !isDiagonalWinner && ifAllMarked){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game lost! There are no draws.");
            alert.setHeaderText("Ha Ha Ha");
            alert.setContentText("Loooooosers!");
            alert.showAndWait();
        }
}

    private boolean ifChecked() {
        ImageView b1 =(ImageView) buttonList.get(0).getGraphic();
        ImageView b2 =(ImageView) buttonList.get(1).getGraphic();
        ImageView b3 =(ImageView) buttonList.get(2).getGraphic();
        ImageView b4=(ImageView) buttonList.get(3).getGraphic();
        ImageView b5 =(ImageView) buttonList.get(4).getGraphic();
        ImageView b6 =(ImageView) buttonList.get(5).getGraphic();
        ImageView b7 =(ImageView) buttonList.get(6).getGraphic();
        ImageView b8=(ImageView) buttonList.get(7).getGraphic();
        ImageView b9=(ImageView) buttonList.get(8).getGraphic();

        if(b1 != null && b2 != null && b3 != null && b4 != null && b5 != null && b6 != null && b7 != null && b8 != null && b9 != null){
            return true;
        }
        return false;
    }

    private boolean chekDiagonal() {
        ImageView b1 =(ImageView) buttonList.get(0).getGraphic();
        ImageView b2 =(ImageView) buttonList.get(4).getGraphic();
        ImageView b3 =(ImageView) buttonList.get(8).getGraphic();
        ImageView b4=(ImageView) buttonList.get(6).getGraphic();
        ImageView b5 =(ImageView) buttonList.get(2).getGraphic();
        if(b1 != null && b2 != null && b3 != null || b2 != null && b4 != null && b5 != null){
            if(b1.getImage().equals(b2.getImage())&& b2.getImage().equals(b3.getImage()) ||
                    b4.getImage().equals(b2.getImage()) && b2.getImage().equals(b5.getImage())){
                System.out.println("Diagonal Win");
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for(int i=0; i<3; i++){
            ImageView b1 =(ImageView) buttonList.get(i).getGraphic();
            ImageView b2 =(ImageView) buttonList.get(i+3).getGraphic();
            ImageView b3 =(ImageView) buttonList.get(i+6).getGraphic();
            if(b1 != null && b2 != null && b3 != null){
                if(b1.getImage().equals(b2.getImage())&& b2.getImage().equals(b3.getImage())){
                    System.out.println(" Vertical Winner");
                    return true;
                }
            }
        }
return false;
    }

    private boolean checkHorizontal() {
        for(int i=0; i<9; i = i + 3){
            ImageView b1 =(ImageView) buttonList.get(i).getGraphic();
            ImageView b2 =(ImageView) buttonList.get(i+1).getGraphic();
            ImageView b3 =(ImageView) buttonList.get(i+2).getGraphic();
            if(b1 != null && b2 != null && b3 != null){
                if(b1.getImage().equals(b2.getImage())&& b2.getImage().equals(b3.getImage())){
                    System.out.println("Horizontal Winner");
                    return true;
                }
            }
        }
        return false;
    }
}
