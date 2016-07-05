package com.casewaresa.framework.Filter.dto;

/**
 * @author casewaredes04
 * @name ColumnMapper.java
 * @date 15/12/2010
 * @desc
 */

public class ColumnsMapper {

    private String name;
    private String label;
    private String type;
    private String property;
    private Integer width;
    private Integer index;

    /**
     * @param name
     * @param label
     * @param type
     */
    public ColumnsMapper(String name, String label, String type, String property) {
	this.name = name;
	this.label = label;
	this.type = type;
	this.property = property;
    }

    public ColumnsMapper(String name, String label, String type,
	    String property, Integer index) {
	this.name = name;
	this.label = label;
	this.type = type;
	this.property = property;
	this.index = index;
    }

    public Integer getIndex() {
	return index;
    }

    public void setIndex(Integer index) {
	this.index = index;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name getName
     * @return name
     * @descp obtiene el valor de name
     */
    public String getName() {
	return name;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name setName
     * @return void
     * @param recibe
     *            el parametro name
     * @descp modifica el atributo name
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name getLabel
     * @return label
     * @descp obtiene el valor de label
     */
    public String getLabel() {
	return label;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name setLabel
     * @return void
     * @param recibe
     *            el parametro label
     * @descp modifica el atributo label
     */
    public void setLabel(String label) {
	this.label = label;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name getType
     * @return type
     * @descp obtiene el valor de type
     */
    public String getType() {
	return type;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name setType
     * @return void
     * @param recibe
     *            el parametro type
     * @descp modifica el atributo type
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name getWidth
     * @return width
     * @descp obtiene el valor de width
     */
    public Integer getWidth() {
	return width;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name setWidth
     * @return void
     * @param recibe
     *            el parametro width
     * @descp modifica el atributo width
     */
    public void setWidth(Integer width) {
	this.width = width;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name getProperty
     * @return property
     * @descp obtiene el valor de property
     */
    public String getProperty() {
	return property;
    }

    /**
     * @type Método de la clase ColumnsMapper.java
     * @name setProperty
     * @return void
     * @param recibe
     *            el parametro property
     * @descp modifica el atributo property
     */
    public void setProperty(String property) {
	this.property = property;
    }

    @Override
    public String toString() {
	return "ColumnsMapper [name=" + name + ", label=" + label + ", type="
		+ type + ", property=" + property + ", width=" + width
		+ ", index=" + index + "]";
    }

}
