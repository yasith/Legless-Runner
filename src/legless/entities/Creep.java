package legless.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import it.randomtower.engine.ME;
import it.randomtower.engine.entity.Entity;

public class Creep extends Entity {

  private int dir = 1;
  private float speed = 3.0f;

  public Creep(float x, float y, Image image) {
    super(x, y, image);

    // Collision stuff
    this.setHitBox(0, 0, image.getWidth(), image.getHeight());
    this.collidable = true;
    this.addType("CREEP");
  }

  @Override
  public void update(GameContainer container, int delta) throws SlickException {
    super.update(container, delta);

    float new_x = x + speed * dir;

    if (collide("CREEP_BLOCK", new_x, y) == null) {
      x = new_x;
    } else {
      dir *= -1;
    }
  }

  @Override
  public void collisionResponse(Entity other) {
    if (other.getClass().toString().equalsIgnoreCase(Bullet.class.toString())) {
      System.out.println("Collided with Creep");
      ME.remove(this);
    }
  }

}
