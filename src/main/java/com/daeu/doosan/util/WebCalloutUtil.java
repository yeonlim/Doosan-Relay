package com.daeu.doosan.util;

public class WebCalloutUtil {

//    @Value("${relay.log.use}")
//    public boolean logUseYn;
//
//    @Value("${relay.log.dir}")
//    public String logDir;
//
//    public void logCreation(Exception e, String fRequestBody) {
//        try {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            String message = "requestBody: " + fRequestBody + System.getProperty("line.separator") + System.getProperty("line.separator") + sw.toString();
//
//            DateTimeFormatter millisFormatter = DateTimeFormat.forPattern("YYYYMMddHHmmssSSS");
//            String datemillis = DateTime.now().toString(millisFormatter);
//            String logFileName = "LOG_" + datemillis + ".txt";
//
//            DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("YYYYMMdd");
//            String date = DateTime.now().toString(dateFormatter);
//            Path dir = Paths.get(logDir, date);
//            Path file = Paths.get(dir.toString(), logFileName);
//
//            if (Files.notExists(dir)) Files.createDirectories(dir);
//
//            Files.write(file, message.getBytes("euc-kr"), StandardOpenOption.CREATE);
//
//            sw.close();
//            pw.close();
//        } catch (JsonProcessingException je) {
//            je.printStackTrace();
//        } catch (IOException ie) {
//            ie.printStackTrace();
//        } catch (Exception ec) {
//            ec.printStackTrace();
//        }
//    }
//
//    public void logCreation(String e, String fRequestBody) {
//        try {
//            String message = "requestBody: " + fRequestBody + System.getProperty("line.separator") + System.getProperty("line.separator") + e;
//
//            DateTimeFormatter millisFormatter = DateTimeFormat.forPattern("YYYYMMddHHmmssSSS");
//            String datemillis = DateTime.now().toString(millisFormatter);
//            String logFileName = "LOG_" + datemillis + ".txt";
//
//            DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("YYYYMMdd");
//            String date = DateTime.now().toString(dateFormatter);
//            Path dir = Paths.get(logDir, date);
//            Path file = Paths.get(dir.toString(), logFileName);
//
//            if (Files.notExists(dir)) Files.createDirectories(dir);
//
//            Files.write(file, message.getBytes("euc-kr"), StandardOpenOption.CREATE);
//        } catch (JsonProcessingException je) {
//            je.printStackTrace();
//        } catch (IOException ie) {
//            ie.printStackTrace();
//        } catch (Exception ec) {
//            ec.printStackTrace();
//        }
//    }
//
//    public void logCreationSFDC(Exception e, String fRequestBody, Calendar startDate, String IFID, String MSGGUID) {
//        String ifQuery = "Select Id, Name, Calling__c, Target__c, Cycle__c, ForceLogging__c, SendReceiveType__c, Description__c, URL__c, LastModifiedById From Interface__c WHERE Name = "+"\'"+IFID+"\'"+" LIMIT 1";
////        String key = UUID.randomUUID().toString();
//        try {
//            WebServiceUtil.login();
//            System.out.println("ifQuery > "+ifQuery);
//            QueryResult ifResult = WebServiceUtil.connection.query(ifQuery);
//            Interface__c objInterface;
//            InterfaceLog__c objLog;
//            System.out.println("ifResult.getSize() > "+ifResult.getSize());
//            if(ifResult.getSize() > 0) {
//                SObject[] ifRecord = ifResult.getRecords();
//                objInterface = (Interface__c) ifRecord[0];
//
//                Calendar endDate = Calendar.getInstance();
//
//                StringWriter sw = new StringWriter();
//                PrintWriter pw = new PrintWriter(sw);
//                e.printStackTrace(pw);
//                String message = sw.toString();
//
//                objLog = new InterfaceLog__c();
//                objLog.setInterface__c(objInterface.getId());
//                objLog.setRequestUser__c(objInterface.getLastModifiedById());
//                objLog.setInterfaceDate__c(startDate);
//                objLog.setRequestDateTime__c(startDate);
//                objLog.setResponseDateTime__c(endDate);
//                objLog.setGUID__c(MSGGUID);
//                objLog.setStatus__c("F");
//                objLog.setErrorCode__c("RELAY SERVER ERROR");
//                if(message.length() > 32768) objLog.setErrorText__c(message.substring(0, 32768));
//                else objLog.setErrorText__c(message);
//
//                SaveResult[] logSaveResults = WebServiceUtil.connection.create(new SObject[] { objLog });
//
//                System.out.println("logSaveResults[0].isSuccess() > "+logSaveResults[0].isSuccess());
//                System.out.println("logSaveResults[0].getId() > "+logSaveResults[0].getId());
//                if(!logSaveResults[0].isSuccess()) System.out.println(logSaveResults[0].getErrors()[0].getMessage());
//
//                Attachment objReqAttachment = new Attachment();
//                objReqAttachment.setName("request.json");
//                objReqAttachment.setBody(fRequestBody.getBytes("euc-kr"));
//                objReqAttachment.setParentId(logSaveResults[0].getId());
//
//                if(message.length() > 32768) {
//                    Attachment objExcAttachment = new Attachment();
//                    objExcAttachment.setName("exception.log");
//                    objExcAttachment.setBody(message.getBytes("euc-kr"));
//                    objExcAttachment.setParentId(logSaveResults[0].getId());
//
//                    WebServiceUtil.connection.create(new SObject[] { objReqAttachment, objExcAttachment });
//                } else {
//                    WebServiceUtil.connection.create(new SObject[] { objReqAttachment });
//                }
//
//                sw.close();
//                pw.close();
//            }
//        } catch (ConnectionException ce) {
//            ce.printStackTrace();
//        } catch (Exception ec) {
//            ec.printStackTrace();
//        }
//
////        WebServiceUtil.logout(key);
//    }
//
//    public void logCreationSFDC(String e, String fRequestBody, Calendar startDate, String IFID, String MSGGUID) {
//        String ifQuery = "Select Id, Name, Calling__c, Target__c, Cycle__c, ForceLogging__c, SendReceiveType__c, Description__c, URL__c, LastModifiedById From Interface__c WHERE Name = "+"\'"+IFID+"\'"+" LIMIT 1";
////        String key = UUID.randomUUID().toString();
//        try {
//            WebServiceUtil.login();
//            System.out.println("ifQuery > "+ifQuery);
//            QueryResult ifResult = WebServiceUtil.connection.query(ifQuery);
//            Interface__c objInterface;
//            InterfaceLog__c objLog;
//            System.out.println("ifResult.getSize() > "+ifResult.getSize());
//            if(ifResult.getSize() > 0) {
//                SObject[] ifRecord = ifResult.getRecords();
//                objInterface = (Interface__c) ifRecord[0];
//
//                Calendar endDate = Calendar.getInstance();
//
//                objLog = new InterfaceLog__c();
//                objLog.setInterface__c(objInterface.getId());
//                objLog.setRequestUser__c(objInterface.getLastModifiedById());
//                objLog.setInterfaceDate__c(startDate);
//                objLog.setRequestDateTime__c(startDate);
//                objLog.setResponseDateTime__c(endDate);
//                objLog.setGUID__c(MSGGUID);
//                objLog.setStatus__c("F");
//                objLog.setErrorCode__c("RELAY SERVER ERROR");
//                if(e.length() > 32768) objLog.setErrorText__c(e.substring(0, 32768));
//                else objLog.setErrorText__c(e);
//
//                SaveResult[] logSaveResults = WebServiceUtil.connection.create(new SObject[] { objLog });
//
//                System.out.println("logSaveResults[0].isSuccess() > "+logSaveResults[0].isSuccess());
//                System.out.println("logSaveResults[0].getId() > "+logSaveResults[0].getId());
//                if(!logSaveResults[0].isSuccess()) System.out.println(logSaveResults[0].getErrors()[0].getMessage());
//
//                Attachment objReqAttachment = new Attachment();
//                objReqAttachment.setName("request.json");
//                objReqAttachment.setBody(fRequestBody.getBytes("euc-kr"));
//                objReqAttachment.setParentId(logSaveResults[0].getId());
//
//                if(e.length() > 32768) {
//                    Attachment objExcAttachment = new Attachment();
//                    objExcAttachment.setName("exception.log");
//                    objExcAttachment.setBody(e.getBytes("euc-kr"));
//                    objExcAttachment.setParentId(logSaveResults[0].getId());
//
//                    WebServiceUtil.connection.create(new SObject[] { objReqAttachment, objExcAttachment });
//                } else {
//                    WebServiceUtil.connection.create(new SObject[] { objReqAttachment });
//                }
//            }
//        } catch (ConnectionException ce) {
//            ce.printStackTrace();
//        } catch (Exception ec) {
//            ec.printStackTrace();
//        }
//
////        WebServiceUtil.logout(key);
//    }
//
//    public void updateInterfaceInfo(String IFID, String MSGSTATUS) {
//        String ifQuery = "Select Id, LastResponseDate__c, LastSuccessYn__c From Interface__c WHERE Name = "+"\'"+IFID+"\'"+" LIMIT 1";
////        String key = UUID.randomUUID().toString();
//        try {
//            WebServiceUtil.login();
//
//            QueryResult ifResult = WebServiceUtil.connection.query(ifQuery);
//            Interface__c objInterface;
//
//            if(ifResult.getSize() > 0) {
//                SObject[] ifRecord = ifResult.getRecords();
//                objInterface = (Interface__c) ifRecord[0];
//
//                Calendar endDate = Calendar.getInstance();
//
//                objInterface.setLastResponseDate__c(endDate);
//                objInterface.setLastSuccessYn__c(MSGSTATUS);
//
//                WebServiceUtil.connection.update(new SObject[] { objInterface });
//            }
//        } catch (ConnectionException ce) {
//            ce.printStackTrace();
//        } catch (Exception ec) {
//            ec.printStackTrace();
//        }
//
////        WebServiceUtil.logout(key);
//    }
//
//    public void updateInterfaceInfo(String IFID, String MSGSTATUS, Calendar startDate) {
//        String ifQuery = "Select Id, LastRequestDate__c, LastResponseDate__c, LastSuccessYn__c From Interface__c WHERE Name = "+"\'"+IFID+"\'"+" LIMIT 1";
////        String key = UUID.randomUUID().toString();
//        try {
//            WebServiceUtil.login();
//
//            QueryResult ifResult = WebServiceUtil.connection.query(ifQuery);
//            Interface__c objInterface;
//
//            if(ifResult.getSize() > 0) {
//                SObject[] ifRecord = ifResult.getRecords();
//                objInterface = (Interface__c) ifRecord[0];
//
//                Calendar endDate = Calendar.getInstance();
//
//                objInterface.setLastRequestDate__c(startDate);
//                objInterface.setLastResponseDate__c(endDate);
//                objInterface.setLastSuccessYn__c(MSGSTATUS);
//
//                WebServiceUtil.connection.update(new SObject[] { objInterface });
//            }
//        } catch (ConnectionException ce) {
//            ce.printStackTrace();
//        } catch (Exception ec) {
//            ec.printStackTrace();
//        }
//
////        WebServiceUtil.logout(key);
//    }
}
