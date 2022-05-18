package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class HUD extends Stage {

    @Override
    public void draw() {
        super.draw();
        getBatch().begin();
        BitmapFont font = new BitmapFont();
        font.setColor(Color.GRAY);
        ArrayList<String> list = getKeys();
        for (int i = 0; i < list.size(); i++) {
            font.draw(getBatch(), list.get(i), 50, 550 + (i * 20));
        }
        getBatch().end();
    }

    public ArrayList<String> getKeys() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Press R to Rest");
        list.add("Press S for Sound");
        list.add("Press V for Vision");
        list.add("Press -/+ to speed");
        list.add("Press D for Dijkstra");
        list.add("Press M for Memory Map");
        list.add("Press B for Brick&Mortar");
        list.add("Press Q to Switch agent type");
        return list;
    }
}
