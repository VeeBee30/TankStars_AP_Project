package com.mygdx.game;

import Animations.TankAnimation;
import Factory_design.Screen_factory;
import GameUtilities.Game_database;
import GameUtilities.Input_Output;
import Screens.Loading_screen;
import Screens.Main_menu;
import Screens.play;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	float SCREEN_WIDTH = 1920;
	float SCREEN_HEIGHT = 1080;

	public int LOADING_SCREEN = 1;
	public int MAIN_MENU = 0;

	Loading_screen load_screen = null;
	public Main_menu main_screen;

	TankAnimation anim;

	float time = 0;
	Input_Output in_out ;

	public Game_database database;

	public float getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}

	public float getSCREEN_HEIGHT() {
		return SCREEN_HEIGHT;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public Screen_factory factory;
	
	@Override
	public void create () {
		factory = new Screen_factory(this);
		batch = new SpriteBatch();

		in_out = new Input_Output();
		main_screen = new Main_menu(this);

		// here we have to do deserialization
//		try {
//			if(in_out.deserialize() == null){
//				database = new Game_database(500, 30);
//				System.out.println("New formed");
//			}
//			else{
//				database = in_out.deserialize();
//				System.out.println("old is used");
//			}
//
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		}
		database = new Game_database(300, 20);
		change_screen(1);
//		database.initialize_everything();
//		setScreen(new play(this, database.tank1, database.tank1_flip));
	}

	public void change_screen(int val){
		switch(val){
			case 0:
				if(main_screen == null){
					main_screen = new Main_menu(this);
				}
				setScreen(main_screen);
			case 1:
				if(load_screen == null){
					load_screen = new Loading_screen(this);
				}
				setScreen(load_screen);
		}
	}
	@Override
	public void render () {
		super.render();
//		anim.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
