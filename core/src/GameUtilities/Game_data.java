package GameUtilities;

import GameStuff.Game_Map;
import GameStuff.Tank;
import Screens.play;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Game_data implements Serializable {
    play play_screen;
    LocalDateTime time;
    String ID;
    Tank tank;

    public Game_data(Tank T, LocalDateTime t, play screen){
        this.tank = T;
        this.time = t;
        this.play_screen = screen;
    }

    public static void serialize() throws IOException{

    }

    public static void deserialize() throws IOException, ClassNotFoundException{

    }
}
