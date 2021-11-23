package interface2584;

import java.io.Serializable;
import javafx.scene.layout.StackPane;
import jeu2584.Case;

public class Tuile implements Serializable {

    public Case c;
    public StackPane p;
    public int[] xY = new int[2];

    public Tuile(Case c, StackPane p) {
        this.c = c;
        this.p = p;
        this.xY[0] = c.getX();
        this.xY[1] = c.getY();
    }
}
