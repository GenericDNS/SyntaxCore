package com.syntax.lib.mysql;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Adapter {

    private String table;
    private String hostName;
    private String port;
    private String username;
    private String password;
    private String database;
    private Connection connection;

    public Adapter(String hostName, String port, String username, String password, String database) {
        this.hostName = hostName;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        connectDatabase();
    }

    public void connectDatabase() {
        if (!this.isConnected()) {
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostName + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                Bukkit.getConsoleSender().sendMessage("Die Verbindung zur Datenbank wurde hergestellt.");
            } catch (SQLException var2) {
                var2.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("Die Verbindung zur Datenbank konnte nicht hergestellt werden.");
            }
        }

    }

    public void createTable(String tableName, String... entries) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS `" + tableName + "` (`");
        int counter = 0;
        String[] var5 = entries;
        int var6 = entries.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String string = var5[var7];
            if (counter + 1 >= entries.length) {
                stringBuilder.append(string + "` text)");
                break;
            }

            stringBuilder.append(string + "` text, `");
            ++counter;
        }

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(stringBuilder.toString());
            preparedStatement.execute();
        } catch (SQLException var9) {
            var9.printStackTrace();
        }

    }

    public void removeEverythingFromTable(String specificTable, String column, String value) {
        String query = "DELETE FROM " + specificTable + " WHERE " + column + "='" + value + "'";

        try {
            PreparedStatement preparedStmt = this.connection.prepareStatement(query);
            preparedStmt.execute();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }

    public ResultSet getResult(String query) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public String getAllFromTable(String specificTable, String column) {
        String query = "SELECT * FROM " + specificTable;
        ResultSet resultSet = this.getResult(query);
        CopyOnWriteArrayList list = Lists.newCopyOnWriteArrayList();

        try {
            while(resultSet.next()) {
                list.add(resultSet.getString(column));
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        return !list.isEmpty() ? (String)list.get(0) : null;
    }

    public List<String> getListFromTable(String specificTable, String column) {
        String query = "SELECT * FROM " + specificTable;
        ResultSet resultSet = this.getResult(query);
        CopyOnWriteArrayList list = Lists.newCopyOnWriteArrayList();

        try {
            while(resultSet.next()) {
                list.add(resultSet.getString(column));
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        return !list.isEmpty() ? list : null;
    }

    public String getEntryFromTable(String specificTable, String column, String value, String neededColumn) {
        String query = "SELECT * FROM " + specificTable + " WHERE " + column + "='" + value + "'";
        ResultSet resultSet = this.getResult(query);
        CopyOnWriteArrayList list = Lists.newCopyOnWriteArrayList();

        try {
            while(resultSet.next()) {
                list.add(resultSet.getString(neededColumn));
            }
        } catch (SQLException var9) {
            return "";
        }

        return list.isEmpty() ? "" : (String)list.get(0);
    }

    public ResultSet getResultsAllFromTable(String specificTable, String column, String value) {
        String query = "SELECT * FROM " + specificTable + " WHERE " + column + "='" + value + "'";
        ResultSet resultSet = this.getResult(query);
        return resultSet;
    }

    public ResultSet getAllFromTable(String specificTable) {
        String query = "SELECT * FROM " + specificTable;
        ResultSet resultSet = this.getResult(query);
        return resultSet;
    }

    public void removeFromTable(String specificTable, String column, String key) {
        String query = "DELETE FROM " + specificTable + " WHERE " + column + "='" + key + "'";

        try {
            PreparedStatement preparedStmt = this.connection.prepareStatement(query);
            preparedStmt.execute();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }

    public void removeMoreFromTable(String specificTable, List<String> keys, List<String> values) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("DELETE FROM " + specificTable + " WHERE ");

        for(int i = 0; i < keys.size(); ++i) {
            queryBuilder.append((String)keys.get(i) + "='" + (String)values.get(i) + "'" + (i + 1 >= keys.size() ? "" : " AND "));
        }

        try {
            PreparedStatement preparedStmt = this.connection.prepareStatement(queryBuilder.toString());
            preparedStmt.execute();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }

    public boolean existsInTable(String specificTable, String key, String value) {
        ResultSet rs = this.getResult("SELECT * FROM " + specificTable + " WHERE " + key + "='" + value + "'");

        try {
            return rs.next();
        } catch (SQLException var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public boolean existsMoreInTable(String specificTable, String key, String value, String k2, String v2) {
        ResultSet rs = this.getResult("SELECT * FROM " + specificTable + " WHERE " + key + "='" + value + "' AND " + k2 + "='" + v2 + "'");

        try {
            return rs.next();
        } catch (SQLException var8) {
            var8.printStackTrace();
            return false;
        }
    }

    public void updateAllInTable(String specificTable, String setRow, String setValue) {
        String query = "UPDATE " + specificTable + " SET " + setRow + "='" + setValue + "'";

        try {
            PreparedStatement preparedStmt = this.connection.prepareStatement(query);
            preparedStmt.execute();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }

    public void updateInTable(String specificTable, String setRow, String setValue, String keyRow, String keyValue) {
        String query = "UPDATE " + specificTable + " SET " + setRow + "='" + setValue + "' WHERE " + keyRow + "='" + keyValue + "'";

        try {
            PreparedStatement preparedStmt = this.connection.prepareStatement(query);
            preparedStmt.execute();
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

    }

    public void addMoreInTable(String specificTable, List<String> keys, List<String> values) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO " + specificTable + "(");

        int i;
        for(i = 0; i < keys.size(); ++i) {
            if (keys.size() - 1 != i) {
                queryBuilder.append((String)keys.get(i) + ", ");
            } else {
                queryBuilder.append((String)keys.get(i) + ")");
            }
        }

        queryBuilder.append(" VALUES ('");

        for(i = 0; i < values.size(); ++i) {
            if (values.size() - 1 != i) {
                queryBuilder.append((String)values.get(i) + "', '");
            } else {
                queryBuilder.append((String)values.get(i) + "')");
            }
        }

        try {
            PreparedStatement preparedStmt = this.connection.prepareStatement(queryBuilder.toString());
            preparedStmt.execute();
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

    }

    public Object get(String whereresult, String where, String select, String database) {
        ResultSet rs = this.getResult("SELECT " + select + " FROM " + database + " WHERE " + where + "='" + whereresult + "'");
        Object o = null;

        try {
            while(rs.next()) {
                o = rs.getObject(select);
            }

            rs.close();
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

        return o;
    }

    public boolean isConnected() {
        return this.connection != null;
    }

}
