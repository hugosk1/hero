import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {

    public Monster(int x, int y) {
        super(x, y);
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#8b0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "M");
    }

    public Position move() {
        Random random = new Random();
        int move = random.nextInt(4);
        Position position = super.getPosition();

        switch(move) {
            case 0:
                position = new Position(getPosition().getX(), getPosition().getY() - 1);
                break;
            case 1:
                position = new Position(getPosition().getX() + 1, getPosition().getY());
                break;
            case 2:
                position = new Position(getPosition().getX(), getPosition().getY() + 1);
                break;
            case 3:
                position = new Position(getPosition().getX() - 1, getPosition().getY());
                break;
        } return position;
    }
}
