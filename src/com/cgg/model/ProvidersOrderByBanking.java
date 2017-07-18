package com.cgg.model;

import java.util.List;

import com.cgg.model.PLAPIJsonObject.Provider;

public class ProvidersOrderByBanking {

	private List<Provider> providers;
	private String timestamp;
	public List<Provider> getProviders() {
		return providers;
	}
	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
