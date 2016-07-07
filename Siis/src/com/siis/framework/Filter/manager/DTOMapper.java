/**
 * 
 */
package com.siis.framework.Filter.manager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.siis.framework.Filter.anottations.Column;
import com.siis.framework.Filter.anottations.Mapper;
import com.siis.framework.Filter.anottations.Relation;
import com.siis.framework.Filter.anottations.RelationFilter;
import com.siis.framework.Filter.dto.ColumnsMapper;
import com.siis.framework.Filter.dto.MapperReport;

/**
 * @author jhoanseve2
 * @name DTOMapper.java
 * @date 21/02/2011
 * @desc
 */

public class DTOMapper {
	protected static Logger log = Logger.getLogger(XMLMapper.class);
	private static DTOMapper dtoMapper;
	private Map<String, MapperReport> mapDTO;

	private DTOMapper() {
		log.info("Ejecutando metodo [ DTOMapper ]");
		// this.loadXmlMapper(); lee el archivo xml

	}

	public static DTOMapper getInstance() throws JDOMException, IOException {
		if (dtoMapper == null) {
			dtoMapper = new DTOMapper();
			dtoMapper.mapDTO = new HashMap<String, MapperReport>();
		}

		return dtoMapper;
	}

	/**
	 * @throws ClassNotFoundException
	 * @type Método de la clase DTOMapper.java
	 * @name init
	 * @desc
	 */
	public void init(String rutaContexto) throws JDOMException, IOException,
			ClassNotFoundException {
		log.info("Ejecutando metodo [ init ], para la carga de los DTO's");

		SAXBuilder saxBuilder = new SAXBuilder(false);
		saxBuilder.setValidation(false);
		saxBuilder.setFeature("http://xml.org/sax/features/validation", false);
		saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		Document document = saxBuilder.build(rutaContexto+"WEB-INF/classes/configuration.xml");

		for (Object object : document.getRootElement().getChild("typeAliases")
				.getChildren()) {
			Class<?> clazz = Class.forName(((Element) object)
					.getAttributeValue("type"));

			if (clazz.isAnnotationPresent(Mapper.class)) {
				Mapper ma = (Mapper) clazz.getAnnotation(Mapper.class);
				MapperReport mr = new MapperReport(ma.label(), ma.id(), ma
						.view(), ma.viewl(),clazz,ma.viewJ(),ma.viewR());
				mapDTO.put(ma.id(), mr);
			}
		}

		 log.info("DTOMapperCargado");
	}

	public List<ColumnsMapper> getListColumnsById(String id)
			throws ClassNotFoundException, SecurityException,
			NoSuchFieldException {
		log.info("Ejecutando metodo [ getListColumnsById ]");
		
		List<ColumnsMapper> columnsMappers = new LinkedList<ColumnsMapper>();

		for (Field field : mapDTO.get(id).getClazz().getDeclaredFields()) {
			// columnas simples
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				columnsMappers.add(new ColumnsMapper(column.name(), column
						.label(), column.type(), column.property(), column.index()));
				
				// columnas relacionales
			} else if (field.isAnnotationPresent(Relation.class)) {
				Relation relation = field.getAnnotation(Relation.class);
				Class<?> clazzField = mapDTO
						.get(relation.idMapperForRelation()).getClazz();

				String[] filedsRelation = relation.fields();
				for (int i = 0; i < filedsRelation.length; i++) {
					Field fAux = clazzField.getDeclaredField(filedsRelation[i]);
					Column cr = fAux.getAnnotation(Column.class);
					
					columnsMappers.add(new ColumnsMapper(relation.columnsName()[i], relation
							.labels()[i], cr.type(), field.getName() + "."
									+ cr.property(),relation.index()[i]));
				}
			}else if (field.isAnnotationPresent(RelationFilter.class)) {
				RelationFilter relation = field.getAnnotation(RelationFilter.class);
				Class<?> clazzField = mapDTO
						.get(relation.idMapperForRelation()).getClazz();

				String[] filedsRelation = relation.fields();
				for (int i = 0; i < filedsRelation.length; i++) {
					Field fAux = clazzField.getDeclaredField(filedsRelation[i]);
					Column cr = fAux.getAnnotation(Column.class);
					columnsMappers.add(new ColumnsMapper(relation.columnsName()[i], relation
							.labels()[i], cr.type(), relation.property() + "."
									+ cr.property(),relation.index()[i]));
				}
			}
		}

		return columnsMappers;
	}

	public String getNameViewById(String id, String tipo) {
		log.info("Ejecutando metodo getNameViewById ");

		log.info(mapDTO.get(id) + " - " + id);
	   if(tipo.equals("D"))
			return mapDTO.get(id).getView();
	   
	   if(tipo.equals("L"))
			return mapDTO.get(id).getViewl();
	   
	   if(tipo.equals("J"))
		return mapDTO.get(id).getViewJ();
	   
		return mapDTO.get(id).getViewR();
	   
	}
	
	public MapperReport getMapperReport(String id){
	    return mapDTO.get(id);
	}
	
	
	/*
	 * 	
					log.info(fAux.isAnnotationPresent(Column.class));
					while(!fAux.isAnnotationPresent(Column.class)){
						Relation relationAux = field.getAnnotation(Relation.class);
						Class<?> clazzFieldAux = mapDTO.get(relationAux.idMapperForRelation()).getClazz();
						String[] fileds = relationAux.fields();
					    log.info(relationAux);
					    log.info(clazzFieldAux);
						for (int j = 0; j < fileds.length; j++) {
							Field f = clazzFieldAux.getDeclaredField(fileds[j]);
							log.info(f.);
						}
						
					}
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}
