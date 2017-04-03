package com.enduringstack.retrofitdemo7.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;

import com.enduringstack.retrofitdemo7.R;
import com.enduringstack.retrofitdemo7.Utils.InsetViewTransformer;
import com.enduringstack.retrofitdemo7.Utils.RepositoryDetailsViewHandler;
import com.enduringstack.retrofitdemo7.Utils.Utils;
import com.enduringstack.retrofitdemo7.adapter.CardAdapter;
import com.enduringstack.retrofitdemo7.listener.RecyclerItemClickListener;
import com.enduringstack.retrofitdemo7.model.Repository;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public abstract class RepoListActivity extends AppCompatActivity implements BottomSheetLayout.OnSheetStateChangeListener {

    private ProgressDialog progress;
    private CardAdapter cardAdapter;
    private ActionBar actionBar;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    private Repository selectedRepository;
    private View selectedView;
    private boolean bottomSheetVisible;
    private RepositoryDetailsViewHandler bottomSheetHandler;
    private boolean haveNetworkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RepoListActivity.this));
        cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(RepoListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        toggleSelection(view);
                        Repository repo = cardAdapter.getItemAtPosition(position);
                        showRepoDetails(repo);
                    }
                })
        );

        progress = new ProgressDialog(RepoListActivity.this, ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        actionBar = getSupportActionBar();

        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        bottomSheet = LayoutInflater.from(RepoListActivity.this).inflate(R.layout.my_sheet_layout, bottomSheetLayout, false);

        bottomSheetHandler = new RepositoryDetailsViewHandler(bottomSheet, RepoListActivity.this);

        if (!Utils.haveNetworkConnection(RepoListActivity.this)) {
            setSubTitle(getString(R.string.working_offline));
            haveNetworkConnection = false;
        } else {
            haveNetworkConnection = true;
        }
    }

    abstract int getContentView();

    abstract void cancelRequest();

    final boolean haveNetworkConnection() {
        return haveNetworkConnection;
    }

    /**
     * Append new item to list
     *
     * @param repo Repository object
     */
    final void addItem(Repository repo) {
        cardAdapter.addData(repo);
    }

    /**
     * Show progress dialog with specified message
     *
     * @param message message to show on dialog
     */
    final void showProgress(String message) {
        progress.setMessage(message);
        progress.show();
    }

    /**
     * Hide progress dialog
     */
    final void hideProgress() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    /**
     * Show subtitle on ActionBar
     *
     * @param text text to show as subtitle
     */
    final void setSubTitle(String text) {
        actionBar.setSubtitle(text);
    }

    private void showRepoDetails(Repository repo) {
        selectedRepository = repo;

        bottomSheetLayout.removeOnSheetStateChangeListener(this);
        bottomSheetLayout.addOnSheetStateChangeListener(this);
        bottomSheetLayout.showWithSheetView(bottomSheet, new InsetViewTransformer());
    }

    private void toggleSelection(View view) {
        removeSelection();
        view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        selectedView = view;
    }

    private void removeSelection() {
        if (selectedView != null) {
            selectedView.setBackgroundColor(0);
        }
    }

    @Override
    public void onSheetStateChanged(BottomSheetLayout.State state) {
        if (state == BottomSheetLayout.State.PEEKED && !bottomSheetVisible) {
            bottomSheetVisible = true;
            bottomSheetHandler.reload(selectedRepository);
        } else if (state == BottomSheetLayout.State.HIDDEN) {
            bottomSheetVisible = false;
            bottomSheetHandler.reset();
            removeSelection();
            cancelRequest();
        }
    }
}