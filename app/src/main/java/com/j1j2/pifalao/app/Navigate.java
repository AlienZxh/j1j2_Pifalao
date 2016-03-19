package com.j1j2.pifalao.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.j1j2.data.model.City;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSimple;
import com.j1j2.data.model.ProductSort;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.feature.products.ProductsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.workarounds.bundler.Bundler;

/**
 * Created by alienzxh on 16-3-4.
 */
public class Navigate {


    public Navigate() {
    }

    public void navigateToGuide(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.guideActivity().start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.guideActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToStoreStyleomeActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.storeStyleHomeActivity(module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.storeStyleHomeActivity(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToLocationActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, City city) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.locationActivity(city).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.locationActivity(city).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCityActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.cityActivity().start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.cityActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToServicePointActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.servicePointActivity(servicePoint).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.servicePointActivity(servicePoint).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToServicesActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ServicePoint servicePoint) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.servicesActivity(servicePoint).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.servicesActivity(servicePoint).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductsActivityFromSort(Activity context, ActivityOptionsCompat options, boolean isFinish, ProductSort productSort, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SORT).productSort(productSort).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SORT).productSort(productSort).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductsActivityFromSearch(Activity context, ActivityOptionsCompat options, boolean isFinish, String key, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SEARCH).key(key).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.productsActivity(module, ProductsActivity.PRODUCTS_TYPE_SEARCH).key(key).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToProductDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, ProductSimple productSimple) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.productDetailActivity(productSimple).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.productDetailActivity(productSimple).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToMainActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.mainActivity(module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.mainActivity(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToSearchActivity(Activity context, ActivityOptionsCompat options, boolean isFinish,Module module) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.searchActivity(module).start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.searchActivity(module).intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToLogin(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        if (null == options || Build.VERSION.SDK_INT < 16) {
            Bundler.loginActivity().start(context);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            ActivityCompat.startActivity(context, Bundler.loginActivity().intent(context),
                    options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }
}
