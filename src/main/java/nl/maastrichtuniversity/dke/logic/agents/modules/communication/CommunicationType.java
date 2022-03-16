package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

public enum CommunicationType {
    VISION(false, true,false),
    SOUND(true, false, false),
    SMELL(false, false , true);

    private final boolean hear;
    private final boolean see;
    private final boolean smell;

    CommunicationType(boolean hearIt, boolean see, boolean smelly){
        this.hear = hearIt;
        this.see = see;
        this.smell = smelly;
    }
}
