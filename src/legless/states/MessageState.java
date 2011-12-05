package legless.states;

import legless.Game;
import it.randomtower.engine.ME;
import it.randomtower.engine.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MessageState extends World implements GameState {

  private String message;

  private boolean newGame;

  public MessageState(int id, GameContainer container, String message) {
    super(id, container);

    this.setWidth(container.getWidth());
    this.setHeight(container.getHeight());
    this.message = message;

    ME.world = this;

  }

  @Override
  public void render(GameContainer container, StateBasedGame game, Graphics g)
      throws SlickException {
    super.render(container, game, g);

    g.drawString(message, container.getWidth() / 2, container.getHeight() / 2);

  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_SPACE) {
      newGame = true;
    }
  }

  @Override
  public void update(GameContainer container, StateBasedGame game, int delta)
      throws SlickException {
    if (newGame) {
      System.out.println("STARTING NEW GAME");
      game.enterState(Game.GAME_STATE, new FadeOutTransition(),
          new FadeInTransition());
    }
  }

}
