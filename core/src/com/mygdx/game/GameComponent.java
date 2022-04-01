package com.mygdx.game;

public class GameComponent extends MovableStage {

    private final EnvironmentView environmentView;
    private final AgentView agentView;

    public GameComponent(EnvironmentView environmentView, AgentView agentView) {
        super(environmentView.getWidth(), environmentView.getHeight());
        this.environmentView = environmentView;
        this.agentView = agentView;
    }

    public void draw() {
        super.draw();
        drawBatch();
    }

    private void drawBatch() {
        getBatch().begin();
        environmentView.draw(getBatch());
        agentView.draw(getBatch());
        getBatch().end();
    }

}
