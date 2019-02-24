package com.raphael.rapha.myNews.payment;

import android.app.Activity;
import android.view.View;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.raphael.rapha.myNews.sharedPreferencesAccess.InAppPaymentService;

import java.util.List;

public class MyBillingUpdateListener implements BillingManager.BillingUpdatesListener {

    Activity activity;
    PurchasedItemsListener purchasedItemsListener;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPurchasedItemsListener(PurchasedItemsListener purchasedItemsListener) {
        this.purchasedItemsListener = purchasedItemsListener;
    }


    @Override
    public void onBillingClientSetupFinished() {


    }

    @Override
    public void onConsumeFinished(String token, int result) {

        if (result == BillingClient.BillingResponse.OK) {
        }
    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchases) {
        for (Purchase p : purchases) {
            if(p.getSku().equals(InAppPaymentService.MONTHLY_PAYMENT_SKU)){
                if(activity != null){
                    InAppPaymentService.setUserSubscribed(activity,true);
                }
                if(purchasedItemsListener != null){
                    purchasedItemsListener.onPurchasedItemsHandled();
                }
            }
        }



    }

}
