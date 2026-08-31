package ru.project.ui.creation.random;

public class RandomFillSettings {

  private String minGroupNumber;
  private String maxGroupNumber;

  private Double minAverageGrade;
  private Double maxAverageGrade;

  private Integer minRecordBookNumber;
  private Integer maxRecordBookNumber;

  public RandomFillSettings() {
    this.minGroupNumber = null;
    this.maxGroupNumber = null;

    this.minAverageGrade = null;
    this.maxAverageGrade = null;

    this.minRecordBookNumber = null;
    this.maxRecordBookNumber = null;
  }

  public String getMinGroupNumber() {
    return minGroupNumber;
  }

  public void setMinGroupNumber(String minGroupNumber) {
    this.minGroupNumber = minGroupNumber;
  }

  public String getMaxGroupNumber() {
    return maxGroupNumber;
  }

  public void setMaxGroupNumber(String maxGroupNumber) {
    this.maxGroupNumber = maxGroupNumber;
  }

  public Double getMinAverageGrade() {
    return minAverageGrade;
  }

  public void setMinAverageGrade(Double minAverageGrade) {
    this.minAverageGrade = minAverageGrade;
  }

  public Double getMaxAverageGrade() {
    return maxAverageGrade;
  }

  public void setMaxAverageGrade(Double maxAverageGrade) {
    this.maxAverageGrade = maxAverageGrade;
  }

  public Integer getMinRecordBookNumber() {
    return minRecordBookNumber;
  }

  public void setMinRecordBookNumber(Integer minRecordBookNumber) {
    this.minRecordBookNumber = minRecordBookNumber;
  }

  public Integer getMaxRecordBookNumber() {
    return maxRecordBookNumber;
  }

  public void setMaxRecordBookNumber(Integer maxRecordBookNumber) {
    this.maxRecordBookNumber = maxRecordBookNumber;
  }
}
