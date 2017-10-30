package info.androidhive.firebase.model;

public class Rating {

    public int Movie_id;
    public int Rating;
    public int User_id;

    public Rating()
    {
    }

    public Rating(int movie_id, int ratings, int user_id)
    {
        this.Movie_id = movie_id;
        this.Rating = ratings;
        this.User_id = user_id;
    }
}
