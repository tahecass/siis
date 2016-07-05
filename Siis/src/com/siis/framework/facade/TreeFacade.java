package com.casewaresa.framework.facade;



public class TreeFacade {

//	/** desc: Esta clase es singlenton */
//	private static final TreeFacade pTreeFacade = new TreeFacade();
//	/**
//	 * desc esta es la variable [ log ] de la clase [ ActionStandar.java ]
//	 */
//	protected static Logger log = Logger.getLogger(TreeFacade.class);
//
//	/**
//	 * @type Constructor de la clase COTParametrizacionFacade
//	 * @name LogginFacade
//	 * @return void
//	 * @desc Crea una instancia de COTParametrizacionFacade
//	 */
//	private TreeFacade() {
//
//		super();
//	}
//
//	/**
//	 * @type M�todo de la clase COTParametrizacionFacade
//	 * @name getCOTParametrizacionFacade
//	 * @return LogginFacade
//	 * @desc permite obtener la instancia del objeto COTParametrizacionFacade
//	 *       (singlenton)
//	 */
//	public static TreeFacade getFacade() {
//
//		return pTreeFacade;
//	}
//
//	/****************************************************************************************/
//	/** METODOS DE LA FACHADA **/
//	/****************************************************************************************/
//	
//		
//	public <T> void buildTree(Object objeto, List<MyItemTree> listaDatos, Class<T> clazz,
//			Object... consultas) throws Exception {
//		log.info("Ejecutando método [ buildTree(" + ((Tree) objeto).getId()
//				+ ", No. Reg: " + listaDatos.size() + ") ]... ");
//
//		SimpleTreeNode nodo = null;
//		SimpleTreeNode nodoAux = null;
//
//		ArrayList<SimpleTreeNode> aLTree = new ArrayList<SimpleTreeNode>();
//
//		Integer nivelInicial = 0;
//		Integer nivelAux = 0;
//
//		nodo = new SimpleTreeNode(new MyItemTree("0", "0", "root", 0),
//				new ArrayList<MyItemTree>());
//
//		aLTree.add(0, nodo);
//
//		for (int i = 0; i < listaDatos.size(); i++) {
//			MyItemTree itemTree = listaDatos.get(i);
//			nodo = new SimpleTreeNode(itemTree, new ArrayList<MyItemTree>());
//
//			if (i == 0)
//				nivelInicial = itemTree.getNivel();
//
//			nivelAux = (itemTree.getNivel() - nivelInicial) + 1;
//
//			if (nivelAux != 0)
//				nodoAux = (SimpleTreeNode) aLTree.get(nivelAux - 1);
//			nodoAux.getChildren().add(nodo);
//
//			if (nivelAux >= aLTree.size()) {
//				aLTree.add(nivelAux, nodo);
//			} else {
//				aLTree.set(nivelAux, nodo);
//			}
//		}
//
//		Tree tree = (Tree) objeto;
//		tree.clear();
//
//		// create treemodel and assigned its root
//		SimpleTreeNode root = (SimpleTreeNode) aLTree.get(0);
//
//		SimpleTreeModel treeModel = new SimpleTreeModel(root);
//
//		log.info(consultas);
//		log.info(consultas.length);
//
//		if (consultas != null && consultas.length > 0) {
//			// Se ejecuta en el caso de que el TreeItemRender requiera una lista
//			// de datos para un tratamiento especial
//			log.info("secuencia para la consulta : .."
//					+ ((IBeanAbstracto) consultas[1]).getPrimaryKey());
//			log.info("deshabilitar arbol ? : .." + consultas[2].toString());
//
//			ITreeItemRenderGrupos treeRender = (ITreeItemRenderGrupos) clazz
//					.newInstance();
//			treeRender.setList((List<Object>) ParametrizacionFac.getFacade()
//					.obtenerListado(consultas[0].toString(),
//							((IBeanAbstracto) consultas[1])));
//			treeRender
//					.setDeshabilitar(consultas[2].toString().equals("true") ? true
//							: false);
//
//			tree.setModel(treeModel);
//			tree.setTreeitemRenderer(treeRender);
//
//		} else {
//			// Lo que normalmente se ejecuta
//
//			// create a TreeItemRenderer
//			TreeitemRenderer treeRender = (TreeitemRenderer) clazz
//					.newInstance();
//
//			tree.setModel(treeModel);
//			tree.setTreeitemRenderer(treeRender);
//		}
//	}
//
//	public Object getDataItemSelected(Tree tree, List<Integer> pathItemSelected) {
//		log.info("Ejecutando método [ getDataItemSelected ]...");
//
//		this.printPath(pathItemSelected);
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//
//		SimpleTreeNode aux = root;
//		for (int i = 0; i < pathItemSelected.size(); i++) {// Hasta el nodo
//															// padre
//
//			aux = (SimpleTreeNode) aux.getChildAt((Integer) pathItemSelected
//					.get(i));
//		}
//		return aux.getData();
//	}
//
//	/**
//	 * @user : wcalderon
//	 * @name : addItem
//	 * @return: void
//	 * @param : tree - Componente Tree de ZK, que será afectado con el cambio
//	 * @param : path - Listado con los indices de las ubicaciones de cada uno de
//	 *        los nodos hasta llegar al seleccionado
//	 * @param : newChild - Nodo a ser insertado
//	 * @desc : Este método agrega un nuevo item a un arbol, teniendo en cuenta
//	 *       el path del nodo seleccioando
//	 */
//	public void addItem(Tree tree, List<Integer> pathItemParent, SimpleTreeNode newChild) {
//		log.info("Ejecutando método [ addItem ]...");
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//
//		SimpleTreeNode aux = root;
//		for (int i = 0; i < pathItemParent.size(); i++) {// Hasta el nodo padre
//			aux = (SimpleTreeNode) aux.getChildAt((Integer) pathItemParent
//					.get(i));
//		}
//		aux.getChildren().add(newChild);// Se adiciona el nuevo nodo
//
//		tree.setModel(new SimpleTreeModel(root));
//		tree.setTreeitemRenderer(tree.getTreeitemRenderer());
//	}
//
//	public void addItemSorted(Tree tree, List<Object> pathItemParent,
//			SimpleTreeNode newChild) {
//		log.info("Ejecutando método [ addItem ]...");
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//
//		SimpleTreeNode aux = root;
//		for (int i = 0; i < pathItemParent.size(); i++) {// Hasta el nodo padre
//			aux = (SimpleTreeNode) aux.getChildAt((Integer) pathItemParent
//					.get(i));
//		}
//		aux.getChildren().add(newChild);// Se adiciona el nuevo nodo
//
//		Collections.sort(aux.getChildren(), new SortChilds());
//		tree.setModel(new SimpleTreeModel(root));
//		tree.setTreeitemRenderer(tree.getTreeitemRenderer());
//	}
//
//	/**
//	 * @user : wcalderon
//	 * @name : setItem
//	 * @return: void
//	 * @param : tree - Componente Tree de ZK, que será afectado con el cambio
//	 * @param : path - Listado con los indices de las ubicaciones de cada uno de
//	 *        los nodos hasta llegar al seleccionado
//	 * @param : newChild - Nodo a ser insertado
//	 * @desc : Este método actualiza un item de un arbol, teniendo en cuenta el
//	 *       path del nodo que se afectará
//	 */
//	public void setItem(Tree tree, List<Integer> pathItemSelected, Object data) {
//		log.info("Ejecutando método [ setItem ]...");
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//
//		SimpleTreeNode aux = root;
//		for (int i = 0; i < pathItemSelected.size() - 1; i++) {// Hasta el padre
//																// del nodo que
//																// será afectado
//			aux = (SimpleTreeNode) aux.getChildAt((Integer) pathItemSelected
//					.get(i));
//		}
//
//		SimpleTreeNode node = (SimpleTreeNode) aux.getChildren().get(
//				(Integer) pathItemSelected.get(pathItemSelected.size() - 1));
//		SimpleTreeNode newNode = new SimpleTreeNode(data, node.getChildren());
//		aux.getChildren().set(
//				(Integer) pathItemSelected.get(pathItemSelected.size() - 1),
//				newNode); // Se remplaza el nodo seleccionado
//
//		tree.setModel(new SimpleTreeModel(root));
//		// tree.setTreeitemRenderer(new TreeItemRenderer());
//		tree.setTreeitemRenderer(tree.getTreeitemRenderer());
//	}
//
//	/**
//	 * @user : wcalderon
//	 * @name : removeItem
//	 * @return: void
//	 * @param : tree - Componente Tree de ZK, que será afectado con el cambio
//	 * @param : path - Listado con los indices de las ubicaciones de cada uno de
//	 *        los nodos hasta llegar al seleccionado
//	 * @desc : Este método eliminará un item de un arbol, teniendo en cuenta el
//	 *       path del nodo que se afectará
//	 */
//	public void removeItem(Tree tree, List<Integer> pathItemSelected) {
//		log.info("Ejecutando método [ removeItem ]...");
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//		SimpleTreeNode nodePad = root;
//		for (int i = 0; i < pathItemSelected.size() - 1; i++) {// Hasta el padre
//																// del nodo que
//																// será afectado
//			nodePad = (SimpleTreeNode) nodePad
//					.getChildAt((Integer) pathItemSelected.get(i));
//		}
//		SimpleTreeNode nodeAct = (SimpleTreeNode) nodePad
//				.getChildAt((Integer) pathItemSelected.get(pathItemSelected
//						.size() - 1));//
//		nodePad.getChildren().remove(nodeAct);
//
//		tree.setModel(new SimpleTreeModel(root));
//		tree.setTreeitemRenderer(tree.getTreeitemRenderer());
//	}
//
//	public void swapOrderChildItems(Tree tree, List<Integer> pathItemSelected,
//			String operation) {
//		log.info("Ejecutando método [ swapOrderChildItems ]...");
//
//		Integer posNodeA = null;
//		Integer posNodeB = null;
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//		SimpleTreeNode aux = root;
//		for (int i = 0; i < pathItemSelected.size() - 1; i++) {// Hasta el padre
//																// de los nodos
//																// que serán
//																// afectados
//			aux = (SimpleTreeNode) aux.getChildAt((Integer) pathItemSelected
//					.get(i));
//		}
//
//		posNodeA = (Integer) pathItemSelected.get(pathItemSelected.size() - 1);
//		SimpleTreeNode nodeA = (SimpleTreeNode) aux.getChildren().get(posNodeA);
//
//		if (operation.equals(IConstantes.SUBIR_ORDEN)) {
//			posNodeB = posNodeA - 1;
//		} else if (operation.equals(IConstantes.BAJAR_ORDEN)) {
//			posNodeB = posNodeA + 1;
//		}
//		SimpleTreeNode nodeB = (SimpleTreeNode) aux.getChildren().get(posNodeB);
//		aux.getChildren().set(posNodeA, nodeB);
//		aux.getChildren().set(posNodeB, nodeA);
//
//		tree.setModel(new SimpleTreeModel(root));
//		tree.setTreeitemRenderer(tree.getTreeitemRenderer());
//	}
//
//	public String printPath(List<Integer> pathItemSelected) {
//		String pathStr = "Path[";
//		for (int i = 0; i < pathItemSelected.size(); i++) {
//			if (i < pathItemSelected.size() - 1)
//				pathStr = pathStr + pathItemSelected.get(i) + ",";
//			else
//				pathStr = pathStr + pathItemSelected.get(i);
//		}
//		pathStr = pathStr + "]";
//
//		return pathStr;
//	}
//
//	public void changeLevelItems(Tree tree, List<Integer> pathItemSelected,
//			String operation) {
//		log.info("Ejecutando método [ changeLevelItems ]...");
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//
//		SimpleTreeNode nodePad = root;
//		SimpleTreeNode nodeAct = null;
//		Integer posNodeAct = (Integer) pathItemSelected.get(pathItemSelected
//				.size() - 1);
//
//		this.printPath(pathItemSelected);
//
//		for (int i = 0; i < pathItemSelected.size() - 1; i++) {// Hasta el padre
//																// del nodo que
//																// será afectado
//			nodePad = (SimpleTreeNode) nodePad
//					.getChildAt((Integer) pathItemSelected.get(i));
//		}
//		nodeAct = (SimpleTreeNode) nodePad.getChildAt(posNodeAct);
//
//		if (operation.equals(IConstantes.BAJAR_NIVEL)) {
//			SimpleTreeNode nodeAnt = (SimpleTreeNode) nodePad
//					.getChildAt(posNodeAct - 1);// Se obtiene el nodo anterior
//			nodeAnt.getChildren().add(nodeAct);// Se agrega el nodo seleccionado
//												// al nodo Anterior
//
//		} else if (operation.equals(IConstantes.SUBIR_NIVEL)) {
//			SimpleTreeNode nodeAbu = root;
//			for (int i = 0; i < pathItemSelected.size() - 2; i++) {// Hasta el
//																	// Abuelo
//																	// del nodo
//																	// que será
//																	// afectado
//				nodeAbu = (SimpleTreeNode) nodeAbu
//						.getChildAt((Integer) pathItemSelected.get(i));
//			}
//			nodeAbu.getChildren().add(nodeAct);
//
//		}
//
//		// this.removeItem(tree, path);
//		nodePad.getChildren().remove(nodeAct);
//
//		tree.setModel(new SimpleTreeModel(root));
//		tree.setTreeitemRenderer(tree.getTreeitemRenderer());
//	}
//
//	public SimpleTreeNode getNodeByPath(Tree tree, List<Object> pathItemSelected) {
//		log.info("Ejecutando método [ getNodeByPath ]...");
//
//		SimpleTreeNode root = (SimpleTreeNode) tree.getModel().getRoot(); // Obtenemos
//																			// la
//																			// raíz
//																			// del
//																			// arbol
//
//		SimpleTreeNode aux = root;
//		for (int i = 0; i < pathItemSelected.size(); i++) {// Hasta el nodo
//															// Buscado
//			aux = (SimpleTreeNode) aux.getChildAt((Integer) pathItemSelected
//					.get(i));
//		}
//		return aux;
//	}
//
//	/**
//	 * @user : wcalderon
//	 * @name : getTreeItemByPath
//	 * @return: Treeitem - Búscado
//	 * @param : tree - Componente Tree de ZK, del que se que será afectado con
//	 *        el cambio
//	 * @param : path - Listado con los indices de las ubicaciones de cada uno de
//	 *        los nodos hasta llegar al seleccionado
//	 * @desc : Este método encuentra, selecciona y retorna un item, teniendo en
//	 *       cuenta el path del nodo búscado
//	 */
//	public Treeitem getTreeItemByPath(Tree tree, List<Integer> pathItemSelected) {
//		log.info("ejecutando [ getTreeItemByPath ]... ");
//
//		try {
//			if (pathItemSelected.size() > 0) {
//				int[] p = new int[pathItemSelected.size()];
//
//				for (int i = 0; i < pathItemSelected.size(); i++) {
//					p[i] = (Integer) pathItemSelected.get(i);
//				}
//				Treeitem itemSelected = tree.renderItemByPath(p);
//				itemSelected.setSelected(true);
//
//				return itemSelected;
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			return null;
//		}
//	}

}