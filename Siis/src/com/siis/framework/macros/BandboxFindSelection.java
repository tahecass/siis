package com.casewaresa.framework.macros;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.casewaresa.framework.action.impl.IInicializarComponentes;
import com.casewaresa.framework.assembler.AssemblerStandard;
import com.casewaresa.framework.dto.IBeanAbstracto;
import com.casewaresa.framework.facade.ParametrizacionFac;
import com.casewaresa.framework.macros.contract.IBandboxAditionalEventBanboxFind;
import com.casewaresa.framework.util.Utilidades;

public class BandboxFindSelection extends Bandbox implements
	IInicializarComponentes {

    /**
     * conponentes
     */
    private static final long serialVersionUID = 7669050113428633036L;
    protected static Logger log = Logger.getLogger(BandboxFindSelection.class);
    private Radiogroup idMCRTSZRgrCriterio;
    private Listbox idMCRTSZLbxlista;
    private AssemblerStandard assemblerStandard;
    private Textbox tSecuencia;
    private Button botonBorrar;
    private Caption idMCRZCptEtiqueta;
    private Listheader idMCRTSZLhdCodigo;
    private Listheader idMCRTSZLhdNombre;
    private Component action;
    private IBandboxAditionalEventBanboxFind interfaz;
    private Row idMCRSTZRowCriterioSeleccionado;
    private Label idMCRSTZLblCriterioSeleccionado;
    private Textbox idMCRSTZTbxCriterioSeleccionado;
    private boolean criterioUnoActivo = true;

    /*
     * Datos para la contruccion del primer criterio de busqueda
     */

    private String consultaCriterio1;
    private String consultaObtenerCriterio1;
    private String etiquetaCriterio1;
    private IBeanAbstracto objetoClaseCriterio1;
    private Map<String, Object> parametrosCriterio1 = null;
    private boolean usaNombreCriterio1 = true;
    private String etiquetaCodigoCriterio1;
    private String etiquetaNombreCriterio1;
    private boolean isAsignadoCriterio1 = false;
    private boolean usaCodigoCriterio1 = true;
    private boolean usaColumnaCodigoCriteirio1 = true;
    private boolean usaColumnaNombreCriterio1 = true;
    private boolean toLowerCaseCriterio1 = false;

    /*
     * Datos para la contruccion del segundo criterio de busqueda
     */
    private String consultaCriterio2;
    private String consultaObtenerCriterio2;
    private String etiquetaCriterio2;
    private IBeanAbstracto objetoClaseCriterio2;
    private Map<String, Object> parametrosCriterio2 = null;
    private boolean usaNombreCriterio2 = true;
    private String etiquetaCodigoCriterio2;
    private boolean isAsignadoCriterio2 = false;
    private boolean usaCodigoCriterio2 = true;
    private boolean usaColumnaCodigoCriteirio2 = true;
    private boolean usaColumnaNombreCriterio2 = true;
    private boolean toLowerCaseCriterio2 = false;

    public String getConsultaCriterio1() {
	return consultaCriterio1;
    }

    public void setConsultaCriterio1(String consultaCriterio1) {
	this.consultaCriterio1 = consultaCriterio1;
    }

    public String getConsultaObtenerCriterio1() {
	return consultaObtenerCriterio1;
    }

    public void setConsultaObtenerCriterio1(String consultaObtenerCriterio1) {
	this.consultaObtenerCriterio1 = consultaObtenerCriterio1;
    }

    public String getEtiquetaCriterio1() {
	return etiquetaCriterio1;
    }

    public void setEtiquetaCriterio1(String etiquetaCriterio1) {
	this.etiquetaCriterio1 = etiquetaCriterio1;
    }

    public IBeanAbstracto getObjetoClaseCriterio1() {
	return objetoClaseCriterio1;
    }

    public void setObjetoClaseCriterio1(IBeanAbstracto objetoClaseCriterio1) {
	this.objetoClaseCriterio1 = objetoClaseCriterio1;
    }

    public Map<String, Object> getParametrosCriterio1() {
	return parametrosCriterio1;
    }

    public void setParametrosCriterio1(Map<String, Object> parametrosCriterio1) {
	this.parametrosCriterio1 = parametrosCriterio1;
    }

    public boolean isUsaNombreCriterio1() {
	return usaNombreCriterio1;
    }

    public void setUsaNombreCriterio1(boolean usaNombreCriterio1) {
	this.usaNombreCriterio1 = usaNombreCriterio1;
    }

    public void setEtiquetaCodigoCriterio1(String etiquetaCodigoCriterio1) {
	this.etiquetaCodigoCriterio1 = etiquetaCodigoCriterio1;
    }

    public String getEtiquetaCodigoCriterio1() {
	return etiquetaCodigoCriterio1;
    }

    public String getEtiquetaNombreCriterio1() {
	return etiquetaNombreCriterio1;
    }

    public void setEtiquetaNombreCriterio1(String etiquetaNombreCriterio1) {
	this.etiquetaNombreCriterio1 = etiquetaNombreCriterio1;
    }

    public boolean isAsignadoCriterio1() {
	return isAsignadoCriterio1;
    }

    public void setAsignadoCriterio1(boolean isAsignadoCriterio1) {
	this.isAsignadoCriterio1 = isAsignadoCriterio1;
    }

    public boolean isUsaCodigoCriterio1() {
	return usaCodigoCriterio1;
    }

    public void setUsaCodigoCriterio1(boolean usaCodigoCriterio1) {
	this.usaCodigoCriterio1 = usaCodigoCriterio1;
    }

    public boolean isUsaColumnaCodigoCriteirio1() {
	return usaColumnaCodigoCriteirio1;
    }

    public void setUsaColumnaCodigoCriteirio1(boolean usaColumnaCodigoCriteirio1) {
	this.usaColumnaCodigoCriteirio1 = usaColumnaCodigoCriteirio1;
    }

    public boolean isUsaColumnaNombreCriterio1() {
	return usaColumnaNombreCriterio1;
    }

    public void setUsaColumnaNombreCriterio1(boolean usaColumnaNombreCriterio1) {
	this.usaColumnaNombreCriterio1 = usaColumnaNombreCriterio1;
    }

    public boolean isToLowerCaseCriterio1() {
	return toLowerCaseCriterio1;
    }

    public void setToLowerCaseCriterio1(boolean toLowerCaseCriterio1) {
	this.toLowerCaseCriterio1 = toLowerCaseCriterio1;
    }

    public String getConsultaCriterio2() {
	return consultaCriterio2;
    }

    public void setConsultaCriterio2(String consultaCriterio2) {
	this.consultaCriterio2 = consultaCriterio2;
    }

    public String getConsultaObtenerCriterio2() {
	return consultaObtenerCriterio2;
    }

    public void setConsultaObtenerCriterio2(String consultaObtenerCriterio2) {
	this.consultaObtenerCriterio2 = consultaObtenerCriterio2;
    }

    public String getEtiquetaCriterio2() {
	return etiquetaCriterio2;
    }

    public void setEtiquetaCriterio2(String etiquetaCriterio2) {
	this.etiquetaCriterio2 = etiquetaCriterio2;
    }

    public IBeanAbstracto getObjetoClaseCriterio2() {
	return objetoClaseCriterio2;
    }

    public void setObjetoClaseCriterio2(IBeanAbstracto objetoClaseCriterio2) {
	this.objetoClaseCriterio2 = objetoClaseCriterio2;
    }

    public Map<String, Object> getParametrosCriterio2() {
	return parametrosCriterio2;
    }

    public void setParametrosCriterio2(Map<String, Object> parametrosCriterio2) {
	this.parametrosCriterio2 = parametrosCriterio2;
    }

    public boolean isUsaNombreCriterio2() {
	return usaNombreCriterio2;
    }

    public void setUsaNombreCriterio2(boolean usaNombreCriterio2) {
	this.usaNombreCriterio2 = usaNombreCriterio2;
    }

    public String getEtiquetaCodigoCriterio2() {
	return etiquetaCodigoCriterio2;
    }

    public void setEtiquetaCodigoCriterio2(String etiquetaCodigoCriterio2) {
	this.etiquetaCodigoCriterio2 = etiquetaCodigoCriterio2;
    }

    public boolean isAsignadoCriterio2() {
	return isAsignadoCriterio2;
    }

    public void setAsignadoCriterio2(boolean isAsignadoCriterio2) {
	this.isAsignadoCriterio2 = isAsignadoCriterio2;
    }

    public boolean isUsaCodigoCriterio2() {
	return usaCodigoCriterio2;
    }

    public void setUsaCodigoCriterio2(boolean usaCodigoCriterio2) {
	this.usaCodigoCriterio2 = usaCodigoCriterio2;
    }

    public boolean isUsaColumnaCodigoCriteirio2() {
	return usaColumnaCodigoCriteirio2;
    }

    public void setUsaColumnaCodigoCriteirio2(boolean usaColumnaCodigoCriteirio2) {
	this.usaColumnaCodigoCriteirio2 = usaColumnaCodigoCriteirio2;
    }

    public boolean isUsaColumnaNombreCriterio2() {
	return usaColumnaNombreCriterio2;
    }

    public void setUsaColumnaNombreCriterio2(boolean usaColumnaNombreCriterio2) {
	this.usaColumnaNombreCriterio2 = usaColumnaNombreCriterio2;
    }

    public boolean isToLowerCaseCriterio2() {
	return toLowerCaseCriterio2;
    }

    public void setToLowerCaseCriterio2(boolean toLowerCaseCriterio2) {
	this.toLowerCaseCriterio2 = toLowerCaseCriterio2;
    }

    private AssemblerStandard getAssemblerStandard() {

	if (assemblerStandard == null) {
	    assemblerStandard = new AssemblerStandard();
	}
	return assemblerStandard;
    }

    public IBeanAbstracto setObjeto(String campo, String criterio,
	    IBeanAbstracto obj) {
	if (criterio != null && !criterio.isEmpty()) {
	    criterio = "%" + criterio.trim().toUpperCase() + "%";
	}
	if (campo.equals("codigo")) {

	    obj.setCodigo(criterio);
	} else {
	    if (campo.equals("nombre")) {

		obj.setNombre(criterio);
	    }
	}

	return obj;
    }

    public IBeanAbstracto setObjeto(IBeanAbstracto obj, String campo,
	    String criterio) {
	if (criterio != null && !criterio.isEmpty()) {
	    criterio = "%" + criterio.trim() + "%";
	}
	if (campo.equals("codigo")) {

	    obj.setCodigo(criterio);
	} else {
	    if (campo.equals("nombre")) {

		obj.setNombre(criterio);
	    }
	}

	return obj;
    }

    @Override
    public void cargarComponentesVista() {
	this.idMCRTSZRgrCriterio = (Radiogroup) this
		.getFellow("idMCRTSZRgrCriterio");
	this.idMCRTSZLbxlista = (Listbox) this.getFellow("idMCRTSZLbxlista");
	this.tSecuencia = (Textbox) this.getFellow("tSecuencia");
	this.botonBorrar = (Button) this.getFellow("botonBorrar");
	this.idMCRZCptEtiqueta = (Caption) this.getFellow("idMCRTSZLhdCodigo");
	this.idMCRTSZLhdCodigo = (Listheader) this
		.getFellow("idMCRTSZLhdCodigo");
	this.idMCRTSZLhdNombre = (Listheader) this.getFellow("idMCRZLhdNombre");
	this.idMCRSTZRowCriterioSeleccionado = (Row) this
		.getFellow("idMCRSTZRowCriterioSeleccionado");
	this.idMCRSTZLblCriterioSeleccionado = (Label) this
		.getFellow("idMCRSTZLblCriterioSeleccionado");
	this.idMCRSTZTbxCriterioSeleccionado = (Textbox) this
		.getFellow("idMCRSTZTbxCriterioSeleccionado");

	idMCRZCptEtiqueta.setLabel(getEtiquetaCriterio1());
	this.idMCRSTZLblCriterioSeleccionado.setValue(getEtiquetaCriterio1());

	idMCRTSZRgrCriterio.getItemAtIndex(1).setVisible(usaNombreCriterio1);
	idMCRTSZRgrCriterio.getItemAtIndex(0).setVisible(usaCodigoCriterio1);

	if (!usaCodigoCriterio1)
	    idMCRTSZRgrCriterio.setSelectedIndex(1);

	if (etiquetaCodigoCriterio1 != null) {
	    idMCRTSZRgrCriterio.getItemAtIndex(0).setLabel(
		    etiquetaCodigoCriterio1);
	    idMCRTSZLhdCodigo.setLabel(etiquetaCodigoCriterio1);
	}

	if (etiquetaNombreCriterio1 != null) {
	    idMCRTSZRgrCriterio.getItemAtIndex(1).setLabel(
		    etiquetaNombreCriterio1);
	    idMCRTSZLhdNombre.setLabel(etiquetaNombreCriterio1);
	}

	if (!usaColumnaCodigoCriteirio1) {
	    idMCRTSZLhdCodigo.setVisible(false);
	}
	if (!usaColumnaNombreCriterio1) {
	    idMCRTSZLhdNombre.setVisible(false);
	}

    }

    @SuppressWarnings("unchecked")
    public void onConsultar(String criterio) {
	log.info("Ejecutando el metodo[onConsultar]");
	Radio element = null;

	if (action instanceof IBandboxAditionalEventBanboxFind)
	    interfaz = (IBandboxAditionalEventBanboxFind) action;

	try {

	    element = idMCRTSZRgrCriterio.getSelectedItem();
	    element = idMCRTSZRgrCriterio.getSelectedItem();
	    String campo = (element.getValue() != null) ? element.getValue()
		    .toString() : null;

	    List<IBeanAbstracto> listaDatos = null;

	    if (criterioUnoActivo) {

		if (parametrosCriterio1 == null) {
		    if (!isToLowerCaseCriterio1()) {
			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio1(),
					setObjeto(campo, criterio,
						getObjetoClaseCriterio1()));

		    } else {

			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio1(),
					setObjeto(getObjetoClaseCriterio1(),
						campo, criterio));
		    }
		} else {

		    parametrosCriterio1.put(
			    "OBJETO",
			    setObjeto(getObjetoClaseCriterio1(), campo,
				    criterio));
		    listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
			    .getFacade().obtenerListado(getConsultaCriterio1(),
				    getParametrosCriterio1());

		}

		getObjetoClaseCriterio1().setCodigo(null);
		getObjetoClaseCriterio1().setNombre(null);

	    } else {

		if (parametrosCriterio2 == null) {
		    if (!isToLowerCaseCriterio2()) {
			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio2(),
					setObjeto(campo, criterio,
						getObjetoClaseCriterio2()));

		    } else {

			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio2(),
					setObjeto(getObjetoClaseCriterio2(),
						campo, criterio));
		    }
		} else {

		    parametrosCriterio2.put(
			    "OBJETO",
			    setObjeto(getObjetoClaseCriterio2(), campo,
				    criterio));
		    listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
			    .getFacade().obtenerListado(getConsultaCriterio2(),
				    getParametrosCriterio2());

		}

		getObjetoClaseCriterio2().setCodigo(null);
		getObjetoClaseCriterio2().setNombre(null);

	    }

	    idMCRTSZLbxlista.getItems().clear();

	    if (listaDatos != null && !listaDatos.isEmpty()) {
		for (final IBeanAbstracto object : listaDatos) {
		    final Listitem fila = getAssemblerStandard()
			    .crearListitemDesdeDto(object);
		    final BandboxFindSelection padre = this;
		    fila.addEventListener(Events.ON_CLICK,
			    new EventListener<Event>() {

				public void onEvent(Event arg0)
					throws Exception {
				    onSeleccionarMaestro(object, padre);
				}
			    });
		    if (interfaz != null) {
			fila.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
				    public void onEvent(Event arg0)
					    throws Exception {
					interfaz.onValidateSeleccion(fila,
						object);

				    }
				});
		    }

		    idMCRTSZLbxlista.appendChild(fila);
		}
	    } else {
		idMCRTSZLbxlista.getItems().clear();
		Listcell celda = new Listcell("No se encontraron registros");
		celda.setSpan(2);
		Listitem fila = new Listitem();
		fila.appendChild(celda);
		idMCRTSZLbxlista.appendChild(fila);
	    }

	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
    }

    @SuppressWarnings("unchecked")
    public void onConsultarOtro(String criterio) {
	log.info("Ejecutando el metodo[onConsultarOtro]");
	Radio element = null;
	try {

	    element = idMCRTSZRgrCriterio.getSelectedItem();
	    element = idMCRTSZRgrCriterio.getSelectedItem();
	    String campo = (element.getValue() != null) ? element.getValue()
		    .toString() : null;

	    List<IBeanAbstracto> listaDatos = null;

	    if (criterioUnoActivo) {

		if (parametrosCriterio1 == null) {

		    if (!isToLowerCaseCriterio1()) {
			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio1(),
					setObjeto(campo, criterio,
						getObjetoClaseCriterio1()));

		    } else {

			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio1(),
					setObjeto(getObjetoClaseCriterio1(),
						campo, criterio));

		    }

		} else {
		    if (!isToLowerCaseCriterio1()) {

			parametrosCriterio1.put(
				"OBJETO",
				setObjeto(campo, criterio,
					getObjetoClaseCriterio1()));
			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio1(),
					getParametrosCriterio1());

		    } else {

			parametrosCriterio1.put(
				"OBJETO",
				setObjeto(getObjetoClaseCriterio1(), campo,
					criterio));
			listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
				.getFacade().obtenerListado(
					getConsultaCriterio1(),
					getParametrosCriterio1());

		    }
		}

		getObjetoClaseCriterio1().setCodigo(null);
		getObjetoClaseCriterio1().setNombre(null);

	    } else {
		parametrosCriterio2.put("CRITERIO",
			this.idMCRSTZTbxCriterioSeleccionado
				.getAttribute("SECUENCIA_CRITERIO_SELECCION"));

		if (!isToLowerCaseCriterio2()) {

		    parametrosCriterio2.put(
			    "OBJETO",
			    setObjeto(campo, criterio,
				    getObjetoClaseCriterio1()));
		    listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
			    .getFacade().obtenerListado(getConsultaCriterio2(),
				    getParametrosCriterio2());

		} else {

		    parametrosCriterio2.put(
			    "OBJETO",
			    setObjeto(getObjetoClaseCriterio1(), campo,
				    criterio));
		    listaDatos = (List<IBeanAbstracto>) ParametrizacionFac
			    .getFacade().obtenerListado(getConsultaCriterio2(),
				    getParametrosCriterio2());

		}

		getObjetoClaseCriterio2().setCodigo(null);
		getObjetoClaseCriterio2().setNombre(null);

	    }

	    idMCRTSZLbxlista.getItems().clear();

	    if (listaDatos != null && !listaDatos.isEmpty()) {
		for (final IBeanAbstracto object : listaDatos) {
		    final Listitem fila = getAssemblerStandard()
			    .crearListitemDesdeDto(object);
		    final BandboxFindSelection padre = this;
		    fila.addEventListener(Events.ON_CLICK,
			    new EventListener<Event>() {

				public void onEvent(Event arg0)
					throws Exception {
				    onSeleccionarMaestro(object, padre);
				}
			    });
		    if (interfaz != null) {
			fila.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
				    public void onEvent(Event arg0)
					    throws Exception {
					interfaz.onValidateSeleccion(fila,
						object);

				    }
				});
		    }

		    idMCRTSZLbxlista.appendChild(fila);

		    if (listaDatos.size() == 1) {
			onSeleccionarMaestro(listaDatos.get(0), this);
			if (interfaz != null) {
			    interfaz.onValidateSeleccion(fila, object);
			}
		    }
		}
	    } else {
		idMCRTSZLbxlista.getItems().clear();
		Listcell celda = new Listcell("No se encontraron registros");
		celda.setSpan(2);
		Listitem fila = new Listitem();
		fila.appendChild(celda);
		idMCRTSZLbxlista.appendChild(fila);
	    }

	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
    }

    /**
     * @type Metodo de la clase BandboxFindSelection.java
     * @name onSeleccionarMaestro
     * @param object
     * @param padre
     * @throws Exception
     * @desc
     */
    private void onSeleccionarMaestro(final IBeanAbstracto object,
	    final BandboxFindSelection padre) throws Exception {
	log.info("Ejecutando metodo [ onSeleccionarMaestro ]");

	if (criterioUnoActivo) {
	    if (isUsaColumnaCodigoCriteirio1() && isUsaColumnaNombreCriterio1()) {
		Utilidades.onSeleccionarCriterio(object,
			getConsultaObtenerCriterio1(),
			idMCRSTZTbxCriterioSeleccionado, isAsignadoCriterio1());
	    } else {
		Utilidades.onSeleccionarCriterio(object,
			getConsultaObtenerCriterio1(),
			idMCRSTZTbxCriterioSeleccionado, isAsignadoCriterio1(),
			isUsaColumnaCodigoCriteirio1(),
			isUsaColumnaNombreCriterio1());
	    }

	    criterioUnoActivo = false;
	    idMCRTSZLbxlista.getItems().clear();

	} else {

	    if (isUsaColumnaCodigoCriteirio2() && isUsaColumnaNombreCriterio2()) {
		Utilidades.onSeleccionar(object, getConsultaObtenerCriterio2(),
			padre, this.tSecuencia, isAsignadoCriterio2);
	    } else {
		Utilidades.onSeleccionar(object, getConsultaObtenerCriterio2(),
			padre, this.tSecuencia, isAsignadoCriterio2,
			isUsaColumnaCodigoCriteirio2(),
			isUsaColumnaNombreCriterio2());
	    }

	    idMCRTSZLbxlista.getItems().clear();
	    padre.setOpen(false);

	    if (botonBorrar != null) {
		botonBorrar.setVisible(true);
		botonBorrar.setDisabled(false);
	    }

	}

    }

    public void onVolver() {
	log.info("Ejecutando el metodo[volver]");

	this.criterioUnoActivo = true;
	idMCRSTZRowCriterioSeleccionado.setVisible(false);
	idMCRTSZLbxlista.getItems().clear();

	if (botonBorrar != null) {
	    botonBorrar.setVisible(true);
	    botonBorrar.setDisabled(false);
	}

    }

}
