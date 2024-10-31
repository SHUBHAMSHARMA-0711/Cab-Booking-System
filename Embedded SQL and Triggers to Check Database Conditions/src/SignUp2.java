import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;

public class SignUp2 extends JFrame {
    private JTextField username;
    private JPasswordField passPasswordField;
    private JButton createAccountButton;
    private JButton backButton;
    private JTextField email;
    private JTextField phone;
    private JTextField firstName;
    private JTextField location;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JLabel phoneNumberLabel;
    private JLabel firstNameLabel;
    private JLabel currentLocationLabel;
    private JPanel SignUpPanel;
    private JLabel middleNameLabel;
    private JTextField enterMiddleNameTextField;
    private JTextField enterLastNameTextField;
    private JTextField enterAlternatePhoneNumberTextField;
    private JTextField enterAgeTextField;

    public SignUp2(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(SignUpPanel);
        this.pack();
        this.setVisible(true);
        //frameInit();
        createAccountButton.addComponentListener(new ComponentAdapter() {
        });
        createAccountButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertSignupDeatils();
            }
        });
    }

    public void insertSignupDeatils(){
        try {
            String usernameStr = username.getText();
            char []passArr = passPasswordField.getPassword();
            StringBuilder passStr= new StringBuilder();
            for (char k :passArr){
                passStr.append(k);
            }
            String emailStr = email.getText();
            int phoneStr = Integer.parseInt(phone.getText());
            String firstNameStr = firstName.getText();
            String midNameStr = enterMiddleNameTextField.getText();
            String lastNameStr = enterLastNameTextField.getText();
            int altphoneStr = Integer.parseInt(enterAlternatePhoneNumberTextField.getText());
            int ageStr = Integer.parseInt(enterAgeTextField.getText());
            String locationStr = location.getText();
            System.out.println(usernameStr);
            System.out.println(passStr);
            System.out.println(emailStr);
            System.out.println(phoneStr);
            System.out.println(firstNameStr);

            try{
                //Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cab2","root","parveen");
                String sql = "insert into customer(firstName,midName,lastName,password,username,email,phoneNumber,altPhoneNumber,age)values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,firstNameStr);
                pst.setString(2,midNameStr);
                pst.setString(3,lastNameStr);
                pst.setString(4, String.valueOf(passStr));
                pst.setString(5,usernameStr);
                pst.setString(6,emailStr);
                pst.setInt(7,phoneStr);
                pst.setInt(8,altphoneStr);
                pst.setInt(9,ageStr);
                int count = pst.executeUpdate();
                if (count>0){
                    JOptionPane.showMessageDialog(this,"Success");
                    JFrame login = new CustomerLoginPage("login");
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this,"failed");
                }


//            Statement stmt=con.createStatement();
//            ResultSet rs=stmt.executeQuery("select * from trip");
//            while(rs.next())
//                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
//            con.close();
            }
            catch(Exception e){ System.out.println(e);}
        }
        catch (Exception e){
            System.out.println("One or more field is empty or Invalid");
            JOptionPane.showMessageDialog(this,"One or more field is empty or Invalid");
        }

    }

    public static void main(String[] args) {
        JFrame signup = new SignUp2("login");
        signup.setVisible(true);
        signup.setResizable(false);
    }


}
