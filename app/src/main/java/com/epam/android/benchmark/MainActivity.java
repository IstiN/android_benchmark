package com.epam.android.benchmark;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IMember;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String SOURCE_10000 = "source.json";
    public static final String SOURCE_1000 = "source_1000.json";
    public static final String SOURCE_100 = "source_100.json";

    public static final String SOURCE = SOURCE_10000;

    private TextView mLogView;

    private IMember mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogView = (TextView) findViewById(R.id.log_view);
        mMember = BenchmarkApplication.get(this, BenchmarkApplication.MEMBER_SYSTEM_KEY);
        mMember.onActivityCreate(this);
        Long memberCreationTime = BenchmarkApplication.get(this, BenchmarkApplication.MEMBER_CREATION_TIME);
        mLogView.setText(mMember.getName());
        log("member_creation_time", memberCreationTime);
    }

    private void log(final String message, final Long value) {
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              mLogView.setText(mLogView.getText() + "\n" + message + ": " + value);
                          }
                      }
        );
    }

    @Override
    protected void onDestroy() {
        mMember.onActivityDestroy(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/*");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, mLogView.getText());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onProcessClick(View view) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final long now = System.currentTimeMillis();
                process();
                log("onProcessClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void process() {
        try {
            InputStream inputStream = getAssets().open(SOURCE);
            mMember.process(getApplication(), inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("check asset name", e);
        } catch (Exception e) {
            throw new IllegalStateException("your code is crashed", e);
        }
    }

    public void onGetCachedEntitiesClick(View view) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final long now = System.currentTimeMillis();
                getCachedEntities();
                log("onGetCachedEntitiesClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void getCachedEntities() {
        List<IEntity> cachedEntities = mMember.getCachedEntities(getApplication());
        int size = cachedEntities.size();
        for (int i = 0; i < size; i++) {
            cachedEntities.get(i).print();
        }
        mMember.finishWorkWithCachedEntities(cachedEntities);
    }

    public void onGetCachedEntitiesWithFilterClick(View view) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final long now = System.currentTimeMillis();
                getCachedEntitiesWithFilter();
                log("onGetCachedEntitiesWithFilterClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void getCachedEntitiesWithFilter() {
        //filter params will be provided in future
        // 100
        List<IEntity> cachedEntitiesWithFilter = mMember.getCachedEntitiesWithFilter(getApplication(), true, "Arlene Gibbs", 30, 40);
        // 1000
        //List<IEntity> cachedEntitiesWithFilter = mMember.getCachedEntitiesWithFilter(getApplication(), true, "Amy Adams", 700, 800);
        // 10000
        //List<IEntity> cachedEntitiesWithFilter = mMember.getCachedEntitiesWithFilter(getApplication(), false, "Evans Lowery", 3800, 4000);
        int size = cachedEntitiesWithFilter.size();
        for (int i = 0; i < size; i++) {
            cachedEntitiesWithFilter.get(i).print();
        }
        mMember.finishWorkWithCachedEntities(cachedEntitiesWithFilter);
    }

    public void onDeleteClick(View view) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final long now = System.currentTimeMillis();
                delete();
                log("onDeleteClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void delete() {
        mMember.delete(getApplication());
    }

    public void onFullTestClick(View view) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                final long now = System.currentTimeMillis();
                process();
                getCachedEntities();
                getCachedEntitiesWithFilter();
                delete();
                log("onFullTestClick", System.currentTimeMillis() - now);
            }
        });
    }
}
