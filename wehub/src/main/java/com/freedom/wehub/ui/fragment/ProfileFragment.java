package com.freedom.wehub.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedom.wecore.bean.User;
import com.freedom.wecore.common.Key;
import com.freedom.wecore.common.WeFragment;
import com.freedom.wecore.tools.ImageBridge;
import com.freedom.wecore.tools.RxBus;
import com.freedom.wehub.R;
import com.freedom.wehub.contract.AccountContract;
import com.freedom.wehub.event.FragmentVisibleEvent;
import com.freedom.wehub.presenter.AccountPresenter;

/**
 * @author vurtne on 18-May-18.
 */
public class ProfileFragment extends WeFragment<AccountContract.IProfilerView, AccountPresenter> implements
        AccountContract.IProfilerView{

    private Toolbar mToolbar;
    private ImageView mAvatarView;
    private TextView mUserTv;
    private TextView mNameTv;
    private TextView mRepositoriesTv;
    private TextView mFollowersTv;
    private TextView mFollowingTv;
    private TextView mGistsTv;

    private User mUser;


    @Override
    protected int contentView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.toolbar);
        mAvatarView = findViewById(R.id.iv_avatar);
        mUserTv = findViewById(R.id.tv_user);
        mNameTv = findViewById(R.id.tv_name);
        mRepositoriesTv = findViewById(R.id.tv_repositories);
        mGistsTv = findViewById(R.id.tv_gists);
        mFollowersTv = findViewById(R.id.tv_followers);
        mFollowingTv = findViewById(R.id.tv_following);
//        mAvatarBackgroundView = (ImageView) findViewById(R.id.iv_avatar_bg);
//        mAvatarView = (ImageView) findViewById(R.id.iv_avatar);
    }

    @Override
    protected void initStatusBar(int statusHeight) {
        FrameLayout.LayoutParams toolParams = (FrameLayout.LayoutParams) mToolbar.getLayoutParams();
        toolParams.height += statusHeight / 1.5;
        mToolbar.setPadding(0, (int) (statusHeight / 1.5),0,0);
        mToolbar.setLayoutParams(toolParams);

    }

    @Override
    protected void initEvent() {

    }

    /**
     * //        ImageBridge.displayBlurImageValue(mUser.getAvatarUrl(),mAvatarBackgroundView,50);
     * */
    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArgs();
        if (bundle == null){
            return;
        }
        String user = bundle.getString(Key.USER);
        if (TextUtils.isEmpty(user)){
            return;
        }
        showLoad();
        mPresenter.requestPersonInfo(user);
    }

    @Override
    public void requestPerson(User user) {
        hideLoad();
        mUser = user;
        if (mUser == null){
            return;
        }
        RxBus.get().post(FragmentVisibleEvent.create(mToolbar));
        ImageBridge.displayRoundImageWithDefault(mUser.getAvatarUrl(), mAvatarView,R.drawable.ic_hub_round);
        mUserTv.setText(mUser.getLogin());
        mNameTv.setText(mUser.getName());
        mFollowersTv.setText(mUser.getFollowers() + "");
        mFollowingTv.setText(mUser.getFollowing() + "");
        mRepositoriesTv.setText(user.getPublicRepos() + "");
        mGistsTv.setText(user.getPublicGists() + "");
//        mUserTv.setText();
//        mUserTv.setText(mUser.getLogin());
//        mUserTv.setText(mUser.getLogin());
    }
}
