import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

public class CustomerLoginPage extends JFrame {
    private JButton loginButton;
    private JButton signUpButton;
    private JTextField usernameTextField;
    private JLabel UsernameLabel;
    private JLabel PasswordLabel;
    private JPanel customerLoginPanel;
    private JPasswordField passwordField1;


    public CustomerLoginPage(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(customerLoginPanel);
        this.pack();
        this.setVisible(true);
        //frameInit();
        loginButton.addComponentListener(new ComponentAdapter() {
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                login();
            }
        });
        signUpButton.addComponentListener(new ComponentAdapter() {
        });
        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame signUp2 = new SignUp2("login");
                dispose();
            }
        });
    }

    public void login(){
        try {
            String usernameStr = usernameTextField.getText();
            char []passArr = passwordField1.getPassword();
            StringBuilder passStr= new StringBuilder();
            for (char k :passArr){
                passStr.append(k);
            }
            System.out.println(usernameStr);
            System.out.println(passStr);

            try{
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cab2","root","parveen");
                String sql = "select id from customer where username = ? and password =?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,usernameStr);
                pst.setString(2, String.valueOf(passStr));
                ResultSet rs= pst.executeQuery();
                if (rs.next()){
                    JOptionPane.showMessageDialog(this," Login Success");
                    JFrame mainScreen = new mainScreen("HomeScreen");
                    MainDriver.custId=rs.getInt(1);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this,"Login failed");
                }
            }
            catch(Exception e){ System.out.println(e);}
        }
        catch (Exception e){
            System.out.println("One or more filed is empty");
            JOptionPane.showMessageDialog(this,"One or more filed is empty");
        }
    }

    public static void main(String[] args) {
        JFrame cust = new CustomerLoginPage("login");
        cust.setVisible(true);
        cust.setResizable(false);
    }

}
