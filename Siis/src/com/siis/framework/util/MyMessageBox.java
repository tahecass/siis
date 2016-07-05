package com.casewaresa.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.mesg.Messages;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.impl.MessageboxDlg;

public class MyMessageBox {
	private static final Log log = Log.lookup(MyMessageBox.class);
	private static String _templ = "/iucomunes/MessageBox.zul";
	public static final String QUESTION = "z-msgbox z-msgbox-question";
	public static final String EXCLAMATION = "z-msgbox z-msgbox-exclamation";
	public static final String INFORMATION = "z-msgbox z-msgbox-information";
	public static final String ERROR = "z-msgbox z-msgbox-error";
	public static final String NONE = null;
	public static final int OK = 1;
	public static final int CANCEL = 2;
	public static final int YES = 16;
	public static final int NO = 32;
	public static final int ABORT = 256;
	public static final int RETRY = 512;
	public static final int IGNORE = 1024;
	public static final String ON_YES = "onYes";
	public static final String ON_NO = "onNo";
	public static final String ON_RETRY = "onRetry";
	public static final String ON_ABORT = "onAbort";
	public static final String ON_IGNORE = "onIgnore";
	public static final String ON_OK = "onOK";
	public static final String ON_CANCEL = "onCancel";
	private static final Button[] DEFAULT_BUTTONS = { Button.OK };
	private static final String DEFAULT_WIDTH = "260pt";
	private static final String DEFAULT_HEIGHT = "100pt";
 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Button show(String message, String title, Button[] buttons, String[] btnLabels, String icon, Button focus, EventListener listener, Map<String, String> params, String width, String height){
		Map arg = new HashMap();
		arg.put("message", message);
		arg.put("title", (title != null) ? title : Executions.getCurrent().getDesktop().getWebApp().getAppName());
		arg.put("icon", icon);
		arg.put("width", width!=null?width:DEFAULT_WIDTH);
		arg.put("height", height!=null?height:DEFAULT_HEIGHT);

		if ((buttons == null) || (buttons.length == 0)) {
			buttons = DEFAULT_BUTTONS;
		}
		
		int btnmask = 0;
		
		for (int j = 0; j < buttons.length; ++j) {
			if (buttons[j] == null) {
				throw new IllegalArgumentException("The " + j + "-th button is null");
			}
			btnmask += buttons[j].id;
			arg.put(buttons[j].id, Integer.valueOf(buttons[j].id)); //arg.put(buttons[j].stringId, Integer.valueOf(buttons[j].id));
		}

		arg.put("buttons", Integer.valueOf(btnmask));

		if (params != null) {
			arg.putAll(params);
		}
		
		MessageboxDlg dlg = (MessageboxDlg)Executions.createComponents(_templ, null, arg);
 
		dlg.setEventListener(listener);
		dlg.setButtons(buttons, btnLabels);
		
		dlg.setWidth(width!=null?width:DEFAULT_WIDTH);
		dlg.setHeight(height!=null?height:DEFAULT_HEIGHT); 
			  
		if (focus != null) dlg.setFocus(focus);

		if (dlg.getDesktop().getWebApp().getConfiguration().isEventThreadEnabled()) {
			try {
				dlg.doModal();
			} catch (Throwable ex) {
				try {
					dlg.detach();
				} catch (Throwable ex2) {
					log.warningBriefly("Failed to detach when recovering from an error", ex2);
				}
				throw UiException.Aide.wrap(ex);
			}
			return dlg.getResult();
		}
		dlg.doHighlighted();
		return Button.OK;
	}
 
	public static Button show(String message, String title, Button[] buttons, String[] btnLabels, String icon, Button focus, EventListener<ClickEvent> listener){
		return show(message, title, buttons, btnLabels, icon, focus, listener, null, null, null);
	}
 
	public static Button show(String message, String title, Button[] buttons, String icon, Button focus, EventListener<ClickEvent> listener){
		return show(message, title, buttons, null, icon, focus, listener, null, null, null);
	}
 
	public static Button show(String message, String title, Button[] buttons, String icon, EventListener<ClickEvent> listener){
		return show(message, title, buttons, null, icon, null, listener, null, null, null);
	}
 
	public static Button show(String message, String title, Button[] buttons, String icon){
		return show(message, title, buttons, null, icon, null, null, null, null, null);
	}

	public static Button show(String message, String title, Button[] buttons, String icon, String width, String height){
		return show(message, title, buttons, null, icon, null, null, null, width, height);
	}
 
	public static Button show(String message, Button[] buttons, EventListener<ClickEvent> listener){
		return show(message, null, buttons, null, "z-msgbox z-msgbox-information", null, listener, null, null, null);
	}
	
	public static int show(String message, String title, int buttons, String icon, EventListener<Event> listener){
		return show(message, title, buttons, icon, 0, listener);
	}
 
