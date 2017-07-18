package com.cgg.producer;

public class URLProducerBuilder extends Builder <URLProducerBuilder> {

	private int maxVisit = 200;
	private int counter = 0;
	private int maxVisitForSinglePage = 200;

	public URLProducerBuilder create(Builder builder) {

		this.toVisitLinks(builder.getToVisitLinks()).visitedLinks(builder.getVisitedLinks())
				.simpleVisitedLinks(builder.getSimpleVisitedLinks()).domainUrl(builder.getDomainUrl())
				.logger(builder.getLogger()).logFile(builder.getLogFile()).threadID(builder.getThreadID())
				.width(builder.getWidth()).height(builder.getHeight()).isMobile(builder.getIsMobile());

		return this;
	}

	public URLProducerBuilder maxVisit(final int maxVisit) {

		this.maxVisit = maxVisit;
		return this;
	}

	public URLProducerBuilder counter(final int counter) {

		this.counter = counter;
		return this;
	}

	public URLProducerBuilder maxVisitForSinglePage(final int maxVisitForSinglePage) {

		this.maxVisitForSinglePage = maxVisitForSinglePage;
		return this;
	}

	public URLProducer build() {

		return new URLProducer(this);
	}

	public int getMaxVisit() {
		return maxVisit;
	}

	public void setMaxVisit(int maxVisit) {
		this.maxVisit = maxVisit;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMaxVisitForSinglePage() {
		return maxVisitForSinglePage;
	}

	public void setMaxVisitForSinglePage(int maxVisitForSinglePage) {
		this.maxVisitForSinglePage = maxVisitForSinglePage;
	}
}
