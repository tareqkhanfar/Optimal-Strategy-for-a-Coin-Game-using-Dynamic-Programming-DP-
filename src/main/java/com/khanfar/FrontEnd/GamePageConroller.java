package com.khanfar.FrontEnd;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class GamePageConroller implements Initializable {
    @FXML
    private Button back;
    @FXML
    private Button showTable;

    @FXML
    private Button start;
    @FXML
    private HBox main;

    @FXML
    private VBox player2;

    @FXML
    private VBox playerOne;

    @FXML
    void backOnAction(ActionEvent event) {

    }

    @FXML
    void startOnAction(ActionEvent event) {

    }

    @FXML
    void tableOnAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main.setAlignment(Pos.CENTER);
        main.setSpacing(30);
        int a[] = {20, 1,30,2,5,3 ,4 ,23, 23, 5,35, 235, 235,53} ;

        Thread start = new Thread(

                    new Runnable() {

                        @Override
                        public void run() {
                            int a[] = {20, 1,30,2,5,3 ,4 ,23, 23, 5,35, 235, 235,53} ;

                            try {
                                Algorithim(a) ;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
            ) ;
        start.start();
        for (int i = 0 ; i < a.length ; i++) {
            Circle circle = new Circle();
            Text text = new Text(a[i]+"");
            text.setBoundsType(TextBoundsType.VISUAL);
            circle.setRadius(45);
            circle.setFill(Color.SALMON);
            circle.setStroke(Color.BLACK);
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle, text);
            main.getChildren().addAll(stack);
        }




    }

    private void Algorithim(int coins[]) throws InterruptedException {
        Coins table[][] = new Coins[coins.length][coins.length] ;
        for (int i = 0 ; i < coins.length ; i++) {

            for (int j = 0 ; j < coins.length ; j++) {
                table[i][j] = new Coins() ;
                if (i==j) {
                    table[i][j] = new Coins(coins[i] , 0, coins[i] ,0,0);
                }
            }
        }

        int i = 0 , j = 1 ,counter =1;
        LinkedList<Integer> playerOne = new LinkedList<Integer>() , playerTwo = new LinkedList<Integer>() ;
        while (counter != coins.length-1){

            if (j==coins.length) {
                i=0;
                counter++ ;
                j =counter ;
                // System.out.println();
            }
            // System.out.print(" ("+i +","+j+") ");

            int firstMin = table[i + 1][j].min;
            int secondMin = table[i][j - 1].min;
            int MaxValue = coins[i] + firstMin;
            int MaxValue2 = coins[j] + secondMin;
            int result = Math.max(MaxValue, MaxValue2);
            int resultMin = 0 , resultMin2 = 0;
            if (MaxValue == MaxValue2) {
                resultMin = table[i + 1][j].max;
                resultMin2 = table[i][j-1].max ;
                if (resultMin == resultMin2) {
                    int CurrentMax = Math.max(coins[i] , coins[j]) ;
                    int currentI = -1 , currentJ = -1 ;
                    if (CurrentMax == coins[i]) {
                        currentI = i+1;
                        currentJ = j;
                    }
                    else if (CurrentMax == coins[j]) {
                        currentI = i ;
                        currentJ = j-1 ;
                    }
                    table[i][j] = new Coins(result, resultMin , CurrentMax , currentI,currentJ);

                }
                // System.out.println("I : " + i + "J : " +j);
                // table[i][j] = new Coins(result, resultMin , Math.max(coins[i] , coins[j]) , 0,0);
            }

            else if (result == MaxValue) {
                resultMin = table[i + 1][j].max;
                table[i][j] = new Coins(result, resultMin , coins[i] , i+1 , j );
            } else if (result == MaxValue2) {
                resultMin = table[i][j - 1].max;
                table[i][j] = new Coins(result, resultMin , coins[j] , i , j-1);

            }

            //table[i][j] = new Coins(result, resultMin);
            j++ ;
            i++;
        }
        for ( i = 0 ; i < coins.length ; i++) {
            for ( j = 0 ; j < coins.length ; j++) {
                System.out.print(table[i][j].toString()+"  ");
            }
            System.out.println();
        }

        i = 0 ; j= coins.length - 1;

        int c = 0 ;
        while (c != coins.length ) {
            if (c % 2 == 0) {
                playerOne.add(table[i][j].currentCoin) ;
                int finalI = i;
                int finalJ = j;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        createCircleForPlayerOne(table[finalI][finalJ].currentCoin);
                    }
                });
                System.out.println("I : "+i+" j : "+j);
            }
            else {
                int finalI = i;
                int finalJ = j;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        createCircleForPlayerTwo(table[finalI][finalJ].currentCoin);
                    }
                });
                playerTwo.add(table[i][j].currentCoin) ;
            }
            int tempI = table[i][j].currentI ;
            int tempJ = table[i][j].currentJ ;
            i = tempI ;
            j = tempJ ;
            c++ ;
            Thread.sleep(800);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FinalAnswerForPlayerOne(table[0][coins.length - 1 ] .max);
                FinalAnswerForPlayerTwo(table[0][coins.length - 1 ] .min);

            }
        });

        System.out.println("Coins for Player One " + playerOne.toString() );
        System.out.println("Coins for Player Two " + playerTwo.toString() );


    }

    public  void createCircleForPlayerOne(int coin) {
        Circle circle = new Circle();
        Text text = new Text(coin+"");
        text.setBoundsType(TextBoundsType.VISUAL);
        circle.setRadius(45);
        circle.setFill(Color.CADETBLUE);
        circle.setStroke(Color.BLACK);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        playerOne.getChildren().addAll(stack);
    }
    public  void createCircleForPlayerTwo(int coin) {
        Circle circle = new Circle();
        Text text = new Text(coin+"");
        text.setBoundsType(TextBoundsType.VISUAL);
        circle.setRadius(45);
        circle.setFill(Color.CADETBLUE);
        circle.setStroke(Color.BLACK);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        player2.getChildren().addAll(stack);
    }
    public  void FinalAnswerForPlayerOne(int coin) {
        Rectangle rectangle = new Rectangle(100 ,100);
        Text text = new Text("Result : "+coin);
        text.setBoundsType(TextBoundsType.VISUAL);
        //circle.setRadius(45);
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.BLACK);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        playerOne.getChildren().addAll(stack);
    }
    public  void FinalAnswerForPlayerTwo(int coin) {
        Rectangle rectangle = new Rectangle(100 ,100);
        Text text = new Text("Result : "+coin);
        text.setBoundsType(TextBoundsType.VISUAL);
        //circle.setRadius(45);
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.BLACK);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        player2.getChildren().addAll(stack);
    }
}
