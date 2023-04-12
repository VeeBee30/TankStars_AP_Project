package Factory_design;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGdxGame;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.assertEquals;

public class TestRunner {
    MyGdxGame game;
    public TestRunner(MyGdxGame g){
        game = g;
    }

    class MyTest {
        @Test
        public void testSum() {
            assertEquals(null, game.factory.screen("main_menu", game));
        }
    }

    public static void main(String[] args) {
        Result result= JUnitCore.runClasses(MyTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}

