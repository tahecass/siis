/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casewaresa.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.zkoss.calendar.impl.SimpleDateFormatter;

/**
 *
 * @author Admin
 */
public class MyDateCalendarsFormater extends SimpleDateFormatter{

    
    /**
     * 
     */
    private static final long serialVersionUID = 8036485228404966899L;

    public String getCaptionByDate(Date date, Locale locale, TimeZone timezone) {
	SimpleDateFormat df = new SimpleDateFormat("d/M/yy", locale);
	df.setTimeZone(timezone);
	return df.format(date);
    }
    
}
