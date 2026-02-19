package de.hwg_lu.bwi520.modell;

public class Rating {

	private int RatingID;
	private int rating;
	private String Comment;
	private User User;
	public Rating(int ratingID, int rating, String comment, de.hwg_lu.bwi520.modell.User user) {
		super();
		RatingID = ratingID;
		this.rating = rating;
		Comment = comment;
		User = user;
	}
	public int getRatingID() {
		return RatingID;
	}
	public void setRatingID(int ratingID) {
		RatingID = ratingID;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public User getUser() {
		return User;
	}
	public void setUser(User user) {
		User = user;
	}
	
	
}
