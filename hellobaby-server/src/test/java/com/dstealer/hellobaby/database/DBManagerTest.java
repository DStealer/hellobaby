package com.dstealer.hellobaby.database;

import org.h2.tools.Console;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class DBManagerTest {
    public static void main(String[] args) throws Exception {
        Path dbPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource(".").toURI()).resolveSibling("../database");
        Console.main("-ifExists", "-webAllowOthers", "-baseDir", dbPath.toFile().getPath());
    }
}