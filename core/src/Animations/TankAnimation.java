package Animations;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class TankAnimation extends ApplicationAdapter {
    SpriteBatch batch;
    Animation<TextureRegion> animation;
    float elapsed;

    public TankAnimation(MyGdxGame game){
        batch = game.getBatch();
    }

    @Override
    public void create () {
        animation = com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("tank-stars-animations/1-tank-stars-animations.gif").read());
    }

    @Override
    public void render () {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsed), 10f, 10f, 500, 182);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}

//animations from -- spine