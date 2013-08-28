package observer;

public interface Observable {
	public void addObserver(Observer obs);
	public void remObserver(Observer obs);
	public void notifyObservers();
}
