package com.casewaresa.framework.provider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.zkoss.zk.ui.Execution;

@SuppressWarnings({"unchecked"})
public class ThemeProvider implements org.zkoss.zk.ui.util.ThemeProvider {

    private String themeName, fileList;

    public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public ThemeProvider() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/zkthemer.properties");
            if (inputStream == null)
                throw new RuntimeException("Cannot find zkthemer.properties");
            
            Properties prop = new Properties();
            prop.load(inputStream);
            
            this.themeName = (String) prop.get("theme");
            this.fileList = (String) prop.get("fileList");
            
            if (this.themeName == null) {
                throw new RuntimeException("zkthemer.properties found, but missing 'theme' entry");
            }
            inputStream.close();             
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	@SuppressWarnings("rawtypes")
	public Collection getThemeURIs(Execution exec, List uris) {

        List newUris = new ArrayList<Object>(uris);
        
        for (Object object : newUris) {
        	 String uri = (String) object;
        	 if (uri.startsWith("~./")) {
                 uri = "~./" + themeName + "/" + uri.substring(3);
             }
        	 uris.add(uri);
		}
        return uris;
    }

	public String beforeWCS(Execution exec, String uri) {
		return uri;
	}

	public String beforeWidgetCSS(Execution exec, String uri) {
		String fileName = uri.substring(uri.lastIndexOf("/") + 1);
		
		if (!(fileList.indexOf(fileName) < 0 ))
			uri = (new StringBuilder("~./")).append(themeName).append("/")
					.append(uri.substring(3)).toString();
		return uri;
	}

	public int getWCSCacheControl(Execution exec, String uri) {
		return -1;//safe to cache
	}
}