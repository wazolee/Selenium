package TA.Selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class Logger {
	private File logFile;
	private FileWriter fw;
	private Formatter formatter;

	public FileWriter getFw() {
		return this.fw;
	}

	public void setFw(FileWriter fw) {
		try {
			this.fw = new FileWriter(logFile, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File getLogFile() {
		return logFile;
	}

	public Logger() {
		this(null);
	}

	public Logger(String logFileName) {
		if (!(logFile == (null))) {
			this.logFile = new File(logFileName);
		} else {
			this.logFile = new File(new SimpleDateFormat("yyyy_MM_dd_HH_mm'.log'").format(new Date()));
		}
		try {
			setFormatter(this.logFile);
		} catch (Exception e) {
			CreateEntry(e.getMessage());
			e.printStackTrace();
		} finally {
			this.formatter.close();
		}
	}

	public Formatter getFormatter() {
		return formatter;
	}

	private void setFormatter(File f) {
		try {
			this.formatter = new Formatter(new FileWriter(f, true));
		} catch (IOException e) {
			CreateEntry(e.getMessage());
			e.printStackTrace();
		}
	}

	public void CreateEntry(String entry) {
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (Exception e) {
				CreateEntry(e.getMessage());
				e.printStackTrace();
			}
		}
		this.AppendTextToLog(entry);
	}

	// private Exception AppendTextToLog(String s){
	// try{
	// this.fw = new FileWriter(this.logFile, true);
	// this.bw = new BufferedWriter(this.fw);
	// this.out = new PrintWriter(bw);
	// out.println(s);
	// out.close();
	// } catch (IOException e) {
	//// throw e;
	// return e;
	// }
	// return null;
	// }
	private void AppendTextToLog(String s) {
		this.formatter.format("%s\r\n", s);
		this.formatter.close();
	}

}