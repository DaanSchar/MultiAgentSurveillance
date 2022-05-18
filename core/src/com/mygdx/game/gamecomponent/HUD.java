package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameGUI;

import java.util.ArrayList;

public class HUD extends Stage {

    BitmapFont font;
    ArrayList<Boolean> booleanList;

    public HUD() {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        booleanList = new ArrayList<>();
        init();
    }

    @Override
    public void draw() {
        super.draw();
        getBatch().begin();
        ArrayList<String> list = getKeys();
        String timeIntervalString = String.format("%.2f",GameGUI.getTimeInterval()*10);
        font.draw(getBatch(), "PLAY BACK SPEED "+ timeIntervalString, 50, 100);
        for (int i = 0; i < list.size(); i++) {
            font.setColor(Color.GRAY);
            if (booleanList.get(i)) {
                font.setColor(Color.BLUE);
            }
            font.draw(getBatch(), list.get(i), 50, 550 + (i * 20));
        }
        getBatch().end();
    }

    public ArrayList<String> getKeys() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Press R to Reset");
        list.add("Press S for Sound");
        list.add("Press V for Vision");
        list.add("Press -/+ to speed");
        list.add("Press D for Dijkstra");
        list.add("Press M for Memory Map");
        list.add("Press B for Brick&Mortar");
        list.add("Press Q to Switch agent type");
        list.add("Press P to PAUSE");
        return list;
    }

    public void setKey(int i) {
        if (booleanList.get(i)) {
            booleanList.set(i, false);
            System.out.print(booleanList.get(i));
        } else {
            booleanList.set(i, true);
            System.out.print(booleanList.get(i));

        }
    }

    public void init() {
        for (int i = 0; i < 9; i++) {
            booleanList.add(false);
        }
    }



}
