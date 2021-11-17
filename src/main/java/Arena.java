import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.List;

public class Arena {
    private Hero hero;
    private int width, height;


    public Arena(int width, int height) {
        this.hero = new Hero(10, 10);
        this.width = width;
        this.height = height;
    }

    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            default:
                break;
        }
    }
    private boolean canHeroMove(Position position) {
        if(position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height) {
        } return false;
    }

    private void moveHero(Position position) {
        if(canHeroMove(position)) {
            hero.setPosition(position);
        }
    }
}
