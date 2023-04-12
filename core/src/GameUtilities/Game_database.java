package GameUtilities;

import GameStuff.Missiles;
import GameStuff.Tank;
import Screens.play;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.Vector;

public class Game_database implements Serializable {
    private static final long serialVersionUID = -700663200805791236L;
    public Tank T;
    public int COINS;
    public int GEMS;

    // now variables for each player
    // missiles quantity
    // position
    // health

    private int volume;
    public Vector<Vector2> position_player1;
    public Vector<Vector2> position_player2;
    public Vector<Double> health_player1;
    public Vector<Double> health_player2;
    public Vector<Vector<Integer>> missiles_player1;
    public Vector<Vector<Integer>> missiles_player2;
    public Vector<String> player_tanks;
    transient public Tank tank1;
    transient public Tank tank2;
    transient public Tank tank3;
    transient public Tank tank1_flip;
    transient public Tank tank2_flip;
    transient public Tank tank3_flip;

    transient public Missiles missile1;
    transient public Missiles missile2;
    transient public Missiles missile3;
    transient public Missiles missile4;
    transient public Missiles missile5;
    transient public Missiles special_missile;

    public Vector2 n;


    public Game_database(int coins, int gems){
        this.GEMS = gems;
        this.COINS = coins;
        position_player1 = new Vector<Vector2>();
        position_player2 = new Vector<Vector2>();
        health_player1 = new Vector<Double>();
        health_player2 = new Vector<Double>();
        missiles_player1 = new Vector<Vector<Integer>>();
        missiles_player2 = new Vector<Vector<Integer>>();
        player_tanks = new Vector<String>();
    }

    public void initialize_everything(){
        initialize_missiles();
        add_tank_abrams();
        add_tank_helios();
        add_tank_buratino();
    }

    public Tank getTank() {
        return T;
    }

    public void setTank(Tank t) {
        T = t;
    }

    public int getCOINS() {
        return COINS;
    }

    public void setCOINS(int COINS) {
        this.COINS = COINS;
    }

    public int getGEMS() {
        return GEMS;
    }

    public void setGEMS(int GEMS) {
        this.GEMS = GEMS;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void add_tank_abrams(){
        Vector<Missiles> missiles = new Vector<Missiles>();
        missiles.add(missile1);
        missiles.add(missile2);
        missiles.add(missile3);
        Vector<Integer> quantity = new Vector<Integer>();
        quantity.add(2);
        quantity.add(1);
        quantity.add(3);

        tank1 = new Tank("Abrams", "tanks/Abrams.png", 300, com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/1-tank-stars-animations.gif").read()), 0, missiles, quantity);
        tank1_flip = new Tank("Abrams", "tanks/Abrams-flip.png", 300, com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/1-tank-stars-animations.gif").read()), 0, missiles, quantity);

    }

    public void add_tank_helios(){
        Vector<Missiles> missiles = new Vector<Missiles>();
        missiles.add(missile2);
        missiles.add(missile3);
        missiles.add(missile4);
        Vector<Integer> quantity = new Vector<Integer>();
        quantity.add(2);
        quantity.add(1);
        quantity.add(3);

        tank2 = new Tank("Helios", "tanks/buratino.png", 850, com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/3-tank-stars-animations.gif").read()), 0, missiles, quantity);
        tank2_flip = new Tank("Helios", "tanks/buratino-flip.png", 850, com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/3-tank-stars-animations.gif").read()), 0, missiles, quantity);
    }

    public void add_tank_buratino(){
        Vector<Missiles> missiles = new Vector<Missiles>();
        missiles.add(missile3);
        missiles.add(missile4);
        missiles.add(missile5);
        Vector<Integer> quantity = new Vector<Integer>();
        quantity.add(2);
        quantity.add(1);
        quantity.add(3);
        tank3 = new Tank("Buratino", "tanks/helios.png", 850, com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/6-tank-stars-animations.gif").read()), 0, missiles, quantity);
        tank3_flip = new Tank("Buratino", "tanks/helios-flip.png", 850, com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/6-tank-stars-animations.gif").read()), 0, missiles, quantity);

    }

    public void initialize_missiles(){
        missile1 = new Missiles("Big one", 0, 0, 200, 200, 0, "missiles/bigone.png");
        missile2 = new Missiles("frost bite", 0, 0, 50, 50, 0, "missiles/frostbite.png");
        missile3 = new Missiles("frost blast", 0, 0, 100, 100, 0, "missiles/frostblast.png");
        missile4 = new Missiles("vertical slam", 0, 0, 100, 100, 100, "missiles/vertical.png");
        missile5 = new Missiles("assault drone", 0, 0, 100, 100, 100, "missiles/assault.png");

        special_missile = new Missiles("special_missile", 0, 0, 500, 500, 0, "missiles/GAANDFAAD.png");

    }
}

//we will access from game data class and make changes in this class only so that we have to save only one Game_data to Serizable inteface;
