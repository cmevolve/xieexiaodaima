
package com.glory.svn;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNKitUtil {
    private static String line = System.getProperty("line.separator", "/n");
    private static SVNRepository repository = null;
    private static Properties properties = new Properties();

    static {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("svnconfig.properties"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            properties.load(bufferedReader);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public SVNKitUtil() {
    }

    public static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();

        String uName;
        try {
            uName = (String)properties.get("SVN_URL");
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(uName));
        } catch (SVNException var3) {
        }

        uName = (String)properties.get("SVN_USERNAME");
        String passwd = (String)properties.get("SVN_PASSWORD");
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(uName, passwd.toCharArray());
        repository.setAuthenticationManager(authManager);
    }

    public static void filterCommitHistoryTest() throws Exception {
        long startRevision = 100L;
        String versionStr = "100";

        try {
            File vFile = new File("svn_version.txt");
            InputStream inputStream = new FileInputStream(vFile);
            byte[] byets = new byte[(int)vFile.length()];
            inputStream.read(byets);
            versionStr = new String(byets);
            inputStream.close();
        } catch (IOException var19) {
            var19.printStackTrace();
        }

        if(null!=versionStr&& !"".equals(versionStr)){
        	
        
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
		
		                this.fillResult(svnlogentry);
		            }
		
		            public void fillResult(SVNLogEntry svnlogentry) {
		                history.addAll((Collection)svnlogentry.getChangedPaths().keySet());
		            }
		        });
	        }
	        
	        
	      
	
	        Set<String> fileNameSet = new HashSet();
	        StringBuilder sBuilder = new StringBuilder();
	        Iterator var13 = history.iterator();
	
	        while(var13.hasNext()) {
	            String path = (String)var13.next();
	            if (!delPathSet.contains(path)) {
	                String className;
	                if (path.contains("WebRoot")) {
	                    className = path.substring(path.indexOf("WebRoot"));
	                    fileNameSet.add(className);
	                }
	
	                if (path.contains("src")) {
	                    className = path.substring(path.indexOf("src"));
	                    className = className.replace("src/main/java", "WebRoot/WEB-INF/classes");
	                    className = className.replace("src/com", "WebRoot/WEB-INF/classes/com");
	                    className = className.replace("src/main/resources", "WebRoot/WEB-INF/classes");
	                    className = className.replace("src", "WebRoot/WEB-INF/classes");
	                    className = className.replace(".java", ".class");
	                    fileNameSet.add(className);
	                }
	            }
	        }
	
	        int i = 0;
	        Iterator var30 = fileNameSet.iterator();
	
	        while(var30.hasNext()) {
	            String namePath = (String)var30.next();
	            ++i;
	            sBuilder.append(namePath);
	            if (i != fileNameSet.size()) {
	                sBuilder.append(line);
	            }
	        }
	
	        try {
	            File file = new File("file_list.txt");
	            if (!file.exists()) {
	                try {
	                    file.createNewFile();
	                } catch (IOException var15) {
	                    var15.printStackTrace();
	                }
	            }
	
	            OutputStream outputStream = new FileOutputStream(file);
	            outputStream.write(sBuilder.toString().getBytes());
	            outputStream.flush();
	            outputStream.close();
	        } catch (Exception var16) {
	            var16.printStackTrace();
	        }
        
        }

    }

    public static void main(String[] args) throws Exception {
        setupLibrary();
        filterCommitHistoryTest();
        System.out.println("Over");
    }
}
