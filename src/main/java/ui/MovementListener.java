package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MovementListener implements KeyListener {

    private WindowFrame window;
    private boolean running = false;
    private int movementStepWaitTime = 50;
    private int movementStepDistance = 10;
    private int movementStepDistanceDiagonal = Math.toIntExact(Math.round(movementStepDistance / Math.sqrt(2)));
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    public MovementListener(WindowFrame window){
        this.window = window;

        /*
         Start another thread to do the work of figuring out the movement direction and then call the move methods.
         */
        running = true;
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable movementListenerThread = () -> {
            processMovement();
            if (!keepGoing()) {
                service.shutdown();
            }
        };
        service.scheduleAtFixedRate(movementListenerThread, 0, movementStepWaitTime, TimeUnit.MILLISECONDS);
    }

    private void processMovement() {
        boolean isMovingLeft = moveLeft && !moveRight;
        boolean isMovingRight= moveRight && !moveLeft;
        boolean isMovingUp = moveUp && !moveDown;
        boolean isMovingDown= moveDown && !moveUp;

        boolean isDiagonal = (isMovingLeft || isMovingRight) && (isMovingUp || isMovingDown);

        int distance = movementStepDistance;
        if (isDiagonal) {
            distance = movementStepDistanceDiagonal;
        }

        if (isMovingLeft) {
            window.moveLeft(distance);
        }
        if (isMovingRight) {
            window.moveRight(distance);
        }
        if (isMovingUp ){
            window.moveUp(distance);
        }
        if (isMovingDown) {
            window.moveDown(distance);
        }
    }

    private boolean keepGoing() {
        return running;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 65, 37 -> moveLeft = true;
            case 68, 39 -> moveRight = true;
            case 87, 38 -> moveUp = true;
            case 83, 40 -> moveDown = true;
            default -> System.out.println("keyPressed: " + e.getKeyChar() + " - " + e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 65, 37 -> moveLeft = false;
            case 68, 39 -> moveRight = false;
            case 87, 38 -> moveUp = false;
            case 83, 40 -> moveDown = false;
            default -> System.out.println("keyReleased: " + e.getKeyChar() + " - " + e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
