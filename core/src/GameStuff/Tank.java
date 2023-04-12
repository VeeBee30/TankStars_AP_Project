package GameStuff;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;
import java.util.Vector;

public class Tank implements Serializable {
    public Vector<Missiles> missiles;
    public Vector<Integer> quantity;

    public String Name;
    public String Image;
    public int HP;
    public Animation<TextureRegion> animation;
    int level;
    // add sound here

    public Tank(String name, String image, int health, Animation<TextureRegion> a, int l, Vector<Missiles> m, Vector<Integer> q){
        this.Name = name;
        this.HP = health;
        this.animation = a;
        this.level = l;
        this.Image = image;
        this.missiles = m;
        this.quantity = q;
    }
    public void upgrade_level(){
        if(level < 5) {
            level += 1;
        }
    }
}
