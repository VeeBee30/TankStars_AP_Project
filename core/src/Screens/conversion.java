package Screens;

import GameUtilities.Input_Output;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.io.IOException;

public class conversion  extends ScreenAdapter implements Screen {

    Texture background;
    Viewport viewport;
    MyGdxGame game;
    OrthographicCamera cam;
    SpriteBatch batch;
    Stage stage;

    Label COINS;
    Label Gems;

    Texture tank;
    Skin skin;

    public conversion(MyGdxGame g) {
        this.game = g;
        cam = new OrthographicCamera();
        viewport = new FitViewport(717, 322, cam);
        batch = game.getBatch();
        cam.setToOrtho(false, 717, 322);
        stage = new Stage(viewport);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        batch.draw(background, 0, 0, 717, 322);
//        batch.draw(tank, 125, 30, 800, 800);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("conversionscreen.jpeg"));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));

        Texture sett = new Texture(Gdx.files.internal("conversionscreen.jpeg"));
        final TextureRegion sett_region = new TextureRegion(sett, 717, 322);
        final Image sett_image = new Image(sett_region);
        sett_image.setPosition(490, 400);

        Texture sett1 = new Texture(Gdx.files.internal("cross.png"));
        TextureRegion sett_r = new TextureRegion(sett1);
        TextureRegionDrawable sett_dr = new TextureRegionDrawable(sett_r);

        ImageButton Settings = new ImageButton(sett_dr);
        Settings.setSize(20, 20);
        Settings.setPosition(690, 270);

//        BUTTONS
        final TextButton FIVE_HUNDRED = new TextButton("500", skin);
        final TextButton FIFTEEN_HUNDRED = new TextButton("1500", skin);
        final TextButton TWENTYFIVE_HUNDRED = new TextButton("2500", skin);
        final TextButton FIVE_THOUSAND = new TextButton("5000", skin);
        final TextButton TEN_THOUSAND = new TextButton("10000", skin);


//        BUTTONS SET KRNE KE LIYE
        FIVE_HUNDRED.setSize(130, 28);
        FIVE_HUNDRED.setPosition(15, 65);
        FIFTEEN_HUNDRED.setSize(130, 28);
        FIFTEEN_HUNDRED.setPosition(155, 65);
        TWENTYFIVE_HUNDRED.setSize(130, 28);
        TWENTYFIVE_HUNDRED.setPosition(300, 65);
        FIVE_THOUSAND.setSize(130, 28);
        FIVE_THOUSAND.setPosition(442, 65);
        TEN_THOUSAND.setSize(130, 28);
        TEN_THOUSAND.setPosition(585, 65);


        FIVE_HUNDRED.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(500 <= game.database.COINS){
                    game.database.COINS -= 500;
                    game.database.GEMS += 80;
                    game.setScreen(new conversion(game));
                }
            }
        }));


        FIFTEEN_HUNDRED.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(1500 <= game.database.COINS){
                    game.database.COINS -= 1500;
                    game.database.GEMS += 500;
                    game.setScreen(new conversion(game));
                }
            }
        });

        TWENTYFIVE_HUNDRED.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
              if(2500 <= game.database.COINS){
                    game.database.COINS -= 2500;
                    game.database.GEMS += 1200;
                    game.setScreen(new conversion(game));
                }
            }
        });

        FIVE_THOUSAND.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(5000 <= game.database.COINS){
                    game.database.COINS -= 5000;
                    game.database.GEMS += 2500;
                    game.setScreen(new conversion(game));
                }
            }
        });

        TEN_THOUSAND.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(1000 <= game.database.COINS){
                    game.database.COINS -= 1000;
                    game.database.GEMS += 6500;
                    game.setScreen(new conversion(game));
                }
            }
        });

        Settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.main_screen.getInstance());
            }
        });


        COINS = new Label(Integer.toString(game.database.COINS) ,skin, "white");
        Gems = new Label(Integer.toString(game.database.GEMS), skin, "white");
        COINS.setScale(3, 3);

        COINS.setPosition(200, 15);
        Gems.setPosition(490, 15);

        stage.addActor(sett_image);
        stage.addActor(Settings);
        stage.addActor(FIVE_HUNDRED);
        stage.addActor(FIFTEEN_HUNDRED);
        stage.addActor(TWENTYFIVE_HUNDRED);
        stage.addActor(FIVE_THOUSAND);
        stage.addActor(TEN_THOUSAND);
        stage.addActor(COINS);
        stage.addActor(Gems);

    }


    @Override
    public void dispose() {

    }
}