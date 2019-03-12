
//itay zemah
//312277007
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AfekaInstruments {

	public static void main(String[] args) {
		ArrayList<MusicalInstrument> allInstruments = new ArrayList<MusicalInstrument>();
		Scanner keyboard = new Scanner(System.in);
		File file = getInstrumentsFileFromUser(keyboard);

		loadInstrumentsFromFile(file, allInstruments);

		if (allInstruments.size() == 0) {
			System.out.println("There are no instruments in the store currently");
			keyboard.close();
			return;
		}

		printInstruments(allInstruments);

		int different = getNumOfDifferentElements(allInstruments);

		System.out.println("\n\nDifferent Instruments: " + different);

		MusicalInstrument mostExpensive = getMostExpensiveInstrument(allInstruments);

		System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive);
		try {
			startInventoryMenu(allInstruments);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} finally {
			keyboard.close();
		}
	}

	public static void startInventoryMenu(ArrayList<MusicalInstrument> list) {
		Scanner keyboard = new Scanner(System.in);
		AfekaInventory inventory = new AfekaInventory();

		while (true) { // the menu work until wrong input or number is not on
						// the list
			int choice = 0;
			System.out.println("\n---------------------------------------------------------------------------------\n"
					+ "AFEKA MUSICAL INSTRUMENT INVENTORY MENU\n"
					+ "---------------------------------------------------------------------------------\n"
					+ "1. Copy All String Instruments To Inventory\n" + "2. Copy All Wind Instruments To Inventory\n"
					+ "3. Sort Instruments By Brand And Price\n" + "4. Search Instrument By Brand And Price\n"
					+ "5. Delete Instruments\n" + "6. Delete all Instruments\n" + "7. Print Inventory Instruments\n"
					+ "Choose your option or any other key to EXIT\n" + "Your Option:");
			try {
				choice = keyboard.nextInt();
			} catch (InputMismatchException ex) {
				choice = 0;
			}
			keyboard.nextLine(); // clean the buffer.

			switch (choice) {
			case 1:
				inventory.addAllStringInstrument(inventory.getList(), list);
				System.out.println("\nAll String Instruments Added Successfully!\n");
				break;
			case 2:
				inventory.addAllWindInstruments(inventory.getList(), list);
				System.out.println("\nAll Wind Instruments Added Successfully!\n");
				break;
			case 3:
				inventory.SortByBrandAndPrice(inventory.getList());
				System.out.println("\nInstruments Sorted Successfully!\n");
				break;
			case 4:
				System.out.println("SEARCH INSTRUMENT:");
				MusicalInstrument searchInstrument = searchForInstrument(keyboard, inventory);
				if (searchInstrument != null) {
					System.out.println(searchInstrument.toString());
				} else {
					System.out.println("\n Instrument Not Found!\n");
				}
				break;
			case 5:
				System.out.println("DELETE INSTRUMENT:");
				MusicalInstrument instrumentToDel = searchForInstrument(keyboard, inventory);
				if (instrumentToDel != null) {
					System.out.println("Result:\n" + instrumentToDel.toString() + "\nAre You Sure?(Y/N) ");
					if (yes_no_choice(keyboard) == 'Y') {
						inventory.removeInstrument(inventory.getList(), instrumentToDel);
						System.out.println("Instrument Deleted Successfully!");
					}
				} else {
					System.out.println("\n Instrument Not Found!\n");
				}
				break;
			case 6:
				System.out.println("DELETE ALL INSTRUMENTS: \nAre You Sure?(Y/N)");
				if (yes_no_choice(keyboard) == 'Y') {
					inventory.removeAll(inventory.getList());
					System.out.println("All Instruments Deleted Successfully!");
				}
				break;
			case 7:
				System.out.println(inventory.toString());
				break;
			default: // any other number ends the program
				throw new IllegalArgumentException("finished");

			}
		}

	}

	public static char yes_no_choice(Scanner keyboard) {
		boolean incorrectInput = true;
		char input_YES_NO = ' ';
		do {
			input_YES_NO = keyboard.next().charAt(0);
			if (input_YES_NO == 'Y' || input_YES_NO == 'y') {
				incorrectInput = false;
				input_YES_NO = 'Y';
			} else if (input_YES_NO == 'N' || input_YES_NO == 'n') {
				incorrectInput = false;
				input_YES_NO = 'N';
			} else {
				System.out.println("you must choice Y/N\nAre You Sure?(Y/N) ");
			}

		} while (incorrectInput);

		return input_YES_NO;
	}

	public static MusicalInstrument searchForInstrument(Scanner keyboard, AfekaInventory inventory) {
		System.out.println("Brand: ");
		String searchBrand = keyboard.nextLine();
		Number searchPrice = null;
		do {
			try {
				System.out.println("Price: ");
				searchPrice = new Double(keyboard.nextDouble());
			} catch (InputMismatchException ex) {
				System.err.print("Price must be a positive number!\n");
			}
			keyboard.nextLine(); // clear the buffer
		} while (searchPrice == null);
		if (!inventory.isSort()) {
			inventory.SortByBrandAndPrice(inventory.getList());
		}
		int searchResult = inventory.binnarySearchByBrandAndPrice(inventory.getList(), searchBrand, searchPrice);
		if (searchResult == -1) {
			return null;
		}
		return inventory.getList().get(searchResult);

	}

	public static File getInstrumentsFileFromUser(Scanner keyboard) {
		boolean stopLoop = true;
		File file;

		do {
			System.out.println("Please enter instruments file name / path:");
			String filepath = keyboard.nextLine();
			file = new File(filepath);
			stopLoop = file.exists() && file.canRead();

			if (!stopLoop)
				System.out.println("\nFile Error! Please try again\n\n");
		} while (!stopLoop);

		return file;
	}

	public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments) {
		Scanner scanner = null;

		try {

			scanner = new Scanner(file);

			addAllInstruments(allInstruments, loadGuitars(scanner));

			addAllInstruments(allInstruments, loadBassGuitars(scanner));

			addAllInstruments(allInstruments, loadFlutes(scanner));

			addAllInstruments(allInstruments, loadSaxophones(scanner));

		} catch (InputMismatchException | IllegalArgumentException ex) {
			System.err.println("\n" + ex.getMessage());
			System.exit(1);
		} catch (FileNotFoundException ex) {
			System.err.println("\nFile Error! File was not found");
			System.exit(2);
		} finally {
			scanner.close();
		}
		System.out.println("\nInstruments loaded from file successfully!\n");

	}

	public static ArrayList<Guitar> loadGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Guitar> guitars = new ArrayList<Guitar>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			guitars.add(new Guitar(scanner));

		return guitars;
	}

	public static ArrayList<Bass> loadBassGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Bass> bassGuitars = new ArrayList<Bass>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			bassGuitars.add(new Bass(scanner));

		return bassGuitars;
	}

	public static ArrayList<Flute> loadFlutes(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Flute> flutes = new ArrayList<Flute>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			flutes.add(new Flute(scanner));

		return flutes;
	}

	public static ArrayList<Saxophone> loadSaxophones(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Saxophone> saxophones = new ArrayList<Saxophone>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			saxophones.add(new Saxophone(scanner));

		return saxophones;
	}

	public static void addAllInstruments(ArrayList<MusicalInstrument> instruments,
			ArrayList<? extends MusicalInstrument> moreInstruments) {
		for (int i = 0; i < moreInstruments.size(); i++) {
			instruments.add(moreInstruments.get(i));
		}
	}

	public static void printInstruments(ArrayList<MusicalInstrument> instruments) {
		for (int i = 0; i < instruments.size(); i++)
			System.out.println(instruments.get(i));
	}

	public static int getNumOfDifferentElements(ArrayList<MusicalInstrument> afekaInstrumentsArrToCount) {
		ArrayList<MusicalInstrument> temp = new ArrayList<MusicalInstrument>();
		addAllInstruments(temp, afekaInstrumentsArrToCount);
		for (int currentIndex = 0; currentIndex < temp.size(); currentIndex++) {
			for (int i = currentIndex + 1; i < temp.size(); i++) {
				if (temp.get(currentIndex).equals(temp.get(i))) {
					i--; // because the instrument has deleted, I need to check
							// the same index witch is the next Instrument.
					temp.remove(i);
				}

			}
		}

		return temp.size();
	}

	public static MusicalInstrument getMostExpensiveInstrument(ArrayList<MusicalInstrument> instruments) {
		double maxPrice = 0;
		MusicalInstrument mostExpensive = (MusicalInstrument) instruments.get(0);

		for (int i = 0; i < instruments.size(); i++) {
			MusicalInstrument temp = (MusicalInstrument) instruments.get(i);

			if (temp.getPrice().doubleValue() > maxPrice) {
				maxPrice = temp.getPrice().doubleValue();
				mostExpensive = temp;
			}
		}

		return mostExpensive;
	}

}
