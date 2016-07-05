/**
 * 
 */
package com.casewaresa.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


import com.casewaresa.framework.action.ActionStandardBorder;



/**
 * @author casewaredes06
 * @name EvaluarExpresionMatematica.java
 * @date 04/11/2011
 * @desc
 */

public class EvaluarExpresionMatematica extends ActionStandardBorder {
	
	/**
	 * Serial de la clase
	 */
	private static final long serialVersionUID = -8625885292913856742L;
	private Nodos raiz;
	private Integer periodo;
	private boolean parent = true;
	
	/**
	 * 
	 * @type Erma la expresion matematica o la formula presupuestal utilizanod
	 *       Valores de la Varibles BD
	 * @name armarExpresion
	 * @param nodo
	 * @param expresion
	 * @desc
	 *
	public void armarExpresionConVarBD(Nodos nodo, String expresion)
			throws NullPointerException {
		log.info("Ejecutando método [ armarExpresionConVarBD ] ");
		
		parent = true;
		BigDecimal valVariable;
	
		while (expresion.startsWith("(") && expresion.endsWith(")")
				&& parent == true) {		
			expresion = romperParentesis(expresion);			
			log.info("Exp pre-->"+expresion);
		}
		int prio = evaluarPrioridad(expresion);
		if (prio == 0) {
			if (expresion.charAt(0) == '$') {
				valVariable = obtenerValorVariableBD(expresion);
				nodo.setInfor(valVariable.toString());
			} else {
				nodo.setInfor(expresion);
			}
		} else {
			nodo.setInfor("" + expresion.charAt(prio));
			nodo.izq = new Nodos();
			armarExpresionConVarBD(nodo.izq, izquierda(expresion, prio));
			nodo.der = new Nodos();
			armarExpresionConVarBD(nodo.der, derecha(expresion, prio));
		}
	}
	
	public void armarExpresionConVarBD(Nodos nodo, String expresion, BigDecimal incremento)
	throws NullPointerException {
		log.info("Ejecutando método [ armarExpresionConVarBD - incremento ] ");

		parent = true;
		BigDecimal valVariable;

		while (expresion.startsWith("(") && expresion.endsWith(")")
		&& parent == true) {		
			expresion = romperParentesis(expresion);			
			log.info("Exp pre-->"+expresion);
		}
		int prio = evaluarPrioridad(expresion);
		if (prio == 0) {
				if (expresion.charAt(0) == '$') {
					valVariable = obtenerValorVariableBD(expresion,incremento);
					nodo.setInfor(valVariable.toString());
				} else {
					nodo.setInfor(expresion);
				}
		} else {
			nodo.setInfor("" + expresion.charAt(prio));
			nodo.izq = new Nodos();
			armarExpresionConVarBD(nodo.izq, izquierda(expresion, prio),incremento);
			nodo.der = new Nodos();
			armarExpresionConVarBD(nodo.der, derecha(expresion, prio), incremento);
		}
}*/
	
	
/*
	public List<String> obtenerVariablesFormula(Nodos nodo, String expresion) {
		log.info("Ejecutando método [ obtenerVariablesFormula ] ");
		parent = true;
	
		while (expresion.startsWith("(") && expresion.endsWith(")")
				&& parent == true) {
			expresion = romperParentesis(expresion);
		}
		int prio = evaluarPrioridad(expresion);
		if (prio == 0) {
			if (expresion.charAt(0) == '$') {
				log.info("Variabe $-->" + expresion);
				if (!listaVariables.contains(expresion)) {
					log.info("Variabe $ LISTA-->" + expresion);
					listaVariables.add(expresion);
				}

			} else {
				nodo.setInfor(expresion);
			}
		} else {
			nodo.setInfor("" + expresion.charAt(prio));
			nodo.izq = new Nodos();
			obtenerVariablesFormula(nodo.izq, izquierda(expresion, prio));
			nodo.der = new Nodos();
			obtenerVariablesFormula(nodo.der, derecha(expresion, prio));
		}
		return listaVariables;
	}
*/
	/**
	 * 
	 * @type Erma la expresion matematica o la formula presupuestal para evaluar
	 *       la estructura remplezando la Variable por 1
	 * @name armarExpresion
	 * @param nodo
	 * @param expresion
	 * @desc
	 */
	public void armarExpresionSinBD(Nodos nodo, String expresion) {
		log.info("Ejecutando método [ armarExpresionSinBD ] ");
		parent = true;
		while (expresion.startsWith("(") && expresion.endsWith(")")
				&& parent == true) {
			expresion = romperParentesis(expresion);
		}
		int prio = evaluarPrioridad(expresion);
		if (prio == 0) {
			if (expresion.charAt(0) == '$') {
				nodo.setInfor(""+1);
			} else {
				nodo.setInfor(expresion);
			}
		} else {
			nodo.setInfor("" + expresion.charAt(prio));
			nodo.izq = new Nodos();
			armarExpresionSinBD(nodo.izq, izquierda(expresion, prio));
			nodo.der = new Nodos();
			armarExpresionSinBD(nodo.der, derecha(expresion, prio));
		}
	}

