import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class menu extends JFrame{
    private JButton myProfileButton;
    private JButton yourRidesButton;
    private JButton walletButton;
    private JButton paymentsButton;
    private JButton aboutButton;
    private JPanel menuPanel;
    private JPanel MyProfilePanel;
    private JTextField firstNameTextField;
    private JButton updateProfileButton;
    private JTextField lastNameTextField;
    private JTextField midNameTextField;
    private JTextField phoneTextField;
    private JTextField emailTextField;
    private JTextField altPhoneTextField;
    private JTextField ageTextField;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel middleNameLabel;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JLabel alternatePhoneNumberLabel;
    private JLabel ageLabel;
    private JButton backToMenuButton;
    private JButton homecreenButton;
    private JLabel passwordLabel;
    private JTextField enterNewPasswordTextField;

    public menu(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(menuPanel);
        MyProfilePanel.setVisible(false);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        //frameInit();
        myProfileButton.addComponentListener(new ComponentAdapter() {
        });
        myProfileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MyProfilePanel.setVisible(true);
            }
        });
        backToMenuButton.addComponentListener(new ComponentAdapter() {
        });
        backToMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MyProfilePanel.setVisible(false);
            }
        });
        yourRidesButton.addComponentListener(new ComponentAdapter() {
        });
        yourRidesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame  userRideHistoryPage= new UserRideHistoryPage("menu");
                dispose();
            }
        });

        updateProfileButton.addComponentListener(new ComponentAdapter() {
        });
        updateProfileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateMyProfile();
            }
        });
        homecreenButton.addComponentListener(new ComponentAdapter() {
        });
        homecreenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame  mainScreen= new mainScreen("mainscreen");
                dispose();
            }
        });
    }

    public void updateMyProfile(){
        try {
            String firstnameStr = firstNameTextField.getText();
            String MnameStr = midNameTextField.getText();
            String LnameStr = lastNameTextField.getText();

            String emailStr = emailTextField.getText();
            String passwordStr = enterNewPasswordTextField.getText();
            int phoneStr = Integer.parseInt(phoneTextField.getText());
            int AltphoneStr = Integer.parseInt(altPhoneTextField.getText());
            int ageStr = Integer.parseInt(ageTextField.getText());

            try{
                //Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cab2","root","parveen");
                String sql = "update customer set firstName=?,midName=?,lastName=?,age=?,email=?,phoneNumber=?,altPhoneNumber =?,password=? where id=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,firstnameStr);
                pst.setString(2, MnameStr);
                pst.setString(3,LnameStr);
                pst.setInt(4,ageStr);
                pst.setString(5,emailStr);
                pst.setInt(6,phoneStr);
                pst.setInt(7,AltphoneStr);
                pst.setInt(9,MainDriver.custId);
                pst.setString(8,passwordStr);
                System.out.println(pst);
                int count = pst.executeUpdate();

                if (count>0){
                    JOptionPane.showMessageDialog(this,"Success");
                }
                else {
                    JOptionPane.showMessageDialog(this,"failed");
                }

            }
            catch(Exception e){ System.out.println(e);}
        }
        catch (Exception e){
            System.out.println("One or more field is empty or Invalid");
            JOptionPane.showMessageDialog(this,"One or more field is empty or Invalid");
        }

    }

    public static void main(String[] args) {
        JFrame menu = new menu("menu");
        menu.setVisible(true);
        menu.setResizable(false);
    }
}
