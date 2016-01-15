package jp.blackawa;

import java.sql.*;
import java.util.*;

public class App {
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "TEST", "TEST");

        // USER_TAB_COLUMNSの情報を取得してコレクションに変換する
        Map<String, List<String>> tableList = loadUserTabColumns(conn);

        ///////////////////// Output for Testing ///////////////////////////
        for (String key : tableList.keySet()) {
            System.out.println(key + ": " + tableList.get(key).toString());
        }
        ////////////////////////////////////////////////////////////////////

        // 全データをcsvファイルに吐き出す。

        // テーブル名、カラム一覧の情報からctlファイルを生成する。

        conn.close();
    }

    /**
     * テーブル名とカラム名の一覧を取得し、それをリストとして返却する。
     * @param conn データベース接続
     * @return テーブル名をキー、そのテーブルが所有するカラム一覧を値に持つマップ
     * @throws SQLException データベースアクセス時に発生しうる例外
     */
    private static Map<String, List<String>> loadUserTabColumns(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME, COLUMN_NAME FROM USER_TAB_COLUMNS ORDER BY TABLE_NAME, COLUMN_NAME");
        Map<String, List<String>> userTabColumnsMap = new HashMap<>();
        // テーブルが持つカラムを全てまとめる
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String columnName = rs.getString("COLUMN_NAME");
            List<String> columns = userTabColumnsMap.get(tableName);
            if (columns == null) {
                userTabColumnsMap.put(tableName, new ArrayList<>(Arrays.asList(columnName)));
            } else {
                columns.add(columnName);
            }
        }
        return userTabColumnsMap;
    }
}
