package com.casewaresa.framework.util;



import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;

import com.casewaresa.framework.provider.ThemeProvider;

public class ZKThemes {
	private static final ZKThemes ZKTheme = new ZKThemes();
	private static final String themePrefix = "theme-";
	
	private ZKThemes() {
		super();
	}

	public static ZKThemes getZKTheme() {
		return ZKTheme;
	}

	/** @desc: provee un mecanismo para el manejo de mensajes */
	protected static Logger log = Logger.getLogger(ZKThemes.class);

	public void createTheme(String theme, String colorHex, String ignoreFiles) throws Exception{
		log.info("Creating ZK Theme...");
		log.debug("Theme: " + theme + " - " + "Color: " + colorHex + " - " + "ignoreFiles: " + ignoreFiles);
		
//	String themeName = themePrefix + theme;
//		Color color = Color.decode("0x"+colorHex);
//		ContextoAplicacion contextoAplicacion = ContextoAplicacion.getInstance();
//		File zkLocation = new File(contextoAplicacion.getRutaContexto() + "WEB-INF/lib/");
//		CreateTheme createTheme = new CreateTheme(themeName, color, zkLocation, ignoreFiles);
//		createTheme.run();
		
		log.info("ZH Theme has been created successfully");
	}
	
	public Boolean applyTheme(String theme) throws Exception{
		log.info("Ejecutando el m�todo [ applyTheme() ]...");
		log.debug("Ejecutando el m�todo [ applyTheme(" + theme + ") ]...");
		
		Boolean applyTheme = false;
		
		ThemeProvider themeProvider = (ThemeProvider)Executions.getCurrent().getDesktop()
										.getWebApp().getConfiguration().getThemeProvider();
		
		String themeName = themePrefix + theme;
				
		if(!themeProvider.getThemeName().equals(themeName)){
			themeProvider.setThemeName(themeName);	
			applyTheme = true;
			Executions.sendRedirect(null);
		}
		return applyTheme;
	}		
}
