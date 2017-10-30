package info.androidhive.firebase.activity;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import info.androidhive.firebase.APICatcher;
import info.androidhive.firebase.R;
import info.androidhive.firebase.RatingFragment;
import info.androidhive.firebase.model.Movie;
import info.androidhive.firebase.model.MoviesResponse;
import info.androidhive.firebase.rest.ApiClient;
import info.androidhive.firebase.rest.ApiInterface;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {

    int movieId;
    String base_url, url, plot, title, posterPath;
    private ProgressDialog progressDialog;
    ImageView moviePoster;
    CollapsingToolbarLayout collapsingToolbarLayout;
    //RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar);

    private final static String API_KEY = "32f5f1d0ff7289475163c1cfb3ec9061";

    //TmdbMovies tmdbMovies = new TmdbApi(API_KEY).getMovies();

    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                RatingFragment dialogFragment = new RatingFragment();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });

        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", 0);

        moviePoster = (ImageView) findViewById(R.id.poster);
        base_url = "https://image.tmdb.org/t/p/w500";

        new MyTask().execute();
    }

    public class MyTask extends AsyncTask<String, Void, String>
    {
        APICatcher objJson = new APICatcher();
        @Override
        protected String doInBackground(String... params) {
            String unparsed = objJson.getJSONFile(movieId);
            return unparsed;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MovieActivity.this,"Fetching details","Please wait...",false,false);
        }

        @Override
        protected void onPostExecute(String unparsed)
        {
            super.onPostExecute(unparsed);

            try
            {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonObject = new JSONObject(unparsed);

                plot = jsonObject.optString("overview").toString();
                title = jsonObject.optString("title").toString();
                posterPath = jsonObject.optString("backdrop_path").toString();
            }

            catch (JSONException e)
            {e.printStackTrace();}

            TextView details = (TextView) findViewById(R.id.plot);
            details.setText("Plot: \n" + plot);

            //MovieDb movie = tmdbMovies.getMovie(5353, "en");
            //List<PersonCast> cast = tmdbMovies.getCredits(movieId).getCast();
            //for (int i = 0 ; i < cast.size() ; i++)
                //{
//                    Log.d("cast" , cast.get(i).toString());
//                }
            url = base_url.concat(posterPath);
            Picasso.with(MovieActivity.this).load(url).into(moviePoster);
            collapsingToolbarLayout.setTitle(title);

            Log.e("list", url);
            progressDialog.dismiss();
        }
    }
}
