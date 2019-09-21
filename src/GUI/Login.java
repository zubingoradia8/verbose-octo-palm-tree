package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

    private JFrame frame;
    private JPanel pane;
    private JTextField username;
    private JPasswordField password;
    private JButton  submit;
    private JLabel invalid;

    public Login() {
        frame = new JFrame("Login");
        frame.setSize(new Dimension(320, 150));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        pane = new JPanel();
        SpringLayout layout = new SpringLayout();
        pane.setLayout(layout);

        JLabel label1 = new JLabel("Username: ");
        JLabel label2 = new JLabel("Password: ");
        submit = new JButton("Login");
        username = new JTextField("Username");
        password = new JPasswordField("Password");
        invalid = new JLabel();

        username.setSize(new Dimension(150, 50));
        username.setColumns(15);
        password.setSize(new Dimension(150, 50));
        password.setColumns(15);

        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                String username = Login.this.username.getText();
                String password = Login.this.password.getText();

                if(validate(username, password)) {
                    frame.dispose();
                    new TradingWindow();
                } else {
                    invalid.setText("Invalid Username or Password.");
                }
            }
        });

        pane.add(label1);
        pane.add(username);
        pane.add(label2);
        pane.add(password);
        pane.add(submit);
        pane.add(invalid);

        /**
         *void putConstraint(String e1, Component c1, Spring s, String e2, Component c2)
         *Links edge e1 of component c1 to edge e2 of component c2.
         */

        layout.putConstraint(SpringLayout.WEST, label1, 6, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, label1, 6, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, username, 6, SpringLayout.EAST, label1);
        layout.putConstraint(SpringLayout.NORTH, username, 6, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.NORTH, label2, 12, SpringLayout.SOUTH, label1);
        layout.putConstraint(SpringLayout.WEST, label2, 6, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, password, 6, SpringLayout.SOUTH, username);
        layout.putConstraint(SpringLayout.WEST, password, 6, SpringLayout.EAST, label1);
        layout.putConstraint(SpringLayout.NORTH, submit, 24, SpringLayout.SOUTH, label2);
        layout.putConstraint(SpringLayout.WEST, submit, 6, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.WEST, invalid, 6, SpringLayout.EAST, submit);
        layout.putConstraint(SpringLayout.NORTH, invalid, 10, SpringLayout.SOUTH, password);

        frame.add(pane);
        frame.setVisible(true);
    }

    private boolean validate(String username, String password) {
        //TODO using JDBC
        return true;
    }

}
