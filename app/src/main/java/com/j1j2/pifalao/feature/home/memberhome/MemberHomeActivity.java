package com.j1j2.pifalao.feature.home.memberhome;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;

import com.j1j2.common.util.Toastor;
import com.j1j2.common.view.bgabadgewidget.AutoBGABadgeFrameLayout;
import com.j1j2.common.view.scrollablelayout.ScrollableHelper;
import com.j1j2.common.view.scrollablelayout.ScrollableLayout;
import com.j1j2.common.view.smarttablayout.SmartTabLayout;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.UnReadInfo;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.MainTab;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.event.LoginCookieTimeoutEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityMemberhomeBinding;
import com.j1j2.pifalao.feature.home.memberhome.di.MemberHomeModule;
import com.j1j2.pifalao.feature.home.morehome.MoreHomeFragment;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.main.MainAdapter;
import com.j1j2.pifalao.feature.prize.PrizeActivity;
import com.j1j2.pifalao.feature.prizedetail.PrizeDetailActivity;
import com.nineoldandroids.view.ViewHelper;
import com.orhanobut.logger.Logger;
import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.provider.FragmentData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-8-23.
 */
@RequireBundler
public class MemberHomeActivity extends BaseActivity implements MemberHomeLuckyFragment.MemberHomeLuckyFragmentListener
        , MemberHomeFreeFragment.MemberHomeFreeFragmentListener
        , MemberHomeBannerFragment.MemberHomeBannerFragmentListener
        , MemberHomePrizeFragment.MemberHomePrizeFragmentListener
        , ScrollableLayout.OnScrollListener, View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener {

    ActivityMemberhomeBinding binding;

    @Inject
    ActivityApi activityApi;
    @Inject
    Navigate navigate;
    @Inject
    UserRelativePreference userRelativePreference;

    UnReadInfoManager unReadInfoManager = null;

    Map<String, Map<String, List<ActivityProduct>>> stringMapMap;

    List<ActivityWinPrize> activityWinPrizes;

    boolean isFirst = true;

    MemberHomeBannerFragment memberHomeBannerFragment;
    MemberHomeFreeFragment memberHomeFreeFragment;
    List<Fragment> fragments;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new MemberHomeModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memberhome);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.searchview.setOnClickListener(this);
        binding.loginBtn.setOnClickListener(this);
        binding.ruleBtn.setOnClickListener(this);
        //_____________________________________________________________
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(this);

