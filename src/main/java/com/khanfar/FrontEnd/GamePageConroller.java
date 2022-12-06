package com.khanfar.FrontEnd;

import javafx.animation.ScaleTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

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
    private Label title;
    @FXML
    private HBox main;

    @FXML
    private VBox player2;

    @FXML
    private VBox playerOne;
    @FXML
    private Button RunManual;

    Coins tableC[][];



    @FXML
    void backOnAction(ActionEvent event) {

    }
    @FXML
    void manualOnAction(ActionEvent event) {

       // circleArray = new StackPane[ab.length];

        Thread start = new Thread(

                new Runnable() {

                    @Override
                    public void run() {


                        try {

                            SimulationManual( ab , tableC);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        ) ;

        //main = new HBox();


        start.start();


    }

    int ab[] = { 1,3,3,15,1,1} ;

    @FXML
    void startOnAction(ActionEvent event) {

        Thread start = new Thread(

                new Runnable() {

                    @Override
                    public void run() {


                        try {

                            SimaulationAuto(tableC , ab);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        ) ;

        //main = new HBox();


        start.start();




    }

    @FXML
    void tableOnAction(ActionEvent event) {

    }
    StackPane circleArray[] ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.seconds(1));
        scaleTransition.setNode(title);
        scaleTransition.setByY(.1);
        scaleTransition.setByX(.1);
        scaleTransition.setCycleCount(-1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(30);
        circleArray = new StackPane[ab.length];
        try {
            tableC = Algorithim(ab);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0 ; i < ab.length ; i++) {
            Circle circle= new Circle();
            Text text = new Text(ab[i]+"");
            text.setFont(new Font(15));
            text.setBoundsType(TextBoundsType.VISUAL);
            circle.setRadius(45);
            circle.setFill(Color.SALMON);
            circle.setStroke(Color.BLACK);
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle, text);
            circleArray[i] = stack ;
            main.getChildren().addAll(stack);
        }
        Label playerOneLabel = new Label("Player One") , playerTwoLabel = new Label("Player Two") ;
        playerOneLabel.setId("P1");
        playerTwoLabel.setId("P2");
        playerOne.getChildren().addAll(playerOneLabel);
        player2.getChildren().addAll(playerTwoLabel);

    }

    private Coins[][] Algorithim(int coins[]) throws InterruptedException {
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


return table ;
    }


    private boolean checkIfFirstOrLast(int currentCoin, int first, int last) {
       if (currentCoin == first) {
           return true ;
       }
       return  false ;

    }

    public  void createCircleForPlayerOne(int coin , int index) {
       // Circle circle = new Circle();

       StackPane stackPane =  circleArray[index] ;

       /* Text text = new Text(coin+"");
        text.setBoundsType(TextBoundsType.VISUAL);
        circle.setRadius(45);
        circle.setFill(Color.CADETBLUE);
        circle.setStroke(Color.BLACK);

        */
        StrokeTransition strokeTransition = new StrokeTransition();
        strokeTransition.setDuration(Duration.seconds(0.5));
        ((Circle)stackPane.getChildren().get(0)).setFill(Color.PINK);
        strokeTransition.setShape((Circle)stackPane.getChildren().get(0));
        strokeTransition.setFromValue(Color.RED);
        strokeTransition.setToValue(Color.TRANSPARENT);
        strokeTransition.setCycleCount(-1);
        strokeTransition.setAutoReverse(true);
        strokeTransition.play();
        TranslateTransition translateTransition = new TranslateTransition() ;
        translateTransition.setDuration(Duration.millis(1500));
        translateTransition.setNode(stackPane);
        translateTransition.setToY(-150);
        translateTransition.play();

        /*
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
       // playerOne.getChildren().addAll(stack);

         */
    }
    public  void createCircleForPlayerTwo(int coin , int index) {
       // Circle circle = new Circle();
        StackPane stackPane =  circleArray[index] ;
/*
        Text text = new Text(coin+"");
        text.setBoundsType(TextBoundsType.VISUAL);
        circle.setRadius(45);
        circle.setFill(Color.CADETBLUE);
        circle.setStroke(Color.BLACK);

 */
         //StackPane stack = new StackPane();

        StrokeTransition strokeTransition = new StrokeTransition();
        strokeTransition.setDuration(Duration.seconds(0.5));
        ((Circle)stackPane.getChildren().get(0)).setFill(Color.LIGHTSKYBLUE);

        strokeTransition.setShape((Circle)stackPane.getChildren().get(0));
        strokeTransition.setFromValue(Color.RED);
        strokeTransition.setToValue(Color.TRANSPARENT);
        strokeTransition.setCycleCount(-1);
        strokeTransition.setAutoReverse(true);
        strokeTransition.play();
        TranslateTransition translateTransition = new TranslateTransition() ;
        translateTransition.setDuration(Duration.millis(1500));
        translateTransition.setNode(stackPane);
        translateTransition.setToY(150);
        translateTransition.play();
      // StackPane stack = new StackPane();

        //stack.getChildren().addAll(circle, text);
        //player2.getChildren().addAll(stack);

    }
    public  void FinalAnswerForPlayerOne(int coin) {
        Rectangle rectangle = new Rectangle(100 ,100);
        Text text = new Text("Result : "+coin);
        text.setBoundsType(TextBoundsType.VISUAL);
        //circle.setRadius(45);
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.BLACK);
        StrokeTransition strokeTransition = new StrokeTransition();
        strokeTransition.setDuration(Duration.seconds(0.5));
        strokeTransition.setShape(rectangle);
        strokeTransition.setFromValue(Color.BLACK);
        strokeTransition.setToValue(Color.TRANSPARENT);
        strokeTransition.setCycleCount(-1);
        strokeTransition.setAutoReverse(true);
        strokeTransition.play();
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
        StrokeTransition strokeTransition = new StrokeTransition();
        strokeTransition.setDuration(Duration.seconds(0.5));
        strokeTransition.setShape(rectangle);
        strokeTransition.setFromValue(Color.BLACK);
        strokeTransition.setToValue(Color.TRANSPARENT);
        strokeTransition.setCycleCount(-1);
        strokeTransition.setAutoReverse(true);
        strokeTransition.play();
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        player2.getChildren().addAll(stack);
    }
    int c = 0 , k = 0 , m = ab.length -1;
    int i = 0 , j= ab.length - 1;

    public void SimulationManual (int []coins , Coins table[][]) throws InterruptedException {


        if (c != coins.length ) {
            //Thread.sleep(2000);

            if (c % 2 == 0) {
                //playerOne.add(table[i][j].currentCoin) ;
                int finalI = i;
                int finalJ = j;
                if (checkIfFirstOrLast(table[i][j].currentCoin , coins[k] , coins[m]) ){


                    int finalK = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerOne(table[finalI][finalJ].currentCoin , finalK);
                        }
                    });
                    k++ ;


                }
                else {
                    int finalM = m;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerOne(table[finalI][finalJ].currentCoin , finalM);
                        }
                    });
                    m--;

                }


                System.out.println("I : "+i+" j : "+j);
            }
            else {
                int finalI = i;
                int finalJ = j;
                if (checkIfFirstOrLast(table[i][j].currentCoin , coins[k] , coins[m]) ) {
                    int finalK1 = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerTwo(table[finalI][finalJ].currentCoin, finalK1);
                        }

                    });
                    k++;

                }
                else {
                    int finalM1 = m;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerTwo(table[finalI][finalJ].currentCoin, finalM1);
                        }

                    });
                    m-- ;

                }

               // playerTwo.add(table[i][j].currentCoin) ;
            }
            int tempI = table[i][j].currentI ;
            int tempJ = table[i][j].currentJ ;
            i = tempI ;
            j = tempJ ;
            c++ ;
        }else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    FinalAnswerForPlayerOne(table[0][coins.length - 1 ] .max);
                    FinalAnswerForPlayerTwo(table[0][coins.length - 1 ] .min);

                }
            });
        }


      //  System.out.println("Coins for Player One " + playerOne.toString() );
       // System.out.println("Coins for Player Two " + playerTwo.toString() );


    }
    public void SimaulationAuto(Coins table[][] , int coins[]) throws InterruptedException {
        int c = 0 , k = 0 , m = coins.length - 1;
      int  i = 0 , j= coins.length - 1;

        while (c != coins.length ) {
            Thread.sleep(2000);

            if (c % 2 == 0) {
               // playerOne.add(table[i][j].currentCoin) ;
                int finalI = i;
                int finalJ = j;
                if (checkIfFirstOrLast(table[i][j].currentCoin , coins[k] , coins[m]) ){


                    int finalK = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerOne(table[finalI][finalJ].currentCoin , finalK);
                        }
                    });
                    k++ ;


                }
                else {
                    int finalM = m;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerOne(table[finalI][finalJ].currentCoin , finalM);
                        }
                    });
                    m--;

                }


                System.out.println("I : "+i+" j : "+j);
            }
            else {
                int finalI = i;
                int finalJ = j;
                if (checkIfFirstOrLast(table[i][j].currentCoin , coins[k] , coins[m]) ) {
                    int finalK1 = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerTwo(table[finalI][finalJ].currentCoin, finalK1);
                        }

                    });
                    k++;

                }
                else {
                    int finalM1 = m;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createCircleForPlayerTwo(table[finalI][finalJ].currentCoin, finalM1);
                        }

                    });
                    m-- ;

                }

              //  playerTwo.add(table[i][j].currentCoin) ;
            }
            int tempI = table[i][j].currentI ;
            int tempJ = table[i][j].currentJ ;
            i = tempI ;
            j = tempJ ;
            c++ ;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FinalAnswerForPlayerOne(table[0][coins.length - 1 ] .max);
                FinalAnswerForPlayerTwo(table[0][coins.length - 1 ] .min);

            }
        });

       // System.out.println("Coins for Player One " + playerOne.toString() );
      // System.out.println("Coins for Player Two " + playerTwo.toString() );
    }
}
