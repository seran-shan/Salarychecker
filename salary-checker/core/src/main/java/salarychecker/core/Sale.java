package salarychecker.core;

/**
 * Util class for a sale.
 */
public class Sale {

  private String salesID;
  private String anleggStatus;
  private String salgsType;
  private String campaign;
  private String brand;
  private String TX3;
  private String rebate;
  private String NVK;
  private String product;
  private double provisjon;

  /**
   * Creates Sale with all relevant parameters.
   *
   * @param salesID date of sale
   * @param anleggStatus status
   * @param salgsType type of sale
   * @param campaign campaign
   * @param brand brand
   * @param TX3 TX3
   * @param rebate discount on sale
   * @param NVK NVK
   * @param product product of sale
   * @param provisjon commission
   */
  public Sale(String salesID, String anleggStatus, String salgsType, String campaign,
              String brand, String TX3, String rebate, String NVK, String product,
              Double provisjon) {

    this.salesID = salesID;
    this.anleggStatus = anleggStatus;
    this.salgsType = salgsType;
    this.campaign = campaign;
    this.brand = brand;
    this.TX3 = TX3;
    this.rebate = rebate;
    this.NVK = NVK;
    this.product = product;
    this.provisjon = provisjon;
  }

  /**
   * Empty constructor needed for test.
   */
  public Sale() {
  }

  /**
   * Set and get methods.
   */
  public double getProvisjon() {
    return this.provisjon;
  }

  public void setProvisjon(double provisjon) {
    this.provisjon = provisjon;
  }

  public void updateProvisjon(int number) {
    this.provisjon += number;
  }

  public String getSalesID() {
    return this.salesID;
  }

  public void setSalesID(String salesID) {
    this.salesID = salesID;
  }

  public String getAnleggStatus() {
    return this.anleggStatus;
  }

  public void setAnleggStatus(String anleggStatus) {
    this.anleggStatus = anleggStatus;
  }

  public String getSalgsType() {
    return this.salgsType;
  }

  public void setSalgsType(String salgsType) {
    this.salgsType = salgsType;
  }

  public String getCampaign() {
    return this.campaign;
  }

  public void setCampaign(String campaign) {
    this.campaign = campaign;
  }

  public String getBrand() {
    return this.brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getTX3() {
    return this.TX3;
  }

  public void setTX3(String TX3) {
    this.TX3 = TX3;
  }

  public String getRebate() {
    return this.rebate;
  }

  public void setRebate(String rebate) {
    this.rebate = rebate;
  }

  public String getNVK() {
    return this.NVK;
  }

  public void setNVK(String NVK) {
    this.NVK = NVK;
  }

  public String getProduct() {
    return this.product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

}
