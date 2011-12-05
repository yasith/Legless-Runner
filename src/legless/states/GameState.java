package legless.states;

import java.util.Arrays;
import java.util.List;

import legless.Game;
import legless.entities.Creep;
import legless.entities.Hero;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;
import it.randomtower.engine.*;
import it.randomtower.engine.actors.StaticActor;

public class GameState extends World {

  private TiledMap map;
  private Hero hero;

  public GameState(int id, GameContainer container) {
    super(id, container);
  }

  @Override
  public void init(GameContainer container, StateBasedGame game)
      throws SlickException {
    // TODO Auto-generated method stub
    super.init(container, game);

    this.setWidth(1000);
    this.setHeight(100);

    map = new TiledMap("data/map.tmx");
    loadEntityFromMap(map, Arrays.asList("Floor"));

    hero = new Hero(0, 0);
    this.add(hero);
    Camera cam = new Camera(hero, container.getWidth(), container.getHeight());
    this.setCamera(cam);
    ME.world = this;
  }

  private void loadEntityFromMap(TiledMap tmap, List<String> asList)
      throws SlickException {
    // int layerIndex = -1;
    System.out.println("Number of Layers in the map: " + map.getLayerCount());
    for (int i = 0; i < tmap.getWidth(); ++i) {
      for (int j = 0; j < tmap.getHeight(); ++j) {
        
        System.out.println(i + " " + j);

        // Block Layer
        int tid = tmap.getTileId(i, j, 0);
        if (tid > 0) {
          Image img = tmap.getTileImage(i, j, 0);
          StaticActor te = new StaticActor(i * img.getWidth(), j
              * img.getHeight(), img.getWidth(), img.getHeight(), img);
          te.collidable = true;
          te.depth = 1;
          te.setAlpha(1.0f);
          te.addType("FLOOR");
          this.add(te);
        }

        System.out.println("DONE");

        // Creeps Layer
        tid = tmap.getTileId(i, j, 1);
        if (tid > 0) {
          String tType = tmap.getTileProperty(tid, "type", null);
          System.out.println(tType);
          if (tType.equalsIgnoreCase("creep_block")) {
            System.out.println(tid);
            System.out.println(tType);
            Image img = tmap.getTileImage(i, j, 1);
            StaticActor te = new StaticActor(i * img.getWidth(), j
                * img.getHeight(), img.getWidth(), img.getHeight(), img);
            te.collidable = true;
            te.depth = 1;
            te.setAlpha(1.0f);
            te.addType("CREEP_BLOCK");
            this.add(te);
          }

          if (tType.equalsIgnoreCase("creep")) {
            System.out.println("DONE");
            Image img = tmap.getTileImage(i, j, 1);
            Creep creep = new Creep(i * img.getWidth(), j * img.getHeight(),
                img);
            this.add(creep);
          }

          if (tType.equalsIgnoreCase("star")) {
            System.out.println("DOasdfasdNE");
            Image img = new Image("data/star.png");
            StaticActor star = new StaticActor(i * 32, j * 32, img.getWidth(),
                img.getHeight(), img);

            star.collidable = true;
            star.setAlpha(1.0f);
            star.addType("STAR");

            this.add(star);
          }
        }

      }
    }
  }

  @Override
  public void render(GameContainer container, StateBasedGame stateBasedGame,
      Graphics g) throws SlickException {

    super.render(container, stateBasedGame, g);

    for (int i = 0; i < hero.getHealth(); i++) {
      Image heart = null;
      try {
        heart = new Image("data/heart.png");
      } catch (SlickException e) {
        e.printStackTrace();
      }
      g.drawImage(heart, 32 * i, 10);
    }

    // g.drawString("HelloWorld", 300, 200);

  }

  @Override
  public void enter(GameContainer container, StateBasedGame game)
      throws SlickException {
    super.enter(container, game);

    System.out.println("Entered New Game");

    hero.x = 0;
    hero.y = 0;

    hero.setHealth(3);
    hero.setWin(false);
    hero.setDead(false);
  }

  @Override
  public void update(GameContainer container, StateBasedGame game, int delta)
      throws SlickException {
    super.update(container, game, delta);

    if (hero.getDead()) {
      game.enterState(Game.LOSE_STATE, new FadeOutTransition(),
          new FadeInTransition());
    }

    if (hero.getWin()) {
      game.enterState(Game.WIN_STATE, new FadeOutTransition(),
          new FadeInTransition());
    }
  }

}
