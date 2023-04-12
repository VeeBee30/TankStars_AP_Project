package Screens;

import GameStuff.Airdrops;
import GameStuff.Game_player;
import GameStuff.Missiles;
import GameStuff.Tank;
import GameUtilities.Game_data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

import java.time.LocalDateTime;
import java.util.Vector;

public class load_play extends ScreenAdapter implements Screen {
    MyGdxGame game;
    TmxMapLoader loader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    FitViewport view;
    SpriteBatch batch;
    Stage stage;
    Skin skin;

    Array<Body> tempbodies= new Array<Body>();
    Body b2body;
    Body player;
    World world;
    Box2DDebugRenderer b2dr;
    Vector2 movement = new Vector2();
    Vector2 impulse = new Vector2();


    Texture health_background;
    Texture health_player1;
    Texture health_player2;

    Game_player player1;
    Game_player player2;
    Game_player main_player = null;


    Skin touchpadSkin;
    Touchpad.TouchpadStyle touchpadStyle;
    Touchpad touchpad;


    // for aim purpose
    int number_points = 10;
    Vector2 starting_pos = new Vector2(), starting_vel = new Vector2();
    Texture points = new Texture("white-circle.png");;

    float start_time, end_time;
    boolean show_aim;
    Texture missile;
    boolean do_projecitle = false;

    // Tanks
    Tank main_tank1;
    Tank main_tank2;
    Missiles main_missile;

    boolean is_victory = false;
    Animation<TextureRegion> animation_victory = com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/17-tank-stars-animations.gif").read());
    float elapsed;
    boolean is_defeat = false;
    Animation<TextureRegion> animation_defeat = com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/16-tank-stars-animations.gif").read());

    boolean airdrop_initialize = true;

    Airdrops airdrop;
    String missile_name = "";

    Label sp_label_missile;
    Label label_missile_1;
    Label label_missile_2;
    Label label_missile_3;
    int value_sp_missile = 0;
    public load_play(MyGdxGame game, String name, Vector2 player1_pos, Vector2 player2_pos, double player1_h, double player2_h, Vector<Integer> player1_mis, Vector<Integer> player2_mis) {
        this.game = game;
        batch = game.getBatch();
        camera = new OrthographicCamera();
        view = new FitViewport(game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT());

        loader = new TmxMapLoader();
        map = loader.load("tiled map 1/tankstargamemap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera.setToOrtho(false, game.getSCREEN_WIDTH(), game.getSCREEN_HEIGHT());

        world = new World(new Vector2(0, -12), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            fdef.friction=0.1f;
            body.createFixture(fdef);

        }

        stage = new Stage(view);
        if(name.equals("Abrams")){
            main_tank1 = game.database.tank1;
            main_tank2 = game.database.tank1_flip;
        }
        else if(name.equals("Helios")){
            main_tank1 = game.database.tank2;
            main_tank2 = game.database.tank2_flip;
        }
        else if(name.equals("Buratino")) {
            main_tank1 = game.database.tank3;
            main_tank2 = game.database.tank3_flip;
        }

        player1  = new Game_player(player1_pos.x, player1_pos.y, 50, 50, main_tank1, world);
        player2 = new Game_player(player2_pos.x, player2_pos.y, 50, 50, main_tank2, world);

        player1.health = (float) player1_h;
        player1.quantity = player1_mis;
        player2.health = (float) player2_h;
        player2.quantity = player2_mis;

    }


    @Override
    public void show() {
        handleInput();
        initiate_touchpad();

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));

        TextButton FIRE = new TextButton("FIRE", skin);
        FIRE.setSize(300 ,100);
        FIRE.setPosition(1100, 100);

        Texture t = new Texture("46517.png");
        TextureRegion r = new TextureRegion(t);
        TextureRegionDrawable d = new TextureRegionDrawable(r);
        ImageButton Settings = new ImageButton(d);

