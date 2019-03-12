//itay zemah
//312277007
import java.util.ArrayList;

public interface InventoryManagement<T extends MusicalInstrument> {
	
	void addAllStringInstrument(ArrayList<? super MusicalInstrument> target, ArrayList<? extends MusicalInstrument> listToTakeFrom);
	
	void addAllWindInstruments (ArrayList<? super MusicalInstrument> listToIntegrate, ArrayList<? extends MusicalInstrument> listToTakeFrom);
	
	void SortByBrandAndPrice (ArrayList<T> listToSort);
	
	int binnarySearchByBrandAndPrice(ArrayList<T> listToSearch, String brandName, Number price);
	
	void addInstrument(ArrayList<? super MusicalInstrument> listToAdd, T insrumentToAdd);
	
	boolean removeInstrument(ArrayList<? super MusicalInstrument> list, T instrumentToRemove);

	boolean removeAll(ArrayList<? super MusicalInstrument> list);
}
