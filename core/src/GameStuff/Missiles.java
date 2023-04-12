package GameStuff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Missiles implements Serializable {

    public Vector2 position;
    public String Image;
    String name;
    public int impact_radius;
    public int power;
    public int level;
    public Body missile;

    float dim_x;
    float dim_y;

    // for sound

    public Missiles(String n, float pos_x, float pos_y, int radius, int p, int l, String image){
        this.Image = image;
        this.level = l;
        this.name = n;
        this.position = new Vector2(pos_x, pos_y);
        this.impact_radius = radius;
        this.power = p;

    }

    public void definemissile(World world){
        BodyDef bdef =new BodyDef();
        Body b2body ;

        bdef.position.set(position.x, position.y);
        bdef.fixedRotation= true;
        bdef.type= BodyDef.BodyType.DynamicBody;
        b2body= world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        CircleShape shape= new CircleShape();
        shape.setRadius(8);

        fdef.shape= shape;
        fdef.density= 0.5f;                  // this needs to be set as long range or short range
        fdef.friction= 1f;
        b2body.createFixture(fdef);

        Sprite boxsprite = new Sprite(new Texture(this.Image));
        boxsprite.setSize(31, 25);
        boxsprite.setOrigin(boxsprite.getHeight()/2, boxsprite.getWidth()/2);
        b2body.setUserData(boxsprite);
        shape.dispose();
        missile = b2body;
        return;
    }
}
