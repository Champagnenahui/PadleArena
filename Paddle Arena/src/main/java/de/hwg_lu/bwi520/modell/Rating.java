package de.hwg_lu.bwi520.modell;

public class Rating {

    private int ratingId;
    private int rating;
    private String comment;
    private User user;

    public Rating() {
    }

    public Rating(int ratingId, int rating, String comment, User user) {
        this.ratingId = ratingId;
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Rating [ratingId=" + ratingId + ", rating=" + rating
                + ", comment=" + comment + ", user=" + user + "]";
    }
}
