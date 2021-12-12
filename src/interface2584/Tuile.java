package interface2584;

import java.io.Serializable;
import javafx.scene.layout.StackPane;
import jeu2584.Case;

public class Tuile implements Serializable {

    private Case c;
    private StackPane p;
    private int[] xY = new int[2];

    public Tuile(Case c, StackPane p) {
        this.c = c;
        this.p = p;
        this.xY[0] = c.getX();
        this.xY[1] = c.getY();
    }
    
    public int getCaseX() {
        return this.c.getX();
    }
    
    public int getCaseY() {
        return this.c.getY();
    }
    
    public StackPane getStackPane() {
        return this.p;
    }
    
    public int getX() {
        return this.xY[0];
    }
    
    public int getY() {
        return this.xY[1];
    }
}
