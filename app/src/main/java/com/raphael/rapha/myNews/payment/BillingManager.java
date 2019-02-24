package com.raphael.rapha.myNews.payment;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.raphael.rapha.myNews.sharedPreferencesAccess.InAppPaymentService;

import java.util.ArrayList;
import java.util.List;

public class BillingManager implements PurchasesUpdatedListener {

    Activity activity;
    PurchasedItemsListener purchasedItemsListener;
    BillingClient billingClient;
    boolean isServiceConnected = false;
    int billingClientResponseCode;
    BillingUpdatesListener billingUpdatesListener;
    private static final String TAG = "BillingManager";

    public BillingManager(Activity activity, final BillingUpdatesListener billingUpdatesListener) {

        this.activity = activity;
        this.billingUpdatesListener = billingUpdatesListener;

        billingClient = BillingClient.newBuilder(activity).setListener(this).build();
        startServiceConnection(new Runnable() {
            @Override
            public void run() {
                billingUpdatesListener.onBillingClientSetupFinished();

            }
        });

    }

    public void setPurchasedItemsListener(PurchasedItemsListener purchasedItemsListener){
        this.purchasedItemsListener = purchasedItemsListener;
    }

    public void startServiceConnection(final Runnable runnable) {

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                Log.i(TAG, "responsecode: "  + responseCode);
                if (responseCode == BillingClient.BillingResponse.OK) {
                    Log.i(TAG, "connected!");

                    isServiceConnected = true;
                    queryPurchases();
                    if (runnable != null) {
                        runnable.run();
                    }
                }
                billingClientResponseCode = responseCode;

            }

            @Override
            public void onBillingServiceDisconnected() {

                isServiceConnected = false;
            }
        });

    }

    public interface BillingUpdatesListener {
        void onBillingClientSetupFinished();

        void onConsumeFinished(String token, @BillingClient.BillingResponse int result);

        void onPurchasesUpdated(List<Purchase> purchases);
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK) {
            billingUpdatesListener.onPurchasesUpdated(purchases);
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            Log.i(TAG, "onPurchasesUpdated() – user cancelled the purchase flow – skipping");
        } else {
            Log.w(TAG, "onPurchasesUpdated() got unknown resultCode: " + responseCode);
        }

    }

    private void executeServiceRequest(Runnable runnable) {
        Log.i(TAG, "executeServiceRequest");
        if (isServiceConnected) {
            Log.i(TAG, "executeServiceRequest connected");
            runnable.run();
        } else {
            Log.i(TAG, "executeServiceRequest not connected");
            // If the billing service disconnects, try to reconnect once.
            startServiceConnection(runnable);
        }
    }

    public void queryPurchases() {
        Log.i(TAG, "queryPurchases()");
        Runnable queryToExecute = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Runnable");
                long time = System.currentTimeMillis();
                Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
                if (areSubscriptionsSupported()) {
                    Log.i(TAG, "areSubscriptionsSupported");
                    Purchase.PurchasesResult subscriptionResult
                            = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
                    if (subscriptionResult.getResponseCode() == BillingClient.BillingResponse.OK) {
                        purchasesResult.getPurchasesList().addAll(
                                subscriptionResult.getPurchasesList()
                        );
                    } else {
                        // Handle any error response codes.
                    }
                } else if (purchasesResult.getResponseCode() == BillingClient.BillingResponse.OK) {
                    // Skip subscription purchases query as they are not supported.
                } else {
                    // Handle any other error response codes.
                }
                onQueryPurchasesFinished(purchasesResult);
            }
        };
        executeServiceRequest(queryToExecute);
    }

    private void handlePurchasedSubscriptions(List<Purchase> purchases){
        boolean monthlySubscription = false;
        for(Purchase purchase : purchases){
            if(purchase.getSku().equals(InAppPaymentService.MONTHLY_PAYMENT_SKU)){
                monthlySubscription = true;
            }
        }
        InAppPaymentService.setUserSubscribed(activity,monthlySubscription);
        if(purchasedItemsListener != null){
            purchasedItemsListener.onPurchasedItemsHandled();
        }
    }

    private void onQueryPurchasesFinished(Purchase.PurchasesResult result) {
        Log.i(TAG, "onQueryPurchasesFinished");
        // Have we been disposed of in the meantime? If so, or bad result code, then quit
        if (billingClient == null || result.getResponseCode() != BillingClient.BillingResponse.OK) {
            Log.w(TAG, "Billing client was null or result code (" + result.getResponseCode()
                    + ") was bad – quitting");
            return;
        }

        Log.d(TAG, "Query inventory was successful. SIze:"  + result.getPurchasesList().size());
        handlePurchasedSubscriptions(result.getPurchasesList());

        // Update the UI and purchases inventory with new list of purchases
        // mPurchases.clear();
        onPurchasesUpdated(BillingClient.BillingResponse.OK, result.getPurchasesList());
    }

    public boolean areSubscriptionsSupported() {
        int responseCode = billingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS);
        if (responseCode != BillingClient.BillingResponse.OK) {
            Log.w(TAG, "areSubscriptionsSupported() got an error response: " + responseCode);
        }
        return responseCode == BillingClient.BillingResponse.OK;
    }

    public void initiatePurchaseFlow(final String skuId, final ArrayList<String> oldSkus,
                                     final @BillingClient.SkuType String billingType) {
        Runnable purchaseFlowRequest = new Runnable() {
            @Override
            public void run() {
                BillingFlowParams mParams = BillingFlowParams.newBuilder().
                        setSku(skuId).setType(billingType).setOldSkus(oldSkus).build();
                billingClient.launchBillingFlow(activity, mParams);
            }
        };
        executeServiceRequest(purchaseFlowRequest);

    }

    public void consumeAsync(final String purchaseToken) {
        // If we’ve already scheduled to consume this token – no action is needed (this could happen
        // if you received the token when querying purchases inside onReceive() and later from
        // onActivityResult()
        // Generating Consume Response listener
        final ConsumeResponseListener onConsumeListener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String purchaseToken) {
                // If billing service was disconnected, we try to reconnect 1 time
                // (feel free to introduce your retry policy here).
                billingUpdatesListener.onConsumeFinished(purchaseToken, responseCode);
            }
        };

        // Creating a runnable from the request to use it inside our connection retry policy below
        Runnable consumeRequest = new Runnable() {
            @Override
            public void run() {
                // Consume the purchase async
                billingClient.consumeAsync(purchaseToken, onConsumeListener);
            }
        };

        executeServiceRequest(consumeRequest);
    }

}
