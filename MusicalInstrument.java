
//itay zemah
//312277007
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class MusicalInstrument implements InstrumentFunc<MusicalInstrument>, Cloneable {
	private Number price;
	private String brand;

	public MusicalInstrument(String brand, Number price) {
		setBrand(brand);
		setPrice(price);
	}

	public MusicalInstrument(Scanner scanner) {
		double price = 0;
		String brand;

		try {
			price = scanner.nextDouble();
		} catch (InputMismatchException ex) {
			throw new InputMismatchException("Price not found!");
		}
		setPrice(price);
		scanner.nextLine();
		brand = scanner.nextLine();
		setBrand(brand);
	}

	public MusicalInstrument clone() {
		return null;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Number getPrice() {
		return price;
	}

	public void setPrice(Number price) {
		if (price.doubleValue() > 0) {
			if (Math.floor(price.doubleValue()) != Math.ceil(price.doubleValue()))
				this.price = new Double(price.doubleValue());
			else {
				this.price = new Integer(price.intValue());
			}
		}

		else
			throw new InputMismatchException("Price must be a positive number!");

	}

	protected boolean isValidType(String[] typeArr, String material) {
		for (int i = 0; i < typeArr.length; i++) {
			if (material.equals(typeArr[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof MusicalInstrument))
			return false;

		MusicalInstrument otherInstrument = (MusicalInstrument) o;

		return getPrice().doubleValue() == otherInstrument.getPrice().doubleValue()
				&& getBrand().equalsIgnoreCase(otherInstrument.getBrand());
	}

	@Override
	public String toString() {
		return String.format("%-8s %-9s| Price:" + (getPrice() instanceof Double ? "%-8.2f," : "%-8d,"), getBrand(),
				getClass().getCanonicalName(), getPrice());
	}

	@Override
	public int compareTo(MusicalInstrument other) {
		if (this.getBrand().compareTo(other.getBrand()) == 0) {
			if (this.getPrice().doubleValue() - (other.getPrice().doubleValue()) > 0) {
				return 1;
			} else if (this.getPrice().doubleValue() - (other.getPrice().doubleValue()) == 0) {
				return 0;
			} else {
				return -1;
			}
		} else if ((this.getBrand().compareTo(other.getBrand()) > 0)) {
			return 1;

		} else {
			return -1;
		}
	}
}
