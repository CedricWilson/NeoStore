package com.example.neostore2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neostore2.Adapters.AdapterProduct;
import com.example.neostore2.Data.DataProduct;
import com.example.neostore2.Helpers.HelperShared;
import com.example.neostore2.Helpers.RetroViewModel;

import java.util.List;

public class ActivityProductTable extends ActivityBase {
    private RecyclerView mRecyclerView;
    private AdapterProduct mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RetroViewModel model;


    @Override
    protected void onStart() {
        super.onStart();
        if (!HelperShared.Helper.getInstance(this).isLoggedIn()) {
            Intent home = new Intent(getApplicationContext(), ActivityLogin.class);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(home);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_table);

        BackPressed(R.anim.slide_in_left, R.anim.slide_out_right);

        Toolbar toolbar = findViewById(R.id.hometoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        model = new ViewModelProvider(this).get(RetroViewModel.class);


        mRecyclerView = findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        TextView title = findViewById(R.id.tbProduct);
        Intent ptable = getIntent();
        String val = ptable.getStringExtra("id");

        switch (val) {
            case "1":
                title.setText("Table");
                ;
                break;
            case "2":
                title.setText("Chair");
                ;
                break;
            case "3":
                title.setText("Sofa");
                ;
                break;
            case "4":
                title.setText("Bed");
                ;
                break;
            default:
                throw new IllegalStateException("Unexpected value: ");
        }

        model.listproduct(val).observe(this, new Observer<List<DataProduct>>() {
            @Override
            public void onChanged(List<DataProduct> dataProducts) {
                List<DataProduct> exampleList = dataProducts;

                mAdapter = new AdapterProduct(exampleList, ActivityProductTable.this);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_producttable, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}