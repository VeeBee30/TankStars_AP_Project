package GameStuff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.Vector;

public class Game_player implements Serializable {
    public float health;
    public float i_health;
    public Vector<Missiles> missiles;
    public Vector<Integer> quantity;
    public float fuel;
    public Tank tank;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 impulse_body = new Vector2();
    public Vector2 force_body = new Vector2();
    public Body player_body;

    public Game_player(float pos_x, float pos_y, float vel_x, float vel_y, Tank T, World world){
        health = T.HP;
        i_health = health;
        fuel = 100;
        position = new Vector2(pos_x, pos_y);
        velocity = new Vector2(vel_x, vel_y);
        tank = T;
        missiles = tank.missiles;
        quantity = new Vector<Integer>();
        for(int i=0;i<tank.quantity.size();i++){
            quantity.add(tank.quantity.get(i));
        }
    }

    public void definePlayer(World world){
        BodyDef bdef =new BodyDef();
        Body b2body ;

        bdef.position.set(position.x, position.y);
        bdef.fixedRotation= true;
        bdef.type= BodyDef.BodyType.DynamicBody;
        b2body= world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        CircleShape shape= new CircleShape();
        shape.setRadius(12);

        fdef.shape= shape;
        fdef.density= 10f;
        fdef.friction= 0.1f;
        b2body.createFixture(fdef);

        Sprite boxsprite = new Sprite(new Texture(tank.Image));
        boxsprite.setSize(100, 100);
        boxsprite.setOrigin(boxsprite.getHeight()/2, boxsprite.getWidth()/2);
        b2body.setUserData(boxsprite);
        shape.dispose();
        player_body = b2body;
        return;
    }
}
