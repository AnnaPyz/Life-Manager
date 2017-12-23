package app.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnector {

	Connection conn;

	public DBConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Nie zarejestrowano sterownika!");
		}
	}

	public Connection connInit() {
		String url = "jdbc:mysql://localhost:3306/lifemanager";
		String user = "root";
		File f = new File("pass.txt");
		String pass="";
		try {
			Scanner sc = new Scanner(f);
			pass = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e1) {
			System.out.println("B³êdny odczyt z pliku password!");
		}

		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("Nie mogê po³¹czyæ siê z baz¹ danych");
		}
		return conn;
	}

}
