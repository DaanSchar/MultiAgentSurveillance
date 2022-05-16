package nl.maastrichtuniversity.dke.agents.modules.communication;

public enum CommunicationType {
    VISION_BLUE(false, true, false),
    VISION_RED(false, true, false),
    VISION_GREEN(false, true, false),
    SOUND(true, false, false),
    SMELL(false, false, true);


    private final boolean hear;
    private final boolean see;
    private final boolean smell;

    CommunicationType(boolean hearIt, boolean see, boolean smelly) {
        this.hear = hearIt;
        this.see = see;
        this.smell = smelly;
    }
}
