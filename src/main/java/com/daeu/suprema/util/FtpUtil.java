package com.daeu.suprema.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {
	
	private String ip;
	private int port;
	private String username;
	private String password;
	
	private FTPClient ftp;
	
	public FtpUtil(String ip, int port, String username, String password) {
		ftp = new FTPClient();
		ftp.setControlEncoding("euc-kr"); //한글 처리
		
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public void connect() throws Exception {
		int reply;
		ftp.connect(ip, port);
		System.out.println("FTP> Connecting to " + ip + ":" + port + "...");
		System.out.print("FTP> " + ftp.getReplyString());
		
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			System.err.println("FTP> Server refused connection.");
	        System.exit(1);
		} else {
			this.login();
			ftp.enterLocalPassiveMode();
		}
	}
	
	public boolean login() throws Exception {
		boolean login = false;
		
		login = ftp.login(username, password);
		if (login) System.out.println("FTP> Logged in.");
		else System.out.println("FTP> Not logged in.");
		
		return login;
	}
	
	public boolean logout() throws Exception {
		boolean logout = false;
		
		logout = ftp.logout();
		if (logout) System.out.println("FTP> Logged out.");
		else System.out.println("FTP> Not logged out.");
		
		return logout;
	}
	
	public FTPFile[] list(String path) throws Exception {
		FTPFile[] files = null;
		
		System.out.println("FTP> Retrieving directory listing of " + "\"" + path + "\"" + "...");
		files = this.ftp.listFiles(path);
		System.out.println("FTP> Directory listing of " + "\"" + path + "\"" + " successful");
		System.out.println("FTP> Directory list size " + files.length);
		
		return files;
	}
	
	public boolean downloadFile(String source, String target, String name) throws Exception {
		boolean flag = false;
		OutputStream outputStream = null;
		try {
			System.out.println("FTP> Starting download of " + source);
			File local = new File(target, name);
			File dir = new File(local.getParent());
			if (!dir.exists()) dir.mkdirs();
			outputStream = new FileOutputStream(local);
			
			flag = ftp.retrieveFile(source, outputStream);
			if (flag) System.out.println("FTP> File transfer successful of " + source);
			else System.out.println("FTP> File transfer fail of " + source);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (outputStream != null) outputStream.close();
		}
		return flag;
	}
	
//	public void cd(String path) {
//		try {
//			ftp.changeWorkingDirectory(path);
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
//	}
	
	public void disconnect() throws Exception {
		this.logout();
		ftp.disconnect();
		System.out.println("FTP> Disconnected from server");
	}
	
	public boolean isConnected() throws Exception {
		boolean flag = false;
		
		flag = ftp.isConnected();
		
		return flag;
	}
}
