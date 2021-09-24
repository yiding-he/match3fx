package com.hyd.match3fx.controller;

import com.hyd.match3fx.event.GameStartEvent;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class MainController {

    public static final Color CELL_BORDER_COLOR = Color.web("#000000");
    public static final Color CELL_BORDER_COLOR_SELECTED = Color.web("#DDDDDD");

    private static final Random RANDOM = new SecureRandom();

    public Canvas cvBackground;
    public Group board;
    private final Map<Rectangle, Point2D> positionsMap = new HashMap<>();

    private Rectangle selectA, selectB;

    private final Paint[] cellColors = new Paint[]{
            Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.PURPLE
    };

    @Autowired
    private ApplicationEventPublisher publisher;

    public void initialize() {
        publisher.publishEvent(new GameStartEvent());

        GraphicsContext background = cvBackground.getGraphicsContext2D();
        background.setFill(CELL_BORDER_COLOR);
        background.fillRect(0, 0, 402, 402);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle(i * 50 + 2, j * 50 + 2, 48, 48);
                r.setFill(cellColors[RANDOM.nextInt(cellColors.length)]);
                // r.setFill(Color.WHITE);
                r.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onCellClicked(r));
                board.getChildren().add(r);
                positionsMap.put(r, new Point2D(i, j));
            }
        }

    }

    private void onCellClicked(Rectangle r) {
        if (selectA == null) {
            select(r);
        } else if (r == selectA) {
            unselect(r);
        } else if (isNeighbor(r, selectA)) {
            selectB = r;
            switchCell();
        } else {
            unselect(selectA);
            select(r);
        }
    }

    private void switchCell() {
        Point2D posA = positionsMap.get(selectA);
        Point2D posB = positionsMap.get(selectB);
        System.out.println("Switch " + posA + " with " + posB);
        selectA.setX(posB.getX() * 50 + 2);
        selectA.setY(posB.getY() * 50 + 2);
        selectB.setX(posA.getX() * 50 + 2);
        selectB.setY(posA.getY() * 50 + 2);
        positionsMap.put(selectA, posB);
        positionsMap.put(selectB, posA);
        unselect(selectA);
        unselect(selectB);
        selectA = null;
        selectB = null;
    }

    private boolean isNeighbor(Rectangle r1, Rectangle r2) {
        Point2D pos1 = positionsMap.get(r1);
        Point2D pos2 = positionsMap.get(r2);
        return Math.abs((pos1.getX() + pos1.getY()) - (pos2.getX() + pos2.getY())) == 1;
    }

    private void select(Rectangle r) {
        selectA = r;
        r.toFront();
        r.setStroke(CELL_BORDER_COLOR_SELECTED);
    }

    private void unselect(Rectangle r) {
        selectA = null;
        r.setStroke(null);
    }

    @EventListener
    public void onGameStart(GameStartEvent event) {

    }
}
