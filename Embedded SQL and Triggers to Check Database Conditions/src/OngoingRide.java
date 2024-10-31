import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OngoingRide extends JFrame {
    private JButton endRideButton;
    private JButton makePaymentFromWalletButton;
    private JButton reportButton;
    private JButton mainScreenButton;
    private JPanel endingPanel;
    private JTextArea reportAnyIssueTextArea;
    private JSlider slider1;
    private JLabel rateYourRideLabel;
    private JPanel myPanel;
    private JLabel rideDetailsLabel;
    private JLabel statusMessageLabel;
    private JRadioButton onlineRadioButton;
    private JRadioButton cashRadioButton;
    private JTextField couponCodeTextField;
    private JEditorPane editorPane1;

    private int tripId;
    private int bookingId=0;
    private int paymentId;
    private int amount;
    public boolean tripStarted=false;
    public boolean tripEnded=false;

    public OngoingRide(String title,int bookingId){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(myPanel);
        endingPanel.setVisible(false);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.bookingId=bookingId;
        findTripId();
        showRideDetails();
        //frameInit();
        endRideButton.addComponentListener(new ComponentAdapter() {
        });
        endRideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                endingPanel.setVisible(true);
                endRide();
            }
        });
        makePaymentFromWalletButton.addComponentListener(new ComponentAdapter() {
        });
        makePaymentFromWalletButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                makingPayment();
            }
        });
        mainScreenButton.addComponentListener(new ComponentAdapter() {
        });
        mainScreenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame  mainScreen= new mainScreen("menu");
                dispose();
            }
        });
        reportButton.addComponentListener(new ComponentAdapter() {
        });
        reportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                makeReport();
            }
        });
    }

    public void findTripId(){

            try{
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cab2","root","parveen");
                String sql = "select id from trip where bookingId=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1,bookingId);
                System.out.println(pst);
                ResultSet rs= pst.executeQuery();
                if (rs.next()){
                    tripStarted=true;
                    tripId=rs.getInt(1);
                    System.out.println("trip id "+tripId);
                }
            }
            catch(Exception e){ System.out.println(e);}

    }

    public void showRideDetails(){
        // add trip details
            try{
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cab2","root","parveen");
                String sql = "select * from booking where id= "+bookingId;
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs= pst.executeQuery();
                String all="";
                while(rs.next()){
                    String row = "Booking Id: "+rs.getInt(1) +"\n"+ "Driver Id: "+rs.getInt(3)+"  "+
                            "Car RC No. : "+rs.getInt(4) +"\n"+
                            "Pickup Location: "+rs.getString(5) +"\n"+
                            "Destination Location: "+rs.getString(6) +"\n"+
                            "Booking Time: "+rs.getString(7) +"\n"+
                            "Trip Fare: "+rs.getString(8) +"  "+
                            "Status: "+rs.getString(9) +"  "
                            ;
                    System.out.println(row);
                    all = all.concat(row+"\n");
                }
                editorPane1.setText(all);
            }
            catch(Exception e){ System.out.println(e);}
    }

    public void makeReport(){
        String reportStr = reportAnyIssueTextArea.getText();
        int rating = slider1.getValue();
        System.out.println(reportStr);
        System.out.println(rating);
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cab2","root","parveen");
            String sql = "update trip set tripRating =? where id =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(2,tripId);
            pst.setInt(1,rating);
            System.out.println(pst);
            pst.executeUpdate();
            // new
            sql = "insert into report(bookingId,issue) values(?,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1,bookingId);
            pst.setString(2,reportStr);
            int count = pst.executeUpdate();
            if (count>0){
                JOptionPane.showMessageDialog(this,"Success");
                JFrame mainScreen = new mainScreen("mainscreen");
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this,"failed");
            }

        }
        catch(Exception e){ System.out.println(e);}
    }
    public void endRide(){
        if (tripEnded==true){
            JOptionPane.showMessageDialog(this,"Already Ended");
            return;
        }
        tripEnded=true;
        try{
            getNewPaymentId();
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cab2","root","parveen");
            String sql = "update trip set paymentId =? , status=? where id =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(3,tripId);
            pst.setInt(1,paymentId);
            pst.setString(2,"success");
            pst.executeUpdate();
            // new
            sql = "update booking set status=? where id =? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1,bookingId);
            pst.setString(2,"success");
            int count = pst.executeUpdate();
            if (count>0){
                JOptionPane.showMessageDialog(this,"Success trip Ended");
                JFrame mainScreen = new mainScreen("mainscreen");
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this,"failed");
            }

        }
        catch(Exception e){ System.out.println(e);}


    }


    public void makingPayment() {
        boolean cash =false;
        boolean online = false;
        String mode="";
        if (tripStarted==false || tripEnded==false){
            JOptionPane.showMessageDialog(this,"Trip not yet Started! make payment after ending trip");
            return;
        }
        if (cashRadioButton.isSelected() && onlineRadioButton.isSelected()){
            JOptionPane.showMessageDialog(this,"Select any one mode");
            return;
        }
        if (cashRadioButton.isSelected()){
            cash=true;
            mode="cash";
        }
        else if (onlineRadioButton.isSelected()){
            online=true;
            mode="online";
        }
        int price = calcPrice();
        getNewPaymentId();

        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cab2","root","parveen");
            String sql = "insert into payment(id,type,status,amount)values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,paymentId);
            pst.setString(2, mode);
            pst.setString(3,"Success");
            pst.setInt(4,price);
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

    public int calcPrice(){
        int discount=0;
        try{
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cab2","root","parveen");

            String cid = couponCodeTextField.getText();
            String sql = "select discount from coupon where id ="+cid;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs= pst.executeQuery();
            while(rs.next()){
                discount = rs.getInt(1);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return amount - (discount*amount);
    }


    public void getNewPaymentId(){
        try{
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cab2","root","parveen");
            String sql = "select max(id) from payment";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs= pst.executeQuery();
            while(rs.next()){
                paymentId = rs.getInt(1) +1;
                System.out.println(paymentId);
            }
        }
        catch(Exception e){ System.out.println(e);}
    }

    public static void main(String[] args) {
        JFrame ongoingRide = new OngoingRide("menu",0);
        ongoingRide.setVisible(true);
        ongoingRide.setResizable(false);
    }
}
