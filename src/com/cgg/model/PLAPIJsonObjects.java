package com.cgg.model;

import java.util.List;

import com.cgg.pl.category.PersonalLoan;

public class PLAPIJsonObjects <T extends PersonalLoan> {

	private List<T> productsForAPI;
	private List<T> featuredProductsForAPI;
	private Long timestamp;
	private Integer totalFeaturedProducts;
	private Integer totalNonFeaturedProducts;
	private Integer totalRecords;
	private String dataFileLastUpdateDate;
	
	public List<T> getProductsForAPI() {
		return productsForAPI;
	}
	public void setProductsForAPI(List<T> productsForAPI) {
		this.productsForAPI = productsForAPI;
	}
	public List<T> getFeaturedProductsForAPI() {
		return featuredProductsForAPI;
	}
	public void setFeaturedProductsForAPI(List<T> featuredProductsForAPI) {
		this.featuredProductsForAPI = featuredProductsForAPI;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getTotalFeaturedProducts() {
		return totalFeaturedProducts;
	}
	public void setTotalFeaturedProducts(Integer totalFeaturedProducts) {
		this.totalFeaturedProducts = totalFeaturedProducts;
	}
	public Integer getTotalNonFeaturedProducts() {
		return totalNonFeaturedProducts;
	}
	public void setTotalNonFeaturedProducts(Integer totalNonFeaturedProducts) {
		this.totalNonFeaturedProducts = totalNonFeaturedProducts;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public String getDataFileLastUpdateDate() {
		return dataFileLastUpdateDate;
	}
	public void setDataFileLastUpdateDate(String dataFileLastUpdateDate) {
		this.dataFileLastUpdateDate = dataFileLastUpdateDate;
	}
	
}
