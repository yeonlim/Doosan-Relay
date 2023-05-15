package com.daeu.fts.util;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import com.daeu.fts.socket.server.FileMessageServer;
import com.daeu.fts.socket.server.FileMessageServerHandler;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class FtpUtil {
	private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	static FtpUtil ftpUtil;
	private String ip;
	private int port;
	private String username;
	private String password;
	private String path;
	
	private FTPClient ftp;

	static public FtpUtil getInstance(Environment env) throws Exception {
		if (ftpUtil == null) {
			ftpUtil = new FtpUtil(env);
		}

		return ftpUtil;
	}

	private FtpUtil(Environment env) throws Exception {
		ftp = new FTPClient();
		ftp.setControlEncoding("euc-kr"); // 한글 처리
		ftp.setFileType(FTP.BINARY_FILE_TYPE);

		this.ip       = env.getProperty("NAS.FTP.IP"  , String.class);
		this.port     = env.getProperty("NAS.FTP.PORT", Integer.class);
		this.username = env.getProperty("NAS.FTP.ID"  , String.class);
		this.password = env.getProperty("NAS.FTP.PW"  , String.class);
		this.path     = env.getProperty("NAS.FTP.PATH", String.class);

		this.connect();
	}

	public FtpUtil(String ip, int port, String username, String password, String path) throws Exception {
		ftp = new FTPClient();
		ftp.setControlEncoding("euc-kr"); // 한글 처리
		ftp.setFileType(FTP.BINARY_FILE_TYPE);

		this.ip       = ip;
		this.port     = port;
		this.username = username;
		this.password = password;
		this.path     = path;

		this.connect();
	}

	public void connect() throws Exception {
		if(ftp.isConnected()) return;
		ftp.connect(ip, port);

		logger.info("FTP> Connecting to {}:{} ...", ip, port);
		logger.info("FTP> {}", ftp.getReplyString());

		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			logger.info("FTP> Server refused connection.");
	        System.exit(1);
		} else {
			this.login();
			ftp.enterLocalPassiveMode();
			this.cd(path);
		}
	}
	
	public boolean login() throws Exception {
		boolean login = ftp.login(username, password);
		logger.info(login ? "FTP> Logged in." : "FTP> Not logged in.");

		return login;
	}
	
	public boolean logout() throws Exception {
		boolean logout = ftp.logout();
		logger.info(logout ? "FTP> Logged out." : "FTP> Not logged out.");

		return logout;
	}

	public boolean fileExists(String fileName) throws Exception {
		return this.getFileNameList(null).contains(fileName);
	}

	public Set<String> getFileNameList(String path) throws Exception {
		Set<String> fileName = new HashSet<>();
		FTPFile[] files = this.getFileList(path);
		for(FTPFile file : files) {
			fileName.add(file.getName());
		}

		return fileName;
	}
	
	public FTPFile[] getFileList(String path) throws Exception {
		logger.info("FTP> Retrieving directory listing of \"{}\" ...", path);
		FTPFile[] files = path == null ? this.ftp.listFiles() : this.ftp.listFiles(path);
		logger.info("FTP> Directory listing of \"{}\" successful", path);
		logger.info("FTP> Directory list size {}", files.length);
		
		return files;
	}
	
	public boolean downloadFile(String source, String target, String name) throws Exception {
		boolean flag = false;
		OutputStream outputStream = null;

		try {
			logger.info("FTP> Starting download of {}", source);

			File local = new File(target, name);
			File dir = new File(local.getParent());
			if (!dir.exists()) dir.mkdirs();

			outputStream = new FileOutputStream(local);
			
			flag = ftp.retrieveFile(source, outputStream);
			if(flag) logger.info("FTP> File transfer successful of {}", source);
			else     logger.info("FTP> File transfer fail of {}", source);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (outputStream != null) outputStream.close();
		}
		return flag;
	}
	
	public void cd(String path) {
		try {
			logger.info("{} changeWorkingDirectory : {}", ftp.changeWorkingDirectory(path), path);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void disconnect() throws Exception {
		this.logout();
		ftp.disconnect();
		logger.info("FTP> Disconnected from server");
	}
	
	public boolean isConnected() throws Exception {
		return ftp.isConnected();
	}
}
