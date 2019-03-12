//itay zemah
//312277007
import java.util.ArrayList;
import java.util.Collections;


public class AfekaInventory implements InventoryManagement<MusicalInstrument> {

	private ArrayList<MusicalInstrument> list;
	private double totalAmount = 0;
	private boolean isSort;

	public AfekaInventory() {
		this.list = new ArrayList<MusicalInstrument>();
		setTotalAmount(this.totalAmount);
		setSort(false);

	}

	public double sumNumbers(Number num1, Number num2) {
		return num1.doubleValue() + num2.doubleValue();
	}

	public ArrayList<MusicalInstrument> getList() {
		return this.list;
	}

	@Override
	public void addAllStringInstrument(ArrayList<? super MusicalInstrument> target,
			ArrayList<? extends MusicalInstrument> listToTakeFrom) {
		for (int i = 0; i < listToTakeFrom.size(); i++) {
			if (listToTakeFrom.get(i) instanceof StringInstrument) {
				addInstrument(target, listToTakeFrom.get(i));

			}
		}
	}

	@Override
	public void addAllWindInstruments(ArrayList<? super MusicalInstrument> listToIntegrate,
			ArrayList<? extends MusicalInstrument> listToTakeFrom) {
		for (int i = 0; i < listToTakeFrom.size(); i++) {
			if (listToTakeFrom.get(i) instanceof WindInstrument) {
				addInstrument(listToIntegrate, ((MusicalInstrument) listToTakeFrom.get(i)));
			}
		}
	}

	@Override
	public void addInstrument(ArrayList<? super MusicalInstrument> target, MusicalInstrument insrumentToAdd) {
		target.add(0, insrumentToAdd);
		setTotalAmount(insrumentToAdd.getPrice());
		setSort(false);
	}

	@Override
	public void SortByBrandAndPrice(ArrayList<MusicalInstrument> listToSort) {
		Collections.sort(listToSort);
		// boolean hasChange = true;
		// for (int i = listToSort.size() - 1; i >= 0 && hasChange; i--) {
		// hasChange = false;
		// for (int j = 0; j < i; j++) {
		// if (listToSort.get(j).compareTo(listToSort.get(j + 1)) > 0) {
		// swap(listToSort, j, j + 1);
		// hasChange = true;
		// }
		// }
		// }
		setSort(true);
	}

	// private void swap(ArrayList<T> listToSort, int i, int j) {
	// T temp = listToSort.get(i);
	// listToSort.remove((i));
	// listToSort.add(i, listToSort.get(i));
	// listToSort.remove(listToSort.get(j));
	// listToSort.add(j, temp);
	//
	// }

	@Override
	public int binnarySearchByBrandAndPrice(ArrayList<MusicalInstrument> listToSearch, String brandName, Number price) {
		if (!isSort) {
			System.out.println("Can not search in unsorted array.");
			return -1;
		}
		int mid;
		int low = 0, high = listToSearch.size() - 1;
		while (low <= high) {
			mid = (low + high) / 2;
			if (listToSearch.get(mid).getBrand().equalsIgnoreCase(brandName)) {
				if (listToSearch.get(mid).getPrice().doubleValue() == price.doubleValue()) {
					return mid;
				} else if (listToSearch.get(mid).getPrice().doubleValue() > price.doubleValue()) {
					high = mid - 1;
				} else {
					low = mid + 1;
				}
			} else if (listToSearch.get(mid).getBrand().compareToIgnoreCase(brandName) > 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return -1;
	}

	// && list.get(indexOfIstrumentInArray).equals(instrumentToRemove)
	@Override
	public boolean removeInstrument(ArrayList<? super MusicalInstrument> list, MusicalInstrument instrumentToRemove) {
//		if (!isSort()) { // the list must be sorted before searching an
//			// Instrument.
//			// SortByBrandAndPrice(list);
//			System.out.println("can not remove from unsorted array.!");
//			return false;
//
//		}
		// int indexOfIstrumentInArray = binnarySearchByBrandAndPrice(list,
		// instrumentToRemove.getBrand(),
		// instrumentToRemove.getPrice());
		// if (indexOfIstrumentInArray != -1) {
		 if (list.indexOf(instrumentToRemove) != -1) {
			setTotalAmount(instrumentToRemove.getPrice().doubleValue() * (-1));
			list.remove(instrumentToRemove);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(ArrayList<? super MusicalInstrument> list) {
		while (!list.isEmpty()) {
			if (list.get(list.size() - 1) instanceof MusicalInstrument) {
				removeInstrument(list, (MusicalInstrument) list.get(list.size() - 1));
			}
		}
		if (getTotalAmount() == 0) { // check if succeeded
			setSort(false);
			return true;
		}
		return false;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	private void setTotalAmount(Number number) {
		this.totalAmount = sumNumbers(this.totalAmount, number);
	}

	public boolean isSort() {
		return isSort;
	}

	private void setSort(boolean isSort) {
		this.isSort = isSort;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n-------------------------------------------------------------------------\n"
				+ "AFEKA MUSICAL INSTRUMENTS INVENTORY\n"
				+ "-------------------------------------------------------------------------\n");
		if (list.size() == 0) {
			sb.append("There Is No Instruments To Show.").toString();
		} else {
			for (int i = 0; i < list.size(); i++) {
				sb.append(this.getList().get(i) + "\n");
			}

		}

		return sb.toString() + String.format("\n Total Price: %7.2f \t Sorted: %s", this.getTotalAmount(), this.isSort);
	}
}
