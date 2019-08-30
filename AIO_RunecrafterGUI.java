package AIO_Runecrafter;

import AIO_Runecrafter.data.EssenceType;
import AIO_Runecrafter.data.RuneType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIO_RunecrafterGUI extends JFrame {

    private JComboBox runeTypeComboBox;
    private JComboBox essenceTypeComboBox;
    private JButton initiate;
    private Dimension size;

    public AIO_RunecrafterGUI(){

        setTitle("Sweti's AIO Runecrafter Configuration");

        setLayout(new FlowLayout());

        initiate = new JButton("Start!");

        runeTypeComboBox = new JComboBox(RuneType.values());
        essenceTypeComboBox = new JComboBox(EssenceType.values());

        add(runeTypeComboBox);
        add(essenceTypeComboBox);
        add(initiate);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        size = new Dimension(500,100);
        setMinimumSize(size);

        /*
        runeTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RuneType runeType = (RuneType) runeTypeComboBox.getSelectedItem();
            }
        });

         */

        initiate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            AIO_Runecrafter.runeType = (RuneType) runeTypeComboBox.getSelectedItem();
            AIO_Runecrafter.essenceType = (EssenceType) essenceTypeComboBox.getSelectedItem();
            setVisible(false);
            }
        });
    }

}
