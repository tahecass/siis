package com.casewaresa.framework.util;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;

public class ValidacionesCamposUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void maximoMinimo(final Object objeto1, final Object objeto2) {
		if (objeto1 instanceof Intbox && objeto2 instanceof Intbox) {
			((Intbox) objeto2).addEventListener("onBlur", new EventListener() {
				public void onEvent(Event event) {
					if (((Intbox) objeto1).getErrorMessage() != null) {
						((Intbox) objeto1).clearErrorMessage();
						((Intbox) objeto2).clearErrorMessage();
					}
					if (((Intbox) objeto1).getValue() != null
							&& ((Intbox) objeto2).getValue() != null) {
						if (((Intbox) objeto1).getValue() > ((Intbox) objeto2)
								.getValue()) {
							throw new WrongValueException(
									(Intbox) objeto2,
									"Este valor debe ser mayor"
											+ (((Intbox) objeto2).getName() != null ? " que "
													+ ((Intbox) objeto1)
															.getName()
													: ""));
						}
					}

				}
			});
			((Intbox) objeto1).addEventListener("onBlur", new EventListener() {
				public void onEvent(Event event) {
					if (((Intbox) objeto2).getErrorMessage() != null) {
						((Intbox) objeto2).clearErrorMessage();
						((Intbox) objeto1).clearErrorMessage();
					}

					if (((Intbox) objeto1).getValue() != null
							&& ((Intbox) objeto2).getValue() != null) {
						if (((Intbox) objeto1).getValue() > ((Intbox) objeto2)
								.getValue()) {
							throw new WrongValueException(
									(Intbox) objeto1,
									"Este valor debe ser menor"
											+ (((Intbox) objeto1).getName() != null ? " que "
													+ ((Intbox) objeto2)
															.getName()
													: ""));
						}
					}
				}
			});
		}
	}
}
