package com.daeu.suprema.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	public static Calendar stringToCalendar(String date) throws Exception {
		SimpleDateFormat strFmt = new SimpleDateFormat("yyyyMMdd");
		strFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		dateFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat datetimeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		if (date.length() == 8) {
			cal.setTime(strFmt.parse(date));
		}
		else if (date.length() == 10) {
			cal.setTime(dateFmt.parse(date));
		}
		else if (date.length() == 19) {
			cal.setTime(datetimeFmt.parse(date));
		}
		else {
			throw new Exception("날짜형식이 맞지 않습니다. VALUE:"+date);
		}
		
		return cal;
	}
	
	public static String datetimeToStr(Calendar date) throws Exception {
		SimpleDateFormat datetimeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return datetimeFmt.format(date.getTime());
	}
	
	public static String dateToStr(Calendar date) throws Exception {
		SimpleDateFormat datetimeFmt = new SimpleDateFormat("yyyyMMdd");
		
		return datetimeFmt.format(date.getTime());
	}
	
	public static Boolean ynToBoolean(String bool) throws Exception {
		Boolean value = null;
		
		if (bool.toUpperCase().equals("Y")) value = true;
		else value = false;
//		else if (bool.toUpperCase().equals("N")) value = false;
//		else throw new Exception("필드 값이 'Y', 'N' 형식이 아닙니다. VALUE:"+bool);
		
		return value;
	}
	
	/**
	 * 파일의 경로에서 파일이름을 추출합니다.
	 * @param path
	 * @return 
	 */
	public static String extractFileName(String path) {
		if (path == null) {
			return null;
		}
		int pos = path.lastIndexOf("/");
		if(pos == -1) {
			pos = path.lastIndexOf("\\");
		}
		
		if(pos == -1){
			return path;
		}
		return (pos != -1 ? path.substring(pos + 1, path.length()) : path);
	}
	
	public static String extractSuffix(String path) {
		if (path == null) {
			return null;
		}
		int pos = path.lastIndexOf(".");
		return (pos != -1 ? path.substring(pos + 1, path.length()) : path);
	}
	
	public static String extractSuffixWith(String path) {
		if (path == null) {
			return null;
		}
		int pos = path.lastIndexOf(".");
		return (pos != -1 ? path.substring(pos, path.length()) : path);
	}
	
	/**
	 * 파일의 경로에서 경로만을 추출합니다.
	 * @param path
	 * @return 
	 */
	public static String extractPath(String path) {
		int pos = path.lastIndexOf("/");
		if(pos == -1) {
			pos = path.lastIndexOf("\\");
		}
		
		if(pos == -1){
			return "";
		}
		
		String ret = path.substring(0, path.length() - CommonUtil.extractFileName(path).length() - 1);
		return (pos != -1 ? ret : path);
	}
	
	public static String convertSlash(String path ) {
		String osName = System.getProperty("os.name").toLowerCase();
		String result = "";
		if (osName.contains("windows")) {
			result = path.replaceAll("/", "\\\\");
		} else {
			result = path.replaceAll("\\\\", "/");
		}
		return result;
	}
	
	/*
	 * path의 separator를 변환한다.
	 * 
	 * path : 변환 할 path
	 * backSlash : 변환 후 separator (true=\, false=/)
	 */
	public static String convertSlash(String path, boolean backSlash) {
		String result = "";
		if(path == null || path.isEmpty()){
			return result;
		}else{
			if(backSlash){
				result = path.replace("\\", "/").replaceAll("[/]+", "/").replace("/", "\\");
			}else{
				result = path.replace("\\", "/").replaceAll("[/]+", "/");
			}
		}
		return result;
	}
	
	/*
	 * 양쪽 끝부분의 separator를 삭제한다.
	 * 
	 * path : 삭제 할 path
	 * type : 삭제 구분 (0=앞뒤 삭제, 1=앞부분 삭제, 2=끝부분 삭제)
	 * backSlash : 삭제 후 separator (true=\, false=/)
	 */
	public static String convertSubstrSlash(String path, int type, boolean backSlash) {
		String result = "";
		if(path == null || path.isEmpty()){
			return result;
		}else{
			result = CommonUtil.convertSlash(path, backSlash);
			if(result.length() > 1){
				if(type == 0){
					if(result.charAt(0) == '/' || result.charAt(0) == '\\'){
						result = result.substring(1, result.length());
					}
					if(result.charAt(result.length()-1) == '/' || result.charAt(result.length()-1) == '\\'){
						result = result.substring(0, result.length()-1);
					}
				}else if(type == 1){
					if(result.charAt(0) == '/' || result.charAt(0) == '\\'){
						result = result.substring(1, result.length());
					}
				}else if(type == 2){
					if(result.charAt(result.length()-1) == '/' || result.charAt(result.length()-1) == '\\'){
						result = result.substring(0, result.length()-1);
					}
				}
			}else if(result.charAt(0) == '/' || result.charAt(0) == '\\'){
				return "";
			}
		}
		return result;
	}
	public static String convertWorkspaceFileToAgentFile(String agentBasePath, String agentResourcePath, String agentResourceName ) {
		
		String convertAgentBasePath = CommonUtil.convertSlash(agentBasePath);
		int subLength = convertAgentBasePath.length() - 1;
		if (convertAgentBasePath.charAt(subLength) == '/'||  convertAgentBasePath.charAt(subLength) == '\\') {
			convertAgentBasePath = convertAgentBasePath.substring(0, subLength);
		}
		
		String fullPath = convertAgentBasePath + File.separator  + agentResourcePath + File.separator + agentResourceName;
		return fullPath.replace('\\', '/').replaceAll("[/]+", "/");
	}
	
	public static String replacePath(String basePath, String path, boolean backSlash){
		String result = "";
		if(path == null || path.isEmpty()){
			return result;
		}else{
			if(backSlash){
				result = path.replace("\\", "/").replaceAll("[/]+", "/").replace("/", "\\").replace(basePath.replace("\\", "/").replaceAll("[/]+", "/").replace("/", "\\"), "");
			}else{
				result = path.replace("\\", "/").replaceAll("[/]+", "/").replace(basePath.replace("\\", "/").replaceAll("[/]+", "/"), "");
			}
		}
		return result;
	}
	
	public static List<String> list(File source) {
		List<String> ret = new ArrayList<String>();
		
		File[] list = source.listFiles();
		for (File file : list) {
			if (file.isDirectory()) {
				ret.addAll(list(file));
			} else {
				ret.add(file.getAbsolutePath());
			}
		}
		
		return ret;
	}
	
	public static List<File> getFiles(File root, String suffix) {
		if (suffix == null || suffix.equals("")) {
			return new ArrayList<File>();
		}
		
		List<String> suffixes = new ArrayList<String>();
		String[] tempSuffixes = suffix.split(",");
		for (String s : tempSuffixes) {
			suffixes.add(s);
		}
		
		List<File> fileListWithSuffix = new ArrayList<File>();
		
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				fileListWithSuffix.addAll(getFiles(file, suffix));
			}
			
			if (file.isFile()) {
				if (suffixes.contains(extractSuffix(file.getAbsolutePath().toLowerCase()))) {
					fileListWithSuffix.add(file);
				}
			}
		}
		
		return fileListWithSuffix;
	}
	
	public static List<File> getSubDirecotyList(File root, int targetDepth, int currentDepth) {
		List<File> subDirectories = new ArrayList<File>();
		
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				if (targetDepth == currentDepth) {
					subDirectories.add(file);
				} else {
					getSubDirecotyList(file, targetDepth, currentDepth + 1);
				}
			}
		}
		
		return subDirectories;
	}
	
	public static String getFindSubDirectory(File root, String dirName) {
		String result = "";
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				if (file.getName().equals(dirName)) {
					result = file.getAbsolutePath();
					break;
				} else {
					result = getFindSubDirectory(file, dirName);
				}
			}
		}
		return result;
	}
	
	
	public static String getFindFile(File root, String fileExtFilter) {
		String result = "";
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				result = getFindFile(file, fileExtFilter);
			}else{
				if (file.getName().endsWith(fileExtFilter)) {
					result = file.getAbsolutePath();
					break;
				}
			}
		}
		return result;
	}
	
	public static List<File> getFindBuildFile(File root) {
		List<File> result = new ArrayList<File>();
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				result.addAll(getFindBuildFile(file));
			}else{
				String fileName = file.getName().toLowerCase();
				if (fileName.indexOf("build") >= 0 && fileName.endsWith(".xml")) {
					result.add(file);
				}
			}
		}
		return result;
	}
	
	public static void removeDirectory(File root, boolean removeRoot) {
		if (root == null || root.isFile()) {
			return;
		}
		
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				removeDirectory(file);
			}
			
			if (file.isFile()) {
				file.delete();
			}
		}
		
		if (removeRoot) {
			root.delete();
		}
	}
	
	public static void removeDirectory(File root) {
		if (root == null || root.isFile()) {
			return;
		}
		
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				removeDirectory(file);
			}
			
			if (file.isFile()) {
				file.delete();
			}
		}
		
		root.delete();
	}
	
	public static List<File> getAllFiles(File root) {
		return getAllFiles(root, new ArrayList<String>());
	}
	
	public static List<File> getAllFiles(File root, List<String> excludesDirs) {
		List<File> fileList = new ArrayList<File>();
		
		File[] files = root.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					if (!excludesDirs.contains(file.getName())) {
						fileList.addAll(getAllFiles(file));
					}
				}else{
					if (!excludesDirs.contains(file.getName())) {
						fileList.add(file);
					}
				}
			}
		}
		
		return fileList;
	}
	
	public static List<File> getAllFilesIncludeExtention(File root, String extension) {
		List<File> fileList = new ArrayList<File>();
		
		File[] files = root.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					fileList.addAll(getAllFilesIncludeExtention(file, extension));
				}else{
					if (file.getName().toLowerCase().endsWith(extension)) {
						fileList.add(file);
					}
				}
			}
		}
		
		return fileList;
	}
	
	public static List<File> getAllFileList(File root) {
		return getAllFileList(root, new ArrayList<String>());
	}
	
	public static List<File> getAllFileList(File root, List<String> excludesDirs) {
		List<File> fileList = new ArrayList<File>();
		
		File[] files = root.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					if (!excludesDirs.contains(file.getName())) {
						fileList.add(file);
						fileList.addAll(getAllFileList(file));
					}
				}else{
					if (!excludesDirs.contains(file.getName())) {
						fileList.add(file);
					}
				}
			}
		}
		
		return fileList;
	}
	
	public static void copyDirectory(File sourceDirectory, File destinationDirectory) {
		copyDirectory(sourceDirectory, destinationDirectory, new ArrayList<String>());
	}
	
	public static void copyDirectory(File sourceDirectory, File destinationDirectory, List<String> excludeDirs) {
		if (sourceDirectory.isFile()) {
			return;
		}
		
		String sourcePath = sourceDirectory.getAbsolutePath();
		List<File> files = getAllFiles(sourceDirectory, excludeDirs);
		for (File file : files) {
			String filePath = destinationDirectory.getAbsolutePath() + File.separator + file.getAbsolutePath().substring(sourcePath.length() + 1, file.getAbsolutePath().length());
			File target = new File(filePath);
			copy(file, target);
		}
	}
	
	public static void copyDirectoryFM(File sourceDirectory, File destinationDirectory) {
		copyDirectoryFM(sourceDirectory, destinationDirectory, new ArrayList<String>());
	}
	
	public static void copyDirectoryFM(File sourceDirectory, File destinationDirectory, List<String> excludeDirs) {
		if (sourceDirectory.isFile()) {
			return;
		}
		
		String sourcePath = sourceDirectory.getAbsolutePath();
		List<File> files = getAllFileList(sourceDirectory, excludeDirs);
		for (File file : files) {
			String filePath = destinationDirectory.getAbsolutePath() + File.separator + file.getAbsolutePath().substring(sourcePath.length() + 1, file.getAbsolutePath().length());
			File target = new File(filePath);
			if(file.isDirectory()){
				if(!target.exists()){
					target.mkdirs();
				}
			}
			copy(file, target);
		}
	}
	
	/**
	 * 파일을 복사합니다.
	 * @param source 소스 파일
	 * @param target 대상 파일
	 */
	public static void copy(File source, File target) {
//		logger.debug("source:[{}]", source.getAbsolutePath());
//		logger.debug("target:[{}]", target.getAbsolutePath());
		
		if (source.isDirectory()) {
			return;
		}
		
		target.getParentFile().mkdirs();
		
//		logger.debug("start to copy from [{}] to [{}].", source.getAbsolutePath(), target.getAbsolutePath());
		
		try {
			copyFileNIO(source, target);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			// Unix 시스템의 환경에 따라 NIO 버그가 존재하는것으로 보고됨.
			logger.warn("nio copy failed. try the bio copy.");
			try {
				copyFileBIO(source, target);
			} catch(IOException ioee) {
				ioee.printStackTrace();
			}
		}
		
//		logger.debug("ended copying from [{}] to [{}].", source.getAbsolutePath(), target.getAbsolutePath());
	}
	
	public static void copyFileNIO(File source, File target) throws IOException {
		FileInputStream fileIn = null;
		FileOutputStream fileOut = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		
		try {
			fileIn = new FileInputStream(source);
			inChannel = fileIn.getChannel();
			
			fileOut = new FileOutputStream(target);
			outChannel = fileOut.getChannel();
			
			long size = source.length();
			inChannel.transferTo(0, size, outChannel);
		} finally {
			if(outChannel != null) {
				try {
					outChannel.close();
				} catch(Exception e) {}
			}
			
			if(fileOut != null) {
				try {
					fileOut.close();
				} catch(Exception e) {}
			}
			
			if(inChannel != null) {
				try {
					inChannel.close();
				} catch(Exception e) {}
			}
			
			if(fileIn != null) {
				try {
					fileIn.close();
				} catch(Exception e) {}
			}
		}
	}
	
	private static void copyFileBIO(File source, File target) throws IOException {
		BufferedInputStream fileIn = null;
		BufferedOutputStream fileOut = null;
		
		try {
			byte[] buff = new byte[1024 * 8];
			int len = -1;
			
			fileIn = new BufferedInputStream(new FileInputStream(source));
			fileOut = new BufferedOutputStream(new FileOutputStream(target));
			
			while((len = fileIn.read(buff)) != -1) {
				fileOut.write(buff, 0, len);
				fileOut.flush();
			}
		} finally {
			if(fileOut != null) {
				try {
					fileOut.close();
				} catch(Exception e) {}
			}
			
			if(fileIn != null) {
				try {
					fileIn.close();
				} catch(Exception e) {}
			}
		}
	}
	
	public static List<File> getDirFileList(String dirPath) {
		// 디렉토리 파일 리스트
		List<File> dirFileList = null;
		 
		// 파일 목록을 요청한 디렉토리를 가지고 파일 객체를 생성함
		File dir = new File(dirPath);
		 
		// 디렉토리가 존재한다면
		if (dir.exists()) {
			// 파일 목록을 구함
			File[] files = dir.listFiles();
			
			// 파일 배열을 파일 리스트로 변화함
			dirFileList = Arrays.asList(files);
		}
		
		return dirFileList;
	}
	
	public static boolean isBanaryFile(String filename) {
		if (filename.endsWith(".class")) {
			return true;
		}else if (filename.endsWith(".o")) {
			return true;
		}else if (filename.endsWith(".pdb")) {
			return true;
		}else if (filename.endsWith(".pch")) {
			return true;
		}
		
		return false;
	}
	
	private static Set<String> supportedSuffixList = new HashSet<String>();
	static {
		supportedSuffixList.add("java");
		supportedSuffixList.add("class");
		supportedSuffixList.add("jsp");
		supportedSuffixList.add("asp");
		supportedSuffixList.add("php");
		supportedSuffixList.add("php3");
		supportedSuffixList.add("php4");
		supportedSuffixList.add("phtml");
		supportedSuffixList.add("inc");
		supportedSuffixList.add("htm");
		supportedSuffixList.add("html");
		supportedSuffixList.add("xhtml");
		supportedSuffixList.add("c");
		supportedSuffixList.add("cpp");
		supportedSuffixList.add("cc");
		supportedSuffixList.add("cs");
		supportedSuffixList.add("sln");
		supportedSuffixList.add("js");
		supportedSuffixList.add("xml");
		supportedSuffixList.add("h");
		supportedSuffixList.add("m");
		supportedSuffixList.add("vb");
		supportedSuffixList.add("aspx");
		supportedSuffixList.add("config");
		supportedSuffixList.add("master");
		supportedSuffixList.add("css");
		supportedSuffixList.add("erl");
		supportedSuffixList.add("groovy");
		supportedSuffixList.add("json");
		supportedSuffixList.add("py");
	}
	
	public static boolean isSupportedFile(String suffix) {
		boolean result = supportedSuffixList.contains(suffix);
		logger.debug("{} : supportted[{}]", suffix, result);
		
		return result;
	}
	
	public static boolean isSupportedFile(File file) {
		boolean result = false;
		if (file.isFile()) {
			result = supportedSuffixList.contains(extractSuffix(file.getName().toLowerCase()));
			if (result) {
				logger.debug("{} is supported file.", file.getAbsolutePath());
			} else {
				logger.debug("{} is not supported file.", file.getAbsolutePath());
			}
			return result;
		} else {
			logger.debug("{} is not file.", file.getAbsolutePath());
		}
		
		return result;
	}
	
	public static Set<File> getSupportedFileList(File root) {
		Set<File> supportedFileList = new HashSet<File>();
		
		if (root == null) {
			return null;
		}
		
		File[] fileList = root.listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				Set<File> supportedFiles = getSupportedFileList(file);
				if (supportedFiles.size() != 0) {
					supportedFileList.addAll(supportedFiles);
				}
			}
			
			if (isSupportedFile(file)) {
				supportedFileList.add(file);
			}
		}
		
		return supportedFileList;
	}
	
	public static Set<String> getLanguageList(File root) {
		Set<String> languages = new HashSet<String>();
		
		if (root == null) {
			return null;
		}
		
		File[] fileList = root.listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				Set<String> engineList = getLanguageList(file);
				if (engineList.size() > 0) {
					languages.addAll(engineList);
				}
			}else if(file.isFile()){
				Set<String> language = getLanguage(file);
				languages.addAll(language);
			}
		}
		
		return languages;
	}
	
	public static Set<String> getLanguage(String suffix) {
		Set<String> languageSet = new HashSet<String>();
		String targetSuffix = suffix.toLowerCase();
		if (targetSuffix.equals("java") || targetSuffix.equals("class")) {
			languageSet.add("JAVA");
		} else if (targetSuffix.equals("jsp")) {
			languageSet.add("JSP");
		} else if (targetSuffix.equals("m") || targetSuffix.equals("mm")) {
			languageSet.add("objc");
		} else if (targetSuffix.equals("c") || targetSuffix.equals("cpp") || targetSuffix.equals("h") || targetSuffix.equals("hpp") || targetSuffix.equals("hh") || targetSuffix.equals("hxx") || targetSuffix.equals("cs") || targetSuffix.equals("cc")) {
			languageSet.add("CPP");
		} else if (targetSuffix.equals("asp")) {
			languageSet.add("ASP");
		} else if (targetSuffix.equals("csproj") || targetSuffix.equals("sln") || targetSuffix.equals("cs") || targetSuffix.equals("config") || targetSuffix.equals("dll") || targetSuffix.equals("pdb") || targetSuffix.equals("master") || targetSuffix.equals("resx") || targetSuffix.equals("settings") || targetSuffix.equals("manifest") || targetSuffix.equals("master")) {
			languageSet.add("CSHARP");
		} else if (targetSuffix.equals("php") || targetSuffix.equals("php3") || targetSuffix.equals("php4") || targetSuffix.equals("php5") || targetSuffix.equals("phtml") || targetSuffix.equals("htm") || targetSuffix.equals("html") || targetSuffix.equals("inc")) {
			languageSet.add("PHP");
		} else if (targetSuffix.equals("htm") || targetSuffix.equals("html") || targetSuffix.equals("xhtml") || targetSuffix.equals("rhtml") || targetSuffix.equals("shtml") || targetSuffix.equals("phtml") || targetSuffix.equals("jsp") || targetSuffix.equals("jspf") || targetSuffix.equals("jsf") || targetSuffix.equals("php") || targetSuffix.equals("php3") || targetSuffix.equals("php4") || targetSuffix.equals("php5") || targetSuffix.equals("inc") || targetSuffix.equals("erb") || targetSuffix.equals("asp") || targetSuffix.equals("aspx")) {
			languageSet.add("HTML");
		} else if (targetSuffix.equals("csproj") || targetSuffix.equals("vbproj") || targetSuffix.equals("sln") || targetSuffix.equals("aspx") || targetSuffix.equals("cs") || targetSuffix.equals("vb") || targetSuffix.equals("config") || targetSuffix.equals("dll") || targetSuffix.equals("pdb") || targetSuffix.equals("master") || targetSuffix.equals("resx") || targetSuffix.equals("settings") || targetSuffix.equals("manifest") || targetSuffix.equals("master")) {
			languageSet.add("ASPNET");
		} else if (targetSuffix.equals("js")) {
			languageSet.add("JS");
		} else if (targetSuffix.equals("xml")) {
			languageSet.add("XML");
		} else if (targetSuffix.equals("py")) {
			languageSet.add("PYTHON");
		} else if (targetSuffix.equals("json")) {
			languageSet.add("JSON");
		} else if (targetSuffix.equals("css")) {
			languageSet.add("CSS");
		} else if (targetSuffix.equals("groovy")) {
			languageSet.add("GROOVY");
		} else if (targetSuffix.equals("erl")) {
			languageSet.add("ERLANG");
		} else if (targetSuffix.equals("abap") || targetSuffix.equals("ab4") || targetSuffix.equals("flow")) {
			languageSet.add("ABAP");
		}
		
		return languageSet;
	}
	
	public static Set<String> getLanguage(File file) {
		return getLanguage(extractSuffix(file.getAbsolutePath()));
	}
	
	public static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+(.+?);", Pattern.DOTALL);
	public static final Pattern COMMENT_PATTERN = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", Pattern.DOTALL);
	
	public static void setConfiguration(File config) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(config);
			
			fw.write("[general]\n");
			fw.write("anon-access = none\n");
			fw.write("auth-access = write\n");
			fw.write("password-db = passwd\n");
			fw.write("[sasl]\n");
			fw.write("\n");
			
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void setPassword(File passwd, String userId, String password) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(passwd);
			
			fw.write("[users]\n");
			
			if (userId != null) {
				fw.write(userId + " = " + password + "\n");
			}
			
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createSyncLogFile(List<String> listLog, String filePath){
		StringBuffer sb = new StringBuffer();
		BufferedWriter fw = null;
		for(String logStr : listLog){
			sb.append(logStr + "\n");
		}
		
		if(listLog.size() > 0){
			File dir = new File(filePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssmd", Locale.KOREA);
    		Date currentTime = new Date ();
    		String newFileName = formatter.format ( currentTime );
    		
			String fileName = filePath + File.separator + newFileName + ".txt";
			try{
                
	            fw = new BufferedWriter(new FileWriter(fileName, true));
	             
	            // 파일안에 문자열 쓰기
	            fw.write(sb.toString());
	            fw.flush();
	             
	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{
	        	try { fw.close(); } catch (IOException e) {}
	        }
		}
	}
}
