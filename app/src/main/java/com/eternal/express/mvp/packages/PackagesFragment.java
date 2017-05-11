package com.eternal.express.mvp.packages;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eternal.express.R;
import com.eternal.express.data.bean.Package;
import com.eternal.express.interfaze.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eternal.express.R.id.emptyView;
import static com.eternal.express.R.id.fab;
import static com.eternal.express.R.id.recyclerView;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:50
 * @desc ${TODO}
 */

public class PackagesFragment extends Fragment implements PackagesContract.View {


    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(emptyView)
    LinearLayout mEmptyView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(fab)
    FloatingActionButton mFab;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;
    private PackagesContract.Presenter presenter;
    private PackagesAdapter adapter;
    private String selectedPackageNumber;

    /**
     * 创建fragment对象
     *
     * @return
     */
    public static PackagesFragment newInstance() {
        return new PackagesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* 1. 获取要显示的view */
        View view = inflater.inflate(R.layout.fragment_packages, container, false);
        ButterKnife.bind(this, view);

        /* 2. 初始化view */
        initViews(view);

        /* 3. 初始化监听 */
        initListener();

        /* 4. 加载数据 */

        /* 5. 设置加载menu */
        setHasOptionsMenu(true);


        return view;
    }

    /**
     * 初始化点击事件
     */
    private void initListener() {
        // 1. 导航栏点击切换fragment
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_all:
                        presenter.setFiltering(PackageFilterType.ALL_PACKAGES);
                        break;
                    case R.id.nav_on_the_way:
                        presenter.setFiltering(PackageFilterType.ON_THE_WAY_PACKAGES);
                        break;
                    case R.id.nav_delivered:
                        presenter.setFiltering(PackageFilterType.DELIVERED_PACKAGES);
                        break;
                }

                // 每次切换后, 更新数据
                presenter.loadPackages();
                return true;
            }
        });

        // 2. fab点击添加订单
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 3. 下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshPackages();
            }
        });
    }

    /**
     * 初始化view
     *
     * @param view
     */
    @Override
    public void initViews(View view) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 触摸事件
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        // 给recyclerview的item绑定触摸事件
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 界面显示的时候, 重新读取数据库的数据
     */
    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    /**
     * 界面退到后台时, 清除读取数据库数据的任务
     */
    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
        // 取消显示加载view
        setLoadingIndicator(false);
    }

    /**
     * 加载menu菜单
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.packages_list, menu);
    }

    /**
     * 设置menu item的点击事件
     * 这个方法只在onCreateOptionsMenu 创建的菜单被选中时才会被触发
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_mark_all_read:
                break;
        }
        return true;
    }

    /**
     * 这个方法只在onCreateContextMenu 创建的菜单被选中时才会被触发
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item == null || selectedPackageNumber == null) {
            return false;
        }
        switch (item.getItemId()) {
            case R.id.action_set_readable:
                // 设置包裹的读取状态, 参数一: 获取选中的运单号      参数二: boolean 判断是否已读
                presenter.setPackageReadable(getSelectedPackageNumber(), !item.getTitle().equals(getString(R.string.set_read)));
                adapter.notifyDataSetChanged();
                break;
            case R.id.action_copy_code:
                // 复制运单号
                copyPackageNumber();
                break;
            case R.id.action_share:
                // 分享
                presenter.setShareData(getSelectedPackageNumber());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void setPresenter(PackagesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * 设置加载指示器
     * @param active
     */
    @Override
    public void setLoadingIndicator(final boolean active) {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(active);
            }
        });
    }

    /**
     * 当没有数据时, 显示empty界面
     * 隐藏recyclerview
     * @param toShow
     */
    @Override
    public void showEmptyView(boolean toShow) {
        if (toShow) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * recyclerview 显示item
     * 创建adapter
     * 加载数据
     * recyclerview绑定adapter
     * @param list
     */
    @Override
    public void showPackages(@NonNull List<Package> list) {
        if (adapter == null) {
            adapter = new PackagesAdapter(getContext(), list);
            // 设置item的点击事件
            adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                }
            });
            mRecyclerView.setAdapter(adapter);
        } else {
            // 刷新数据
            adapter.updateData(list);
        }
        // 判断是否显示空界面
        showEmptyView(list.isEmpty());
    }

    /**
     * 建立共享信息并创建一个选择器来共享数据。
     * @param pack
     */
    @Override
    public void shareTo(@NonNull Package pack) {
        String shareData = pack.getName()
                + "\n( "
                + pack.getNumber()
                + " "
                + pack.getCompanyChineseName()
                + " )\n"
                + getString(R.string.latest_status);
        if (pack.getData() != null && !pack.getData().isEmpty()) {
            shareData = shareData
                    + pack.getData().get(0).getContext()
                    + pack.getData().get(0).getFtime();
        } else {
            shareData = shareData + getString(R.string.get_status_error);
        }
        // DO NOT forget surround with try catch statement.
        // There may be no activity on users' device to handle this intent.
        try {
            Intent intent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareData);
            startActivity(Intent.createChooser(intent, getString(R.string.share)));

        } catch (ActivityNotFoundException e) {
            Snackbar.make(mFab, R.string.something_wrong, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除item后, 显示snackbar, 给用户撤销删除的机会
     * @param packageName
     */
    @Override
    public void showPackageRemovedMsg(String packageName) {
        String msg = packageName
                + " "
                + getString(R.string.package_removed_msg);
        Snackbar.make(mFab, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 回复删除的package
                        presenter.recoverPackage();
                    }
                })
                .show();
    }

    /**
     * 复制快递单号到剪切板
     */
    @Override
    public void copyPackageNumber() {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("text", getSelectedPackageNumber());
        manager.setPrimaryClip(data);
        Snackbar.make(mFab, R.string.package_number_copied, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 当网络有问题时, 显示错误提示
     */
    @Override
    public void showNetworkError() {
        Snackbar.make(mFab, R.string.network_error, Snackbar.LENGTH_SHORT)
                .setAction(R.string.go_to_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到系统的设置界面
                        startActivity(new Intent().setAction(Settings.ACTION_SETTINGS));
                    }
                })
                .show();
    }

    /**
     * Work with the activity which fragment attached to.
     * Store the number which is selected.
     * @param packId The selected package number.
     */
    public void setSelectedPackage(@NonNull String packId) {
        this.selectedPackageNumber = packId;
    }

    /**
     * Work with the activity which fragment attached to.
     * Get the number which is selected.
     * @return The selected package number.
     */
    public String getSelectedPackageNumber() {
        return selectedPackageNumber;
    }

}
