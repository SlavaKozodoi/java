import java.sql.*;

public class DB_HW {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "database";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDbConn() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr,LOGIN,PASS);
        return dbConn;
    }

    public void getJohn(String table)throws SQLException, ClassNotFoundException{
        String sql = "SELECT * FROM " + table + " WHERE `login` LIKE 'John'";
        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery(sql);
        while(res.next()){
            System.out.print(res.getString("id") + "   ");
            System.out.print(res.getString("login"));

        }
    }

        public void getItems(String table)throws SQLException, ClassNotFoundException{
            String sql = "SELECT * FROM " + table + " WHERE `category` LIKE 'hats'";
            Statement statement = getDbConn().createStatement();
            ResultSet res = statement.executeQuery(sql);
            while(res.next()){
                System.out.print("\n" + res.getString("id") + "\t");
                System.out.print(res.getString("title") + "\t");
                System.out.print(res.getString("price") + "\t");
                System.out.print(res.getString("category"));

            }
        }

    public void insertOrder(int user_id,int item_id) throws SQLException, ClassNotFoundException
    {
        String sql = "INSERT INTO `orders` (user_id,item_id) VALUES (?,?)";

        PreparedStatement prSt = getDbConn().prepareStatement(sql);
        prSt.setInt(1,user_id);
        prSt.setInt(2,item_id);
        prSt.executeUpdate();

    }

    public void getOrders()throws SQLException, ClassNotFoundException{
        String sql = "SELECT login,title FROM `orders` \n" +
                "JOIN `users` ON orders.user_id = users.id\n" +
                "JOIN `items` ON orders.item_id = items.id;";
        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery(sql);
        while(res.next()){
            System.out.print(res.getString("login") + " - ");
            System.out.println(res.getString("title"));


        }
    }

}
