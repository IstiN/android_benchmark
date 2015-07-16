package com.epam.android.benchmark;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    public static final String SOURCE_100 = "source_100.json";
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
        final long now = System.currentTimeMillis();
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                process();
                log("onProcessClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void process() {
        try {
            InputStream inputStream = getAssets().open(SOURCE_100);
            mMember.process(getApplication(), inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("check asset name");
        }
    }

    public void onGetCachedEntitiesClick(View view) {
        final long now = System.currentTimeMillis();
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                getCachedEntities();
                log("onGetCachedEntitiesClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void getCachedEntities() {
        mMember.getCachedEntities(getApplication());
    }

    public void onGetCachedEntitiesWithFilterClick(View view) {
        final long now = System.currentTimeMillis();
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                getCachedEntitiesWithFilter();
                log("onGetCachedEntitiesWithFilterClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void getCachedEntitiesWithFilter() {
        //filter params will be provided in future
        mMember.getCachedEntitiesWithFilter(getApplication(), null, null, null, null);
    }

    public void onDeleteClick(View view) {
        final long now = System.currentTimeMillis();
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                delete();
                log("onDeleteClick", System.currentTimeMillis() - now);
            }
        });
    }

    private void delete() {
        mMember.delete(getApplication());
    }

    public void onFullTestClick(View view) {
        final long now = System.currentTimeMillis();
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                process();
                getCachedEntities();
                getCachedEntitiesWithFilter();
                delete();
                log("onFullTestClick", System.currentTimeMillis() - now);
            }
        });
    }
}
