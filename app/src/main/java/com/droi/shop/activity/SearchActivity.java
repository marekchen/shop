package com.droi.shop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.shop.R;
import com.droi.shop.adapter.SimpleAdapter;
import com.droi.shop.fragment.ItemListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    private static final String SP_SEARCH_HISTORY_FILE = "search_history";
    private static final String SP_SEARCH_HISTORY_KEY = "search";
    private String mSeparator;
    @BindView(R.id.search_history_layout)
    LinearLayout mSearchHistoryLayout;
    @BindView(R.id.search_result_layout)
    LinearLayout mSearchResultLayout;
    @BindView(R.id.toolbar_search)
    EditText mSearchView;
    @BindView(R.id.search_progress)
    ProgressBar mProgressBar;

    @OnClick(R.id.search_history_clear)
    void clear() {
        clearSearchHistory();
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSeparator = ";";
        initToolbar();
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchContent = mSearchView.getText().toString();
                    if (!searchContent.isEmpty()) {
                        performSearch(searchContent);
                    } else {
                        Toast.makeText(SearchActivity.this, R.string.search_content_not_empty, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        mSearchHistoryLayout.setVisibility(View.GONE);
        mSearchResultLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        refreshSearchHistory();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_search) {
                    String searchContent = mSearchView.getText().toString();
                    if (!searchContent.isEmpty()) {
                        performSearch(searchContent);
                    } else {
                        Toast.makeText(SearchActivity.this, R.string.search_content_not_empty, Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSearchHistory();
    }

    private void refreshSearchHistory() {
        final ArrayList<String> searchHistory = getSearchHistory();
        if (!searchHistory.isEmpty()) {
            mSearchHistoryLayout.setVisibility(View.VISIBLE);
            ListView listView = (ListView) findViewById(R.id.search_history_list);
            listView.setAdapter(new SimpleAdapter(this, searchHistory));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    performSearch(searchHistory.get(position));
                }
            });
        }
    }

    private void performSearch(String searchContent) {
        hideSoftKeyBoard(mSearchView);
        DroiAnalytics.onEvent(this, "search");
        saveSearchHistory(searchContent);
        Intent intent = new Intent(this, ItemsActivity.class);
        intent.putExtra(ItemListFragment.ITEM_NAME, searchContent);
        intent.putExtra(ItemListFragment.TYPE, ItemListFragment.TYPE_SEARCH);
        startActivity(intent);
    }

    private void saveSearchHistory(String searchContent) {
        SharedPreferences sp = getSharedPreferences(SP_SEARCH_HISTORY_FILE, 0);
        SharedPreferences.Editor editor = sp.edit();
        String savedContent = sp.getString(SP_SEARCH_HISTORY_KEY, "");
        if (savedContent.isEmpty()) {
            editor.putString(SP_SEARCH_HISTORY_KEY, searchContent + mSeparator);
        } else {
            if (savedContent.contains(searchContent)) {
                savedContent = savedContent.replaceAll(searchContent + mSeparator, "");
            }
            savedContent += searchContent + mSeparator;
            editor.putString(SP_SEARCH_HISTORY_KEY, savedContent);
        }
        editor.apply();
    }

    private ArrayList<String> getSearchHistory() {
        ArrayList<String> searchHistory = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences(SP_SEARCH_HISTORY_FILE, 0);
        String savedContent = sp.getString(SP_SEARCH_HISTORY_KEY, "");
        if (!savedContent.isEmpty()) {
            String[] savedArray = savedContent.split(mSeparator);
            for (int i = savedArray.length - 1; i > -1; i--) {
                if (!savedArray[i].isEmpty() && searchHistory.size() < 10) {
                    searchHistory.add(savedArray[i]);
                }
            }
        }
        return searchHistory;
    }

    private void clearSearchHistory() {
        SharedPreferences sp = getSharedPreferences(SP_SEARCH_HISTORY_FILE, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
        mSearchHistoryLayout.setVisibility(View.GONE);
        mSearchResultLayout.setVisibility(View.GONE);
    }

    private void hideSoftKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
