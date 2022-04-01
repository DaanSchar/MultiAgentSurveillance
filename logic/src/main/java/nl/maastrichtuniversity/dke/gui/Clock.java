package nl.maastrichtuniversity.dke.gui;

import javax.swing.*;

public final class Clock {

    private final JLabel time = new JLabel();
    private int elapsedTime = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    // using string.format to make sec/min/hour appear with 00 before the actual time
    private String secondsString = String.format("%02d", seconds);
    private String minutesString = String.format("%02d", minutes);
    private String hoursString = String.format("%02d", hours);

    private final int SECONDS_IN_MILLI = 1000;

    javax.swing.Timer timer = new javax.swing.Timer(SECONDS_IN_MILLI, e -> {

        elapsedTime = elapsedTime + SECONDS_IN_MILLI;
        hours = (elapsedTime / 3600000);
        minutes = (elapsedTime / 60000) % 60;
        seconds = (elapsedTime / SECONDS_IN_MILLI) % 60;
        secondsString = String.format("%02d", seconds);
        minutesString = String.format("%02d", minutes);
        hoursString = String.format("%02d", hours);
        time.setText(" " + hoursString + ":" + minutesString + ":" + secondsString);
    });

    public JLabel getTime() {
        return time;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        timer.stop();
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        secondsString = String.format("%02d", seconds);
        minutesString = String.format("%02d", minutes);
        hoursString = String.format("%02d", hours);
        time.setText(hoursString + ":" + minutesString + ":" + secondsString);
    }
}
