package com.j1j2.pifalao.feature.productdetail;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.j1j2.data.model.ProductImg;
import com.j1j2.pifalao.R;

import java.util.List;

/**
 * Created by alienzxh on 16-3-16.
 */
public class ProductImgAdapter extends PagerAdapter {

    private List<ProductImg> productImgs;


    public ProductImgAdapter(List<ProductImg> productImgs) {
        this.productImgs = productImgs;
    }

    // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
    @Override
    public int getCount() {
        return null == productImgs ? 0 : productImgs.size();
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        ((ViewPager) view).removeView((View) object);
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.view_bannerimg, container, false);
        SimpleDraweeView draweeView = (SimpleDraweeView) root.findViewById(R.id.draw);
        draweeView.setImageURI(Uri.parse(productImgs.get(position).getImgUrl()));
        ((ViewPager) container).addView(root, 0);
        root.setTag(position);
        return root;
    }
}
