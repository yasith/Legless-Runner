package legless.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import it.randomtower.engine.ME;
import it.randomtower.engine.entity.Entity;

public class Hero extends Entity {

  private Image image;
  
  private float velocityY = 0;
  private float gravity = -1.0f;
  private float velocityX = 5.0f;
  
  private int direction = 1;
  private int health = 0;
  
  private boolean dead = false;
  private boolean win = false;

  private long bulletTime = 0;
  private long deathTime = 0;

  public Hero(float x, float y) {
    super(x, y);
    try {
      image = new Image("data/hero.png");
    } catch (SlickException e) {
      e.printStackTrace();
    }

    this.setGraphic(image);

    // Controllers
    this.define("RIGHT", Input.KEY_RIGHT);
    this.define("LEFT", Input.KEY_LEFT);
    this.define("UP", Input.KEY_UP);
    this.define("FIRE", Input.KEY_SPACE);

    // Collision stuff
    this.setHitBox(0, 0, image.getWidth(), image.getHeight());
    this.addType("HERO");

  }

  @Override
  public void update(GameContainer container, int delta) throws SlickException {
    super.update(container, delta);

    if (check("UP") && collide("FLOOR", x, y + 10) != null) {
      velocityY = -20;
    }

    velocityY = velocityY - gravity;

    if (collide("FLOOR", x, y + velocityY) == null) {
      y += velocityY;
    } else {
      velocityY = 0;
    }

    if (check("RIGHT") && collide("FLOOR", x + velocityX, y) == null) {
      direction = 1;
      x += velocityX * direction;
    }

    if (check("LEFT") && collide("FLOOR", x - velocityX, y) == null) {
      direction = -1;
      x += velocityX * direction;
    }

    if (direction == 1) {
      this.setGraphic(image.getFlippedCopy(true, false));
    } else {
      this.setGraphic(image);
    }

    if (check("FIRE")) {

      long nowTime = System.currentTimeMillis();

      if (nowTime - bulletTime > 500) {
        bulletTime = nowTime;
        Bullet b = new Bullet(x + 20, y + 50);
        b.setDirection(direction);
        ME.world.add(b);
      }
    }

    if (collide("CREEP", x, y) != null) {
      long nowTime = System.currentTimeMillis();
      if (nowTime - deathTime > 500) {
        health--;
        deathTime = nowTime;
      }
      if (health == -1) {
        this.dead = true;
      }
    }

    if (collide("STAR", x, y) != null) {
      this.win = true;
    }

  }

  public boolean getDead() {
    return this.dead;
  }

  public int getHealth() {
    return this.health;
  }

  public boolean getWin() {
    return this.win;
  }

  public void setWin(boolean win) {
    this.win = win;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setDead(boolean dead) {
    this.dead = dead;
  }

}
