package Factory_design;

import com.mygdx.game.MyGdxGame;

public abstract class factory {
    MyGdxGame g;
    public factory(MyGdxGame game){
        g = game;
    }
    public abstract Object screen(String need, MyGdxGame game);
}
