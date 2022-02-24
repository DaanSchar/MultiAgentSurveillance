package nl.maastrichtuniversity.dke.settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clock {

    JLabel time = new JLabel();
    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;

    // using string.format to make sec/min/hour appear with 00 before the actual time
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);

    javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            elapsedTime = elapsedTime + 1000;
            hours = (elapsedTime / 3600000);
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            time.setText(" " + hours_string + ":" + minutes_string + ":" + seconds_string);
        }
    });

    public JLabel getTime() { return time; }

    public void start() { timer.start(); }

    public void stop() { timer.stop(); }

    public void reset() {
        timer.stop();
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);
        time.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
    }
}
