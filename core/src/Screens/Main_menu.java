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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.io.IOException;

public class Main_menu extends ScreenAdapter implements Screen {
    Texture background;
    Viewport viewport;
    MyGdxGame game;
    OrthographicCamera cam;
    SpriteBatch batch;
    Stage stage;

    Label COINS;
    Label Gems;
    float elapsed;

    Main_menu singleton = null;

    public Main_menu(MyGdxGame g){
        this.game = g;
        cam = new OrthographicCamera();
        viewport = new FitViewport(game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT(), cam);
        batch = game.getBatch();
        cam.setToOrtho(false, game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT());
        stage = new Stage(viewport);

    }

    @Override
    public void render(float delta) {
        elapsed += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        batch.draw(background, 0, 0, game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT());
        batch.draw(game.database.tank1.animation.getKeyFrame(elapsed), 10f, 190f, 1000, 318);
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
        System.out.println("main menu is called");
        background = new Texture(Gdx.files.internal("main_menu.jpg"));
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));


        final TextButton NEW_GAME = new TextButton("NEW GAME",skin);
        final TextButton LOAD_GAME = new TextButton("LOAD GAME", skin);
        final TextButton EXIT = new TextButton("EXIT", skin);


        Texture sett = new Texture(Gdx.files.internal("settings.png"));
        TextureRegion sett_r = new TextureRegion(sett);
        TextureRegionDrawable sett_dr = new TextureRegionDrawable(sett_r);

        Texture add_button = new Texture(Gdx.files.internal("add_button.png"));
        TextureRegion add_button_c_r = new TextureRegion(add_button);
        TextureRegionDrawable add_button_c_dr = new TextureRegionDrawable(add_button_c_r);
//        TextureRegion add_button_g_r = new TextureRegion(add_button);
//        TextureRegionDrawable add_button_g_dr = new TextureRegionDrawable(add_button_c_r);

        ImageButton Settings = new ImageButton(sett_dr);
        ImageButton plus_coins = new ImageButton(add_button_c_dr);
        ImageButton plus_gems = new ImageButton(add_button_c_dr);

        Settings.setSize(101, 104);
        Settings.setPosition(50, 930);
        plus_coins.setSize(69, 69);
        plus_coins.setPosition(1530, 980);
        plus_gems.setSize(69, 69);
        plus_gems.setPosition(1800, 980);

        NEW_GAME.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new choose_tank_1(game));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                NEW_GAME.getLabel().setFontScale(2, 2);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                NEW_GAME.getLabel().setFontScale(1,1);
            }
        });

        LOAD_GAME.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new load_screen(game));
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                LOAD_GAME.getLabel().setFontScale(2, 2);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                LOAD_GAME.getLabel().setFontScale(1,1);
            }
        });

        EXIT.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // now we have to same game database
                Input_Output in_out = new Input_Output();
                try {
                    in_out.serialize(game.database);
                } catch (IOException e) {
                    System.out.println("Input output Exception");
                }

                Gdx.app.exit();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                EXIT.getLabel().setFontScale(2, 2);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                EXIT.getLabel().setFontScale(1,1);
            }
        });

        plus_coins.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new conversion(game));
            }
        });

        plus_gems.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new conversion(game));
            }
        });

        Settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("This is clicked");
            }
        });

        NEW_GAME.setSize(400, 140);
        NEW_GAME.setPosition(1300, 600);
        LOAD_GAME.setSize(400, 140);
        LOAD_GAME.setPosition(1300, 400);
        EXIT.setSize(400, 140);
        EXIT.setPosition(1300, 200);

        COINS = new Label(Integer.toString(game.database.COINS) ,skin, "white");
        Gems = new Label(Integer.toString(game.database.GEMS), skin, "white");
        COINS.setScale(2, 2);

        Gems.setPosition(1450, 1000);
        COINS.setPosition(1750, 1000);

        stage.addActor(NEW_GAME);
        stage.addActor(LOAD_GAME);
        stage.addActor(EXIT);
        stage.addActor(Settings);
        stage.addActor(plus_coins);
        stage.addActor(plus_gems);
        stage.addActor(COINS);
        stage.addActor(Gems);

    }


    @Override
    public void dispose() {

    }


    public Main_menu getInstance(){
        if(singleton == null){
            singleton = new Main_menu(game);
            return singleton;
        }
        else{
            // set coins and gems
            return singleton;
        }
    }
}
