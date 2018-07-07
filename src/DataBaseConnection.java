
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DataBaseConnection 
{
    public static Connection con;
    public static Statement stmt;
    
    public static Connection open()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_bank","root","");
            
        }
        catch (Exception ex)
        {
            System.out.println("Connection not created");
            ex.printStackTrace();
        }
        return con;
    }
    
    public static void close(Connection con)
    {
        try
        {
            if(con!=null)
            {
                con.close();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Connection not closed");
            ex.printStackTrace();
        }
    }
    
}
