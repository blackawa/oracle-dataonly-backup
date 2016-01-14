package jp.blackawa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "TEST", "TEST");

        // user_tab_columnsから自分が所有するテーブルのリストを取得する。
        List<Table> tables = readTableList(conn);

        // 各テーブルのカラムリストを取得する。
        for (Table table : tables) {
            readColumnOf(conn, table);
        }

        // 全データをcsvファイルに吐き出す。

        // テーブル名、カラム一覧の情報からctlファイルを生成する。

        conn.close();
    }

    /**
     * 自身が所有するテーブル名のリストを取得する。
     * @param conn データベースへの接続
     * @return 自身が所有するテーブル名のリスト
     * @throws SQLException データベースアクセス時の例外
     */
    private static List<Table> readTableList(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet tables = stmt.executeQuery("select table_name from user_tab_columns group by table_name");
        List<Table> tablesList = new ArrayList<>();
        while (tables.next()) {
            tablesList.add(new Table("TABLE_NAME"));
        }
        tables.close();
        stmt.close();
        return tablesList;
    }

    /**
     * 与えられたテーブルが持つカラムのリストを取得する。
     * @param conn データベースへの接続
     * @param table テーブル
     * @throws SQLException データベースアクセス時の例外
     */
    private static void readColumnOf(Connection conn, Table table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet columns = stmt.executeQuery("select column_name from user_tab_columns where table_name = " + table.getTableName());
        // TODO Add columns to Table class
    }
}
