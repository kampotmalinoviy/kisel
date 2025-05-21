package desktopapp;

import javax.swing.*;
import java.awt.*;


public class UserInputBase extends JFrame {

    protected final JPanel formPanel = getFormPanel();

    protected final GridBagConstraints gbc = getGrid();

    public UserInputBase() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(173, 216, 230));
        add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(173, 216, 230));
    }


    private static GridBagConstraints getGrid() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        return gbc;
    }

    private static JPanel getFormPanel()
    {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(173, 216, 230));

        return formPanel;
    }

}
