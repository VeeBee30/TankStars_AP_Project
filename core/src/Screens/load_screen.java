package Screens;

//public class load_screen {
//}
//
//


import Screens.Main_menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class load_screen extends ScreenAdapter implements Screen {

    MyGdxGame game;
    OrthographicCamera camera;

    SpriteBatch batch;
    Stage stage;

    private Texture backgroundImage;
    Viewport viewport;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;

    public load_screen(MyGdxGame game) {
        this.game = game;
        batch = game.getBatch();
        backgroundImage = new Texture(Gdx.files.internal("ded00065333621.5af0ea8ca2b29.png"));
        camera = new OrthographicCamera();
        viewport = new FitViewport(1400, 788,camera);
        camera.setToOrtho(false, 1400, 788);
        stage =new Stage(viewport);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
        TextButton t1 = new TextButton("SAVE GAME 1", skin);
        TextButton t2 = new TextButton("SAVE GAME 2", skin);
        TextButton t3 = new TextButton("SAVE GAME 3", skin);
        TextButton t4 = new TextButton("SAVE GAME 4", skin);

        t1.setSize(300, 100);
        t2.setSize(300, 100);
        t3.setSize(300, 100);
        t4.setSize(300, 100);

        t1.setPosition(550, 550);
        t2.setPosition(550, 430);
        t3.setPosition(550, 310);
        t4.setPosition(550, 190);

        t1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.database.health_player1.size() == 1){
                    game.setScreen(new load_play(game, game.database.player_tanks.get(0), game.database.position_player1.get(0), game.database.position_player2.get(0), game.database.health_player1.get(0), game.database.health_player2.get(0), game.database.missiles_player1.get(0), game.database.missiles_player2.get(0)));
                }
//
            }
        });

        t2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.database.health_player1.size() == 2){
                    game.setScreen(new load_play(game, game.database.player_tanks.get(1), game.database.position_player1.get(1), game.database.position_player2.get(1), game.database.health_player1.get(1), game.database.health_player2.get(1), game.database.missiles_player1.get(1), game.database.missiles_player2.get(1)));
                }
            }
        });

        t3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.database.health_player1.size() == 3){
                    game.setScreen(new load_play(game, game.database.player_tanks.get(2), game.database.position_player1.get(2), game.database.position_player2.get(2), game.database.health_player1.get(2), game.database.health_player2.get(2), game.database.missiles_player1.get(2), game.database.missiles_player2.get(2)));
                }
            }
        });

        t4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.database.health_player1.size() == 4){
                    game.setScreen(new load_play(game, game.database.player_tanks.get(3), game.database.position_player1.get(3), game.database.position_player2.get(3), game.database.health_player1.get(3), game.database.health_player2.get(3), game.database.missiles_player1.get(3), game.database.missiles_player2.get(3)));
                }
            }
        });

        Gdx.input.setInputProcessor(stage);

        myTexture = new Texture(Gdx.files.internal("back-button.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        button.setPosition(15,720);
        button.setSize(45, 45);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.main_screen.getInstance());

            }
        });
        stage.addActor(button);
        stage.addActor(t1);
        stage.addActor(t2);
        stage.addActor(t3);
        stage.addActor(t4);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();


        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        batch.draw(backgroundImage, 0, 0, 1400, 788);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}