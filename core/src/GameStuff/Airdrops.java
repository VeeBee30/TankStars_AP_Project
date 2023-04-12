package GameStuff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Airdrops {
    public Vector2 position;
    String Image;
    String name;

    public Body airdrop;

    float dim_x;
    float dim_y;

    Missiles missile;

    // for sound

    public Airdrops(float pos_x, float pos_y, String image, World world, Missiles miss){
        this.Image = image;

        this.position = new Vector2(pos_x, pos_y);

        airdrop = defineairdrop(world);
        missile = miss;
    }

    public Body defineairdrop(World world){
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
        fdef.density= 100f;                  // this needs to be set as long range or short range
        fdef.friction= 1f;
        b2body.createFixture(fdef);

        Sprite boxsprite = new Sprite(new Texture(this.Image));
        boxsprite.setSize(50, 50);
        boxsprite.setOrigin(boxsprite.getHeight()/2, boxsprite.getWidth()/2);
        b2body.setUserData(boxsprite);
        shape.dispose();

        return b2body;
    }
}
