package ru.project.ui.collection.search;

public class SearchSettings {

  private FilterRelation relation;

  private boolean groupNumberFilterEnabled;
  private boolean averageGradeFilterEnabled;
  private boolean recordBookNumberFilterEnabled;

  private String minGroupNumber;
  private String maxGroupNumber;

  private Double minAverageGrade;
  private Double maxAverageGrade;

  private Integer minRecordBookNumber;
  private Integer maxRecordBookNumber;

  public SearchSettings() {
    this.relation = FilterRelation.AND;

    this.groupNumberFilterEnabled = false;
    this.averageGradeFilterEnabled = false;
    this.recordBookNumberFilterEnabled = false;

    this.minGroupNumber = null;
    this.maxGroupNumber = null;

    this.minAverageGrade = null;
    this.maxAverageGrade = null;

    this.minRecordBookNumber = null;
    this.maxRecordBookNumber = null;
  }

  public FilterRelation getRelation() {
    return relation;
  }

  public void setRelation(FilterRelation relation) {
    this.relation = relation;
  }

  public boolean isGroupNumberFilterEnabled() {
    return groupNumberFilterEnabled;
  }

  public void setGroupNumberFilterEnabled(boolean enabled) {
    this.groupNumberFilterEnabled = enabled;
  }

  public boolean isAverageGradeFilterEnabled() {
    return averageGradeFilterEnabled;
  }

  public void setAverageGradeFilterEnabled(boolean enabled) {
    this.averageGradeFilterEnabled = enabled;
  }

  public boolean isRecordBookNumberFilterEnabled() {
    return recordBookNumberFilterEnabled;
  }

  public void setRecordBookNumberFilterEnabled(boolean enabled) {
    this.recordBookNumberFilterEnabled = enabled;
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
