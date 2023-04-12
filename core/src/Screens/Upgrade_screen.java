package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Upgrade_screen extends ScreenAdapter implements Screen {
    Texture background;
    Viewport viewport;
    MyGdxGame game;
    OrthographicCamera cam;
    SpriteBatch batch;
    Stage stage;
    Skin skin;

    Label COINS;
    Label Gems;

    Texture tank;
    float elapsed;
    public Upgrade_screen(MyGdxGame g){
        this.batch = g.getBatch();
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

    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("main_menu.jpg"));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));

        Texture back_button = new Texture(Gdx.files.internal("back-button.png"));
        TextureRegion back_button_r = new TextureRegion(back_button);
        TextureRegionDrawable back_button_r_dr = new TextureRegionDrawable(back_button_r);

        Texture add_button = new Texture(Gdx.files.internal("add_button.png"));
        TextureRegion add_button_c_r = new TextureRegion(add_button);
        TextureRegionDrawable add_button_c_dr = new TextureRegionDrawable(add_button_c_r);

        Texture upgrade_button = new Texture(Gdx.files.internal("upgrade-button.jpg"));
        TextureRegion upgrade_button_c_r = new TextureRegion(upgrade_button);
        TextureRegionDrawable upgrade_button_c_dr = new TextureRegionDrawable(upgrade_button_c_r);

        //        Missiles pic buttons
        Texture bigone = new Texture(Gdx.files.internal("bigone.png"));
        TextureRegion bigone1 = new TextureRegion(bigone);
        TextureRegionDrawable bigone2 = new TextureRegionDrawable(bigone1);

        Texture frostbite = new Texture(Gdx.files.internal("frostbite.png"));
        TextureRegion frostbite1 = new TextureRegion(frostbite);
        TextureRegionDrawable frostbite2 = new TextureRegionDrawable(frostbite1);

        Texture frostblast = new Texture(Gdx.files.internal("frostblast.png"));
        TextureRegion frostblast1 = new TextureRegion(frostblast);
        TextureRegionDrawable frostblast2 = new TextureRegionDrawable(frostblast1);


        Texture verticalslam = new Texture(Gdx.files.internal("vertical slam.png"));
        TextureRegion verticalslam1 = new TextureRegion(verticalslam);
        TextureRegionDrawable verticalslam2 = new TextureRegionDrawable(verticalslam1);

        Texture assaultdrone = new Texture(Gdx.files.internal("assault drone.png"));
        TextureRegion assaultdrone1 = new TextureRegion(assaultdrone);
        TextureRegionDrawable assaultdrone2 = new TextureRegionDrawable(assaultdrone1);


        ImageButton b_button = new ImageButton(back_button_r_dr);
        ImageButton plus_coins = new ImageButton(add_button_c_dr);
        ImageButton plus_gems = new ImageButton(add_button_c_dr);
        ImageButton upgrade_tank = new ImageButton(upgrade_button_c_dr);

//        missiles
        ImageButton bigone_missile = new ImageButton(bigone2);
        ImageButton frostbite_missile= new ImageButton(frostbite2);
        ImageButton frostblast_missile= new ImageButton(frostblast2);
        ImageButton verticalslam_missile= new ImageButton(verticalslam2);
        ImageButton assaultdrone_missile= new ImageButton(assaultdrone2);

        final TextButton upgrade_bigone= new TextButton("UPGRADE", skin);
        final TextButton upgrade_frostbite= new TextButton("UPGRADE", skin);
        final TextButton upgrade_frostblast= new TextButton("UPGRADE", skin);
        final TextButton upgrade_verticalslam = new TextButton("UPGRADE", skin);
        final TextButton upgrade_assaultdrone = new TextButton("UPGRADE", skin);


        b_button.setSize(91, 91);
        b_button.setPosition(71, 942);
        plus_coins.setSize(69, 69);
        plus_coins.setPosition(1530, 980);
        plus_gems.setSize(69, 69);
        plus_gems.setPosition(1800, 980);
        upgrade_tank.setSize(400, 141);
        upgrade_tank.setPosition(250, 50);

        bigone_missile.setSize(700,500);
        bigone_missile.setPosition(900,500);
        frostbite_missile.setSize(700, 500);
        frostbite_missile.setPosition(1150,500);
        frostblast_missile.setSize(700,500);
        frostblast_missile.setPosition(1400,500);
        verticalslam_missile.setSize(700,500);
        verticalslam_missile.setPosition(1000,100);
        assaultdrone_missile.setSize(700,500);
        assaultdrone_missile.setPosition(1300,100);

        upgrade_bigone.setSize(150, 50);
        upgrade_bigone.setPosition(1175, 630);
        upgrade_frostbite.setSize(150,50);
        upgrade_frostbite.setPosition(1427,630);
        upgrade_frostblast.setSize(150,50);
        upgrade_frostblast.setPosition(1675,630);
        upgrade_verticalslam.setSize(150,50);
        upgrade_verticalslam.setPosition(1275,223);
        upgrade_assaultdrone.setSize(150,50);
        upgrade_assaultdrone.setPosition(1575,223);

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

        b_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.main_screen.getInstance());
            }
        });
        upgrade_tank.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirm_payment(500);
            }
        });

        COINS = new Label(Integer.toString(game.database.COINS) ,skin, "white");
        Gems = new Label(Integer.toString(game.database.GEMS), skin, "white");
        COINS.setScale(2, 2);

        Gems.setPosition(1450, 1000);
        COINS.setPosition(1750, 1000);

        stage.addActor(b_button);
        stage.addActor(plus_coins);
        stage.addActor(plus_gems);
        stage.addActor(upgrade_tank);
        stage.addActor(COINS);
        stage.addActor(Gems);
        stage.addActor(bigone_missile);
        stage.addActor(frostbite_missile);
        stage.addActor(frostblast_missile);
        stage.addActor(verticalslam_missile);
        stage.addActor(assaultdrone_missile);
        stage.addActor(upgrade_bigone);
        stage.addActor(upgrade_frostbite);
        stage.addActor(upgrade_frostblast);
        stage.addActor(upgrade_verticalslam);
        stage.addActor(upgrade_assaultdrone);

        tank = new Texture("tanks/Abrams.png");
    }

    @Override
    public void dispose() {

    }

    public void confirm_payment(int value){
        Texture sett = new Texture(Gdx.files.internal("confirm_payment_upgrade.png"));
        final TextureRegion sett_region = new TextureRegion(sett, 600, 360);
        final Image sett_image = new Image(sett_region);
        sett_image.setPosition(650, 370);

        final TextButton CANCEL = new TextButton("CANCEL", skin);
        final TextButton PAY = new TextButton(Integer.toString(value), skin);

        CANCEL.setSize(200, 75);
        CANCEL.setPosition(690, 500);
        PAY.setSize(200, 75);
        PAY.setPosition(990, 500);

        CANCEL.addListener((new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CANCEL.remove();
                PAY.remove();
                sett_image.remove();
            }
        }));

        PAY.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CANCEL.remove();
                PAY.remove();
                sett_image.remove();
                // pay function is called
            }
        });


        stage.addActor(sett_image);
        stage.addActor(CANCEL);
        stage.addActor(PAY);
    }
}


// it also has two screens : 1st is upgrade and 2nd is confirm payment
