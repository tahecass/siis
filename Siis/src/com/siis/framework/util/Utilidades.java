package com.siis.framework.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkex.zul.Colorbox;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.A;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.InputElement;

import sun.awt.image.BufferedImageGraphicsConfig;

import com.siis.dto.Persona;
import com.siis.framework.action.IActionGrid2;
import com.siis.framework.action.IActionListbox;
import com.siis.framework.action.impl.ExcepcionPopupAction;
import com.siis.framework.contract.IConstantes;
import com.siis.framework.contract.IExcepcion;
import com.siis.framework.dto.Autenticacion;
import com.siis.framework.dto.IBeanAbstracto;
import com.siis.framework.excepciones.impl.Excepcion;
import com.siis.framework.facade.ParametrizacionFac;
import com.siis.framework.helper.PropiedadesHelper;
import com.siis.framework.helper.ZKProcesosComunesHelper;

/**
 * @author Futco
 * @name IUtilidades.java
 * @date 3/12/2010
 * @desc utilidades dentro del software
 */
public class Utilidades {

	protected static Logger log = Logger.getLogger(Utilidades.class);

	/**
	 * @type M�todo de la clase IUtilidades.java
	 * @name getToken
	 * @param posc
	 * @param cadena
	 * @param delimitador
	 * @return
	 * @desc De una cadena recibida, retorna un token de acuerdo a la poscion
	 */
	public static String getToken(int posc, String cadena, String delimitador) {

		StringTokenizer stk = new StringTokenizer(cadena, delimitador);
		int cont = 0;
		String retorno = null;

		while (stk.hasMoreTokens()) {
			cont++;

			if (cont == posc) {
				retorno = stk.nextToken();
			} else {
				stk.nextToken();
			}
		}

		return retorno;
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name deshabilitarCampos
	 * @param componentePadre
	 * @desc recibe el componente padre y deshabilita todos los campos de
	 *       entrada que hay en el
	 */
	public static void deshabilitarCamposControlador(Component componentePadre) {

		
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name deshabilitarCampos
	 * @param componentePadre
	 * @desc recibe el componente padre y habilita todos los campos de entrada
	 *       que hay en el
	 */
	public static void habilitarCamposControlador(Component componentePadre) {
	
	}

	/**
	 * @type Método de la clase Utilidades.java
	 * @name deshabilitarCampos
	 * @param componentePadre
	 * @desc recibe el componente padre y deshabilita todos los campos de
	 *       entrada que hay en el
	 */
	public static void deshabilitarCampos(Component componentePadre) {
		
	}

	

	/**
	 * @type Método de la clase Utilidades.java
	 * @name deshabilitarCampos
	 * @param componentePadre
	 * @desc recibe el componente padre y habilita todos los campos de entrada
	 *       que hay en el
	 */
	public static void habilitarCampos(Component componentePadre) {
	
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name limpiarCampos
	 * @param componentePadre
	 * @desc limpia los campos de un formulario
	 */
	public static void limpiarCampos(Component componentePadre) {

	
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name altura
	 * @param valor
	 * @return float
	 * @desc calcula la altura de la imagen reduciendo su valor inicial
	 */
	public static float altura(int valor) {
		float tamano = valor;
		while (tamano > 100) {
			tamano = (tamano / 2);
		}
		return tamano;
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name ancho
	 * @param valor
	 * @return float
	 * @desc calcula el ancho de la imagen reduciendo su valor inicial
	 */
	public static float ancho(int valor) {
		float tamano = valor;
		while (tamano > 200) {
			tamano = (tamano / 2);
		}
		return tamano;
	}

	public static BufferedImage createCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage result = gc.createCompatibleImage(w, h,
				Transparency.TRANSLUCENT);
		Graphics2D g2 = result.createGraphics();
		g2.drawRenderedImage(image, null);
		g2.dispose();
		return result;
	}

	private static BufferedImage resize(BufferedImage image, int width,
			int height) {
		int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image
				.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	public static BufferedImage resizeImage(Image imagen, int width, int height)
			throws Exception {
		BufferedImage image = createCompatibleImage(ImageIO.read(imagen
				.getStreamData()));
		image = resize(image, width, height);
		return image;
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name escalarImagen
	 * @param ancho
	 * @param largo
	 * @desc permite escalar el tama�o de una imagen apartir de la altura y el
	 *       ancho especificado.
	 */
	public static void escalarImagen(int ancho, int largo,
			org.zkoss.image.Image imageSel, org.zkoss.zul.Image icono) {

		ImageIcon icon = null;
		icon = imageSel.toImageIcon();
		java.awt.Image iScala = icon.getImage().getScaledInstance(ancho, largo,
				java.awt.Image.SCALE_SMOOTH);

		Icon icon2 = new ImageIcon(iScala);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(icon2));
		frame.pack();
		BufferedImage bufferedImage = new BufferedImage(icon2.getIconWidth(),
				icon2.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		frame.getContentPane().paint(bufferedImage.getGraphics());
		icono.setContent(bufferedImage);
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name getImagen
	 * @param bytes
	 * @return AImage
	 * @throws IOException
	 * @desc genera una imagen apartir de un array de bytes
	 */
	public static AImage getImagen(byte[] bytes) throws IOException {
		return new AImage("icono", bytes);
	}

	/**
	 * @param objeto
	 * @param consulta
	 * @param bandboxConsulta
	 * @param textboxSec
	 * @param isAsignado
	 */
	public static void onSeleccionarCriterio(IBeanAbstracto objeto,
			String consulta, Textbox textboxSeleccion, boolean isAsignado)
			throws Exception {
		log.info("Ejecutando el metodo[onSeleccionarCriterio]");

		if (isAsignado) {
			objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
					.obtenerRegistro(consulta, objeto);
		} else {
			ParametrizacionFac.getFacade().obtenerRegistro(consulta, objeto);
		}

		textboxSeleccion.setValue("[" + objeto.getCodigo() + "] "
				+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
		textboxSeleccion.setDisabled(true);
		textboxSeleccion.setAttribute("SECUENCIA_CRITERIO_SELECCION", ""
				+ objeto.getPrimaryKey());
		textboxSeleccion.setAttribute("codigo", objeto.getCodigo());
		textboxSeleccion.setAttribute("nombre", objeto.getNombre());
		textboxSeleccion.getParent().setVisible(true);

	}

	public static void onSeleccionarCriterio(IBeanAbstracto objeto,
			String consulta, Textbox textboxSeleccion, boolean isAsignado,
			boolean columnaCodigo, boolean columnaNombre) throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");

		if (isAsignado) {
			objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
					.obtenerRegistro(consulta, objeto);
		} else {
			ParametrizacionFac.getFacade().obtenerRegistro(consulta, objeto);
		}
		if (columnaCodigo && columnaNombre) {
			textboxSeleccion.setValue("[" + objeto.getCodigo() + "] "
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
			textboxSeleccion.setTooltiptext("[" + objeto.getCodigo() + "] "
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
		} else if (columnaCodigo) {
			textboxSeleccion.setValue("[" + objeto.getCodigo() + "]");
			textboxSeleccion.setTooltiptext("[" + objeto.getCodigo() + "]");
		} else if (columnaNombre) {
			textboxSeleccion.setValue(objeto.getNombre());
			textboxSeleccion.setTooltiptext(objeto.getNombre());
		}
		textboxSeleccion.setValue("" + objeto.getPrimaryKey());
		textboxSeleccion.setAttribute("codigo", objeto.getCodigo());
		textboxSeleccion.setAttribute("nombre", objeto.getNombre());
		textboxSeleccion.setDisabled(true);
		textboxSeleccion.setAttribute("SECUENCIA_CRITERIO_SELECCION", ""
				+ objeto.getPrimaryKey());
		textboxSeleccion.getParent().setVisible(true);

	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name onSeleccionar
	 * @param objeto
	 * @param consulta
	 * @param bandboxConsulta
	 * @param textboxSec
	 * @desc se activa al seleccionar un objeto de un bandbox
	 */
	public static void onSeleccionar(IBeanAbstracto objeto, String consulta,
			Bandbox bandboxConsulta, Textbox textboxSec, boolean isAsignado)
			throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");
		bandboxConsulta.setTooltiptext("");

		if (isAsignado) {
			objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
					.obtenerRegistro(consulta, objeto);
		} else {
			ParametrizacionFac.getFacade().obtenerRegistro(consulta, objeto);
		}
		log.info(objeto);
		if (objeto instanceof Persona) {
			bandboxConsulta.setValue(objeto.toString());
			bandboxConsulta.setTooltiptext(objeto.toString());
		} else {
			bandboxConsulta.setValue((objeto.getCodigo() != null ? "["
					+ objeto.getCodigo() + "] " : "")
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
			bandboxConsulta.setTooltiptext((objeto.getCodigo() != null ? "["
					+ objeto.getCodigo() + "] " : "")
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
		}

		textboxSec.setValue("" + objeto.getPrimaryKey());
		textboxSec.setAttribute("codigo", objeto.getCodigo());
		textboxSec.setAttribute("nombre", objeto.getNombre());
		textboxSec.setAttribute("objeto", objeto);

	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name onSeleccionar
	 * @param objeto
	 * @param consulta
	 * @param bandboxConsulta
	 * @param textboxSec
	 * @desc se activa al seleccionar un objeto de un bandbox
	 */
	public static IBeanAbstracto onSeleccionar(IBeanAbstracto objeto,
			String consulta, Bandbox bandboxConsulta, Textbox textboxSec,
			boolean isAsignado, String... valores) throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");

		bandboxConsulta.setTooltiptext("");

		if (isAsignado) {
			objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
					.obtenerRegistro(consulta, objeto);
		} else {
			ParametrizacionFac.getFacade().obtenerRegistro(consulta, objeto);
		}
		log.info(objeto);
		if (objeto instanceof Persona) {
			bandboxConsulta.setValue(objeto.toString());
			bandboxConsulta.setTooltiptext(objeto.toString());
		} else {
			bandboxConsulta.setValue((objeto.getCodigo() != null ? "["
					+ objeto.getCodigo() + "] " : "")
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
			bandboxConsulta.setTooltiptext((objeto.getCodigo() != null ? "["
					+ objeto.getCodigo() + "] " : "")
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
		}

		textboxSec.setValue("" + objeto.getPrimaryKey());
		textboxSec.setAttribute("codigo", objeto.getCodigo());
		textboxSec.setAttribute("nombre", objeto.getNombre());
		textboxSec.setAttribute("objeto", objeto);

		return objeto;
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name onSeleccionar
	 * @param objeto
	 * @param consulta
	 * @param bandboxConsulta
	 * @param textboxSec
	 * @desc se activa al seleccionar un objeto de un bandbox
	 */
	public static void onSeleccionar(IBeanAbstracto objeto,
			Bandbox bandboxConsulta, Textbox textboxSec, boolean isAsignado)
			throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");

		bandboxConsulta.setTooltiptext("");

		if (objeto instanceof Persona) {
			bandboxConsulta.setValue(objeto.toString());
			bandboxConsulta.setTooltiptext(objeto.toString());
		} else {
			bandboxConsulta.setValue((objeto.getCodigo() != null ? "["
					+ objeto.getCodigo() + "] " : "")
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
			bandboxConsulta.setTooltiptext((objeto.getCodigo() != null ? "["
					+ objeto.getCodigo() + "] " : "")
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
		}

		textboxSec.setValue("" + objeto.getPrimaryKey());
		textboxSec.setAttribute("codigo", objeto.getCodigo());
		textboxSec.setAttribute("nombre", objeto.getNombre());
		textboxSec.setAttribute("objeto", objeto);

	}

	public static void onSeleccionar(IBeanAbstracto objeto, String consulta,
			Bandbox bandboxConsulta, Textbox textboxSec, boolean isAsignado,
			boolean columnaCodigo, boolean columnaNombre) throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");

		bandboxConsulta.setTooltiptext("");

		if (isAsignado) {
			objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
					.obtenerRegistro(consulta, objeto);
		} else {
			ParametrizacionFac.getFacade().obtenerRegistro(consulta, objeto);
		}
		if (columnaCodigo && columnaNombre) {
			bandboxConsulta.setValue("[" + objeto.getCodigo() + "] "
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
			bandboxConsulta.setTooltiptext("[" + objeto.getCodigo() + "] "
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
		} else if (columnaCodigo) {
			bandboxConsulta.setValue("[" + objeto.getCodigo() + "]");
			bandboxConsulta.setTooltiptext("[" + objeto.getCodigo() + "]");
		} else if (columnaNombre) {
			bandboxConsulta.setValue(objeto.getNombre());
			bandboxConsulta.setTooltiptext(objeto.getNombre());
		}
		textboxSec.setValue("" + objeto.getPrimaryKey());
		textboxSec.setAttribute("codigo", objeto.getCodigo());
		textboxSec.setAttribute("nombre", objeto.getNombre());
		textboxSec.setAttribute("objeto", objeto);

	}

	public static void onSeleccionar(Long secuencia, String consulta,
			Bandbox bandboxConsulta, Textbox textboxSec) throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");

		bandboxConsulta.setTooltiptext("");
		Object objeto = new Object();
		objeto = ParametrizacionFac.getFacade().obtenerRegistro(consulta,
				secuencia);
		StringBuffer buffer = new StringBuffer();
		if (((IBeanAbstracto) objeto).getCodigo() != null) {
			buffer.append("[").append(((IBeanAbstracto) objeto).getCodigo())
					.append("]");
		}

		if (((IBeanAbstracto) objeto).getNombre() != null) {
			buffer.append(((IBeanAbstracto) objeto).getNombre());
		}
		bandboxConsulta.setValue(buffer.toString());
		bandboxConsulta.setTooltiptext(buffer.toString());
		textboxSec.setValue("" + ((IBeanAbstracto) objeto).getPrimaryKey());
		textboxSec.setAttribute("objeto", objeto);

	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name onSeleccionar
	 * @param objeto
	 * @param consulta
	 * @param bandboxConsulta
	 * @param textboxSec
	 * @param isAsignado
	 */
	public static void onSeleccionar(IBeanAbstracto objeto, String consulta,
			Bandbox bandboxConsulta, boolean isAsignado) throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");

		bandboxConsulta.setTooltiptext("");

		if (isAsignado) {
			objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
					.obtenerRegistro(consulta, objeto);
		} else {
			ParametrizacionFac.getFacade().obtenerRegistro(consulta, objeto);
		}

		bandboxConsulta.setValue("[" + objeto.getCodigo() + "] "
				+ objeto.getNombre());
		bandboxConsulta.setTooltiptext("[" + objeto.getCodigo() + "] "
				+ objeto.getNombre());
		bandboxConsulta.setAttribute("dto", objeto);

		bandboxConsulta.close();

	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name onSeleccionar
	 * @param objeto
	 * @param consulta
	 * @param bandboxConsulta
	 * @param textboxSec
	 * @desc se activa al seleccionar un objeto de un bandbox
	 */
	public static void onSeleccionarDinamic(IBeanAbstracto objeto, String sql,
			Bandbox bandboxConsulta, Textbox textboxSec, boolean columnaCodigo,
			boolean columnaNombre) throws Exception {

		log.info("Ejecutando el metodo [ onSeleccionar ]... ");

		bandboxConsulta.setTooltiptext("");

		objeto = (IBeanAbstracto) ParametrizacionFac.getFacade()
				.obtenerDinamico(objeto, sql);

		if (columnaCodigo && columnaNombre) {
			bandboxConsulta.setValue("[" + objeto.getCodigo() + "] "
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
			bandboxConsulta.setTooltiptext("[" + objeto.getCodigo() + "] "
					+ (objeto.getNombre() != null ? objeto.getNombre() : ""));
		} else if (columnaCodigo) {
			bandboxConsulta.setValue("[" + objeto.getCodigo() + "]");
			bandboxConsulta.setTooltiptext("[" + objeto.getCodigo() + "]");
		} else if (columnaNombre) {
			bandboxConsulta.setValue(objeto.getNombre());
			bandboxConsulta.setTooltiptext(objeto.getNombre());
		}
		textboxSec.setValue("" + objeto.getPrimaryKey());
		textboxSec.setAttribute("codigo", objeto.getCodigo());
		textboxSec.setAttribute("nombre", objeto.getNombre());
		textboxSec.setAttribute("objeto", objeto);

	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name validarCampos
	 * @param componentes
	 * @return
	 * @throws InterruptedException
	 * @desc devuelve false si uno o mas campos de la lista tienen el value ""
	 */
	public static boolean validarCampos(List<Component> componentes) {
		boolean sw = true;

		for (Component c : componentes) {
			if (c instanceof Grid) {
				Grid componenteOriginal = (Grid) c;
				if (componenteOriginal.getRows().getChildren().size() == 0) {
					sw = false;
				}
			} else if (getValue(c).trim().isEmpty()) {
				sw = false;
			}
		}

		return sw;
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name crearFila
	 * @param componentes
	 * @return
	 * @throws InterruptedException
	 * @desc construlle una fila a partir de una lista de componentes
	 */
	public static Listitem crearFila(List<Component> componentes) {
		Listitem item = new Listitem();

		for (Component c : componentes) {
			Listcell celda = new Listcell(getValue(c));
			celda.setValue(c.getId());
			item.appendChild(celda);
		}

		return item;
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name xmlToString
	 * @param Element
	 * @desc metodo que convierte un objeto element en un string
	 */
	

	/**
	 * @Class Utilidades.java
	 * @name limpiarHistoricoMensajes
	 * @return void
	 * @desc Este m�todo se encarga de limpiar la grilla que contiene el
	 *       historico de mensajes el parametro es utilizado en el metodo
	 *       setMensajehistorico para que limpie la grilla antes de escribir en
	 *       ella.
	 */
	public static void limpiarHistoricoMensajes(Grid grilla,
			String idGridHistorico, String idGroupHistorico, Component c) {

		Groupbox groupinformacion = null;

		if (grilla == null) {
			grilla = (Grid) c.getFellow(idGridHistorico);
			// ocultamos el groupbox que contiene
			// la grilla
			groupinformacion = (Groupbox) c.getFellow(idGroupHistorico);
			groupinformacion.setVisible(false);

		}
		grilla.getRows().getChildren().clear();
	}

	

	/**
	 * metodo que convierte un String a un Document JDOM
	 * 
	 * @param xml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */


	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name getValue
	 * @param c
	 * @return
	 * @throws InterruptedException
	 * @desc recibe un componente y devuelve el valor contenido en el como
	 *       String
	 */
	public static String getValue(Component c) {
		String valor = "";

		if (c instanceof InputElement) {
			if (c instanceof Datebox) {
				Datebox componente = (Datebox) c;
				valor = componente.getRawText();

				if (!valor.trim().isEmpty()) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
					try {
						valor = format.format(componente.getValue());
					} catch (Exception ex) {
						return "";
					}
				}
			} else {
				InputElement componente = (InputElement) c;
				if (componente.getRawValue() != null) {
					valor = componente.getRawValue().toString();
				} else {
					valor = "";
				}

			}
		} else if (c instanceof Label) {
			Label componente = (Label) c;
			valor = componente.getValue();
		} else if (c instanceof Checkbox) {
			Checkbox componente = (Checkbox) c;
			valor = "" + componente.isChecked();
		} else {
			Object obj;
			try {
				obj = c.getClass().getMethod("getSelectedItem").invoke(c);
				obj = obj.getClass().getMethod("getValue").invoke(obj);
				valor = obj.toString();
			} catch (NoSuchMethodException e) {
				log.error("Componente NO soportado para la extracci�n de valores "
						+ c.toString());
			} catch (Exception e) {
				log.error(e);
			}
		}

		return valor;
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name setValue
	 * @param c
	 * @param valor
	 * @desc modifica el valor de un componente
	 */
	@SuppressWarnings("deprecation")
	public static void setValue(Component c, String valor) {
		if (c instanceof InputElement) {
			InputElement componente = (InputElement) c;
			if (componente instanceof Datebox) {
				if (valor.trim().isEmpty()) {
					((Datebox) componente).setText("");
				} else {
					((Datebox) componente).setValue(new Date(valor));
				}
			} else if (componente instanceof Decimalbox) {
				if (valor.trim().isEmpty()) {
					((Decimalbox) componente).setRawValue(null);
				} else {
					((Decimalbox) componente).setValue(new BigDecimal(valor));
				}
			} else {
				componente.setRawValue(valor);
			}
		
		} else if (c instanceof Listbox) {
			Utilidades.seleccionarListitem((Listbox) c, valor);
		} else if (c instanceof Radiogroup) {
			Utilidades.seleccionarRadio((Radiogroup) c, valor);
		} else if (c instanceof Checkbox) {
			Checkbox componente = (Checkbox) c;
			componente.setChecked(Boolean.parseBoolean(valor));
		}
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name ocultarMostrarHijos
	 * @param padre
	 * @param mostrar
	 * @desc Oculta o muestra los hijos de un componente. El primer parametro es
	 *       el componente y en el segundo true muestra, false oculta
	 */
	public static void ocultarMostrarHijos(Component padre, boolean mostrar) {
		List<Component> componentes = padre.getChildren();

		for (Component componente : componentes) {
			componente.setVisible(mostrar);
		}
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name seleccionarRadio
	 * @param radiogroup
	 * @param value
	 * @desc Selecciona un item en el radiogroup
	 */
	public static void seleccionarRadio(Radiogroup radiogroup, String value) {
		List<Radio> radios = radiogroup.getItems();
		for (Radio radio : radios) {
			if (radio.getValue().equals(value)) {
				radiogroup.setSelectedItem(radio);
				break;
			}
		}
	}

	/**
	 * @type M�todo de la clase Utilidades.java
	 * @name seleccionarListitem
	 * @param listbox
	 * @param value
	 * @desc Selecciona un item en el listbox
	 */
	public static void seleccionarListitem(Listbox listbox, String value) {
		List<Listitem> items = listbox.getItems();
		for (Listitem item : items) {
			if (item.getValue().equals(value)) {
				listbox.setSelectedItem(item);
				break;
			}
		}
	}

	


	
	public static IActionListbox onCargarTabDetalle(Tabpanel contenedor,
			String zulTab, Map<String, Object> arg, IActionListbox action,
			boolean estadoEdicion, boolean estadoRegistro) throws Exception {
		log.info("Ejecutando el metodo[onCargarTabDetalle] ");

//		arg.put("ESTADO_REGISTRO", estadoRegistro);
//		arg.put("ESTADO_EDICION", estadoEdicion);
//
//		if (contenedor.getChildren().size() == 0) {
//			AATEjecutable detalle = obtenerDetalle(zulTab);
//			action = (IActionListbox) Executions.createComponentsDirectly(
//					detalle.getFuente(), "zul", contenedor, arg);
//		} else {
//			action.setParametros(arg);
//			action.getParameterMap();
//			action.initOtherParameter();
//			action.onListarDetalle();
//		}
		return action;
	}

	public static IActionGrid2 onCargarTabDetalle(Tabpanel contenedor,
			String zulTab, Map<String, Object> arg, IActionGrid2 action,
			boolean estadoEdicion, boolean estadoRegistro) throws Exception {
		log.info("Ejecutando el metodo[onCargarTabDetalle] ");

//		arg.put("ESTADO_REGISTRO", estadoRegistro);
//		arg.put("ESTADO_EDICION", estadoEdicion);
//
//		if (contenedor.getChildren().size() == 0) {
//			AATEjecutable detalle = obtenerDetalle(zulTab);
//			action = (IActionGrid2) Executions.createComponentsDirectly(
//					detalle.getFuente(), "zul", contenedor, arg);
//		} else {
//			action.setParametros(arg);
//			action.getParameterMap();
//			action.initOtherParameter();
//			action.onListarDetalle();
//		}
		return action;
	}

	/**
	 * Metodo para asignar la propiedad {@link Button#setAutodisable(String)} a
	 * todos los componentes botones que se encuentren en el contenedor. Pueden
	 * ser hijos o hijos de los hijos, teniendo en cuenta que solo se aplica la
	 * propiedad a aquellos que no se encuentren contendidos dentro del array de
	 * excluyentes
	 * 
	 * @param contenedor
	 *            Es el componente donde se encuentran los botones hijos
	 * @param excluyentes
	 *            vector de componentes a los caules no se le aplica la
	 *            propiedad autodisable
	 * 
	 */

	public static void autodisableButtons(Component contenedor,
			Component... excluyentes) {
		log.info("[info]  ====> llamada al metodo @autodisableButtons");

		Map<String, String> mapaExcluyentes = new HashMap<String, String>();
		if (excluyentes != null) {
			for (Component exc : excluyentes) {
				if (exc.getId() != null && !exc.getId().isEmpty())
					mapaExcluyentes.put(exc.getId(), exc.getId());
			}
		}
		Collection<Component> listaHijos = contenedor.getFellows();
		StringBuffer stringBuffer = new StringBuffer();
		List<Component> listaBotonesAplica = new ArrayList<Component>();
		String autodisable = getAutodisableButtons(stringBuffer, listaHijos,
				mapaExcluyentes, listaBotonesAplica);

		for (int i = 0; i < listaBotonesAplica.size(); i++) {
			Button botonChild = (Button) listaBotonesAplica.get(i);
			botonChild.setAutodisable(autodisable);

		}
	}

	/**
	 * Metodo recursivo para armar el autodisable de los botones teniendo en
	 * cuenta la exclusion definida en el mapa de excluyentes. Cada boton que
	 * aplica lo va agregando a la lista listaBotonesAplica para despues hacer
	 * la asignacion.
	 * 
	 * @param stringBuffer
	 *            Variable que va concatenando los ids de los componentes
	 *            botones que si aplican la propiedad autodisable
	 * @param listaHijos
	 *            lista de componentes a evaluar teniendo en cuenta solo las
	 *            instancias de botones
	 * @param mapaExcluyentes
	 *            mapa de componentes con su respectiva id como llave del mapa a
	 *            los cuales no se le aplica la propiedad autodisable
	 * @param listaBotonesAplica
	 *            Lista de botones encontrados a los cuales le aplicara la
	 *            opcion autodisable
	 * @return
	 */
	private static String getAutodisableButtons(StringBuffer stringBuffer,
			Collection<Component> listaHijos,
			Map<String, String> mapaExcluyentes,
			List<Component> listaBotonesAplica) {

		for (Component child : listaHijos) {
			if (child instanceof Button) {
				if (child.getId() != null && !child.getId().isEmpty()) {
					if (!mapaExcluyentes.containsKey(child.getId())) {
						stringBuffer.append(child.getId()).append(",");
						listaBotonesAplica.add(child);
					}
				}
			} else {
				if (child.getChildren().size() > 0) {
					getAutodisableButtons(stringBuffer, child.getChildren(),
							mapaExcluyentes, listaBotonesAplica);
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * M�todo utilidad para usar en los macro-componentes
	 * 
	 * @param exception
	 */
	public static void lanzarExcepcion(Excepcion exception) {
//		log.info("ejecutando [ lanzarExcepcion ]... ");
//		log.error(exception.getMessage(), exception);
//		ExcepcionPopupAction win = null;
//		if (Executions.getCurrent().getDesktop().getFirstPage()
//				.hasFellow("mensajeExcepcion"))
//			Executions.getCurrent().getDesktop().getFirstPage()
//					.getFellow("mensajeExcepcion").detach();
//		win = (ExcepcionPopupAction) Executions.createComponents(
//				IExcepcion.PANTALLA_EXCEPCIONPOPUP, null, null);
//		// ---mandamos la excepcion
//		win.setException(exception);
//		// --mostramos la pantalla
//		win.doModal();
	}

	/**
	 * @param listadoTotal
	 *            Lista con listas paginadas
	 * @param limite
	 * @return List<List<Object>>
	 */
	public static List<List<Object>> getListadoPaginado(
			List<Object> listadoTotal, int limite) {

		List<List<Object>> listadoPaginado = new ArrayList<List<Object>>();
		if (listadoTotal.size() <= limite) {
			listadoPaginado.add(listadoTotal);
		} else {
			Integer bloques = (int) (listadoTotal.size() / limite);
			Integer modular = (listadoTotal.size() % limite);

			for (int i = 0; i < bloques; i++) {
				listadoPaginado.add(listadoTotal.subList(i * limite,
						(i * limite) + limite));
			}

			if (modular != 0) {
				listadoPaginado.add(listadoTotal.subList((limite * bloques),
						listadoTotal.size()));
			}

		}
		return listadoPaginado;
	}

	/**
	 * M�todo que permite retorno el n�mero con el formato de separador de
	 * decimales de acuerdo a lo configurado en el archivo de propiedades
	 * 
	 * @param numero
	 *            al cual se le aplicara el formato especificado en el archivo
	 *            de propiedades.
	 * @return {@code String}
	 */
	public static String getStringDecimal(BigDecimal numero) {

		String retorno = "";
		if (numero != null) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			String formatoDecimal = PropiedadesHelper.getInstance()
					.getPropiedad("ger.formato_decimal");
			DecimalFormat format = new DecimalFormat(formatoDecimal);

			String separadorDecimal = PropiedadesHelper.getInstance()
					.getPropiedad("ger.signo_decimal");

			if (separadorDecimal != null && separadorDecimal.equals(".")) {
				symbols.setDecimalSeparator('.');
				symbols.setGroupingSeparator(',');
				format.setDecimalFormatSymbols(symbols);
				retorno = format.format(numero);
			} else if (separadorDecimal != null && separadorDecimal.equals(",")) {
				symbols.setDecimalSeparator(',');
				symbols.setGroupingSeparator('.');
				format.setDecimalFormatSymbols(symbols);
				retorno = format.format(numero);
			}

		}
		return retorno;
	}

	public static String getStringDecimalEspecifico(BigDecimal numero) {

		String retorno = "";
		if (numero != null) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			String formatoDecimal = PropiedadesHelper.getInstance()
					.getPropiedad("ger.formato_decimal_especifico");
			DecimalFormat format = new DecimalFormat(formatoDecimal);

			String separadorDecimal = PropiedadesHelper.getInstance()
					.getPropiedad("ger.signo_decimal");

			if (separadorDecimal != null && separadorDecimal.equals(".")) {
				symbols.setDecimalSeparator('.');
				symbols.setGroupingSeparator(',');
				format.setDecimalFormatSymbols(symbols);
				retorno = format.format(numero);
			} else if (separadorDecimal != null && separadorDecimal.equals(",")) {
				symbols.setDecimalSeparator(',');
				symbols.setGroupingSeparator('.');
				format.setDecimalFormatSymbols(symbols);
				retorno = format.format(numero);
			}

		}
		return retorno;
	}

	public static void limpiarListboxConsultas(Listbox idLista) {
		// --- obtenemos el contenedor del
		// formulario
		// ZKProcesosComunesHelper zkHelper = new ZKProcesosComunesHelper();

		// -limpiamos la lista
		ZKProcesosComunesHelper.limpiarListboxConsultas(idLista);
	}

	/**
	 * @name setDesabilitarComponentesListbox
	 * @param componente
	 *            ListBox
	 * @param set
	 */

	public static void setDeshabilitarComponentesListbox(Component componente,
			boolean set) {
		log.info("ejecutando [ setDesabilitarComponentesListbox ]... ");
		if (componente instanceof Listbox) {
			log.info("Listbox");
			Listbox componenteOriginal = (Listbox) componente;
			for (int i = 0; i < componenteOriginal.getItems().size(); i++) {
				if (componenteOriginal.getItemAtIndex(i) instanceof Listitem) {
					log.info("Listitem");
					Listitem listitem = (Listitem) componenteOriginal
							.getItemAtIndex(i);
					for (int j = 0; j < listitem.getChildren().size(); j++) {

						if (listitem.getChildren().get(j) instanceof Listcell) {
							log.info("Listcell");
							Listcell listcell = (Listcell) listitem
									.getChildren().get(j);
							log.info(listcell.getChildren().size() + "");
							for (int k = 0; k < listcell.getChildren().size(); k++) {
								log.info("Listcell2");
								if (listcell.getChildren().get(k) instanceof Textbox) {
									log.info("Textbox");
									((Textbox) listcell.getChildren().get(k))
											.setDisabled(set);
									log.info("ejecutando [ .setDisabled(true); ]... ");
								}
								if (listcell.getChildren().get(k) instanceof Intbox) {
									((Intbox) listcell.getChildren().get(k))
											.setDisabled(set);
								}
								if (listcell.getChildren().get(k) instanceof Chosenbox) {
									((Chosenbox) listcell.getChildren().get(k))
											.setDisabled(set);
								}
								if (listcell.getChildren().get(k) instanceof Button) {
									((Button) listcell.getChildren().get(k))
											.setDisabled(set);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @name setAtributoContexto
	 * @param String
	 *            atributo, String valor. Método para setear valores en el
	 *            Contexto que serán recibido en el motor de base de datos al
	 *            realizar transacciones, estos parámetros serán borrados al
	 *            limpiar el contexto
	 */
	public static void setAtributoContexto(String atributo, String valor) {
		log.info("Ejecutando el metodo: [ setAtributoContexto ]");
		log.debug("setAtributoContexto(atributo => " + atributo + ", valor => "
				+ valor + ")");

		try {
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("ATRIBUTO", atributo);
			parameters.put("VALOR", valor);

			ParametrizacionFac.getFacade().obtenerRegistro(
					"AAP_setAtributoContexto", parameters);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void levantarContexto() {
		log.info("Ejecutando el metodo: [ levantarContexto ]");
//		AATCuentaIceberg usuarioSesion = Utilidades.getUsuarioEnSesion();
//		if (usuarioSesion != null
//				&& usuarioSesion.getSecCuentaIceberg() != null)
//			Utilidades.setAtributoContexto("SEC_CUENTA_ICEBERG", usuarioSesion
//					.getSecCuentaIceberg().toString());
	}

	public static String darFormatoDescripcion(String descripcion){
		Pattern patron = Pattern.compile("[ x0Bf]+[.]+");
		Pattern patron2 = Pattern.compile("([:]{1}[\\s]*[=]{1})|([=]{1})");
		// Now create matcher object.
		Matcher m = patron.matcher(descripcion);
		String resultado = m.replaceAll(" 0.");
		Matcher m2 = patron2.matcher(resultado);
		String result = m2.replaceAll(":");
		descripcion = result.toUpperCase();
		return descripcion;
	}
	
	
	public static void setLimpiarComponentesListbox(Component componente) {
		log.info("ejecutando [ setDesabilitarComponentesListbox ]... ");
		if (componente instanceof Listbox) {
			log.info("Listbox");
			Listbox componenteOriginal = (Listbox) componente;
			for (int i = 0; i < componenteOriginal.getItems().size(); i++) {
				if (componenteOriginal.getItemAtIndex(i) instanceof Listitem) {
					log.info("Listitem");
					Listitem listitem = (Listitem) componenteOriginal
							.getItemAtIndex(i);
					for (int j = 0; j < listitem.getChildren().size(); j++) {

						if (listitem.getChildren().get(j) instanceof Listcell) {
							log.info("Listcell");
							Listcell listcell = (Listcell) listitem
									.getChildren().get(j);
							log.info(listcell.getChildren().size() + "");
							for (int k = 0; k < listcell.getChildren().size(); k++) {
								log.info("Listcell2");
								if (listcell.getChildren().get(k) instanceof Textbox) {
									log.info("Textbox");
									((Textbox) listcell.getChildren().get(k))
											.setRawValue("");
									log.info("ejecutando [ .setDisabled(true); ]... ");
								}
								if (listcell.getChildren().get(k) instanceof Intbox) {
									((Intbox) listcell.getChildren().get(k))
									.setRawValue("");
								}
								
							}
						}
					}
				}
			}
		}
	}
}