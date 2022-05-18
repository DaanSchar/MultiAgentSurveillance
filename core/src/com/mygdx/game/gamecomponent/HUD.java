package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameGUI;
import com.mygdx.game.util.TextureRepository;

import java.util.ArrayList;

public class HUD extends Stage {

    private final BitmapFont font;
    private final ArrayList<Boolean> booleanList;

    public HUD() {
        this.font = new BitmapFont();
        this.booleanList = new ArrayList<>();
        renderTextSharply();
        init();
    }

    @Override
    public void draw() {
        super.draw();
        getBatch().begin();
        getBatch().draw(TextureRepository.getInstance().getTile(new Color(0.00f, 0.0071f, 0.090f, 0.7f)), 40, 525, 250, 193);
        drawPlaySpeed();
        drawKeyTexts();
        getBatch().end();
    }

    private void drawKeyTexts() {
        ArrayList<String> list = getKeys();

        for (int i = 0; i < list.size(); i++) {
            font.setColor(new Color(1 - 0.00f, 1 - 0.0071f, 1 - 0.090f, 0.8f));

            if (booleanList.get(i)) {
                font.setColor(new Color(219/255f, 77/255f, 79/255f, 0.8f));
            }

            String text = list.get(i);
            font.draw(getBatch(), text, 50, 550 + (i * 20));
        }
    }

    private void drawPlaySpeed() {
        String timeIntervalString = String.format("%.2f", 1 / (GameGUI.getTimeInterval() + 1));
        font.draw(getBatch(), "Play Speed: " + timeIntervalString, 50, 100);
    }

    public ArrayList<String> getKeys() {
        ArrayList<String> list = new ArrayList<>();
        list.add("(R)   Reset Game");
        list.add("(S)   Display Sound Areas");
        list.add("(V)   Display Vision Areas");
        list.add("(-/+) Change Game Speed");
        list.add("(D)   Display Paths");
        list.add("(M)   Display Memory Map");
        list.add("(B)   Display Exploration (B&M)");
        list.add("(Q)   Switch between Intruder/Guard");
        list.add("(P)   PAUSE");
        return list;
    }

    public void setKey(int i) {
        if (booleanList.get(i)) {
            booleanList.set(i, false);
        } else {
            booleanList.set(i, true);
        }
    }

    public void init() {
        for (int i = 0; i < 9; i++) {
            booleanList.add(false);
        }
    }

    private void renderTextSharply() {
        this.font.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );
    }


}
