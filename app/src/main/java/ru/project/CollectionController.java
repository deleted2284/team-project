package ru.project;

public class CollectionController {

  private final AppState state;

  public CollectionController(AppState state) {
    this.state = state;
  }

  public void createCollection() {
    DataCollection collection = new DataCollection();

    collection.add("Charlie");
    collection.add("Alice");
    collection.add("Bob");
    collection.add("Alice");

    state.setCollection(collection);
  }

  public DataCollection getCollection() {
    return state.getCollection();
  }

  public boolean hasCollection() {
    return state.hasCollection();
  }

  public void sortCollection() {
    if (!state.hasCollection()) {
      return;
    }

    state.getCollection().sort();
  }

  public boolean contains(String element) {
    return state.hasCollection() && state.getCollection().contains(element);
  }

  public int countOccurrences(String element) {
    if (!state.hasCollection()) {
      return 0;
    }

    return state.getCollection().count(element);
  }
}