//        binding.refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
//                        .getDisplayMetrics()));
        binding.refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                binding.refreshLayout.setRefreshing(true);
            }
        });
        //________________________________________________________________________
        binding.scrollableLayout.setOnScrollListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void loadData() {
        Observable.zip(
                activityApi.queryAvailableActivity(false, 0)
                        .compose(this.<WebReturn<Map<String, Map<String, List<ActivityProduct>>>>>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                activityApi.queryActivityWinPrizeRecords(1)
                        .compose(this.<WebReturn<PagerManager<ActivityWinPrize>>>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new Func2<WebReturn<Map<String, Map<String, List<ActivityProduct>>>>, WebReturn<PagerManager<ActivityWinPrize>>, WebReturn<String>>() {
                    @Override
                    public WebReturn<String> call(WebReturn<Map<String, Map<String, List<ActivityProduct>>>> mapWebReturn, WebReturn<PagerManager<ActivityWinPrize>> pagerManagerWebReturn) {
                        WebReturn<String> stringWebReturn = new WebReturn<>();
                        if (mapWebReturn.isValue()) {
                            stringMapMap = mapWebReturn.getDetail();

                            stringWebReturn.setValue(true);
                        } else {
                            stringWebReturn.setValue(false);
                            stringWebReturn.setErrorMessage(mapWebReturn.getErrorMessage());
                        }

                        if (pagerManagerWebReturn.isValue()) {
                            activityWinPrizes = pagerManagerWebReturn.getDetail().getList();

                        } else {
                            stringWebReturn.setValue(false);
                            stringWebReturn.setErrorMessage((stringWebReturn.getErrorMessage() == null ?
                                    pagerManagerWebReturn.getErrorMessage() :
                                    stringWebReturn.getErrorMessage()) + "\n" + pagerManagerWebReturn.getErrorMessage());
                        }

                        return stringWebReturn;
                    }
                }).subscribe(new WebReturnSubscriber<String>() {
            @Override
            public void onWebReturnSucess(String s) {

            }

            @Override
            public void onWebReturnFailure(String errorMessage) {
                toastor.showSingletonToast(errorMessage);
            }

            @Override
            public void onWebReturnCompleted() {
                if (isFirst) {
                    changeFragment(R.id.HomeBannerFragment, memberHomeBannerFragment = new MemberHomeBannerFragment());
                    changeFragment(R.id.HomePrizeFragment, new MemberHomePrizeFragment());
                    changeFragment(R.id.HomeFreeFragment, memberHomeFreeFragment = new MemberHomeFreeFragment());
                    binding.viewpagerLayout.setVisibility(View.VISIBLE);
                    //____________________________________________________
                    final List<String> strings = new ArrayList<>();
                    fragments = new ArrayList<>();
                    strings.add("全部");
                    fragments.add(Bundler.memberHomeLuckyFragment("全部").create());
                    for (String key : stringMapMap.get("幸运抽奖").keySet()) {
                        strings.add(key);
                        fragments.add(Bundler.memberHomeLuckyFragment(key).create());
                    }
                    strings.add("进度");
                    fragments.add(Bundler.memberHomeLuckyFragment("进度").create());

                    binding.viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                        @Override
                        public Fragment getItem(int position) {
                            return fragments.get(position);
                        }

                        @Override
                        public int getCount() {
                            return 4;
                        }

                        @Override
                        public CharSequence getPageTitle(int position) {
                            return strings.get(position);
                        }
                    });
                    binding.scrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragments.get(0));
                    binding.idStickynavlayoutIndicator.setViewPager(binding.viewpager);
                    binding.idStickynavlayoutIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            binding.scrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragments.get(position));
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    isFirst = false;

                } else {
                    memberHomeFreeFragment.refreshList();
                    ((MemberHomeLuckyFragment) fragments.get(binding.viewpager.getCurrentItem())).refreshList();
                }
                binding.refreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.refreshLayout.setRefreshing(false);
                        binding.refreshLayout.setEnabled(false);
                    }
                });

            }
        });

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLoginCookieTimeoutEvent(LoginCookieTimeoutEvent event) {
        if (MainAplication.get(this).isLogin()) {
            MainAplication.get(this).loginOut();
            EventBus.getDefault().postSticky(new LogStateEvent(false));
        }
    }

    @Override
    public User getUser() {
        if (MainAplication.get(this).isLogin())
            return MainAplication.get(this).getUserComponent().user();
        else
            return null;
    }

    @Override
    public List<ActivityProduct> getFreeActivityProducts() {
        return stringMapMap.get("免费兑换").get("免费兑换");
    }

    @Override
    public List<ActivityWinPrize> getActivityWinPrizes() {
        return activityWinPrizes;
    }

    @Override
    public List<ActivityProduct> getActivityProducts(String key) {

        List<ActivityProduct> allList = new ArrayList<>();
        for (List<ActivityProduct> value : stringMapMap.get("幸运抽奖").values()) {
            allList.addAll(value);
        }

        if (key.equals("全部")) {
            Collections.sort(allList, new Comparator<ActivityProduct>() {
                @Override
                public int compare(ActivityProduct lhs, ActivityProduct rhs) {
                    if (lhs.getDisplayRank() < rhs.getDisplayRank()) {
                        return -1;
                    } else if (lhs.getDisplayRank() > rhs.getDisplayRank()) {
                        return 1;
                    } else
                        return 0;
                }
            });
            return allList;
        } else if (key.equals("进度")) {
            Collections.sort(allList, new Comparator<ActivityProduct>() {
                @Override
                public int compare(ActivityProduct lhs, ActivityProduct rhs) {
                    double lhsRate = (lhs.getConfigs().getMaxUsers() - lhs.getStatistics().getMaxUserRemain())/(double)lhs.getConfigs().getMaxUsers();
                    double rhsRate = (rhs.getConfigs().getMaxUsers() - rhs.getStatistics().getMaxUserRemain())/(double)rhs.getConfigs().getMaxUsers();

                    if (lhsRate > rhsRate) {
                        return -1;
                    } else if (lhsRate < rhsRate) {
                        return 1;
                    } else
                        return 0;
                }
            });
            return allList;
        } else
            return stringMapMap.get("幸运抽奖").get(key);


    }

    @Override
    public void navigateToPoint() {
        if (MainAplication.get(this).isLogin())
            navigate.navigateToVipHome(this, null, false, VipHomeActivity.POINT);
        else
            navigate.navigateToLogin(this, null, false);
    }

    @Override
    public void navigateToPrize(int acyivityType) {
        if (MainAplication.get(this).isLogin())
            navigate.navigateToPrizeActivity(this, null, false, acyivityType);
        else
            navigate.navigateToLogin(this, null, false);
    }

    @Override
    public void navigateToShowOrder() {
        navigate.navigateToShowOrderListActivity(this, null, false);
//        navigate.navigateToShowOrderActivity(this, null, false);
    }

    @Override
    public void navigateToParticipation(int activityType) {
        if (MainAplication.get(this).isLogin())
            navigate.navigateToParticipationRecordActivity(this, null, false, activityType);
        else
            navigate.navigateToLogin(this, null, false);
    }

    @Override
    public void navigateToPrize() {
        navigate.navigateToPrizeActivity(this, null, false, PrizeActivity.PRIZE_PUBLISHED);
    }

    @Override
    public void navigateToPrizeDetail(int activityType, ActivityProduct activityProduct) {
        if (MainAplication.get(this).isLogin())
            navigate.navigateToPrizeDetailActivity(this, null, false, activityProduct.getProductId(), null);
        else
            navigate.navigateToLogin(this, null, false);

    }

    @Override
    public void navigateToPrizeDetail(int activityType, ActivityWinPrize activityWinPrize) {
        if (MainAplication.get(this).isLogin())
            navigate.navigateToPrizeDetailActivity(this, null, false, activityWinPrize.getProductInfo().getProductId(), activityWinPrize);
        else
            navigate.navigateToLogin(this, null, false);
    }


    @Override
    public void onScroll(int currentY, int maxY) {

    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.loginBtn)
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigate.navigateToLogin(this, null, false);
        if (v == binding.searchview)
            navigate.navigateToSearchActivity(this, null, false, userRelativePreference.getSelectedModule(null));
        if (v == binding.ruleBtn)
            navigate.navigateToLotteryRuleActivity(this, null, false);
    }
}
