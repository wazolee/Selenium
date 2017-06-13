package TA.Selenium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import TA.Selenium.Worker.Service;

/**
 * Hello world!
 *
 */
public class App {
	private static String userName;
	private static String password;

	public static void main(String[] args) {
		System.out.println("Some test need your account!");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("username: ");
			userName = br.readLine();
			System.out.println("password: ");
			password = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		 Worker w = new Worker(Service.ChService);
		 w.get("http://dekhes7ltsta.ad001.siemens.net/portal");
//		Worker w = new Worker(Service.IEService);
		w.LoginWithLocalUser(userName, password);
//		WebDriver wd = w.getDriver();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w.quit();
		System.out.println("Hello World!");
	}
}
