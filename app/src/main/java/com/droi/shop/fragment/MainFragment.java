package com.droi.shop.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiQuery;
import com.droi.shop.R;
import com.droi.shop.activity.VideoActivity;
import com.droi.shop.model.Item;
/*import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;*/

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenpei on 2016/5/11.
 */
public class MainFragment extends Fragment implements
        AdapterView.OnItemClickListener {
    private Context mContext;
    private ArrayList<Item> mItems;

    /*    private ArrayList<Banner> mBanners;
        private BannerAdapter mBannerAdapter;
        private ViewPager viewPager;
        private ArrayList<AppInfo> mAppInfos;
        private AppInfoAdapter mAppInfoAdapter;
        private ArrayList<AppType> mAppTypes;
        private AppTypeAdapter mAppTypeAdapter;*/
    private int indexNum = 0;
    private boolean refreshing = false;

   /* private class CallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            // call type
            String type = intent.getStringExtra("type");
            //跳转到通话页面
            try {
                EMClient.getInstance().callManager().answerCall();
            } catch (EMNoActiveCallException e) {
                e.printStackTrace();
            }
            Intent intent1 = new Intent(getActivity(), VideoActivity.class);
            startActivity(intent1);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //initToolBar(view);
        initListView(view);
        /*IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        getActivity().registerReceiver(new CallReceiver(), callFilter);

        Button login = (Button) view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().login("marekchen", "6608432", new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });
            }
        });

        Button login2 = (Button) view.findViewById(R.id.login2);
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().login("marekchenpei", "6608432", new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });
            }
        });

        Button logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().logout(true, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        Log.d("main", "logout聊天服务器成功！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "logout聊天服务器成功！");
                    }
                });
            }
        });

        Button call = (Button) view.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {//单参数
                    EMClient.getInstance().callManager().makeVideoCall("marekchenpei");
                } catch (EMServiceNotReadyException e) {
                    e.printStackTrace();
                }

                *//*try {//多参数
                    EMClient.getInstance().callManager().makeVideoCall("marek", "ext 扩展内容");
                } catch (EMServiceNotReadyException e) {
                    e.printStackTrace();
                }*//*
                //获取扩展内容
                String callExt = EMClient.getInstance().callManager().getCurrentCallSession().getExt();
                EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
                    @Override
                    public void onCallStateChanged(CallState callState, CallError error) {
                        switch (callState) {
                            case CONNECTING: // 正在连接对方

                                break;
                            case CONNECTED: // 双方已经建立连接
                                Intent intent1 = new Intent(getActivity(), VideoActivity.class);
                                startActivity(intent1);
                                break;

                            case ACCEPTED: // 电话接通成功

                                break;
                            case DISCONNECTED: // 电话断了

                                break;
                            case NETWORK_UNSTABLE: //网络不稳定
                                if (error == CallError.ERROR_NO_DATA) {
                                    //无通话数据
                                } else {
                                }
                                break;
                            case NETWORK_NORMAL: //网络恢复正常

                                break;
                            default:
                                break;
                        }

                    }
                });
            }
        });*/

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "MainFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "MainFragment");
    }

    /**
     * 初始化头部工具栏，该工具栏主要用于跳转到{@link SearchActivity}
     *
     * @param view
     */
    /*private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        final TextView search = (TextView) toolbar.findViewById(R.id.toolbar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
            }
        });
    }*/

    /**
     * 初始化ListView
     *
     * @param view
     */
    private void initListView(View view) {
        ListView listview = (ListView) view.findViewById(R.id.fragment_main_list_view);
//        final Subscription subscribe = Observable
//                .create(new Observable.OnSubscribe<List<Item>>() {
//                    @Override
//                    public void call(Subscriber<? super List<Item>> subscriber) {
//                        DroiQuery query = DroiQuery.Builder.newBuilder().limit(8).query(Item.class).build();
//                        DroiError droiError = new DroiError();
//                        List<Item> items = query.runQuery(droiError);
//                        if (droiError.isOk()) {
//                            subscriber.onNext(items);
//                        } else {
//                            subscriber.onNext(null);
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Item>>() {
//                    @Override
//                    public void call(List<Item> items) {
//                        if (items == null) {
//                            //错误
//                        } else {
//                            //更新items
//                        }
//                    }
//                });
        /*ListView listview = (ListView) view.findViewById(R.id.fragment_main_list_view);
        listview.addHeaderView(getHeadView());
        listview.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.view_footer, null));
        if (mAppInfos == null) {
            mAppInfos = new ArrayList<>();
        }
        if (mAppInfos.isEmpty()) {
            fetchAppInfoData();
        }
        mAppInfoAdapter = new AppInfoAdapter(mContext, mAppInfos);
        listview.setAdapter(mAppInfoAdapter);
        listview.setOnItemClickListener(this);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        if (!refreshing) {
                            //当ListView滑动到最底部且没有正在进行的更新时，更新mAppInfos中的数据
                            refreshing  = true;
                            fetchAppInfoData();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/
    }

    /**
     * 获取ListView的头View用于显示Banner和AppInfoType信息
     *
     * @return
     */
    /*private View getHeadView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.view_head_main, null);
        initBanner(view);
        initAppInfoType(view);
        return view;
    }*/

    /**
     * 初始化AppInfoType信息展示
     *
     * @param view
     */
    private void initAppInfoType(View view) {
        /*GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        if (mAppTypes == null) {
            mAppTypes = new ArrayList<>();
        }
        if (mAppTypes.isEmpty()) {
            fetchAppTypeData();
        }
        mAppTypeAdapter = new AppTypeAdapter(mContext, mAppTypes);
        gridView.setAdapter(mAppTypeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置AppInfoType点击事件
                Map<String, String> kv = new HashMap<String, String>();
                kv.put("AppType", mAppTypes.get(position).getName());
                DroiAnalytics.onEvent(getActivity(), "search", kv);
                Intent intent = new Intent(mContext, AppInfosActivity.class);
                intent.putExtra(AppInfosActivity.INTENT_APP_TYPE, mAppTypes.get(position).getName());
                startActivity(intent);
            }
        });*/
    }

    /**
     * 初始化Banner信息展示，Banner主要用于展示推广活动信息
     *
     * @param view
     */
    private void initBanner(final View view) {
        /*RelativeLayout bannerLayout = (RelativeLayout) view.findViewById(R.id.banner_layout);
        final Indicator indicatorLayout = (Indicator) bannerLayout.findViewById(R.id.head_indicator_layout);
        if (mBanners == null) {
            mBanners = new ArrayList<Banner>();
        }
        if (mBanners.isEmpty()) {
            bannerLayout.setVisibility(View.GONE);
            fetchBannerData(bannerLayout, indicatorLayout);
        } else {
            bannerLayout.setVisibility(View.VISIBLE);
        }
        indicatorLayout.setCount(mBanners.size());
        indicatorLayout.select(0);
        viewPager = (ViewPager) view.findViewById(R.id.head_view_pager);
        mBannerAdapter = new BannerAdapter(mContext, mBanners);
        viewPager.setAdapter(mBannerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                indicatorLayout.select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        final GestureDetector tapGestureDetector = new GestureDetector(getActivity(), new TapGestureListener());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });*/
    }

    /**
     * 查询应用信息
     */
    private void fetchItemData() {
        /**
         * 当是下拉刷新时，清除原有数据
         * 当时上拉刷新时，在原有数据基础上添加
         */
        /*DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).orderBy("count", false).offset(indexNum * 10).query(AppInfo.class).build();
        query.runQueryInBackground(new DroiQueryCallback<AppInfo>() {
            @Override
            public void result(List<AppInfo> list, DroiError droiError) {
                refreshing = false;
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        if (indexNum == 0) {
                            mAppInfos.clear();
                        }
                        mAppInfos.addAll(list);
                        mAppInfoAdapter.notifyDataSetChanged();
                        indexNum++;
                    }
                } else {
                    //做请求失败处理
                }
            }
        });*/
    }

    /**
     * 查询App类型信息
     */
    private void fetchAppTypeData() {
        //最多只展示8个
        /*DroiQuery query = DroiQuery.Builder.newBuilder().limit(8).query(AppType.class).build();
        query.runQueryInBackground(new DroiQueryCallback<AppType>() {
            @Override
            public void result(List<AppType> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        mAppTypes.clear();
                        mAppTypes.addAll(list);
                        mAppTypeAdapter.notifyDataSetChanged();
                    }
                } else {
                    //做请求失败处理
                }
            }
        });*/
    }

    /**
     * 查询Banner信息
     *
     * @param bannerLayout
     * @param indicatorLayout
     */
    /*private void fetchBannerData(final RelativeLayout bannerLayout, final Indicator indicatorLayout) {
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(4).query(Banner.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Banner>() {
            @Override
            public void result(List<Banner> list, DroiError droiError) {
                //mAppTypeAdapter.notifyDataSetChanged();
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        mBanners.clear();
                        mBanners.addAll(list);
                        mBannerAdapter.notifyDataSetChanged();
                        bannerLayout.setVisibility(View.VISIBLE);
                        indicatorLayout.setCount(mBanners.size());
                        indicatorLayout.select(0);
                    }
                } else {
                    //做请求失败处理
                }
            }
        });
    }*/

    /**
     * 设置ListView Item的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent intent = new Intent(mContext, AppInfoDetailActivity.class);
        position = position - 1;
        intent.putExtra(AppInfoDetailActivity.APP_INFO_ID, mAppInfos.get(position).getAppId());
        startActivity(intent);*/
    }

    /**
     * 设置Banner点击事件
     */
    class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            /*Intent intent = new Intent(mContext, AppInfoDetailActivity.class);
            intent.putExtra(AppInfoDetailActivity.APP_INFO_ID, mBanners.get(viewPager.getCurrentItem()).getAppInfo().getAppId());
            startActivity(intent);*/
            return true;
        }
    }

}
