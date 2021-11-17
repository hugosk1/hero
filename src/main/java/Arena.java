import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private Hero hero;
    private int width, height;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;


    public Arena(int width, int height) {
        this.hero = new Hero(10, 10);
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();


    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0,0), new TerminalSize(width, height), ' ');

        for (Wall wall: walls) wall.draw(graphics);

        for (Coin coin: coins) coin.draw(graphics);

        for (Monster monster: monsters) monster.draw(graphics);

        hero.draw(graphics);
        moveMonsters();
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
            for(Wall wall: walls) {
                if(wall.getPosition().equals(position))
                    return false;
            } return true;
        } return false;
    }

    private boolean canMonsterMove(Position position) {
        if (position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height) {
            for (Wall wall : walls) {
                if (wall.getPosition().equals(position))
                    return false;
            }
            for (Monster monster : monsters) {
                if (monster.getPosition().equals(position))
                    return false;
            } return true;
        } return false;
    }

    private void moveHero(Position position) {
        if(canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins(position);
        }
    }

    private void moveMonsters() {
        Position position;
        for(Monster monster: monsters) {
            do{
                position = monster.move();
            } while (!canMonsterMove(position));
            monster.setPosition(position);
        }
    }

    public boolean verifyMonsterCollisions() {
        for(Monster monster: monsters) {
            if(monster.getPosition().equals(hero.getPosition())) {
                return true;
            }
        } return false;
    }

    private void retrieveCoins(Position position) {
        for(int i=0; i<coins.size(); i++) {
            if(coins.get(i).getPosition().equals(hero.getPosition())) {
                coins.remove(i);
                break;
            }
        }
    }
}
