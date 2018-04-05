import java.sql.*;

public class TestJDBC {
    static final String USER = "pkaster";
    static final String PASS = "asdfqwer2$!";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/userdb?useSSL=false&serverTimezone=UTC";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.println("Creating statement...");
            Statement stmt = conn.createStatement();
            String sql;
            sql = "select * from user";
            ResultSet rs = stmt.executeQuery(sql);

            rs.first();
            do{
                //Retrieve by column name
                for(int i = 1 ; i <= 4 ; i ++){
                    System.out.println(String.valueOf(rs.getObject(i)));
                }
            }while (rs.next());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
