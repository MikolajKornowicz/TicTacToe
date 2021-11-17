package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;


public class TicTacToeApplication extends Application {

    private final Image bgImage = new Image("file:src/main/resources/background.jpg");
    private final Image x = new Image("file:src/main/resources/pngX.png");
    private final Image o = new Image("file:src/main/resources/pngO.png");
    private boolean isX = true;
    private List<Button> buttonList = new ArrayList<>();
    private final String singleString = "Single Player";
    private final String multiString = "Multi Player";
    private final String easyMode = "Easy Mode";
    private final String hardMode = "Hard Mode";
    private boolean singlePlayer = false;
    private boolean hard = false;
    private final String disabled = "Disabled";
    private List <Button> emptyButton = new LinkedList<>();
    private int move = 0;
    private Set <Button> corner = new HashSet<>();
    private Set <Button> edge = new HashSet<>();


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


        Button newGame = new Button();
        newGame.setStyle("-fx-background-color: white;");
        newGame.setPrefSize(100, 100);
        newGame.setText("New Game");
        grid.add(newGame, 5,0 );
        newGame.setOnAction(event -> {
            primaryStage.close();
            Platform.runLater(() -> {
                try {
                    move = 0;
                    hard = false;
                    isX = true;
                    emptyButton = new LinkedList<>();
                    buttonList = new ArrayList<>();
                    corner = new HashSet<>();
                    edge = new HashSet<>();
                    singlePlayer = false;
                    start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        Button save = new Button();
        save.setStyle("-fx-background-color: white;");
        save.setPrefSize(100, 100);
        save.setText("Save Game");
        grid.add(save, 5,1 );

        Button mode = new Button();
        mode.setStyle("-fx-background-color: white;");
        mode.setPrefSize(100, 100);
        mode.setDisable(true);
        mode.setText(disabled);
        mode.setOnAction(event -> {
            String activeString = mode.getText();
            if (activeString.equals(easyMode)) {
                mode.setText(hardMode);
                hard = true;
            }
            if (activeString.equals(hardMode)) {
                mode.setText(easyMode);
                hard = false;
            }
        });
        grid.add(mode, 5,3 );

        Button type = new Button();
        type.setStyle("-fx-background-color: white;");
        type.setPrefSize(100, 100);
        type.setText(multiString);
        type.setOnAction(event -> {
            String activeString = type.getText();
            if(activeString.equals(multiString)){
                type.setText(singleString);
                mode.setText(easyMode);
                mode.setDisable(false);
                singlePlayer = true;
            }
            if(activeString.equals(singleString)){
                type.setText(multiString);
                mode.setText(disabled);
                mode.setDisable(true);
                singlePlayer = false;
            }
        });
        grid.add(type, 5,2 );



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
                    move ++;
                    isX = !isX;
                    button.setDisable(true);
                    emptyButton.remove(button);
                    checkWin();
                    checkDraw();
                    System.out.println("Move: " + move);
                    System.out.println("Corner List size: " + corner.size());
                    System.out.println("Edge List size: " + edge.size());
                    cpuMove();
                    cpuHard();


                });
                grid.add(button, i+1, j+1);
                buttonList.add(button);
                emptyButton.add(button);

            }
        }

        grid.setGridLinesVisible(false);

        Scene scene = new Scene(grid, 1600, 900, Color.BLACK);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private void checker () {
        ImageView button0 = (ImageView) buttonList.get(0).getGraphic();
        ImageView button1 = (ImageView) buttonList.get(1).getGraphic();
        ImageView button2 = (ImageView) buttonList.get(2).getGraphic();
        ImageView button3 = (ImageView) buttonList.get(3).getGraphic();
        ImageView button5 = (ImageView) buttonList.get(5).getGraphic();
        ImageView button6 = (ImageView) buttonList.get(6).getGraphic();
        ImageView button7 = (ImageView) buttonList.get(7).getGraphic();
        ImageView button8 = (ImageView) buttonList.get(8).getGraphic();

        Button b0 = buttonList.get(0);
        Button b1 = buttonList.get(1);
        Button b2 = buttonList.get(2);
        Button b3 = buttonList.get(3);
        Button b5 = buttonList.get(5);
        Button b6 = buttonList.get(6);
        Button b7 = buttonList.get(7);
        Button b8 = buttonList.get(8);



        if (button0 != null) {
            corner.add(b1);
            System.out.println(" Button 0 used: corner added 1");
        }
        if (button2 != null) {
            corner.add(b2);
            System.out.println("Button 2 used: corner added 2");
        }
        if (button6 != null) {
            corner.add(b6);
            System.out.println("Button 6 used: corner added 3");
        }
        if (button8 != null) {
            corner.add(b8);
            System.out.println("Button 8 used: corner added 4");
        }
        if(button1 != null){
            edge.add(b1);
            System.out.println("edge added 1");
        }
        if(button3 != null){
            edge.add(b3);
            System.out.println("edge added 2");
        }
        if(button5 != null){
            edge.add(b5);
            System.out.println("edge added 3");
        }
        if(button7 != null){
            edge.add(b7);
            System.out.println("edge added 4");
        }
    }
    private void cpuMove () {
        if (!isX && singlePlayer && !hard) {
            Random random = new Random();
            int index = random.nextInt(emptyButton.size());
            Button buttonToFire = emptyButton.get(index);
            buttonToFire.fire();
        }
    }

public void cpuHard () {
    if (singlePlayer) {
        if (!isX) {
            if (hard) {
                checker();

                checkChanceVertically();
                checkChanceDiagonal();
                checkChanceHorizontally();


                ImageView b0 = (ImageView) buttonList.get(0).getGraphic();
                ImageView b1 = (ImageView) buttonList.get(1).getGraphic();
                ImageView b2 = (ImageView) buttonList.get(2).getGraphic();
                ImageView b3 = (ImageView) buttonList.get(3).getGraphic();
                ImageView b4 = (ImageView) buttonList.get(4).getGraphic();
                ImageView b5 = (ImageView) buttonList.get(5).getGraphic();
                ImageView b6 = (ImageView) buttonList.get(6).getGraphic();
                ImageView b7 = (ImageView) buttonList.get(7).getGraphic();
                ImageView b8 = (ImageView) buttonList.get(8).getGraphic();


                if (move == 1 && edge.size() == 1) {
                    buttonList.get(4).fire();
                }
                if (move == 1 && corner.size() <= 4) {
                    buttonList.get(4).fire();
                }
                if (move == 1 && b4 != null) {
                    Random random = new Random();
                    int index = random.nextInt(4);
                    if (index == 0) {
                        buttonList.get(0).fire();
                    }
                    if (index == 1) {
                        buttonList.get(2).fire();
                    }
                    if (index == 2) {
                        buttonList.get(6).fire();
                    }
                    if (index == 3) {
                        buttonList.get(8).fire();
                    }
                }
                if (b4 != null && b4.getImage().equals(o)  && corner.size() == 2) {
                    if (b0 != null && b2 != null) {
                        buttonList.get(1).fire();
                    }
                    if (b0 != null && b6 != null) {
                        buttonList.get(3).fire();
                    }
                    if (b0 != null && b8 != null) {
                        buttonList.get(7).fire();
                    }
                    if (b2 != null && b8 != null) {
                        buttonList.get(5).fire();
                    }
                    if (b2 != null && b6 != null) {
                        buttonList.get(3).fire();
                    }
                    if (b6 != null && b8 != null) {
                        buttonList.get(7).fire();
                    }
                }
                if (b4 != null && b4.getImage().equals(o)  && corner.size() == 1 && edge.size() == 1) {
                    if (b0 != null && b1 != null) {
                        buttonList.get(2).fire();
                    }
                    if (b0 != null && b3 != null) {
                        buttonList.get(6).fire();
                    }
                    if (b0 != null && b7 != null) {
                        buttonList.get(3).fire();
                    }
                    if (b0 != null && b5 != null) {
                        buttonList.get(1).fire();
                    }
                    if (b2 != null && b1 != null) {
                        buttonList.get(0).fire();
                    }
                    if (b2 != null && b5 != null) {
                        buttonList.get(8).fire();
                    }
                    if (b2 != null && b3 != null) {
                        buttonList.get(0).fire();
                    }
                    if (b2 != null && b7 != null) {
                        buttonList.get(8).fire();
                    }
                    if (b6 != null && b3 != null) {
                        buttonList.get(0).fire();
                    }
                    if (b6 != null && b7 != null) {
                        buttonList.get(8).fire();
                    }
                    if (b6 != null && b1 != null) {
                        buttonList.get(0).fire();
                    }
                    if (b6 != null && b5 != null) {
                        buttonList.get(8).fire();
                    }
                    if (b8 != null && b7 != null) {
                        buttonList.get(6).fire();
                    }
                    if (b8 != null && b5 != null) {
                        buttonList.get(2).fire();
                    }
                    if (b8 != null && b1 != null) {
                        buttonList.get(2).fire();
                    }
                    if (b8 != null && b3 != null) {
                        buttonList.get(6).fire();
                    }
                }
                if(edge.size() == 2){
                    buttonList.get(3).fire();
                }

                }
            }
        }
    }


    private void checkChanceDiagonal() {

        ImageView b0 = (ImageView) buttonList.get(0).getGraphic();
        ImageView b2 = (ImageView) buttonList.get(2).getGraphic();
        ImageView b4 = (ImageView) buttonList.get(4).getGraphic();
        ImageView b6 = (ImageView) buttonList.get(6).getGraphic();
        ImageView b8 = (ImageView) buttonList.get(8).getGraphic();

        if (b0 != null && b4 != null && b8 == null) {
            if (b0.getImage().equals(b4.getImage())) {
                buttonList.get(8).fire();
            }
        }
        if (b4 != null && b8 != null && b0 == null) {
            if (b4.getImage().equals(b8.getImage())) {
                buttonList.get(0).fire();
            }
        }
        if (b6 != null && b4 != null && b2 == null) {
            if (b6.getImage().equals(b4.getImage())) {
                buttonList.get(2).fire();
            }
        }
        if(b2 != null && b4 != null & b6 == null){
            buttonList.get(6).fire();
        }
    }

    private void checkChanceHorizontally (){

        ImageView b0 = (ImageView) buttonList.get(0).getGraphic();
        ImageView b1 = (ImageView) buttonList.get(1).getGraphic();
        ImageView b2 = (ImageView) buttonList.get(2).getGraphic();
        ImageView b3 = (ImageView) buttonList.get(3).getGraphic();
        ImageView b4 = (ImageView) buttonList.get(4).getGraphic();
        ImageView b5 = (ImageView) buttonList.get(5).getGraphic();
        ImageView b6 = (ImageView) buttonList.get(6).getGraphic();
        ImageView b7 = (ImageView) buttonList.get(7).getGraphic();
        ImageView b8 = (ImageView) buttonList.get(8).getGraphic();

        if (b0 != null && b1 != null && b2 == null) {
            if (b0.getImage().equals(b1.getImage())) {
                buttonList.get(2).fire();
            }
        }
        if (b1 != null && b2 != null && b0 == null) {
            if (b1.getImage().equals(b2.getImage())) {
                buttonList.get(0).fire();
            }
        }
        if (b0 != null && b2 != null && b1 == null) {
            if (b0.getImage().equals(b2.getImage())) {
                buttonList.get(1).fire();
            }
        }
        if (b3 != null && b4 != null && b5 == null) {
            if (b3.getImage().equals(b4.getImage())) {
                buttonList.get(5).fire();
            }
        }
        if (b4 != null && b5 != null && b3 == null) {
            if (b4.getImage().equals(b5.getImage())) {
                buttonList.get(3).fire();
            }
        }
        if (b3 != null && b5 != null && b4 == null) {
            if (b3.getImage().equals(b5.getImage())) {
                buttonList.get(3).fire();
            }
        }
        if (b6 != null && b7 != null && b8 == null) {
            if (b6.getImage().equals(b7.getImage())) {
                buttonList.get(8).fire();
            }
        }
        if (b7 != null && b8 != null && b6 == null) {
            if (b7.getImage().equals(b8.getImage())) {
                buttonList.get(8).fire();
            }
        }
        if (b6 != null && b8 != null && b7 == null) {
            if (b6.getImage().equals(b8.getImage())) {
                buttonList.get(8).fire();
            }
        }
    }

    private void checkChanceVertically () {

        ImageView b0 = (ImageView) buttonList.get(0).getGraphic();
        ImageView b1 = (ImageView) buttonList.get(1).getGraphic();
        ImageView b2 = (ImageView) buttonList.get(2).getGraphic();
        ImageView b3 = (ImageView) buttonList.get(3).getGraphic();
        ImageView b4 = (ImageView) buttonList.get(4).getGraphic();
        ImageView b5 = (ImageView) buttonList.get(5).getGraphic();
        ImageView b6 = (ImageView) buttonList.get(6).getGraphic();
        ImageView b7 = (ImageView) buttonList.get(7).getGraphic();
        ImageView b8 = (ImageView) buttonList.get(8).getGraphic();

            if (b0 != null && b3 != null && b6 == null) {
                if (b0.getImage().equals(b3.getImage())) {
                    buttonList.get(6).fire();
                }
            }
            if (b3 != null && b6 != null && b0 == null) {
                if (b3.getImage().equals(b6.getImage())) {
                    buttonList.get(0).fire();
                }
            }
            if (b0 != null && b6 != null && b3 == null) {
                if (b0.getImage().equals(b6.getImage())) {
                    buttonList.get(3).fire();
                }
            }
            if (b1 != null && b4 != null && b7 == null) {
                if (b1.getImage().equals(b4.getImage())) {
                    buttonList.get(7).fire();
                }
            }
            if (b4 != null && b7 != null && b1 == null) {
                if (b4.getImage().equals(b7.getImage())) {
                    buttonList.get(1).fire();
                }
            }
            if (b1 != null && b7 != null && b4 == null) {
                if (b1.getImage().equals(b7.getImage())) {
                    buttonList.get(4).fire();
                }
            }
            if (b2 != null && b5 != null && b8 == null) {
                if (b2.getImage().equals(b5.getImage())) {
                    buttonList.get(8).fire();
                }
            }
            if (b5 != null && b8 != null && b2 == null) {
                if (b5.getImage().equals(b8.getImage())) {
                    buttonList.get(2).fire();
                }
            }
            if (b8 != null && b2 != null && b5 == null) {
                if (b8.getImage().equals(b2.getImage())) {
                    buttonList.get(5).fire();
                }
            }
        }

    private void checkWin (){
        boolean isHorizontalWinner = checkHorizontal();
        boolean isVerticalWinner = checkVertical();
        boolean isDiagonalWinner = chekDiagonal();

        if(isHorizontalWinner || isVerticalWinner || isDiagonalWinner ){
            buttonList.forEach(button -> button.setDisable(true));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(!isX){
            alert.setTitle("X won");
            } else {
                alert.setTitle("O won");
            }
            alert.setHeaderText("Congratulation!");
            alert.setContentText("You won!");
            alert.showAndWait();
        }
    }
private void checkDraw() {
    boolean isHorizontalWinner = checkHorizontal();
    boolean isVerticalWinner = checkVertical();
    boolean isDiagonalWinner = chekDiagonal();
    boolean ifAllMarked = ifChecked();
        if (!isHorizontalWinner && !isVerticalWinner && !isDiagonalWinner && ifAllMarked){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Draw");
            alert.setHeaderText("Better luck next time!");
            alert.setContentText("Try again!");
            alert.showAndWait();
        }
}

    private boolean ifChecked() {
     int full = emptyButton.size();

        if(full == 0){
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
        if(b1 != null && b2 != null && b3 != null){
            if(b1.getImage().equals(b2.getImage())&& b2.getImage().equals(b3.getImage())){
                System.out.println("Diagonal Win");
                return true;
            }
        }       if(b2 != null && b4 != null && b5 != null){
            if(b4.getImage().equals(b2.getImage()) && b2.getImage().equals(b5.getImage())){
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
