package com.mygdx.game.justforfun;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameColorComponent extends ShapeRendererStage{

    private final EnvironmentColorView environmentView;


    public GameColorComponent(EnvironmentColorView environmentView) {
        super(environmentView.getWidth(), environmentView.getHeight());
        this.environmentView = environmentView;
    }

    @Override
    public void draw() {
        super.draw();
        getShapeRenderer().setAutoShapeType(true);
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        environmentView.draw(getShapeRenderer());
        getShapeRenderer().end();
    }
}
