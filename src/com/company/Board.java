package com.company;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Board extends JPanel {
    private static final int row = 3;
    private static final int col = 3;
    //image
    private Image imageX;
    private Image imageO;
    //create two dimensional array 3 - 3
    private Cell matrix [][] = new Cell[row][col];
    //set default for player go first
    private String playing = Cell.EMPTY_VALUE;
    //check result
    public static final int RS_DRAW = 0;
    public static final int RS_WIN = 1;
    public static final int RS_CONT = 2;
    //appear alert
    private ResultListener resultListener;
    //player choice who go first
    public Board(String player){
        //get constructor below
        this();
        this.playing = player;
    }
    //for event
    public Board() {
        this.initMatrix();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();

                //cannot play without clicking start game
                if(playing.equals(Cell.EMPTY_VALUE)){
                    return;
                }

                //has sound when click
                soundClick();

                //calculate X and O follow player
                for (int i = 0; i < row; i++){
                    for(int j = 0; j < col; j++){
                        Cell cell = matrix[i][j];

                        int xStart = cell.getX();
                        int yStart = cell.getY();

                        int xEnd = xStart + cell.getW();
                        int yEnd = yStart + cell.getH();

                        if(x >= xStart && x<= xEnd && y >=yStart && y <=yEnd){
                            if(cell.getValue().equals(Cell.EMPTY_VALUE)) {
                                cell.setValue(playing);
                                repaint();
                                int check = result(playing);
                                if(resultListener!= null){
                                    resultListener.noti(playing, check);
                                }
                                if(check == RS_CONT){
                                    //if player is playing X, next player will be returned O, else return X
                                    playing = playing.equals(Cell.X_VALUE) ? Cell.O_VALUE : Cell.X_VALUE;
                                }
                            }
                        }


                    }
                }

            }
        });

        try {
            imageX = ImageIO.read(getClass().getResource("imageX.png"));
            imageO = ImageIO.read(getClass().getResource("imageO.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //sound click
    private synchronized void soundClick(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Clip clip = AudioSystem.getClip();
                    //input file sound form wav
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("sound.wav"));
                    clip.open(audioInputStream);
                    clip.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //initialize matrix 3x3
    private void initMatrix(){
        for (int i = 0; i<row; i++){
            for (int j = 0; j < col; j++){
                Cell cell = new Cell();
                matrix[i][j] = cell;
            }
        }
    }
    public void resetBoard(){
        this.initMatrix();
        this.setPlaying(Cell.EMPTY_VALUE);
        repaint();
    }


    public void setPlaying(String playing) {
        this.playing = playing;
    }
    /*
    * @effect
    * case0: equal (no win)
    * case1: current player win
    * case2: does not play done
    *
    * */
    public int result(String player){
        //win
        //diagonal line 1
        if(this.matrix[0][0].getValue().equals(player)&&this.matrix[1][1].getValue().equals(player)&&this.matrix[2][2].getValue().equals(player)){
            return RS_WIN;
        }
        //diagonal line 2
        if(this.matrix[0][2].getValue().equals(player)&&this.matrix[1][1].getValue().equals(player)&&this.matrix[2][0].getValue().equals(player)){
            return RS_WIN;
        }
        //row1
        if(this.matrix[0][0].getValue().equals(player)&&this.matrix[0][1].getValue().equals(player)&&this.matrix[0][2].getValue().equals(player)){
            return RS_WIN;
        }
        //row2
        if(this.matrix[1][0].getValue().equals(player)&&this.matrix[1][1].getValue().equals(player)&&this.matrix[1][2].getValue().equals(player)){
            return RS_WIN;
        }
        //row3
        if(this.matrix[2][0].getValue().equals(player)&&this.matrix[2][1].getValue().equals(player)&&this.matrix[2][2].getValue().equals(player)){
            return RS_WIN;
        }
        //column1
        if(this.matrix[0][0].getValue().equals(player)&&this.matrix[1][0].getValue().equals(player)&&this.matrix[2][0].getValue().equals(player)){
            return RS_WIN;
        }
        //column2
        if(this.matrix[0][1].getValue().equals(player)&&this.matrix[1][1].getValue().equals(player)&&this.matrix[2][1].getValue().equals(player)){
            return RS_WIN;
        }
        //column3
        if(this.matrix[0][2].getValue().equals(player)&&this.matrix[1][2].getValue().equals(player)&&this.matrix[2][2].getValue().equals(player)){
            return RS_WIN;
        }
        //case 0
        if(this.isBoardFull()){
            return RS_DRAW;
        }
        //case 2
        return RS_CONT;
    }
    /**
     * if player play a step
     *      count++
     *       if count == number of matrix
     *          return board full
     *        else
     *          not yet
    **/
    private boolean isBoardFull(){
        int num = row*col;

        int count = 0;
        for (int i = 0; i<row; i++){
            for (int j = 0; j < col; j++){
                Cell cell = matrix[i][j];
                if(!cell.getValue().equals(Cell.EMPTY_VALUE)){
                    count++;
                }
            }
        }
        if (count == num){
            return true;
        }else{
            return false;
        }
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D graphics2D = (Graphics2D) g;
        //width and height of one square
        int wSquare = getWidth()/3;
        int hSquare = getHeight()/3;

        int k = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                //set the coordinate system (Oxyz)
                int x = j * wSquare;
                int y = i * hSquare;

                Cell cell = matrix[i][j];
                cell.setX(x);
                cell.setY(y);
                cell.setW(wSquare);
                cell.setH(hSquare);

                //set color
                Color color = k % 2 ==0 ? Color.pink : Color.white;
                graphics2D.setColor(color);
                /* Fill the frame */
                graphics2D.fillRect(x,y, wSquare, hSquare);

                if(cell.getValue().equals(Cell.X_VALUE)){
                    Image img = imageX;
                    graphics2D.drawImage(img, x, y, wSquare, hSquare, this);

                }else if (cell.getValue().equals(Cell.O_VALUE)){
                    Image img = imageO;
                    graphics2D.drawImage(img, x, y, wSquare, hSquare, this);

                }

                k++;
            }
        }

    }

}