	/**
	 * @desc Resuelve la expresion aritmetica y retorna el resultado
	 * @param raiz
	 * @return BigDeimal
	 */
	public BigDecimal resolverExpresion(Nodos raiz) {
		log.info("Ejecutando método [ resolverExpresion ] ");
		if (raiz.izq == null) {
			if (evaluarPrioridad(raiz.getInfor()) == 0) {			
				return new BigDecimal(raiz.getInfor());
			} else {
				return BigDecimal.ONE;
			}
		} else if (raiz.izq.izq == null) {
			Nodos no = raiz.izq;
			switch (raiz.getInfor().charAt(0)) {
			case '+':
				try {
					return new BigDecimal(no.getInfor())
							.add(resolverExpresion(raiz.der));
				} catch (Exception ex) {
					return resolverExpresion(raiz.der).add(BigDecimal.ONE);
				}
			case '-':
				try {
					return new BigDecimal(no.getInfor())
							.subtract(resolverExpresion(raiz.der));
				} catch (Exception ex) {
					return BigDecimal.ONE.subtract(resolverExpresion(raiz.der));
				}
			case '*':
				try {
					return new BigDecimal(no.getInfor())
							.multiply(resolverExpresion(raiz.der));
				} catch (Exception ex) {
					return BigDecimal.ONE.multiply(resolverExpresion(raiz.der));
				}
			case '/':
				try {
					return new BigDecimal(no.getInfor()).divide(
							resolverExpresion(raiz.der), 2,
							RoundingMode.HALF_UP);
				} catch (Exception ex) {
					return resolverExpresion(raiz.der).divide(BigDecimal.ONE);
				}
			case '^':
				try {
					return new BigDecimal(Math.pow(
							new BigDecimal(no.getInfor()).doubleValue(),
							resolverExpresion(raiz.der).doubleValue()));
				} catch (Exception ex) {
					return new BigDecimal(Math.pow(1,
							resolverExpresion(raiz.der).doubleValue()));
				}
			}
		} else if (raiz.izq.izq != null) {
			switch (raiz.getInfor().charAt(0)) {
			case '+':
				return resolverExpresion(raiz.izq).add(
						resolverExpresion(raiz.der));
			case '-':
				return resolverExpresion(raiz.izq).subtract(
						resolverExpresion(raiz.der));
			case '*':
				return resolverExpresion(raiz.izq).multiply(
						resolverExpresion(raiz.der));
			case '/':
				return resolverExpresion(raiz.izq).divide(
						resolverExpresion(raiz.der), 2, RoundingMode.HALF_UP);
			case '^':
				return new BigDecimal(Math.pow(resolverExpresion(raiz.izq)
						.doubleValue(), resolverExpresion(raiz.der)
						.doubleValue()));
			}
		}
		return resolverExpresion(raiz.der);
	}

	/**
	 * @desc Extrae la parte hacia la izquierda desde el signo con mas prioridad
	 *       en la expresion
	 * @param expresion
	 * @param poscisionSigno
	 * @return
	 */
	public String izquierda(String expresion, int poscisionSigno) {
		return expresion.substring(0, poscisionSigno);
	}

	/**
	 * @desc Extrae la parte hacia la derecha desde el signo con mas prioridad
	 *       en la expresion
	 * @param expresion
	 * @param poscisionSigno
	 * @return
	 */
	public String derecha(String expresion, int poscisionSigno) {
		return expresion.substring(poscisionSigno + 1, expresion.length());
	}

	/**
	 * @desc Metodo que rompe los parentesis de una cadena
	 * @param expresion
	 * @return
	 */
	public String romperParentesis(String expresion) {
		log.info("Ejecutando método [ romperParentesis ] ");
		int parentesisEncontrados = 1;
		int posc = expresion.length() - 1;
		for (int i = 1; i < expresion.length(); i++) {
			if (expresion.charAt(i) == '(') {
				parentesisEncontrados++;
			}
			if (expresion.charAt(i) == ')') {
				parentesisEncontrados = parentesisEncontrados - 1;
				if (parentesisEncontrados == 0) {
					posc = i;
					break;
				}
			}
		}

		if (posc == (expresion.length() - 1)) {
			return expresion.substring(1, expresion.length() - 1);
		}
		setParent(false);

		return expresion;
	}

