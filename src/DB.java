import java.sql.*;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "java_db";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDbConn() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr,LOGIN,PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConn();
        System.out.println(dbConn.isValid(2000));
    }

    public void createTable(String tableName) throws SQLException, ClassNotFoundException
    {
        String sql = ("CREATE TABLE IF NOT EXISTS " + tableName
                + " (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), password VARCHAR(100))"
                + " ENGINE = MYISAM");

        Statement statement = getDbConn().createStatement();
        statement.executeUpdate(sql);
    }

    public void insertArticle(String title,String text,String date,String autor) throws SQLException, ClassNotFoundException
    {
        String sql = "INSERT INTO `articles` (title,text,date,autor) VALUES (?,?,?,?)";

        PreparedStatement prSt = getDbConn().prepareStatement(sql);
        prSt.setString(1,title);
        prSt.setString(2,text);
        prSt.setString(3,date);
        prSt.setString(4,autor);

        prSt.executeUpdate();

    }

    public void getArticles(String table)throws SQLException, ClassNotFoundException{
        String sql = "SELECT * FROM " + table + " WHERE `title` LIKE '%Post%' OR `id` = 4 ORDER BY `id` ASC LIMIT 2 OFFSET 1";
        Statement statement = getDbConn().createStatement();
        ResultSet res = statement.executeQuery(sql);
        while(res.next()){
            System.out.print(res.getString("id") + "   ");
            System.out.println(res.getString("title"));

        }
    }

    public void updateArticles()throws SQLException, ClassNotFoundException{
        String sql = "UPDATE `articles` SET `title` = ? WHERE `id` = 2";
        PreparedStatement prSt = getDbConn().prepareStatement(sql);
        prSt.setString(1,"Новая обновлёная статья");

        prSt.executeUpdate();
    }

    public void deleteArticles()throws SQLException, ClassNotFoundException
    {
        String sql = "DELETE FROM `articles` WHERE `id` = 4";
        Statement statement = getDbConn().createStatement();
        statement.executeUpdate(sql);
    }

}
