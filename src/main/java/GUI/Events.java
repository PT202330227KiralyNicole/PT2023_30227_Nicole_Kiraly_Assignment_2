package GUI;

import javax.swing.*;
import java.awt.*;

public class Events extends JFrame {
    private JTextArea area;
    private JPanel panel;

    public Events(){
        area = new JTextArea();
        panel = new JPanel();
        panel.add(area);
        this.pack();
        this.setContentPane(panel);
        this.setSize(600,600);
        this.setTitle("LOG OF EVENTS");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
        //thread safety to not interfere with other GUI events
        public static void main(){
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try{
                        Events events = new Events();
                        events.setVisible(true);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        public void setEvents(String text){
        this.area.setText(text);
}
}