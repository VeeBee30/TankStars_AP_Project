package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Loading_screen extends ScreenAdapter implements Screen {
    Texture background;
    Viewport viewport;
    MyGdxGame game;
    OrthographicCamera cam;
    SpriteBatch batch;

    long start_time;
    long elapsed_time;

    Texture loading_image;

    float time = 0;
    public Loading_screen(MyGdxGame g){
        this.game = g;
        cam = new OrthographicCamera();
        viewport = new FitViewport(game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT(), cam);
        batch = game.getBatch();
        cam.setToOrtho(false, game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        batch.draw(background, 0, 0, game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT());
        batch.draw(loading_image,500, 0, 1024, 576);
        batch.end();

        elapsed_time = TimeUtils.timeSinceMillis(start_time);

        if(elapsed_time > 5000){
            game.setScreen(new Main_menu(game));
        }



    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("wp10556268-tank-stars-wallpapers.png"));
        start_time = TimeUtils.millis();

        loading_image = new Texture("loading_image.png");

        // this is to increase efficiency
        game.database.initialize_everything();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
