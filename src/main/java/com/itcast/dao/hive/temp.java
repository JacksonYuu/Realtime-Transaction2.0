package com.itcast.dao.hive;

import java.util.List;

public class temp {

    public static void main(String[] args) throws Exception {

        HiveService jdbc = new HiveService();

        List<String> databases = jdbc.showDatabases();

        for (String database : databases) {

            System.out.println(database);
        }

        jdbc.createTable();

        List<String> tables = jdbc.showTables();

        for (String table : tables) {

            System.out.println(table);
        }

        jdbc.close();
    }
}
