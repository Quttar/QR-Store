package com.example.appqrstore;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appqrstore.databinding.ActivityMainBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RequestQueue queue;

    private RecyclerView cartElems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);

        AppData.Cart = new ArrayList<CartElem>();
        AppData.Cart.add(new CartElem("Спрайт", "1 литр", "https://i.postimg.cc/tpJnWcfG/image.png?dl=1", 3, 90));
        AppData.Cart.add(new CartElem("Чипсы лейс с крабом", "150 грамм", "https://i.postimg.cc/MqpVtHn4/image.png?dl=1", 1, 150));
        AppData.Cart.add(new CartElem("Чебупицца пеперони", "250 грамм", "https://i.postimg.cc/HpNTGSzX/image.png?dl=1", 3, 79));
        AppData.Cart.add(new CartElem("Арбуз", "7 - 9 килограмм", "https://i.postimg.cc/vQYznsJL/image.png?dl=1", 1, 79));
        AppData.Cart.add(new CartElem("Онигири лосось спайс", "150 грамм", "https://i.postimg.cc/z8p3BYk1/image.png?dl=1", 2, 99));
        AppData.Cart.add(new CartElem("Колонка Алиса лайт", "голубая", "https://i.postimg.cc/4fbsSpLT/image.png?dl=1", 1, 4599));

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        //recyclerView.addItemDecoration(new DividerItemDecoration(this,
                //DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_authors) {
//            NavHostFragment.findNavController(FirstFragment.this)
//                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(AppData.Tag, "onActivityResult");

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null){

                String url_get="https://c17f-94-79-33-20.ngrok-free.app/product/" + contents;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_get, null, new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {

                        try {
                            String name = response.getString("name");
                            String description = response.getString("description");
                            String imageUrl = response.getString("imageUrl");
                            int price = response.getInt("price");

                            boolean flag = false;
                            for (int i = 0; i < AppData.Cart.size(); i++){
                                if (AppData.Cart.get(i).name.equals(name)){
                                    AppData.Cart.get(i).count = AppData.Cart.get(i).count + 1;
                                    AppData.Adapter.notifyDataSetChanged();
                                    flag = true;
                                }
                            }

                            if (flag == false){
                                AppData.Cart.add(0, new CartElem(name, description, imageUrl, 1, price));
                                AppData.Adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                    new Response.ErrorListener() {
                    // this is the error listener method which
                    // we will call if we get any error from API.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // below line is use to display a toast message along with our error.
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}