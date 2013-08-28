package Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ObjetMap.ObjetMap;

public class LayeredChunkMap extends Chunk {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des layers
	 */
	private ArrayList<Integer> layers;

	/**
	 * Liste du nombre d'objets par layer.
	 */	
	private ArrayList<Integer> layersObjectCount;
	
	/**
	 * Variable temporaire.
	 */
	private transient int lastUpdateCount;
	
	
	public LayeredChunkMap(int sizeX, int sizeY, int sizeZ, ArrayList<ObjetMap> contenu) {
		super(sizeX, sizeY, sizeZ, contenu);
		addLayer(23);
		addLayer(400);
		addLayer(1020);
	}

	/**
	 * Ajouter un layer
	 * @param layer le layer a ajouter
	 */
	public void addLayer(int layer){
		this.getLayers().add(layer);
		sortLayers();
	}
	
	/**
	 * Enlever un layer
	 * @param layer le layer a enlever
	 */
	public void removeLayer(int layer){
		this.getLayers().remove(layer);
	}
	/**
	 * Obtenir les layers
	 * @return liste des layers
	 */
	private ArrayList<Integer> getLayers() {
		if(layers == null)
			layers = new ArrayList<Integer>();
		return layers;
	}
	
	private int getDepthLayer(int depth){
		int layer = 0;
		for(int i = 0; i < getLayers().size(); i++){
			if(i == 0 && depth <= getLayer(i)){
				layer =  getLayers().indexOf(getLayer(i));
				i = getLayers().size();
			}
			else if(depth <= getLayer(i) && depth > getLayer(i-1)){
				layer =  getLayers().indexOf(getLayer(i));
				i = getLayers().size();
			}
		}
		if(depth > getLayer(getLayers().size() - 1) && depth <= getSizeZ()){
			layer =  getLayers().size();
		}
		return layer;
	}
	
	/**
	 * Obtenir un layer
	 * @param layer numéro du layer
	 * @return valeur du layerr
	 */
	public Integer getLayer(int layer) {
		return getLayers().get(layer);
	}
	
	/**
	 * Obtenir le nombre de layers
	 * @return nombre de layers
	 */
	public int getLayersCount() {
		return getLayers().size();
	}
	
	/**
	 * Trie un objet selon son layer
	 * @param cle Objet a classer
	 */
	@SuppressWarnings("unused")
	private void sortByLayers(ObjetMap cle) {
		int depthLayerA = getDepthLayer(cle.getZ());
		int id =  getAccumuledLayerObjectCount(depthLayerA-1);
		int max = getAccumuledLayerObjectCount(depthLayerA);
		for (int i = id; i < max; i++) {
			ObjetMap other = contenu.get(i);
			if (!other.equals(cle)) {
				// int devant = 0;
				int depthLayerB = getDepthLayer(other.getZ());
				if(depthLayerA > depthLayerB)
					id =  i;
				else if(depthLayerA == depthLayerB){
					int distanceA = (int) cle.getPosY() + cle.getPosX();
					int distanceB = (int) other.getPosY()+ other.getPosX();
					if (distanceA < distanceB)
						id = i;
				}
				
			}
		}
		contenu.remove(cle);
		if(id+1 < contenu.size())
			contenu.add(id + 1, cle);
		else
			contenu.add(cle);
	}
	/**
	 * Permet d'obtenir le nombre d'objet accumulé du layer 0 jusqu'au depthLayer
	 * @param depthLayer layer selectionné
	 * @return nombre d'objet du layer 0 jusqu'au depthLayer
	 */
	private int getAccumuledLayerObjectCount(int depthLayer) {
		int objectCount = 0;
		while(depthLayer >= 0){
			objectCount += this.getLayerObjectCount(depthLayer);
			depthLayer--;
		}
		return objectCount;
	}
	/**
	 * Trie les layers selon leur hauteur.
	 */
	private void sortLayers(){
		Collections.sort(layers, new Comparator<Integer>(){

			@Override
			public int compare(Integer arg0, Integer arg1) {
				return arg0.compareTo(arg1);
			}
			
		});
	}
	/**
	 * 
	 * @return inventaire des objets
	 */
	private ArrayList<Integer> getLayersObjectCount() {
		if(layersObjectCount == null)
			layersObjectCount = new ArrayList<Integer>();
		return layersObjectCount;
	}
	/**
	 * 
	 * @param layer layer sélectionné
	 * @return nombre d'objet dans le layer sélectionné.
	 */
	public int getLayerObjectCount(int layer) {
		return this.getLayersObjectCount().get(layer);
	}
}
