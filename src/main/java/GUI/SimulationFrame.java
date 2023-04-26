package GUI;

import BusinessLogic.SelectionPolicy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {

    private JLabel clientsLabel;
    private JLabel queuesLabel;
    private JLabel simulationLabel;
    private JLabel minArrTimeLabel;
    private JLabel maxArrTimeLabel;
    private JLabel minServTimeLabel;
    private JLabel maxServTimeLabel;
    private JLabel selPolicy;
    private JTextField tf1;
    private JTextField tf2;
    private JTextField tf3;
    private JTextField tf4;
    private JTextField tf5;
    private JTextField tf6;
    private JTextField tf7;
    private JComboBox selectPolicyComboBox;
    private JButton button;

    public SimulationFrame(){

        this.clientsLabel = new JLabel("Nb of clients: ");
        this.queuesLabel = new JLabel("Nb of queues: ");
        this.simulationLabel = new JLabel("Simulation interval: ");
        this.minArrTimeLabel = new JLabel("Min arrival time: ");
        this.maxArrTimeLabel = new JLabel("Max arrival time: ");
        this.minServTimeLabel = new JLabel("Min service time: ");
        this.maxServTimeLabel = new JLabel("Max service time: ");
        this.selPolicy = new JLabel("Selection Policy: ");

        this.button = new JButton("START");

        SelectionPolicy[] selectionPolicies = new SelectionPolicy[]{SelectionPolicy.SHORTEST_QUEUE, SelectionPolicy.SHORTEST_TIME};
        selectPolicyComboBox = new JComboBox<SelectionPolicy>(selectionPolicies);


        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
       // p1.add(Box.createRigidArea(new Dimension(0, 45)));
        p1.add(this.clientsLabel);
        p1.add(Box.createRigidArea(new Dimension(0, 50)));
        p1.add(this.queuesLabel);
        p1.add(Box.createRigidArea(new Dimension(0, 45)));
        p1.add(this.simulationLabel);
        p1.add(Box.createRigidArea(new Dimension(0, 48)));
        p1.add(this.minArrTimeLabel);
        p1.add(Box.createRigidArea(new Dimension(0, 50)));
        p1.add(this.maxArrTimeLabel);
        p1.add(Box.createRigidArea(new Dimension(0, 45)));
        p1.add(this.minServTimeLabel);
        p1.add(Box.createRigidArea(new Dimension(0, 45)));
        p1.add(this.maxServTimeLabel);
        p1.add(Box.createRigidArea(new Dimension(0, 45)));
        p1.add(this.selPolicy);

        this.tf1 = new JTextField();
        this.tf1.setPreferredSize(new Dimension(150, 20));
        this.tf2 = new JTextField();
        this.tf2.setPreferredSize(new Dimension(150, 20));
        this.tf3 = new JTextField();
        this.tf3.setPreferredSize(new Dimension(150, 20));
        this.tf4 = new JTextField();
        this.tf4.setPreferredSize(new Dimension(150, 20));
        this.tf5 = new JTextField();
        this.tf5.setPreferredSize(new Dimension(150, 20));
        this.tf6 = new JTextField();
        this.tf6.setPreferredSize(new Dimension(150, 20));
        this.tf7 = new JTextField();
        this.tf7.setPreferredSize(new Dimension(150, 20));

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.tf1);
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.tf2);
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.tf3);
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.tf4);
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.tf5);
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.tf6);
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.tf7);
        p2.add(Box.createRigidArea(new Dimension(0, 40)));
        p2.add(this.selectPolicyComboBox);
        p2.add(Box.createRigidArea(new Dimension(0, 40 )));
        p2.add(this.button);


        JPanel p3 = new JPanel();
        p3.add(p1);
        p3.add(p2);

//        JPanel p4 = new JPanel();
//        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
//        p4.add(Box.createRigidArea(new Dimension(0, 40)));
//        p4.add(p3);
//        p4.add(Box.createRigidArea(new Dimension(0, 40)));
//        p4.add(this.button);


        this.pack();
        this.setContentPane(p3);
        this.setSize(600, 600);
        this.setTitle("Queues management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // this.setVisible(true);
    }

    public String getClientsTextField(){
        return tf1.getText();
    }
    public String getQueuesTextField(){
        return tf2.getText();
    }
    public String getSimulationTime(){
        return tf3.getText();
    }
    public String getMinArrTimeTextField(){
        return tf4.getText();
    }
    public String getMaxArrTimeTextField(){
        return tf5.getText();
    }
    public String getMinServTimeTextField(){
        return tf6.getText();
    }
    public String getMaxServTimeTextField(){
        return tf7.getText();
    }
    public SelectionPolicy getComboBox(){
        return (SelectionPolicy) selectPolicyComboBox.getSelectedItem();
    }

    public void addStartListener(ActionListener actionListener){
        this.button.addActionListener(actionListener);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    SimulationFrame frame = new SimulationFrame();
                    frame.setTitle("Queue management application");
                    frame.setVisible(true);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }



}
