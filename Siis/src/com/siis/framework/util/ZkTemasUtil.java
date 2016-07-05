package com.casewaresa.framework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Execution;


public class ZkTemasUtil {

	private static String THEME_COOKIE_KEY = "zktheme";

	private static final String DEFAULT_THEME = "default";
	protected static Logger log = Logger.getLogger(ZkTemasUtil.class);

	/**
	 * Sets the theme style in cookie
	 */
	public static void setTheme(Execution exe, String theme) {
		log.info("Ejecutando el metodo [ setTheme...] ");
		String temaFinal = "";
		if (theme.equals(DEFAULT_THEME)) {
			temaFinal = Library.getProperty("org.zkoss.theme.preferred");
		} else {
			temaFinal = theme;
		}

		Cookie cookie = new Cookie(THEME_COOKIE_KEY, temaFinal);
//		cookie.setMaxAge(60 * 60 * 24 * 30); // store 30 days
		cookie.setMaxAge(-1);//Expira al finalizar la sesión del cliente/navegador.
		String cp = exe.getContextPath();
		// if path is empty, cookie path will be request path, which causes
		// problems
		if (cp.length() == 0)
			cp = "/";
		cookie.setPath(cp);
		((HttpServletResponse) exe.getNativeResponse()).addCookie(cookie);

	}

	/**
	 * Returns the theme specified in cookies
	 * 
	 * @param exe
	 *            Execution
	 * @return the name of the theme or "" for default theme.
	 */
	public static String getTheme(Execution exe) {
		Cookie[] cookies = ((HttpServletRequest) exe.getNativeRequest())
				.getCookies();
		if (cookies == null)
			return "";
		for (int i = 0; i < cookies.length; i++) {
			Cookie c = cookies[i];
			if (THEME_COOKIE_KEY.equals(c.getName())) {
				String theme = c.getValue();
				if (theme != null)
					return theme;
			}
		}
		return "";
	}
}
