import com.mysql.cj.xdevapi.JsonParser;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mainScreen extends JFrame{
    private JButton menuButton;
    private JButton findCabsButton;
    private JRadioButton SUVRadioButton;
    private JRadioButton sedanRadioButton;
    private JRadioButton microRadioButton;
    private JButton viewOngoingRideButton;
    private JFormattedTextField searchDestinationFormattedTextField;
    private JPanel myPanel;
    private JFormattedTextField pickupFormattedTextField;
    public int bookingId;

    public boolean OngoingRideAllocated=false;


    public mainScreen(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(myPanel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        checkOngoingTrue();
        //frameInit();
        findCabsButton.addComponentListener(new ComponentAdapter() {
        });
        findCabsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                searchCabs();
            }
        });
        viewOngoingRideButton.addComponentListener(new ComponentAdapter() {
        });
        viewOngoingRideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
               isOngoingTrue();
            }
        });
        menuButton.addComponentListener(new ComponentAdapter() {
        });
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame  menu= new menu("menu");
                dispose();
            }
        });
    }
    public void getNewBookingId(){
            try{
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cab2","root","parveen");
                String sql = "select max(id) from booking";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs= pst.executeQuery();
                while(rs.next()){
                bookingId = rs.getInt(1) +1;
                System.out.println(bookingId);
            }
            }
            catch(Exception e){ System.out.println(e);}
    }

    public void searchCabs(){
        boolean suv =false;
        boolean micro =false;
        boolean sedan =false;
        if (SUVRadioButton.isSelected()){
            suv=true;
        }
        if (microRadioButton.isSelected()){
            micro=true;
        }
        if (sedanRadioButton.isSelected()){
            sedan=true;
        }
        getNewBookingId();
        String destinationStr = searchDestinationFormattedTextField.getText();
        String pickupStr = pickupFormattedTextField.getText();

        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cab2","root","parveen");
            String sql = "insert into booking(custId,pickupStreet,destinationCity,bookingTime,price,status)values(?,?,?,now(),?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,MainDriver.custId);
            pst.setString(2, pickupStr);
            pst.setString(3,destinationStr);
            //pst.setString(4,"2024-2-2 1:1:1");
            pst.setInt(4,(int)(Math.random()*(2500-100+1)+100));
            pst.setString(5,"waiting");
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

    public void isOngoingTrue(){
                try{
                    Connection con= DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/cab2","root","parveen");
                    String sql = "select max(id) from booking where custId=? and (status = ? or status=?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1,MainDriver.custId);
                    pst.setString(2, "waiting");
                    pst.setString(3, "ongoing");
                    ResultSet rs= pst.executeQuery();
                    if (rs.next()){
                        OngoingRideAllocated=true;
                        bookingId=rs.getInt(1);
                        JFrame ongoingRide = new OngoingRide("CurrentRide",bookingId);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(this,"No Ongoing ride");
                    }
                }
                catch(Exception e){ System.out.println(e);}
    }

    public boolean checkOngoingTrue(){
        try{
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cab2","root","parveen");
            String sql = "select max(id) from booking where custId=? and (status = ? or status=?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,MainDriver.custId);
            pst.setString(2, "waiting");
            pst.setString(3, "ongoing");
            ResultSet rs= pst.executeQuery();
            if (rs.next()){
                OngoingRideAllocated=true;
                bookingId=rs.getInt(1);
                System.out.println(bookingId);
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e);
        return false;}
    }


    public static void main(String[] args) {
        JFrame mainScreen = new mainScreen("mainscreen");
        mainScreen.setVisible(true);
        mainScreen.setResizable(false);

    }
}
