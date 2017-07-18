package com.cgg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserCityRecord {
    public final String status;
    public final Result results[];

    @JsonCreator
    public UserCityRecord(@JsonProperty("status") String status, @JsonProperty("results") Result[] results){
        this.status = status;
        this.results = results;
    }

    public static final class Speed {
        public final String max_speed_upload;
        public final String max_speed_download;

        @JsonCreator
        public Speed(@JsonProperty("max_speed_upload") String max_speed_upload, @JsonProperty("max_speed_download") String max_speed_download){
            this.max_speed_upload = max_speed_upload;
            this.max_speed_download = max_speed_download;
        }
    }

    public static final class PriceEquipment {
        public final String homeWifi;

        @JsonCreator
        public PriceEquipment(@JsonProperty("homeWifi") String homeWifi){
            this.homeWifi = homeWifi;
        }
    }
    
    public static final class Top_product {
        public final String totalMonthlyCost;
        public final String dataValue;
        public final String monthlyCost;
        public final String apply_url;
        public final String FourGspeed;
        public final String priceContract;
        public final String totalUpfrontCost;
        public final Speed speed;
        public final String id;
        public final long installationCost;
        public final String broadbandPhone;
        public final String service;
        public final long shippingCost;
        public final String connectionType;
        public final String productTypeName;
        public final String totalContract;
        public final long avgMonthlyCost;
        public final long minPriceContract;
        public final String noOfChannels;
        public final String productId;
        public final String companyName;
        public final long contractLength;
        public final boolean forHome;
        public final String upfrontCost;
        public final String productName;
        public final long discount;
        public final String productLogo;
        public final double monthlyFee;
        public final String networkProvider;
        public final PriceEquipment priceEquipment;

        @JsonCreator
        public Top_product(@JsonProperty("totalMonthlyCost") String totalMonthlyCost, @JsonProperty("dataValue") String dataValue, @JsonProperty("monthlyCost") String monthlyCost, 
        		@JsonProperty("apply_url") String apply_url, @JsonProperty("4Gspeed") String FourGspeed, @JsonProperty("priceContract") String priceContract, @JsonProperty("totalUpfrontCost") String totalUpfrontCost, 
        		@JsonProperty("speed") Speed speed, @JsonProperty("id") String id, @JsonProperty("installationCost") long installationCost, @JsonProperty("broadbandPhone") String broadbandPhone, 
        		@JsonProperty("service") String service, @JsonProperty("shippingCost") long shippingCost, @JsonProperty("connectionType") String connectionType, @JsonProperty("productTypeName") String productTypeName, 
        		@JsonProperty("totalContract") String totalContract, @JsonProperty("avgMonthlyCost") long avgMonthlyCost, @JsonProperty("minPriceContract") long minPriceContract, @JsonProperty("noOfChannels") String noOfChannels, 
        		@JsonProperty("productId") String productId, @JsonProperty("companyName") String companyName, @JsonProperty("contractLength") long contractLength, @JsonProperty("forHome") boolean forHome, 
        		@JsonProperty("upfrontCost") String upfrontCost, @JsonProperty("productName") String productName, @JsonProperty("discount") long discount, @JsonProperty("productLogo") String productLogo, 
        		@JsonProperty("monthlyFee") double monthlyFee, @JsonProperty("networkProvider") String networkProvider, @JsonProperty("priceEquipment") PriceEquipment priceEquipment){
        	
            this.totalMonthlyCost = totalMonthlyCost;
            this.dataValue = dataValue;
            this.monthlyCost = monthlyCost;
            this.apply_url = apply_url;
            this.FourGspeed = FourGspeed;
            this.priceContract = priceContract;
            this.totalUpfrontCost = totalUpfrontCost;
            this.speed = speed;
            this.id = id;
            this.installationCost = installationCost;
            this.broadbandPhone = broadbandPhone;
            this.service = service;
            this.shippingCost = shippingCost;
            this.connectionType = connectionType;
            this.productTypeName = productTypeName;
            this.totalContract = totalContract;
            this.avgMonthlyCost = avgMonthlyCost;
            this.minPriceContract = minPriceContract;
            this.noOfChannels = noOfChannels;
            this.productId = productId;
            this.companyName = companyName;
            this.contractLength = contractLength;
            this.forHome = forHome;
            this.upfrontCost = upfrontCost;
            this.productName = productName;
            this.discount = discount;
            this.productLogo = productLogo;
            this.monthlyFee = monthlyFee;
            this.networkProvider = networkProvider;
            this.priceEquipment = priceEquipment;
        }
    }
    
    public static final class Result {
        public final boolean dm_consent;
        public final String cgg_id;
        public final String vertical;
        public final String language;
        public final String event_id;
        public final String ip;
        public final String event_updated;
        public final long id;
        public final String phone;
        public final boolean tc_consent;
        public final String email;
        public final String event_created;
        public final long event_epoch;
        public final Attributes attributes;
        public final String locale;
        public final String source_url;
        public final String event_key;

        @JsonCreator
        public Result(@JsonProperty("dm_consent") boolean dm_consent, @JsonProperty("cgg_id") String cgg_id, @JsonProperty("vertical") String vertical, @JsonProperty("language") String language, @JsonProperty("event_id") String event_id, @JsonProperty("ip") String ip, @JsonProperty("event_updated") String event_updated, @JsonProperty("id") long id, @JsonProperty("phone") String phone, @JsonProperty("tc_consent") boolean tc_consent, @JsonProperty("email") String email, @JsonProperty("event_created") String event_created, @JsonProperty("event_epoch") long event_epoch, @JsonProperty("attributes") Attributes attributes, @JsonProperty("locale") String locale, @JsonProperty("source_url") String source_url, @JsonProperty("event_key") String event_key){
            this.dm_consent = dm_consent;
            this.cgg_id = cgg_id;
            this.vertical = vertical;
            this.language = language;
            this.event_id = event_id;
            this.ip = ip;
            this.event_updated = event_updated;
            this.id = id;
            this.phone = phone;
            this.tc_consent = tc_consent;
            this.email = email;
            this.event_created = event_created;
            this.event_epoch = event_epoch;
            this.attributes = attributes;
            this.locale = locale;
            this.source_url = source_url;
            this.event_key = event_key;
        }
    }
        
    public static final class Attributes {
            public final boolean authenticated;
            public final String results_page_url;
            public final String apply_phone;
            public final Featured_product featured_products[];
            public final String usercityEventName;
            public final Top_product top_products[];
    
            @JsonCreator
            public Attributes(@JsonProperty("authenticated") boolean authenticated, @JsonProperty("results_page_url") String results_page_url, @JsonProperty("apply_phone") String apply_phone, @JsonProperty("featured_products") Featured_product[] featured_products, @JsonProperty("usercityEventName") String usercityEventName, @JsonProperty("top_products") Top_product[] top_products){
                this.authenticated = authenticated;
                this.results_page_url = results_page_url;
                this.apply_phone = apply_phone;
                this.featured_products = featured_products;
                this.usercityEventName = usercityEventName;
                this.top_products = top_products;
            }
    }
    
    public static final class Featured_product {
        public final String totalMonthlyCost;
        public final String dataValue;
        public final String monthlyCost;
        public final String apply_url;
        public final String FourGspeed;
        public final String priceContract;
        public final String totalUpfrontCost;
        public final Speed speed;
        public final String id;
        public final long installationCost;
        public final String broadbandPhone;
        public final String service;
        public final long shippingCost;
        public final String connectionType;
        public final String productTypeName;
        public final String totalContract;
        public final long avgMonthlyCost;
        public final long minPriceContract;
        public final String noOfChannels;
        public final String productId;
        public final String companyName;
        public final long contractLength;
        public final boolean forHome;
        public final String upfrontCost;
        public final String productName;
        public final long discount;
        public final String productLogo;
        public final long monthlyFee;
        public final String networkProvider;
        public final PriceEquipment priceEquipment;

        @JsonCreator
        public Featured_product(@JsonProperty("totalMonthlyCost") String totalMonthlyCost, @JsonProperty("dataValue") String dataValue, @JsonProperty("monthlyCost") String monthlyCost, 
        		@JsonProperty("apply_url") String apply_url, @JsonProperty("4Gspeed") String FourGspeed, @JsonProperty("priceContract") String priceContract, @JsonProperty("totalUpfrontCost") String totalUpfrontCost, 
        		@JsonProperty("speed") Speed speed, @JsonProperty("id") String id, @JsonProperty("installationCost") long installationCost, @JsonProperty("broadbandPhone") String broadbandPhone, 
        		@JsonProperty("service") String service, @JsonProperty("shippingCost") long shippingCost, @JsonProperty("connectionType") String connectionType, @JsonProperty("productTypeName") String productTypeName, 
        		@JsonProperty("totalContract") String totalContract, @JsonProperty("avgMonthlyCost") long avgMonthlyCost, @JsonProperty("minPriceContract") long minPriceContract, 
        		@JsonProperty("noOfChannels") String noOfChannels, @JsonProperty("productId") String productId, @JsonProperty("companyName") String companyName, @JsonProperty("contractLength") long contractLength,
        		@JsonProperty("forHome") boolean forHome, @JsonProperty("upfrontCost") String upfrontCost, @JsonProperty("productName") String productName, @JsonProperty("discount") long discount, 
        		@JsonProperty("productLogo") String productLogo, @JsonProperty("monthlyFee") long monthlyFee, @JsonProperty("networkProvider") String networkProvider, 
        		@JsonProperty("priceEquipment") PriceEquipment priceEquipment){
        	
            this.totalMonthlyCost = totalMonthlyCost;
            this.dataValue = dataValue;
            this.monthlyCost = monthlyCost;
            this.apply_url = apply_url;
            this.FourGspeed = FourGspeed;
            this.priceContract = priceContract;
            this.totalUpfrontCost = totalUpfrontCost;
            this.speed = speed;
            this.id = id;
            this.installationCost = installationCost;
            this.broadbandPhone = broadbandPhone;
            this.service = service;
            this.shippingCost = shippingCost;
            this.connectionType = connectionType;
            this.productTypeName = productTypeName;
            this.totalContract = totalContract;
            this.avgMonthlyCost = avgMonthlyCost;
            this.minPriceContract = minPriceContract;
            this.noOfChannels = noOfChannels;
            this.productId = productId;
            this.companyName = companyName;
            this.contractLength = contractLength;
            this.forHome = forHome;
            this.upfrontCost = upfrontCost;
            this.productName = productName;
            this.discount = discount;
            this.productLogo = productLogo;
            this.monthlyFee = monthlyFee;
            this.networkProvider = networkProvider;
            this.priceEquipment = priceEquipment;
        }
    }
}
