package com.glory.svn;

import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.util.Version;

import java.io.*;
import java.util.*;

/**
 * SVN 打包版本导入
 *
 * @author lfc
 * @create 2019/12/27 14:10
 * @since 1.0.0
 */
public class SVNkitUtil {
    private static String line = System.getProperty("line.separator", "/n");
    private static SVNRepository repository ;
    private static Properties properties = new Properties();
    static {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("svnconfig.properties"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
        String uName;
        try {
            uName = (String)properties.get("SVN_URL");
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(uName));
        } catch (SVNException e) {
            e.printStackTrace();
        }

        uName = (String)properties.get("SVN_USERNAME");
        String passwd = (String)properties.get("SVN_PASSWORD");
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(uName, passwd.toCharArray());
        repository.setAuthenticationManager(authManager);
//        try {
//            System.out.println("Repository Root:" + repository.getRepositoryRoot(true));
//            System.out.println("Repository UUID:" + repository.getRepositoryUUID(true));
//        } catch (SVNException e) {
//            e.printStackTrace();
//        }
    }

    public static void filterCommitHistoryTest() throws Exception {
        long startRevision = 100L;
        String versionStr = "100";
        InputStream inputStream = null;
        try {
            File vFile = new File("svn_version.txt");
            inputStream = new FileInputStream(vFile);
            byte[] byets = new byte[(int)vFile.length()];
            inputStream.read(byets);
            versionStr = new String(byets,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != inputStream) inputStream.close();
        }

        if(null != versionStr && !"".equals(versionStr)){
            String[] versions = versionStr.split(",");

            long endRevision = -1L;
            final List<String> history = new ArrayList();
            final List<Long> versionList = new ArrayList();
            final Set<String> delPathSet = new HashSet();
            for(String version : versions){
                startRevision = Long.parseLong(version);
                repository.log(new String[]{""}, startRevision, endRevision, true, true, new ISVNLogEntryHandler() {
                    public void handleLogEntry(SVNLogEntry svnlogentry) throws SVNException {
                        versionList.add(svnlogentry.getRevision());
                        Map<String, SVNLogEntryPath> mapLog = svnlogentry.getChangedPaths();
                        Set<String> keySet = mapLog.keySet();
                        Iterator var5 = keySet.iterator();
                        while(var5.hasNext()) {
                            String logkey = (String)var5.next();
                            char cType = ((SVNLogEntryPath)mapLog.get(logkey)).getType();
                            if ('D' == cType) {
                                delPathSet.add(logkey);
                            }
                        }
                        history.addAll((Collection)svnlogentry.getChangedPaths().keySet());
                    }
                });
            }
            Set<String> fileNameSet = new HashSet();
            StringBuilder sBuilder = new StringBuilder();
            Iterator hisIter = history.iterator();

            while(hisIter.hasNext()) {
                String path = (String)hisIter.next();
                if (!delPathSet.contains(path)) {
                    String className;
                    if (path.contains("WebRoot")) {
                        className = path.substring(path.indexOf("WebRoot"));
                        fileNameSet.add(className);
                    }

                    if (path.contains("src")) {
                        className = path.substring(path.indexOf("src"));
                        className = className.replace("src/main/java", "WebRoot/WEB-INF/classes");
                        className = className.replace("src/interface/com", "WebRoot/WEB-INF/classes/com");//只针对车承保
                        className = className.replace("src/com", "WebRoot/WEB-INF/classes/com");
                        className = className.replace("src/main/resources", "WebRoot/WEB-INF/classes");
                        className = className.replace("src", "WebRoot/WEB-INF/classes");
                        className = className.replace(".java", ".class");
                        fileNameSet.add(className);
                    }
                }
            }

            int i = 0;
            Iterator fileIter = fileNameSet.iterator();
            while(fileIter.hasNext()) {
                String namePath = (String)fileIter.next();
                ++i;
                sBuilder.append(namePath);
                if (i != fileNameSet.size()) {
                    sBuilder.append(line);
                }
            }

            OutputStream outputStream = null;
            try {
                File file = new File("file_list.txt");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                outputStream = new FileOutputStream(file);
                outputStream.write(sBuilder.toString().getBytes());
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(null != outputStream) outputStream.close();
            }

        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println("打包工程start");
        setupLibrary();
        filterCommitHistoryTest();
        System.out.println("打包工程end");

    }
}