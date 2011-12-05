package legless.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import it.randomtower.engine.ME;
import it.randomtower.engine.entity.Entity;

public class Bullet extends Entity {

  private Image image;
  private float speed = 5.0f;
  private int direction;

  public Bullet(float x, float y) {
    super(x, y);

    try {
      image = new Image("data/bullet.png");
    } catch (SlickException e) {
      e.printStackTrace();
    }

    this.setGraphic(image);

    this.setHitBox(0, 0, image.getWidth(), image.getHeight());
    this.addType("BULLET");
  }

  @Override
  public void update(GameContainer container, int delta) throws SlickException {
    super.update(container, delta);

    x += speed * direction;
    if (collide("FLOOR", x, y) != null) {
      System.out.println("Collided");
      ME.remove(this);
    }

    if (collide("CREEP", x, y) != null) {
      System.out.println("Collided With Creep");
      ME.remove(this);
    }

  }

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
    if (direction == -1) {
      Image fi = image.getFlippedCopy(true, false);
      this.setGraphic(fi);
    }
  }
}