	/**
	 * @desc Este metodo evalua la prioridad de los signos en la expresion y
	 *       devuelve la poscision de el signo con mayor prioridad
	 * @param expresion
	 * @return
	 */
	public int evaluarPrioridad(String expresion) {
		log.info("Ejecutando método [ evaluarPrioridad ] ");
		int retorno = 0;
		int valor = 0;
		int parentesis = 0;
		for (int i = 0; i < expresion.length(); i++) {
			if (!Character.isDigit(expresion.charAt(i))
					&& expresion.charAt(i) != '.') {
				if (valor == 0 && parentesis == 0) {
					switch (expresion.charAt(i)) {
					case '+':
					case '-':
						valor = 1;
						retorno = i;
						break;
					case '*':
					case '/':
						valor = 2;
						retorno = i;
						break;
					case '^':
						valor = 3;
						retorno = i;
						break;
					case '(':
						parentesis++;
						break;
					case ')':
						parentesis--;
						break;
					}
				} else {
					switch (expresion.charAt(i)) {
					case '+':
					case '-':
						if (valor >= 1 && parentesis == 0) {
							valor = 1;
							retorno = i;
						}
						break;
					case '*':
					case '/':
						if (valor >= 2 && parentesis == 0) {
							valor = 2;
							retorno = i;
						}
						break;
					case '^':
						if (valor == 3 && parentesis == 0) {
							valor = 3;
							retorno = i;
						}
						break;
					case '(':
						parentesis++;
						break;
					case ')':
						parentesis--;
						break;
					}
				}
			}
		}

		return retorno;
	}

	/**
	 * 
	 * @type Retorna True o False si la expresion esta correcta estructuralmente
	 *       o No
	 * @name isCorrectaExpresion
	 * @param expresion
	 * @return boolean
	 */

