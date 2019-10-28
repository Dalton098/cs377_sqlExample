/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlexample;

import java.sql.*;

/**
 *
 * @author dyarosh
 */
public class MSSQLServerApplication {

    private static Connection conn;
    private static Statement stmt;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        establishConnectionToDatabase();
        stmt = conn.createStatement();
        String sql = "SELECT s.[StudentId], s.[StudentName], c.[Class], m.[Major] "
                + "FROM [Assignment4].[dbo].[Student] s  "
                + "JOIN [Assignment4].[dbo].[Class] c ON c.[ClassId] = s.[Class] "
                + "JOIN [Assignment4].[dbo].[Majors] m ON m.[MajorId] = s.[Major]";
        ResultSet rs = executeQueryStatement(sql);
        displayQueryResults(rs);
        stmt.close();
        conn.close();
    }

    private static void establishConnectionToDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String connectionUrl = "jdbc:sqlserver://localhost;integratedSecurity=true;authenticationScheme=JavaKerberos";
                String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Assignment4;integratedSecurity=false;user=sa;password=reallyStrongPwd123";
            conn = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static ResultSet executeQueryStatement(String s) throws SQLException {
        ResultSet rs;
        rs = stmt.executeQuery(s);
        return rs;
    }

    private static void displayQueryResults(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();

        int numOfCols = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i < numOfCols + 1; i++) {
                System.out.print((rsmd.getColumnLabel(i) + ": " + rs.getString(i)
                        + "   "));
            }
            System.out.println();
        }
    }
}