        Settings.setSize(80, 81);
        Settings.setPosition(40, 950);
        Settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings_menu();
            }
        });

        stage.addActor(Settings);
        stage.addActor(FIRE);
        stage.addActor(touchpad);

        health_background = new Texture("background_healthbar.png");
        health_player1 = new Texture("background-front_healthbar.png");
        health_player2 = new Texture("background-front_healthbar.png");

        show_aim = true;

        // for players

        // 1. Tanks

        player1.definePlayer(world);
        player2.definePlayer(world);

        main_player = player1;

        // for choosing missiles
        Texture miss_1 = new Texture(Gdx.files.internal(main_player.missiles.get(0).Image));
        TextureRegion missile1_r = new TextureRegion(miss_1);
        TextureRegionDrawable missile1_dr = new TextureRegionDrawable(missile1_r);
        ImageButton missile_1 = new ImageButton(missile1_dr);

        label_missile_1 = new Label(Integer.toString(main_player.quantity.get(0)), skin);
//
        Texture miss_2 = new Texture(Gdx.files.internal(main_player.missiles.get(1).Image));
        TextureRegion missile2_r = new TextureRegion(miss_2);
        TextureRegionDrawable missile2_dr = new TextureRegionDrawable(missile2_r);
        ImageButton missile_2 = new ImageButton(missile2_dr);

        label_missile_2 = new Label(Integer.toString(main_player.quantity.get(1)), skin);

        Texture miss_3 = new Texture(Gdx.files.internal(main_player.missiles.get(2).Image));
        TextureRegion missile3_r = new TextureRegion(miss_3);
        TextureRegionDrawable missile3_dr = new TextureRegionDrawable(missile3_r);
        ImageButton missile_3 = new ImageButton(missile3_dr);

        label_missile_3 = new Label(Integer.toString(main_player.quantity.get(2)), skin);


        Texture sp_miss_1 = new Texture(Gdx.files.internal(game.database.special_missile.Image));
        TextureRegion sp_missile1_r = new TextureRegion(sp_miss_1);
        TextureRegionDrawable sp_missile1_dr = new TextureRegionDrawable(sp_missile1_r);
        ImageButton sp_missile_1 = new ImageButton(sp_missile1_dr);

        sp_label_missile = new Label(Integer.toString(value_sp_missile), skin);

        missile_1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missile_name = "missile1";
            }
        });
        missile_2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missile_name = "missile2";
            }
        });
        missile_3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missile_name = "missile3";
            }
        });
        sp_missile_1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missile_name = "sp_missile";
            }
        });

        // position - setting
        missile_1.setSize(70,70);
        missile_1.setPosition(700,120);
        missile_2.setSize(70,70);
        missile_2.setPosition(800,120);
        missile_3.setSize(70,70);
        missile_3.setPosition(900,120);
        sp_missile_1.setSize(70,70);
        sp_missile_1.setPosition(1000,120);


        label_missile_1.setPosition(730,100);
        label_missile_2.setPosition(830,100);
        label_missile_3.setPosition(930,100);
        sp_label_missile.setPosition(1030,100);



        stage.addActor(missile_1);
        stage.addActor(missile_2);
        stage.addActor(missile_3);
        stage.addActor(sp_missile_1);
        stage.addActor(label_missile_1);
        stage.addActor(label_missile_2);
        stage.addActor(label_missile_3);
        stage.addActor(sp_label_missile);

        // for firing
        FIRE.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(missile_name.equals("missile1")){
                    if(Integer.parseInt(String.valueOf(label_missile_1.getText())) != 0){
                        show_aim = false;
                        main_missile = main_player.missiles.get(0);
                        main_missile.position.x = main_player.player_body.getPosition().x + 20;
                        main_missile.position.y = main_player.player_body.getPosition().y + 20;
                        main_missile.definemissile(world);
                        do_projecitle = true;
                    }
                }
                else if(missile_name.equals("missile2")){
                    if(Integer.parseInt(String.valueOf(label_missile_2.getText())) != 0){
                        show_aim = false;
                        main_missile = main_player.missiles.get(1);
                        main_missile.position.x = main_player.player_body.getPosition().x + 20;
                        main_missile.position.y = main_player.player_body.getPosition().y + 20;
                        main_missile.definemissile(world);
                        do_projecitle = true;
                    }
                }
                else if(missile_name.equals("missile3")){
                    if(Integer.parseInt(String.valueOf(label_missile_3.getText())) != 0){
                        show_aim = false;
                        main_missile = main_player.missiles.get(2);
                        main_missile.position.x = main_player.player_body.getPosition().x + 20;
                        main_missile.position.y = main_player.player_body.getPosition().y + 20;
                        main_missile.definemissile(world);
                        do_projecitle = true;
                    }
                }
                else if(missile_name.equals("special_missile")){
                    show_aim = false;
                    main_missile = game.database.special_missile;
                    main_missile.position.x = main_player.player_body.getPosition().x + 20;
                    main_missile.position.y = main_player.player_body.getPosition().y + 20;
                    main_missile.definemissile(world);
                    do_projecitle = true;
                }
            }
        });
    }


    public void update(float dt){
        world.step(1/60f,8,3);
    }

    @Override
    public void render(float delta) {
        update(delta);
        update_position();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(camera.combined);

        main_player.player_body.applyForce(main_player.force_body, main_player.player_body.getPosition(),true);

        renderer.setView(camera);
        camera.update();

        renderer.render();
        b2dr.render(world,camera.combined);

        batch.begin();
        world.getBodies(tempbodies);
        for(Body b: tempbodies){
            if(b.getUserData() != null && b.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite) b.getUserData();
                sprite.setPosition(b.getPosition().x - sprite.getWidth()/2, b.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(b.getAngle()* MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }

        // for health bars
        double health1 = (player1.health/player1.i_health);
        batch.draw(health_background, 170, 950, 700, 60);
        batch.draw(health_player1, 170, 950, (float) (700*health1), 60);

        double health2 = (player2.health/player2.i_health);
        batch.draw(health_background, 1120, 950, 700, 60);
        batch.draw(health_player2, (float) (1120 + 700*(1 - health2)), 950, (float) (700*health2), 60);

        // for victory and defeat
        if(player1.health <= 0){
            is_defeat = true;
            is_victory = false;
        }

        if(player2.health <= 0){
            is_victory = true;
            is_defeat = false;
        }

        // for aiming position
        if(show_aim){
            for(int i=0;i<=number_points;i++){
                Vector2 pos = get_projectile_point(starting_pos, starting_vel, i);
                // now change according to knob
                if(touchpad.getKnobPercentX() == 0 && touchpad.getKnobPercentY() == 0){
                    batch.draw(points, pos.x, pos.y, 12, 12);
                }
                else{
                    starting_vel.x = 50 * touchpad.getKnobPercentX()*5;
                    starting_vel.y = 50 * touchpad.getKnobPercentY()*5;
                    batch.draw(points, pos.x, pos.y, 12, 12);
                }
            }
        }

        // for animation purposes
        if(is_defeat){
            elapsed += delta;
            System.out.println(elapsed);

            // animation for defeat
            batch.draw(animation_defeat.getKeyFrame(elapsed), (game.getSCREEN_WIDTH() -446)/2, (game.getSCREEN_HEIGHT() - 306)/2, 446, 306);
            if(elapsed > 10){
                game.setScreen(new Main_menu(game));
            }
        }

        if(is_victory){
            elapsed += delta;
            System.out.println(elapsed);

            // animation for victory
            batch.draw(animation_victory.getKeyFrame(elapsed), (game.getSCREEN_WIDTH() -446)/2, (game.getSCREEN_HEIGHT() - 306)/2, 446, 306);

            if(elapsed > 10){
                game.setScreen(new Main_menu(game));
            }
        }

        batch.end();

        if(do_projecitle){
            main_missile.missile.applyLinearImpulse(impulse, new Vector2(main_player.position.x , main_player.position.y ), true);

//            end_time = TimeUtils.timeSinceMillis(start_time);
            end_time += delta;

            float time_flight = (starting_vel.y/9.81f);

            if(end_time < time_flight/60){
                impulse.x = starting_vel.x * 1000;
                impulse.y = (starting_vel.y - 9.81f*(end_time))*100;
            }

            else{
                impulse.x = starting_vel.x * 1000;
                impulse.y = (starting_vel.y - 9.81f*(end_time))*1000;
//                do_projecitle = false;
            }

            if(end_time > 2*time_flight){

                if(main_player == player1){
                    main_player = player2;
                }
                else{
                    main_player = player1;
                }
                float distance_percent = 0;
                float distance = Math.abs(((main_player.player_body.getPosition().x) - (main_missile.missile.getPosition().x)));

                if(distance < main_missile.impact_radius){
                    distance_percent = 100 - ((distance)/main_missile.impact_radius)*100;
                    System.out.println(distance_percent);
                }
                System.out.println(main_player.health);
                main_player.health = (int) (main_player.health - main_missile.power*distance_percent/100);
                System.out.println(main_player.health);
                world.destroyBody(main_missile.missile);

                parameters_reset();

            }
        }

        stage.act();
        stage.draw();

        // for airdrop
        if((player1.health < player1.i_health/2 || player2.health < player2.i_health/2) && airdrop_initialize){
            float position_x = Math.abs(player1.player_body.getPosition().x - player2.player_body.getPosition().x)/2;
            airdrop = new Airdrops(player1.player_body.getPosition().x + position_x, 1000, "airdrop.png", world, game.database.special_missile);
            airdrop_initialize = false;
            airdrop_flag = true;
        }

        // for moving tank

        if(flag_input && input_time_elapsed < 2){
            input_time_elapsed += delta;
        }
        else{
            input_time_elapsed = 0;
            flag_input = false;
            main_player.force_body.x = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            main_player.force_body.x = -100000;
            flag_input = true;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            main_player.force_body.x = 100000;
            flag_input = true;
        }


        // for airdrop purpose
        if(airdrop_flag == true){
            if((Math.abs(main_player.player_body.getPosition().x - airdrop.airdrop.getPosition().x) < 20)){
                value_sp_missile = 1;
                world.destroyBody(airdrop.airdrop);
                airdrop_flag = false;
            }
        }

    }
    float input_time_elapsed = 0;
    boolean flag_input = false;
    boolean airdrop_flag = false;

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth= width;
        camera.viewportHeight= height;
        camera.update();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public void update_position(){
        starting_pos = main_player.player_body.getPosition();
        starting_vel = main_player.velocity;
//        main_missile.position.x = main_player.position.x +20;
//        main_missile.position.y = main_player.position.y +20;
    }

    public void handleInput(){
        if(input_time_elapsed < 2){
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                main_player.force_body.x = -100000;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.D)){
                main_player.force_body.x = 100000;
            }
        }
        else{
            main_player.force_body.x = 0;
            input_time_elapsed = 0;
        }
    }

    void settings_menu(){

//        BACKGORUND SET KRNE KE LIYE
        Texture sett = new Texture(Gdx.files.internal("settings_background.jpg"));
        final TextureRegion sett_region = new TextureRegion(sett, 549, 700);
        final Image sett_image = new Image(sett_region);
        sett_image.setPosition(650, 170);

//        BUTTONS
        final TextButton RESUME_GAME = new TextButton("RESUME GAME", skin);
        final TextButton SAVE_GAME = new TextButton("SAVE GAME", skin);
        final TextButton RESTART = new TextButton("RESTART", skin);
        final TextButton MAIN_MENU = new TextButton("MAIN_MENU", skin);

//        BUTTONS SET KRNE KE LIYE
        RESUME_GAME.setSize(300, 100);
        RESUME_GAME.setPosition(770, 700);
        SAVE_GAME.setSize(300, 100);
        SAVE_GAME.setPosition(770, 550);
        RESTART.setSize(300, 100);
        RESTART.setPosition(770, 400);
        MAIN_MENU.setSize(300, 100);
        MAIN_MENU.setPosition(770, 250);

        RESUME_GAME.addListener((new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RESUME_GAME.remove();
                SAVE_GAME.remove();
                RESTART.remove();
                MAIN_MENU.remove();
                sett_image.remove();
            }
        }));

        SAVE_GAME.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game is saved
                game.database.player_tanks.add(player1.tank.Name);
                game.database.health_player1.add((double) player1.health);
                game.database.health_player2.add((double) player2.health);
                game.database.missiles_player1.add(player1.quantity);
                game.database.missiles_player2.add(player2.quantity);
                game.database.position_player1.add(player1.player_body.getPosition());
                game.database.position_player2.add(player2.player_body.getPosition());
                System.out.println("game saved to database");

                game.setScreen(new Main_menu(game));
            }
        });

        RESTART.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new play(game, main_tank1, main_tank2));
            }
        });

        MAIN_MENU.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Main_menu(game));
            }
        });

        stage.addActor(sett_image);
        stage.addActor(RESUME_GAME);
        stage.addActor(SAVE_GAME);
        stage.addActor(RESTART);
        stage.addActor(MAIN_MENU);

    }

    public Vector2 get_projectile_point(Vector2 starting_point, Vector2 starting_velocity, int n){
        Vector2 final_position = new Vector2();

        float range = (float) ((starting_velocity.dst(0, 0)*starting_velocity.dst(0, 0)*Math.sin(starting_velocity.angleRad()*2))/(2*9.81f));
        float difference = (float) (range/3.27)/number_points;

        float x = (float) (n*difference);


        float y = (float) ((x*Math.tan(starting_velocity.angleRad())) * (1 - (x/range)));

        final_position.x = x + starting_point.x;
        final_position.y = y + starting_point.y;
        return final_position;
    }

    public void initiate_touchpad(){
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("outer_circlepad.png"));
        touchpadSkin.add("touchKnob", new Texture("inside_pad.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        Drawable D_background = touchpadSkin.getDrawable("touchBackground");
        Drawable D_knob = touchpadSkin.getDrawable("touchKnob");

        D_background.setMinHeight(40);
        D_background.setMinWidth(40);

        D_knob.setMinHeight(100);
        D_knob.setMinWidth(102);

        touchpadStyle.background = D_background;
        touchpadStyle.knob = D_knob;

        touchpad = new Touchpad(5, touchpadStyle);
        touchpad.setBounds(1650, 100, 200, 200);

        touchpad.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
    }

    public void parameters_reset(){
        show_aim = true;
        do_projecitle = false;
        end_time = 0;

        // resetting the labels
        if(missile_name.equals("missile1")){
            int value = main_player.quantity.get(0) - 1;
            main_player.quantity.set(0, value);
        }
        else if(missile_name.equals("missile2")){
            int value = main_player.quantity.get(1) - 1;
            main_player.quantity.set(1, value);
        }
        else if(missile_name.equals("missile3")){
            int value = main_player.quantity.get(2) - 1;
            main_player.quantity.set(2, value);
        }
        else{
            value_sp_missile--;
        }

//        label_missile_1 = new Label(Integer.toString(main_player.quantity.get(0)), skin);
        label_missile_1.setText(Integer.toString(main_player.quantity.get(0)));
        label_missile_2 = new Label(Integer.toString(main_player.quantity.get(1)), skin);
        label_missile_3 = new Label(Integer.toString(main_player.quantity.get(2)), skin);
        sp_label_missile = new Label(Integer.toString(value_sp_missile), skin);

    }

    public String toString(){
        return "This is play screen";
    }
}