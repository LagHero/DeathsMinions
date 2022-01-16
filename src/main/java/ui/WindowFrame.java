package ui;

import javax.swing.*;

public class WindowFrame {

    private DrawRect rect = new DrawRect();

    public WindowFrame() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.addKeyListener(new MovementListener(this));

        frame.getContentPane().add(rect);

        //Display the window.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void moveLeft(int distance){
        rect.setLocation(rect.getX()-distance, rect.getY());
    }

    public void moveRight(int distance){
        rect.setLocation(rect.getX()+distance, rect.getY());
    }

    public void moveUp(int distance){
        rect.setLocation(rect.getX(), rect.getY()-distance);
    }

    public void moveDown(int distance){
        rect.setLocation(rect.getX(), rect.getY()+distance);
    }
}
