import cn.geny.SEmp;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

public class TestOracleJDBC {
    private Connection conn = null;

    public Connection getConnection() {
        if (conn != null) {
            return conn;
        }
        String url = "jdbc:oracle:thin:@192.168.80.131:1521:helowin";
        String user = "hydraone";
        String password = "Passw0rd";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver loading is fail");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public void query(String sql) throws SQLException {
        PreparedStatement pstmt;
        try {
            pstmt = getConnection().prepareStatement(sql);
            //建立一个结果集，用来保存查询出来的结果
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String lastName = rs.getString(2);
                String firstName = rs.getString(3);
                String userid = rs.getString(4);
                Date startDate = rs.getDate(5);
                String comments = rs.getString(6);
                Integer managerId = rs.getInt(7);
                String title = rs.getString(8);
                Integer deptId = rs.getInt(9);
                BigDecimal salary = rs.getBigDecimal(10);
                BigDecimal commissionPct = rs.getBigDecimal(11);
                System.out.println(new SEmp(id, lastName, firstName, userid, startDate, comments, managerId, title, deptId, salary, commissionPct));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCase() throws SQLException {
        String sql = "select * from S_EMP order by start_date";
        query(sql);
    }
}