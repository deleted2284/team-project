package ru.project;

public class AppState {

  private DataCollection collection;

  public DataCollection getCollection() {
    return collection;
  }

  public void setCollection(DataCollection collection) {
    this.collection = collection;
  }

  public boolean hasCollection() {
    return collection != null;
  }
}
