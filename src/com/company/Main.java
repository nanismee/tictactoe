package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static Board board;
    private static JButton btnStart;
    public static void main(String[] args) {
         board = new Board();
         board.setResultListener(new ResultListener() {
             @Override
             public void noti(String player, int status) {
                 if (status == Board.RS_WIN){
                     JOptionPane.showMessageDialog(null,  "Yay " + player + " win!!");
                     resetGame();
                 }else if (status == Board.RS_DRAW){
                     JOptionPane.showMessageDialog(null, "Oh, draw! Let's play again!");
                     resetGame();
                 }
             }

         });
        //Panel
        JPanel jPanel = new JPanel();
        //create a box layout which flow follows to Y axis
        BoxLayout boxLayout = new BoxLayout(jPanel,BoxLayout.Y_AXIS);
        //set layout for box layout
        jPanel.setLayout(boxLayout);

        //Board
        board.setPreferredSize(new Dimension(300, 300));
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);

        //bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(300, 40));
        bottomPanel.setBackground(Color.pink);

        //inside bottom panel
        btnStart = new JButton("Start");
            //add event for button start
        btnStart.addActionListener(new ActionListener() {
            /*when click button start, enforce action
             *   if game open
             *       player can play game
             *   else if game is being played
             *       reset game
             * */
            @Override
            public void actionPerformed(ActionEvent e){
                if(btnStart.getText().equals("Start")){
                    startGame();
                }else{
                    resetGame();
                }

            }
        });

        //add
        jPanel.add(board);
        jPanel.add(bottomPanel);
        bottomPanel.add(btnStart);

        //get size of the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        //create a new frame with title is Tic Tac Toe
        JFrame jFrame = new JFrame("Tic Tac Toe");

        //show the frame
        jFrame.setVisible(true);
        //the frame can be resized
        jFrame.setResizable(true);
        //add board
        jFrame.add(jPanel);

        //set the frame to center of screen
        int x = (int) dimension.getWidth()/2 - (jFrame.getWidth()/2);
        int y = (int) dimension.getHeight()/2 - (jFrame.getHeight()/2);

        jFrame.setLocation(x,y);

        //set frame can resize
        jFrame.pack();

        //close the frame to exit the program
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     private static void startGame(){
         //set player plays first
         int option = JOptionPane.showConfirmDialog(null, "X plays first","Who wants to play first?",JOptionPane.YES_NO_OPTION);
         String playing = (option == 1) ? Cell.O_VALUE : Cell.X_VALUE;
         //option default is X plays first. If option = 1 mean NO, O will plays first.
         //board = (option == 1) ? new Board(Cell.O_VALUE) : new Board(Cell.X_VALUE);
         /**
          * Other way
          * Board board = new Board();
          * if(option == 1){
          *      board = new B  oard(Cell.O_VALUE);
          * }else{
          *      board = new Board(Cell.X_VALUE);
          * }
          */
         board.resetBoard();
         board.setPlaying(playing);
         //when game is playing, text "Start" -> "Reset"
         btnStart.setText("Reset");

     }
     private static void resetGame(){
        btnStart.setText("Start");
        board.resetBoard();
     }
}
