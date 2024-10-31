import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChooseDriver extends JFrame{
    private JButton mainScreenButton;
    private JButton confirmRideButton;
    private JButton findOtherButton;
    private JEditorPane driverCarDetailsEditorPane;
    private JPanel myPanel;

    public ChooseDriver(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(myPanel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        //frameInit();
        findOtherButton.addComponentListener(new ComponentAdapter() {
        });
        findOtherButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                //JFrame  chooseDriver= new ChooseDriver("menu");
//                dispose();
            }
        });
        confirmRideButton.addComponentListener(new ComponentAdapter() {
        });
        confirmRideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                //JFrame  chooseDriver= new ChooseDriver("menu");
//                dispose();
            }
        });
        mainScreenButton.addComponentListener(new ComponentAdapter() {
        });
        mainScreenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame mainScreen = new mainScreen("menu");
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame chooseDriver = new ChooseDriver("menu");
        chooseDriver.setVisible(true);
        chooseDriver.setResizable(false);
    }

}
