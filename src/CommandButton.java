import java.io.IOException;
import java.io.RandomAccessFile; 

import javafx.scene.control.Button;

class CommandButton extends Button implements Command, AddressBookNew1Finals {
	private AddressBookPane p;
	private RandomAccessFile raf;
	
	
	public CommandButton(AddressBookPane pane, RandomAccessFile r) {
		super();
		p = pane;
		raf = r;
	}

	public AddressBookPane getPane() {
		return p;
	}

	public RandomAccessFile getFile() {
		return raf;
	}

	public void setPane(AddressBookPane p) {
		this.p = p;
	}

	@Override
	public void Execute() {

	}
	
	public void writeAddress(long position) {
		try {
			getFile().seek(position);
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetName(), NAME_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetStreet(), STREET_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetCity(), CITY_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetState(), STATE_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetZip(), ZIP_SIZE, getFile());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void readAddress(long position) throws IOException {
		getFile().seek(position);
		String name = FixedLengthStringIO1.readFixedLengthString(NAME_SIZE, getFile());
		String street = FixedLengthStringIO1.readFixedLengthString(STREET_SIZE, getFile());
		String city = FixedLengthStringIO1.readFixedLengthString(CITY_SIZE, getFile());
		String state = FixedLengthStringIO1.readFixedLengthString(STATE_SIZE, getFile());
		String zip = FixedLengthStringIO1.readFixedLengthString(ZIP_SIZE, getFile());
		getPane().SetName(name);
		getPane().SetStreet(street);
		getPane().SetCity(city);
		getPane().SetState(state);
		getPane().SetZip(zip);
	}
	
} // end class CommandButton