	public static int show(String message, String title, int buttons, String icon, int focus){
		return show(message, title, buttons, icon, focus, null);
	}
  
	public static int show(String message, String title, int buttons, String icon, int focus, EventListener<Event> listener){
		return show(message, title, toButtonTypes(buttons), null, icon, (focus != 0) ? toButtonType(focus) : null, toButtonListener(listener), null, null, null).id;
	}

	public static int show(String message, String title, int buttons, String icon){
		return show(message, title, toButtonTypes(buttons), null, icon, null, toButtonListener(null), null, null, null).id;
	}

	public static int show(String message, String title, int buttons, String icon, String width, String height){
		return show(message, title, toButtonTypes(buttons), null, icon, null, toButtonListener(null), null, width, height).id;
	}

	private static Button toButtonType(int btn){
		switch (btn){
			case 2:
				return Button.CANCEL;
			case 16:
				return Button.YES;
			case 32:
				return Button.NO;
			case 256:
				return Button.ABORT;
			case 512:
				return Button.RETRY;
			case 1024:
				return Button.IGNORE; 
		}
		return Button.OK;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Button[] toButtonTypes(int buttons) {
		List btntypes = new ArrayList();
		
		if ((buttons & 0x1) != 0)
			btntypes.add(toButtonType(1));
		if ((buttons & 0x2) != 0)
			btntypes.add(toButtonType(2));
		if ((buttons & 0x10) != 0)
			btntypes.add(toButtonType(16));
		if ((buttons & 0x20) != 0)
			btntypes.add(toButtonType(32));
		if ((buttons & 0x200) != 0)
			btntypes.add(toButtonType(512));
		if ((buttons & 0x100) != 0)
			btntypes.add(toButtonType(256));
		if ((buttons & 0x400) != 0)
			btntypes.add(toButtonType(1024));
		return ((Button[])btntypes.toArray(new Button[btntypes.size()]));
	}

	private static EventListener<ClickEvent> toButtonListener(EventListener<Event> listener) {
		return ((listener != null) ? new ButtonListener(listener) : null);
	}
 
	public static int show(String message){
		return show(message, null, 1, "z-msgbox z-msgbox-information", 0, null);
	}
 
	public static int show(int messageCode, Object[] args, int titleCode, int buttons, String icon){
		return show(messageCode, args, titleCode, buttons, icon, 0, null);
	}
 
	public static int show(int messageCode, Object[] args, int titleCode, int buttons, String icon, int focus){
		return show(messageCode, args, titleCode, buttons, icon, focus, null);
	}

	public static int show(int messageCode, Object[] args, int titleCode, int buttons, String icon, int focus, EventListener<Event> listener){
		return show(Messages.get(messageCode, args), (titleCode > 0) ? Messages.get(titleCode) : null, buttons, icon, focus, listener);
	}
 
	public static int show(int messageCode, Object arg, int titleCode, int buttons, String icon){
		return show(messageCode, arg, titleCode, buttons, icon, 0, null);
	}
 
	public static int show(int messageCode, Object arg, int titleCode, int buttons, String icon, int focus){
		return show(messageCode, arg, titleCode, buttons, icon, focus, null);
	}
 
	public static int show(int messageCode, Object arg, int titleCode, int buttons, String icon, int focus, EventListener<Event> listener){
		return show(Messages.get(messageCode, arg), (titleCode > 0) ? Messages.get(titleCode) : null, buttons, icon, focus, listener);
	}
 
	public static int show(int messageCode, int titleCode, int buttons, String icon){
		return show(messageCode, titleCode, buttons, icon, 0);
	}
 
	public static int show(int messageCode, int titleCode, int buttons, String icon, int focus){
		return show(messageCode, titleCode, buttons, icon, focus, null);
	}
 
	public static int show(int messageCode, int titleCode, int buttons, String icon, int focus, EventListener<Event> listener){
		return show(Messages.get(messageCode), (titleCode > 0) ? Messages.get(titleCode) : null, buttons, icon, focus, listener);
	}
 
	public static void setTemplate(String uri){
		if ((uri == null) || (uri.length() == 0))
			throw new IllegalArgumentException("empty");
		_templ = uri;
	}
 
	public static String getTemplate(){
		return _templ;
	}
 
	@SuppressWarnings("serial")
	private static class ButtonListener implements SerializableEventListener<Messagebox.ClickEvent>{
		private final EventListener<Event> _listener;
		private ButtonListener(EventListener<Event> listener){
			this._listener = listener; 
		}

		public void onEvent(Messagebox.ClickEvent event) throws Exception {
			Messagebox.Button btn = event.getButton();
			this._listener.onEvent(new Event(event.getName(), event.getTarget(), Integer.valueOf((btn != null) ? btn.id : -1)));
		}
 
		public String toString(){
			return this._listener.toString();
		}	
	}
}