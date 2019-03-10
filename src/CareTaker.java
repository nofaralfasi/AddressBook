import java.util.ArrayList;
import java.util.List;

class CareTaker {
	private List<AddressBookPane.Memento> mementoList = new ArrayList<AddressBookPane.Memento>();
	private int index;

	public CareTaker() {
		index = mementoList.size();
	}

	public void add(AddressBookPane.Memento memento) {
		if (memento != null) {
			mementoList.add(memento);
			index = mementoList.size() - 1;
		}
	}

	public AddressBookPane.Memento getPrev() {
		if (mementoList.isEmpty() || index <= 0) {
			return null;
		}
		return mementoList.get(--index);
	}

	public AddressBookPane.Memento getNext() {
		if (mementoList.isEmpty() || index >= mementoList.size() - 1) {
			return null;
		}
		return mementoList.get(++index);
	}
}
