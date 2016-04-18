package com.siis.viewModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.siis.configuracion.Conexion;
import com.siis.dto.Cartera;
import com.siis.dto.Cliente;
import com.siis.dto.DetalleCartera;
import com.siis.dto.Efs;
import com.siis.viewModel.framework.Utilidades;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@SuppressWarnings("serial")
public class CargaMasivaCarteraViewModel extends Window {
	protected static Logger log = Logger.getLogger(CargaMasivaCarteraViewModel.class);
	public List<Efs> listaEfs;
	public Efs EfSeleccionado;

	Conexion con;
	List<DetalleCartera> lista;
	Cliente cliente = new Cliente();

	@Wire
	private Grid idWINFORMEFSZGridFormulario;
	Cartera cartera;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		listaEfs = new ArrayList<Efs>();
		EfSeleccionado = new Efs();

		lista = new LinkedList<DetalleCartera>();
	}

	private boolean validarSoloExtensio(String mimeType) {
		log.debug("Ejecutando M�todo [ validarSoloExtensio ]");

		if (mimeType.trim().toLowerCase().equals("application/xls")
				|| mimeType.trim().toLowerCase().contains("excel")) {
			return true;
		}

		return false;

	}

	@NotifyChange("*")
	@Command
	public void subirArchivo(@BindingParam("media") Media media) {
		log.debug("Ejecutando Método [ subirArchivo ]");

		log.trace("blob ==>" + media);
		log.trace("MimeType ==>" + media.getContentType());

		try {

			if (!validarSoloExtensio(media.getContentType())) {
				Messagebox.show("El tipo de Archivo seleccionado, no corresponde a un archivo con formato XLS ", "",
						Messagebox.ABORT, Messagebox.ERROR);
				return;
			}

			byte[] blob;
			// if (media.getContentType().contains("text/"))
			// blob = media.getStringData().getBytes();
			// else
			blob = IOUtils.toByteArray(media.getStreamData());

			if (blob.length > 0) {

				EfSeleccionado.setContenidoBinarioArchivo(blob);

				InputStream mediais = new ByteArrayInputStream(EfSeleccionado.getContenidoBinarioArchivo());
				leerXls(mediais);

			}

		} catch (IOException e) {
			log.error(e.getCause(), e);
		} catch (Exception e) {
			log.error(e.getCause(), e);
		}

	}

	@NotifyChange("*")
	public void leerXls(InputStream archivo) {
		log.info("Ejecutando el metodo [ leerXls ]");

		Workbook workbook;

		DetalleCartera detalle;
		try {
			// Pasamos el excel que vamos a leer
			workbook = Workbook.getWorkbook(archivo);

			// Seleccionamos la hoja que vamos a leer
			Sheet sheet = workbook.getSheet(0);
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HashMap<String, Object> par = new HashMap<String, Object>();
			cartera = new Cartera();
			// Leer CLiente
			if (!sheet.getCell(1, 0).getContents().isEmpty()) {

				cliente.setNit(sheet.getCell(1, 0).getContents());
				cliente = (Cliente) Conexion.getConexion().obtenerRegistro("obtenerCliente", cliente);
				cartera.setCliente(cliente);

			}

			if (!sheet.getCell(1, 6).getContents().isEmpty()) {
				cartera.setFechaPago(simpleDateFormat.parse(sheet.getCell(1, 6).getContents()));
			}

			java.util.Date date = new java.util.Date();
			cartera.setFechaHoraActualizacion(new Timestamp(date.getTime()));
			cartera.setFechaCreacion(new Date());
//			cartera.setUsuario(Utilidades.obtenerUsuarioSesion());

			// GUARDAR CARTERA Y DETALLES

			par.put("NOMBRE_TABLA", "CARTERAS");
			Integer sigSec = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia", par);

			if (sigSec != null)
				cartera.setSecuencia(sigSec);
			else
				cartera.setSecuencia(1);

			Conexion.getConexion().guardar("guardarCartera", cartera);
			log.info("Carteraguardada");

			// recorremos las filas
			for (int fila = 0; fila < sheet.getRows(); fila++) {

				if (fila > 7) {
					if (!sheet.getCell(0, fila).getContents().isEmpty()) {
						detalle = new DetalleCartera();
						detalle.setCartera(cartera);
						detalle.setNroFactura(sheet.getCell(0, fila).getContents());
						detalle.setVencimiento(simpleDateFormat.parse(sheet.getCell(1, fila).getContents()));
						detalle.setReferencia(sheet.getCell(2, fila).getContents());
						detalle.setTotalCarteraPorVencer(new Double(sheet.getCell(3, fila).getContents()));
						detalle.setValor1(new Double(sheet.getCell(4, fila).getContents()));
						detalle.setValor2(new Double(sheet.getCell(5, fila).getContents()));
						detalle.setValor3(new Double(sheet.getCell(6, fila).getContents()));
						detalle.setValor4(new Double(sheet.getCell(7, fila).getContents()));
						detalle.setFechaActualizacion(new Date());
						detalle.setFechaCreacion(new Date());
						lista.add(detalle);

						par.put("NOMBRE_TABLA", "DETALLE_CARTERA");
						Integer sigSecDet = (Integer) Conexion.getConexion().obtenerRegistro("obtenerSeigSecuencia",
								par);
						if (sigSecDet != null)
							detalle.setSecuencia(sigSecDet);
						else
							detalle.setSecuencia(1);

						Conexion.getConexion().guardar("guardarDetalleCartera", detalle);
						log.info("Detalleguardada");

					}
				}
			}
			setLista(lista);

			Utilidades.mostrarNotificacion("Mensaje", "Se ha guardado la Cartera y sus respectivos detalles ", "INFO");

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Efs getEfSeleccionado() {
		return EfSeleccionado;
	}

	public void setEfSeleccionado(Efs efSeleccionado) {
		EfSeleccionado = efSeleccionado;
	}

	public void setListaEfs(List<Efs> listaEfs) {
		this.listaEfs = listaEfs;
	}

	public List<DetalleCartera> getLista() {
		return lista;
	}

	public void setLista(List<DetalleCartera> lista) {
		this.lista = lista;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cartera getCartera() {
		return cartera;
	}

	public void setCartera(Cartera cartera) {
		this.cartera = cartera;
	}

}