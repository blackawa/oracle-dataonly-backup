package jp.blackawa;

import lombok.Data;

import java.util.List;

/**
 * テーブル情報を保有するクラス
 */
@Data
public class Table {
    public Table(String tableName) {
        this.tableName = tableName;
    }
    private String tableName;
    private List<String> columnNames;
}
