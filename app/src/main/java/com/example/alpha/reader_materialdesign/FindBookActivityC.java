package com.example.alpha.reader_materialdesign;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.alpha.reader_materialdesign.Adapter.FindNewBookRecommendAdapter;
import com.example.alpha.reader_materialdesign.Domain.FindBookItem;
import com.example.alpha.reader_materialdesign.Utils.RecommendUtil;

import java.io.IOException;
import java.util.ArrayList;

public class FindBookActivityC extends AppCompatActivity {

    private ArrayList<FindBookItem> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long start  = System.currentTimeMillis();

        Log.d("lxd", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);
        Toolbar toolbar = findViewById(R.id.bookRecommendToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        new LoadTask().execute();
        while(list.size() == 0){
            Log.i("lxd","list为空");
        }

        recyclerView = findViewById(R.id.main_find_newBookShow_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        FindNewBookRecommendAdapter adapter = new FindNewBookRecommendAdapter(list);
        recyclerView.setAdapter(adapter);

        long end = System.currentTimeMillis();
        Log.i("lxd","onCreate last time is "+ (end - start));
    }



    class LoadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if(getIntent().getIntExtra("kind", -1) == 1) {
                initBookRecommend();
            }else if(getIntent().getIntExtra("kind", -1) == 2){
                initBookConcerned();
            }else if(getIntent().getIntExtra("kind", -1) == 3){
                initBookTop250();
            }
            return null;
        }
    }

    private void initBookRecommend(){
        list.clear();
        try {
            list = RecommendUtil.crawlerExecute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void initBookConcerned(){
        list.clear();
        list = RecommendUtil.getConcernedBook();
    }

    private void initBookTop250(){
        long start  = System.currentTimeMillis();
        list.clear();
        list = RecommendUtil.getTop250Book();
        long end = System.currentTimeMillis();
        Log.i("lxd","initBookRecommend last time is "+ (end - start));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //Intent intent = new Intent(this, MainActivity.class);
                //startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
