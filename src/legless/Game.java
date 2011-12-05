package legless;

import legless.states.GameState;
import legless.states.MessageState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame {

  public static final int GAME_STATE = 1;
  public static final int LOSE_STATE = 2;
  public static final int WIN_STATE = 3;

  public Game(String name) {
    super(name);
  }

  @Override
  public void initStatesList(GameContainer container) throws SlickException {
    addState(new GameState(GAME_STATE, container));
    addState(new MessageState(LOSE_STATE, container, "You Lose!"));
    addState(new MessageState(WIN_STATE, container, "You Win!"));
  }

  public static void main(String args[]) {
    try {
      AppGameContainer container = new AppGameContainer(new Game(
          "Legless Runner"));
      container.setDisplayMode(800, 600, false);
      container.setTargetFrameRate(60);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }

}