	public boolean isCorrectaExpresion(String expresion) {
		log.info("Ejecutando método [isCorrectaExpresion] ");
		try {
			raiz = new Nodos();
			armarExpresionSinBD(raiz, expresion);
			BigDecimal resultado =resolverExpresion(raiz);	
			if(resultado!=null)
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;

		}

	}
/*
	/**
	 * 
	 * @type Obtiene el valor de la formula presupuestal de la BD
	 * @name obtenerValorFormulaPresupuestalBD
	 * @param expresion
	 * @param periodo
	 * @param dtoEscenario
	 * @return
	 * @desc
	 *
	public BigDecimal obtenerValorFormulaPresupuestalBD(String expresion,
			Integer periodo, EPTEscenario dtoEscenario, EPZEscenarioActividadDistribucionFujoCajaAction escenarioActividadFlujoCaja) {
		log.info("Ejecutando método [ obtenerValorFormulaPresupuestalBD ] ");
		BigDecimal resultado = new BigDecimal(0.0);
		variablesSinValor.clear();
		try {
			this.periodo = periodo;
			this.dtoEscenario = dtoEscenario;
			this.escenarioActividadFlujoCaja = escenarioActividadFlujoCaja;
			log.info("Formula Presupuestal-->"+expresion);
			raiz = new Nodos();
		
			armarExpresionConVarBD(raiz, expresion);
			resultado = resolverExpresion(raiz);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);

		} catch (NullPointerException e) {
			resultado = null;
		}

		return resultado;
	}
	
	public BigDecimal obtenerValorFormulaPresupuestalBD(String expresion,
			Integer periodo, EPTEscenario dtoEscenario,
			BigDecimal incremento) {
		log.info("Ejecutando método [ obtenerValorFormulaPresupuestalBD - incremento ] ");
		BigDecimal resultado = new BigDecimal(0.0);
		variablesSinValor.clear();
		try {
			this.periodo = periodo;
			this.dtoEscenario = dtoEscenario;
			log.info("Formula Presupuestal-->"+expresion);
			raiz = new Nodos();
		
			armarExpresionConVarBD(raiz, expresion, incremento);
			resultado = resolverExpresion(raiz);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);

		} catch (NullPointerException e) {
			resultado = null;
		}

		return resultado;
	}
	

	/**
	 * 
	 * @type Obtiene el valor de la variable presupuestal en un periodo del
	 *       escenario
	 * @name obtenerValorVariableBD
	 * @param variable
	 * @return
	 * @desc
	 */
	/*public BigDecimal obtenerValorVariableBD(String variable) {
		log.info("Ejecutando método [ obtenerValorVariableBD ] ");
		variable = variable.substring(3, (variable.length() - 1));
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("CODIGO_VARIABLE", variable);
		parametros.put("PERIODO", periodo);
		parametros.put("SEC_ESCENARIO", dtoEscenario.getSecEscenario());
		BigDecimal valorVariable = null;
		log.info("Perido-->" + periodo);
		log.info("Sec_Escenario-->" + dtoEscenario.getSecEscenario());
		log.info("Varible-->" + variable);
		EPTVariable dtoVariableValor = null;
		try {
			dtoVariableValor = (EPTVariable) ParametrizacionFac.getFacade()
					.obtenerRegistro("EP_obtenerValorVariableBD", parametros);
			dtoVariableValor.setVariable(variable);
			valorVariable = dtoVariableValor.getValorVariable();			

		} catch (Exception e) {
			if (dtoVariableValor == null) {
				this.escenarioActividadFlujoCaja.agregarValoresVariblesEnPeriodos(periodo.toString(), variable);
				variable = "";
				//obtenerValorVariableBD(variable);
			}
	
		}

		return valorVariable;

	}*/
	
/*	public BigDecimal obtenerValorVariableBD(String variable, BigDecimal incremento) {
		log.info("Ejecutando método [ obtenerValorVariableBD - incremento] ");
		variable = variable.substring(3, (variable.length() - 1));
		Map<String, Object> parametros = new HashMap<String, Object>();
		BigDecimal valorVariable = null;		
		EPTVariable dtoVariableValor = null;
		EPTVariable dtoVariable = null;
		try {
			dtoVariable = (EPTVariable)ParametrizacionFac.getFacade()
					.obtenerRegistro("EP_obtenerVariableCodigo",parametros);
			dtoVariableValor = (EPTVariable) ParametrizacionFac.getFacade()
					.obtenerRegistro("EP_obtenerValorVariableBD", parametros);
			dtoVariableValor.setVariable(variable);
			dtoVariableValor.setSecVariable(dtoVariable.getSecVariable());
			dtoVariableValor.setIncremento(dtoVariable.getIncremento());
			dtoVariableValor.setValorMinimo(dtoVariable.getValorMinimo());
			dtoVariableValor.setValorMaximo(dtoVariable.getValorMaximo());
			valorVariable = dtoVariableValor.getValorVariable();
			
			log.info("dtoVariableValor.secVariable-->" + dtoVariableValor.getSecVariable());
			log.info("dtoVariableValor.incremento-->" + dtoVariableValor.getIncremento());
			log.info("dtoVariableValor.valMin-->" + dtoVariableValor.getValorMinimo());
			log.info("dtoVariableValor.valMax-->" + dtoVariableValor.getValorMaximo());
			
			if (incremento!= null){
				if(dtoVariableValor.getIncremento()!=null &&
					dtoVariableValor.getValorMinimo()!=null &&
						dtoVariableValor.getValorMaximo()!=null){
							if(dtoVariableValor.getSecVariable() ==this.dtoEscenario.getProyecto().getVariable().getSecVariable())
							{
							valorVariable = valorVariable.add(incremento);
							log.info("Valor de la Variable con Incremento "+valorVariable);
							}
						}
					}

		} catch (Exception e) {
			log.error(e.getMessage(),e);
	
		}

		return valorVariable;

	}*/


	/**
	 * @type Método de la clase EvaluarExpresionMatematica.java
	 * @name getPeriodo
	 * @return periodo
	 * @descp obtiene el valor de periodo
	 */
	public Integer getPeriodo() {
		return periodo;
	}

	/**
	 * @type Método de la clase EvaluarExpresionMatematica.java
	 * @name setPeriodo
	 * @return void
	 * @param recibe
	 *            el parametro periodo
	 * @descp modifica el atributo periodo
	 */
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	
	/**
	 * @type Método de la clase EvaluarExpresionMatematica.java
	 * @name setParent
	 * @return void
	 * @param recibe
	 *            el parametro parent
	 * @descp modifica el atributo parent
	 */
	public void setParent(boolean parent) {
		this.parent = parent;
	}

	/**
	 * @type Método de la clase EvaluarExpresionMatematica.java
	 * @name isParent
	 * @return parent
	 * @descp obtiene el valor de parent
	 */
	public boolean isParent() {
		return parent;
	}

	
}
