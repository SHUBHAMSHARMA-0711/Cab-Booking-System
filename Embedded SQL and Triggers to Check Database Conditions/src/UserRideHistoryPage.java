import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRideHistoryPage extends JFrame{
    private JButton backToMenuButton;
    private JPanel historyPanel;
    private JEditorPane HIEditorPane;

    public UserRideHistoryPage(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(historyPanel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        generateView();
        //frameInit();
        backToMenuButton.addComponentListener(new ComponentAdapter() {
        });
        backToMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame menu = new menu("menu");
                dispose();
            }
        });
    }

    public void generateView(){
            try{
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cab2","root","parveen");
                String sql = "select * from booking where custId= "+MainDriver.custId+" order by id desc";
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
                    all = all.concat("\n");
                }
                HIEditorPane.setText(all);
            }
            catch(Exception e){ System.out.println(e);}
    }

    public static void main(String[] args) {
        JFrame  userRideHistoryPage= new UserRideHistoryPage("menu");
        userRideHistoryPage.setVisible(true);
        userRideHistoryPage.setResizable(false);
    }
}
