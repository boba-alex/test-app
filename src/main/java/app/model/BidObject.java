package app.model;

public class BidObject {

	private Bid bid;

	public BidObject() {

	}

	public BidObject(Bid bid) {
		this.bid = bid;
	}

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {

		this.bid = bid;
	}
}
