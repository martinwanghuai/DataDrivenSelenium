package com.cgg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class MortgageAPIJsonObjects {
    public final Product featuredProducts[];
    public final Product products[];
    public final long timestamp;
    public final long totalFeaturedProducts;
    public final long totalNonFeaturedProducts;
    public final long totalRecords;
    public final String dataFileLastUpdateDate;

    @JsonCreator
    public MortgageAPIJsonObjects(@JsonProperty("featuredProducts") Product[] featuredProducts, @JsonProperty("products") Product[] products, @JsonProperty("timestamp") long timestamp, @JsonProperty("totalFeaturedProducts") long totalFeaturedProducts, @JsonProperty("totalNonFeaturedProducts") long totalNonFeaturedProducts, @JsonProperty("totalRecords") long totalRecords, @JsonProperty("dataFileLastUpdateDate") String dataFileLastUpdateDate){
        this.featuredProducts = featuredProducts;
        this.products = products;
        this.timestamp = timestamp;
        this.totalFeaturedProducts = totalFeaturedProducts;
        this.totalNonFeaturedProducts = totalNonFeaturedProducts;
        this.totalRecords = totalRecords;
        this.dataFileLastUpdateDate = dataFileLastUpdateDate;
    }

    public static final class Product {
        public final String approvalRate;
        public final String name;
        public final String description;
        public final long lastUpdated;
        public final String image;
        public final String cggId;
        public final boolean hasDesktopApplyButton;
        public final boolean hasApplicationForm;
        public final boolean isApplicationFormGeneric;
        public final boolean hasMobileApplyButton;
        public final boolean hasTabletApplyButton;
        public final String[] footnote;
        public final boolean showCTALeadCapture;
        public final Provider provider;
        public final ProductCategory productCategories[];
        public final String[] feature;
        public final Specification specification[];
        public final Banner banner;
        public final Fee fee;
        public final LoanDetails loanDetails;
        public final Others others;
        public final Attributes attributes;

        @JsonCreator
        public Product(@JsonProperty("approvalRate") String approvalRate, @JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("lastUpdated") long lastUpdated, @JsonProperty("image") String image, @JsonProperty("cggId") String cggId, @JsonProperty("hasDesktopApplyButton") boolean hasDesktopApplyButton, @JsonProperty("hasApplicationForm") boolean hasApplicationForm, @JsonProperty("isApplicationFormGeneric") boolean isApplicationFormGeneric, @JsonProperty("hasMobileApplyButton") boolean hasMobileApplyButton, @JsonProperty("hasTabletApplyButton") boolean hasTabletApplyButton, @JsonProperty("footnote") String[] footnote, @JsonProperty("showCTALeadCapture") boolean showCTALeadCapture, @JsonProperty("provider") Provider provider, @JsonProperty("productCategories") ProductCategory[] productCategories, @JsonProperty("feature") String[] feature, @JsonProperty("specification") Specification[] specification, @JsonProperty("banner") Banner banner, @JsonProperty("fee") Fee fee, @JsonProperty("loanDetails") LoanDetails loanDetails, @JsonProperty("others") Others others, @JsonProperty("attributes") Attributes attributes){
            this.approvalRate = approvalRate;
            this.name = name;
            this.description = description;
            this.lastUpdated = lastUpdated;
            this.image = image;
            this.cggId = cggId;
            this.hasDesktopApplyButton = hasDesktopApplyButton;
            this.hasApplicationForm = hasApplicationForm;
            this.isApplicationFormGeneric = isApplicationFormGeneric;
            this.hasMobileApplyButton = hasMobileApplyButton;
            this.hasTabletApplyButton = hasTabletApplyButton;
            this.footnote = footnote;
            this.showCTALeadCapture = showCTALeadCapture;
            this.provider = provider;
            this.productCategories = productCategories;
            this.feature = feature;
            this.specification = specification;
            this.banner = banner;
            this.fee = fee;
            this.loanDetails = loanDetails;
            this.others = others;
            this.attributes = attributes;
        }

        public static final class Provider {
            public final String cggId;
            public final String name;
            public final String image;
            public final String link;
            public final String linkMobile;
            public final Description description;
            public final String providerDisclaimer;
            public final Terms terms;
            public final Address address;
            public final String productDescription;
    
            @JsonCreator
            public Provider(@JsonProperty("cggId") String cggId, @JsonProperty("name") String name, @JsonProperty("image") String image, @JsonProperty("link") String link, @JsonProperty("linkMobile") String linkMobile, @JsonProperty("description") Description description, @JsonProperty("providerDisclaimer") String providerDisclaimer, @JsonProperty("terms") Terms terms, @JsonProperty("address") Address address, @JsonProperty("productDescription") String productDescription){
                this.cggId = cggId;
                this.name = name;
                this.image = image;
                this.link = link;
                this.linkMobile = linkMobile;
                this.description = description;
                this.providerDisclaimer = providerDisclaimer;
                this.terms = terms;
                this.address = address;
                this.productDescription = productDescription;
            }
    
            public static final class Description {
        
                @JsonCreator
                public Description(){
                }
            }
    
            public static final class Terms {
        
                @JsonCreator
                public Terms(){
                }
            }
    
            public static final class Address {
        
                @JsonCreator
                public Address(){
                }
            }

			public String getCggId() {
				return cggId;
			}

			public String getName() {
				return name;
			}

			public String getImage() {
				return image;
			}

			public String getLink() {
				return link;
			}

			public String getLinkMobile() {
				return linkMobile;
			}

			public Description getDescription() {
				return description;
			}

			public String getProviderDisclaimer() {
				return providerDisclaimer;
			}

			public Terms getTerms() {
				return terms;
			}

			public Address getAddress() {
				return address;
			}

			public String getProductDescription() {
				return productDescription;
			}
        }

        public static final class ProductCategory {
            public final String categoryType;
    
            @JsonCreator
            public ProductCategory(@JsonProperty("categoryType") String categoryType){
                this.categoryType = categoryType;
            }
        }

        public static final class Specification {
    
            @JsonCreator
            public Specification(){
            }
        }

        public static final class Banner {
            public final String bannerDesc;
            public final String bannerType;
            public final BannerExpiryDate bannerExpiryDate;
    
            @JsonCreator
            public Banner(@JsonProperty("bannerDesc") String bannerDesc, @JsonProperty("bannerType") String bannerType, @JsonProperty("bannerExpiryDate") BannerExpiryDate bannerExpiryDate){
                this.bannerDesc = bannerDesc;
                this.bannerType = bannerType;
                this.bannerExpiryDate = bannerExpiryDate;
            }
    
            public static final class BannerExpiryDate {
        
                @JsonCreator
                public BannerExpiryDate(){
                }
            }

			public String getBannerDesc() {
				return bannerDesc;
			}

			public String getBannerType() {
				return bannerType;
			}

			public BannerExpiryDate getBannerExpiryDate() {
				return bannerExpiryDate;
			}
        }

        public static final class Fee {
            public final AprDesc aprDesc[];
            public final FeeDesc feeDesc[];
            public final InsuranceDesc insuranceDesc[];
    
            @JsonCreator
            public Fee(@JsonProperty("aprDesc") AprDesc[] aprDesc, @JsonProperty("feeDesc") FeeDesc[] feeDesc, @JsonProperty("insuranceDesc") InsuranceDesc[] insuranceDesc){
                this.aprDesc = aprDesc;
                this.feeDesc = feeDesc;
                this.insuranceDesc = insuranceDesc;
            }
    
            public static final class AprDesc {
                public final String description;
                public final String key;
                public final String translationKey;
        
                @JsonCreator
                public AprDesc(@JsonProperty("description") String description, @JsonProperty("key") String key, @JsonProperty("translationKey") String translationKey){
                    this.description = description;
                    this.key = key;
                    this.translationKey = translationKey;
                }

				public String getDescription() {
					return description;
				}

				public String getKey() {
					return key;
				}

				public String getTranslationKey() {
					return translationKey;
				}
            }
    
            public static final class FeeDesc {
                public final String description;
                public final String key;
                public final String translationKey;
        
                @JsonCreator
                public FeeDesc(@JsonProperty("description") String description, @JsonProperty("key") String key, @JsonProperty("translationKey") String translationKey){
                    this.description = description;
                    this.key = key;
                    this.translationKey = translationKey;
                }

				public String getDescription() {
					return description;
				}

				public String getKey() {
					return key;
				}

				public String getTranslationKey() {
					return translationKey;
				}
            }
    
            public static final class InsuranceDesc {
                public final String description;
                public final String key;
                public final String translationKey;
        
                @JsonCreator
                public InsuranceDesc(@JsonProperty("description") String description, @JsonProperty("key") String key, @JsonProperty("translationKey") String translationKey){
                    this.description = description;
                    this.key = key;
                    this.translationKey = translationKey;
                }

				public String getDescription() {
					return description;
				}

				public String getKey() {
					return key;
				}

				public String getTranslationKey() {
					return translationKey;
				}
            }

			public AprDesc[] getAprDesc() {
				return aprDesc;
			}

			public FeeDesc[] getFeeDesc() {
				return feeDesc;
			}

			public InsuranceDesc[] getInsuranceDesc() {
				return insuranceDesc;
			}
        }

        public static final class LoanDetails {
            public final long propertyValue;
            public final long borrowingPercentage;
            public final long loanAmount;
            public final long loanTenure;
            public final String loanTenureUnit;
    
            @JsonCreator
            public LoanDetails(@JsonProperty("propertyValue") long propertyValue, @JsonProperty("borrowingPercentage") long borrowingPercentage, @JsonProperty("loanAmount") long loanAmount, @JsonProperty("loanTenure") long loanTenure, @JsonProperty("loanTenureUnit") String loanTenureUnit){
                this.propertyValue = propertyValue;
                this.borrowingPercentage = borrowingPercentage;
                this.loanAmount = loanAmount;
                this.loanTenure = loanTenure;
                this.loanTenureUnit = loanTenureUnit;
            }

			public long getPropertyValue() {
				return propertyValue;
			}

			public long getBorrowingPercentage() {
				return borrowingPercentage;
			}

			public long getLoanAmount() {
				return loanAmount;
			}

			public long getLoanTenure() {
				return loanTenure;
			}

			public String getLoanTenureUnit() {
				return loanTenureUnit;
			}
        }

        public static final class Others {
            public final WidgetHasOfferId widgetHasOfferId;
            public final WidgetHasOfferLinkId widgetHasOfferLinkId;
            public final DoubleClickDesktopAdId doubleClickDesktopAdId;
            public final DoubleClickMobileAdId doubleClickMobileAdId;
            public final DoubleClickTabletAdId doubleClickTabletAdId;
    
            @JsonCreator
            public Others(@JsonProperty("widgetHasOfferId") WidgetHasOfferId widgetHasOfferId, @JsonProperty("widgetHasOfferLinkId") WidgetHasOfferLinkId widgetHasOfferLinkId, @JsonProperty("doubleClickDesktopAdId") DoubleClickDesktopAdId doubleClickDesktopAdId, @JsonProperty("doubleClickMobileAdId") DoubleClickMobileAdId doubleClickMobileAdId, @JsonProperty("doubleClickTabletAdId") DoubleClickTabletAdId doubleClickTabletAdId){
                this.widgetHasOfferId = widgetHasOfferId;
                this.widgetHasOfferLinkId = widgetHasOfferLinkId;
                this.doubleClickDesktopAdId = doubleClickDesktopAdId;
                this.doubleClickMobileAdId = doubleClickMobileAdId;
                this.doubleClickTabletAdId = doubleClickTabletAdId;
            }
    
            public static final class WidgetHasOfferId {
        
                @JsonCreator
                public WidgetHasOfferId(){
                }
            }
    
            public static final class WidgetHasOfferLinkId {
        
                @JsonCreator
                public WidgetHasOfferLinkId(){
                }
            }
    
            public static final class DoubleClickDesktopAdId {
        
                @JsonCreator
                public DoubleClickDesktopAdId(){
                }
            }
    
            public static final class DoubleClickMobileAdId {
        
                @JsonCreator
                public DoubleClickMobileAdId(){
                }
            }
    
            public static final class DoubleClickTabletAdId {
        
                @JsonCreator
                public DoubleClickTabletAdId(){
                }
            }

			public WidgetHasOfferId getWidgetHasOfferId() {
				return widgetHasOfferId;
			}

			public WidgetHasOfferLinkId getWidgetHasOfferLinkId() {
				return widgetHasOfferLinkId;
			}

			public DoubleClickDesktopAdId getDoubleClickDesktopAdId() {
				return doubleClickDesktopAdId;
			}

			public DoubleClickMobileAdId getDoubleClickMobileAdId() {
				return doubleClickMobileAdId;
			}

			public DoubleClickTabletAdId getDoubleClickTabletAdId() {
				return doubleClickTabletAdId;
			}
        }

        public static final class Attributes {
            public final AnnualRateMin annualRateMin;
            public final long healthInsurance;
            public final long borrowingPercentage;
            public final double apr;
            public final double monthlyRepayment;
            public final double monthlyRate;
            public final double euriborRate;
            public final long propertyValue;
            public final double annualRate;
            public final String interestRateType;
            public final DailyRepayment dailyRepayment;
            public final double multiRiskInsurance;
            public final String annualRateDesc;
            public final PromotionDiscount promotionDiscount;
            public final double totalStartUpFee;
            public final AnnualRateMax annualRateMax;
            public final double fixedRate;
            public final TotalCost totalCost;
            public final double monthlyFee;
            public final double taxOnCredit;
            public final double totalRepayment;
    
            @JsonCreator
            public Attributes(@JsonProperty("annualRateMin") AnnualRateMin annualRateMin, @JsonProperty("healthInsurance") long healthInsurance, @JsonProperty("borrowingPercentage") long borrowingPercentage, @JsonProperty("apr") double apr, @JsonProperty("monthlyRepayment") double monthlyRepayment, @JsonProperty("monthlyRate") double monthlyRate, @JsonProperty("euriborRate") double euriborRate, @JsonProperty("propertyValue") long propertyValue, @JsonProperty("annualRate") double annualRate, @JsonProperty("interestRateType") String interestRateType, @JsonProperty("dailyRepayment") DailyRepayment dailyRepayment, @JsonProperty("multiRiskInsurance") long multiRiskInsurance, @JsonProperty("annualRateDesc") String annualRateDesc, @JsonProperty("promotionDiscount") PromotionDiscount promotionDiscount, @JsonProperty("totalStartUpFee") long totalStartUpFee, @JsonProperty("annualRateMax") AnnualRateMax annualRateMax, @JsonProperty("fixedRate") double fixedRate, @JsonProperty("totalCost") TotalCost totalCost, @JsonProperty("monthlyFee") double monthlyFee, @JsonProperty("taxOnCredit") long taxOnCredit, @JsonProperty("totalRepayment") double totalRepayment){
                this.annualRateMin = annualRateMin;
                this.healthInsurance = healthInsurance;
                this.borrowingPercentage = borrowingPercentage;
                this.apr = apr;
                this.monthlyRepayment = monthlyRepayment;
                this.monthlyRate = monthlyRate;
                this.euriborRate = euriborRate;
                this.propertyValue = propertyValue;
                this.annualRate = annualRate;
                this.interestRateType = interestRateType;
                this.dailyRepayment = dailyRepayment;
                this.multiRiskInsurance = multiRiskInsurance;
                this.annualRateDesc = annualRateDesc;
                this.promotionDiscount = promotionDiscount;
                this.totalStartUpFee = totalStartUpFee;
                this.annualRateMax = annualRateMax;
                this.fixedRate = fixedRate;
                this.totalCost = totalCost;
                this.monthlyFee = monthlyFee;
                this.taxOnCredit = taxOnCredit;
                this.totalRepayment = totalRepayment;
            }
    
            public static final class AnnualRateMin {
        
                @JsonCreator
                public AnnualRateMin(){
                }
            }
    
            public static final class DailyRepayment {
        
                @JsonCreator
                public DailyRepayment(){
                }
            }
    
            public static final class PromotionDiscount {
        
                @JsonCreator
                public PromotionDiscount(){
                }
            }
    
            public static final class AnnualRateMax {
        
                @JsonCreator
                public AnnualRateMax(){
                }
            }
    
            public static final class TotalCost {
        
                @JsonCreator
                public TotalCost(){
                }
            }

			public AnnualRateMin getAnnualRateMin() {
				return annualRateMin;
			}

			public long getHealthInsurance() {
				return healthInsurance;
			}

			public long getBorrowingPercentage() {
				return borrowingPercentage;
			}

			public Double getApr() {
				return apr;
			}

			public Double getMonthlyRepayment() {
				return monthlyRepayment;
			}

			public double getMonthlyRate() {
				return monthlyRate;
			}

			public double getEuriborRate() {
				return euriborRate;
			}

			public long getPropertyValue() {
				return propertyValue;
			}

			public double getAnnualRate() {
				return annualRate;
			}

			public String getInterestRateType() {
				return interestRateType;
			}

			public DailyRepayment getDailyRepayment() {
				return dailyRepayment;
			}

			public double getMultiRiskInsurance() {
				return multiRiskInsurance;
			}

			public String getAnnualRateDesc() {
				return annualRateDesc;
			}

			public PromotionDiscount getPromotionDiscount() {
				return promotionDiscount;
			}

			public double getTotalStartUpFee() {
				return totalStartUpFee;
			}

			public AnnualRateMax getAnnualRateMax() {
				return annualRateMax;
			}

			public double getFixedRate() {
				return fixedRate;
			}

			public TotalCost getTotalCost() {
				return totalCost;
			}

			public double getMonthlyFee() {
				return monthlyFee;
			}

			public double getTaxOnCredit() {
				return taxOnCredit;
			}

			public Double getTotalRepayment() {
				return totalRepayment;
			}
        }

		public String getApprovalRate() {
			return approvalRate;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public long getLastUpdated() {
			return lastUpdated;
		}

		public String getImage() {
			return image;
		}

		public String getCggId() {
			return cggId;
		}

		public boolean isHasDesktopApplyButton() {
			return hasDesktopApplyButton;
		}

		public boolean isHasApplicationForm() {
			return hasApplicationForm;
		}

		public boolean isApplicationFormGeneric() {
			return isApplicationFormGeneric;
		}

		public boolean isHasMobileApplyButton() {
			return hasMobileApplyButton;
		}

		public boolean isHasTabletApplyButton() {
			return hasTabletApplyButton;
		}

		public String[] getFootnote() {
			return footnote;
		}

		public boolean isShowCTALeadCapture() {
			return showCTALeadCapture;
		}

		public Provider getProvider() {
			return provider;
		}

		public ProductCategory[] getProductCategories() {
			return productCategories;
		}

		public String[] getFeature() {
			return feature;
		}

		public Specification[] getSpecification() {
			return specification;
		}

		public Banner getBanner() {
			return banner;
		}

		public Fee getFee() {
			return fee;
		}

		public LoanDetails getLoanDetails() {
			return loanDetails;
		}

		public Others getOthers() {
			return others;
		}

		public Attributes getAttributes() {
			return attributes;
		}
    }

	public Product[] getFeaturedProducts() {
		return featuredProducts;
	}

	public Product[] getProducts() {
		return products;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public long getTotalFeaturedProducts() {
		return totalFeaturedProducts;
	}

	public long getTotalNonFeaturedProducts() {
		return totalNonFeaturedProducts;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public String getDataFileLastUpdateDate() {
		return dataFileLastUpdateDate;
	}
}
