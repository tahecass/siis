package com.siis.viewModel.framework;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.jfree.util.Log; 
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import com.siis.dto.Usuario;

import fi.jawsy.jawwa.zk.gritter.Gritter;

public class Utilidades {
	protected static Logger log = Logger.getLogger(Utilidades.class);
	private static Usuario usuario;
	private static Session session;

	public static Component onCargarVentana(Component contenedor, String rutaForma, Map<String, Object> arg)
			throws Exception {

		return Executions.createComponents(rutaForma, contenedor, arg);
	}

	public static void mostrarNotificacion(String titulo, String msg, String tipoNotificacion) {
		String urlImage = "";
		if (tipoNotificacion.equals("INFO")) {
			urlImage = "imagenes/informacion.png";
		} else if (tipoNotificacion.equals("ADVERTENCIA")) {
			urlImage = "imagenes/advertencia.png";
		} else if (tipoNotificacion.equals("ERROR")) {
			urlImage = "imagenes/error.png";
		}
		Gritter.notification().withTitle(titulo).withText(msg).withTime(5000).withImage(urlImage).show();

	}

	public static boolean validarFormulario(AbstractComponent idComponente) {
		// -validamos el formulario
		return validarCampos(idComponente, InputElement.class);
	}

	public static boolean validarCampos(AbstractComponent componentePadre, Class<InputElement> filtro) {
		// --obtenemos los componentes correspondientes al filtro
		List<Component> listaComponentes = getComponentes(componentePadre, filtro);

		for (Iterator<Component> iteradorComponentes = listaComponentes.iterator(); iteradorComponentes.hasNext();) {
			Component componente = iteradorComponentes.next();
			/*
			 * si es un componente de tipo entrada de datos entonces se valida
			 * para otra clase de componentes es necesario imlplementar sus
			 * respectivas validaociones
			 */
			if (componente instanceof InputElement) {
				InputElement componenteValidar = (InputElement) componente;

				// se verifica su validez...
				if (!componenteValidar.isValid()) {
					log.trace("Componente invalido -- " + componenteValidar.getId());
					componenteValidar.setFocus(true);
					return false; // --> hubo un error en el formulario
				}
			}
		} // fin for
		return true;
	}

	public static List<Component> getComponentes(AbstractComponent componentePadre, Class<?> filtro) {
		// --obtenemos los componentes correspondientes al filtro
		return getComponentes(new ArrayList<Component>(), componentePadre, filtro);
	}

	private static List<Component> getComponentes(List<Component> listaComponentesFiltrada,
			AbstractComponent componentePadre, Class<?> filtro) {

		List<Component> listaComponentesHijos = componentePadre.getChildren();
		AbstractComponent componenteHijo = null;

		// -- validamos que la lista no esta vacia
		if (listaComponentesFiltrada == null)
			listaComponentesFiltrada = new ArrayList<Component>();

		for (int i = 0; i < listaComponentesHijos.size(); i++) {
			componenteHijo = (AbstractComponent) listaComponentesHijos.get(i);

			// --si es un componente del tipo del filtro...
			if (filtro != null && filtro.isInstance(componenteHijo)) {
				// log.info("hijo " + i + " -> " + componenteHijo.getId() +
				// " clase: " + componenteHijo.getClass());
				listaComponentesFiltrada.add(componenteHijo);
			} else if (filtro == null) // si no hay filtros entonces le mandamos
			// todos...
			{
				// log.info("hijo " + i + " -> " + componenteHijo.getId() +
				// " clase: " + componenteHijo.getClass());
				listaComponentesFiltrada.add(componenteHijo);
			}

			// --si el componente es un contenedor de m�s objetos entonces..
			// los
			// invocamos tambien a ellos
			if (componenteHijo.getChildren().size() != 0) {
				// CUIDADO!!! recursividad a la vista..
				getComponentes(listaComponentesFiltrada, componenteHijo, filtro);
			}
		} // fin for
		return listaComponentesFiltrada;
	}

	public static void onCargaEmergente(String ruta, Map<String, Object> parametros) {
		log.info("onCargarDetalle");

		try {

			Window wind = (Window) Utilidades.onCargarVentana(null, ruta, parametros);
			wind.setPosition("center");
			wind.doModal();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String formatFecha(Date date, String formato) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(formato);

		if (date != null) {
			return dateFormat.format(date);
		}

		return "";
	}

	public static Usuario obtenerUsuarioSesion() {
		log.info("obtenerUsuarioSesion");
		session = Executions.getCurrent().getDesktop().getSession();

		if (session.getAttribute("usuario") != null) {
			usuario = (Usuario) session.getAttribute("usuario");
			Log.info("uSUARIOOO NO ES NULL==>");
		}
		return usuario;
	}
	
	
	public static String ByteToHex(byte byteData[]){
		//convert the byte to hex format
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<byteData.length;i++) {
			String hex=Integer.toHexString(0xff & byteData[i]);
		 	if(hex.length()==1) hexString.append("0");
		 	hexString.append(hex);
		}
		
		return hexString.toString().toUpperCase();		
	}
	
	public static byte[] HexToByte(String hex){
		return new BigInteger(hex, 16).toByteArray();
	}	
	
//	public static String encrypt(String Data) throws Exception {
//		Key key = new SecretKeySpec(IConstantes.AES_KEY.getBytes("UTF-8"), IConstantes.AES_FACTORY);
//		Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING"); 
//
//		cipher.init(Cipher.ENCRYPT_MODE, key);
//
//        byte[] byteData = cipher.doFinal(Data.getBytes("UTF-8"));
//        
//        return ByteToHex(byteData);   
//    }

//    public static String decrypt(String data) throws Exception {
//		Key key = new SecretKeySpec(IConstantes.AES_KEY.getBytes("UTF-8"), IConstantes.AES_FACTORY);
//
//    	Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING"); 
//		
//		cipher.init(Cipher.DECRYPT_MODE, key);
//
//        byte[] decValue = cipher.doFinal(HexToByte(data));
//
//        return new String(decValue);
//    }
    
	public static String getMD5(String password) throws Exception{
		Log.info("Ejecutando el metodo getMD5 "+password);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes("UTF-8"));
 
		return ByteToHex(md.digest()); 
    }	
 

}
