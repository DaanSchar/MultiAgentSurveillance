//package nl.maastrichtuniversity.dke.settings;
//
//import nl.maastrichtuniversity.dke.agents.Agent;
//import nl.maastrichtuniversity.dke.agents.Intruder;
//import nl.maastrichtuniversity.dke.areas.Area;
//
//import java.util.List;
//
//public class Victory {
//    /* Timer Stuff
//    long startTime = System.currentTimeMillis();
//    long elapsedTime = 0L.
//
//    while (elapsedTime < 2*60*1000) {
//        //perform db poll/check
//        elapsedTime = (new Date()).getTime() - startTime;
//}
//     */
////    enum victory{
////        CONTINIUE,
////        Intruders_WON,
////        Guards_Won;
////
////    }
//
//    Area targetArea;
//
//    public Victory(Area targetArea) {
//        this.targetArea = targetArea;
//    }
//
//    /**
//     * check if intruders get to the target
//     *
//     * @param intrudersList current agent
//     * @return if in target area
//     */
//    public boolean checkIntrudersVictory(List<Intruder> intrudersList) {
//        boolean victory = true;
//        for(Intruder intruder: intrudersList){
//            if (!intruder.getPosition().equals(targetArea.getPosition())) {
//                victory = false;
//                break;
//            }
//        }
//        return  victory;
//    }
//
//    /**
//     * check if guards get all the intruders
//     *
//     * @param intrudersList list of intruders
//     * @return if guards won
//     */
//    public boolean checkGuardVictory(List<Intruder> intrudersList){
//        boolean victory = true;
//        for(Intruder intruder: intrudersList){
//            if (intruder.isAlive()) {
//                victory = false;
//            }
//        }
//        return victory;
//    }
//
//
//}
