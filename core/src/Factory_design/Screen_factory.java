package Factory_design;

import Screens.*;
import com.mygdx.game.MyGdxGame;

public class Screen_factory extends factory{
    public Screen_factory(MyGdxGame game) {
        super(game);
    }

    @Override
    public Object screen(String need, MyGdxGame game) {
        if(need.equals("Upgrade_screen")){
            return new Upgrade_screen(game);
        }
        else if(need.equals("Load_screen")){
            return new load_screen(game);
        }
        else if(need.equals("choose_tank_1")){
            return new choose_tank_1(game);
        }
        else if(need.equals("choose_tank_2")){
            return new choose_tank_2(game);
        }
        else if(need.equals("choose_tank_3")){
            return new choose_tank_3(game);
        }
        else if(need.equals("main_menu")){
            return new Main_menu(game);
        }

        return null;
    }
}
