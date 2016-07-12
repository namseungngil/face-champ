using UnityEngine;
using System;
using System.Collections;
//using GoogleMobileAds;
//using GoogleMobileAds.Api;

public class GoogleMobileAdsManager : MonoBehaviour
{
//    private BannerView bannerView;
//
//    void Start ()
//    {
//        RequestBanner ();
//    }
//
//    private void RequestBanner ()
//    {
//#if UNITY_EDITOR
//        string adUnitId = "unused";
//#elif UNITY_ANDROID
//        string adUnitId = "INSERT_ANDROID_BANNER_AD_UNIT_ID_HERE";
//#elif (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
//        string adUnitId = "INSERT_IOS_BANNER_AD_UNIT_ID_HERE";
//#else
//        string adUnitId = "unexpected_platform";
//#endif
//
//        // Create a 320x50 banner at the top of the screen.
//        bannerView = new BannerView (adUnitId, AdSize.SmartBanner, AdPosition.Bottom);
//        // Register for ad events.
//        bannerView.OnAdLoaded += HandleAdLoaded;
//        bannerView.OnAdFailedToLoad += HandleAdFailedToLoad;
//        bannerView.OnAdLoaded += HandleAdOpened;
//        bannerView.OnAdClosed += HandleAdClosed;
//        bannerView.OnAdLeavingApplication += HandleAdLeftApplication;
//        // Load a banner ad.
//        bannerView.LoadAd (createAdRequest ());
//        bannerView.Show ();
//    }
//
//    // Returns an ad request with custom ad targeting.
//    private AdRequest createAdRequest ()
//    {
//        return new AdRequest.Builder ()
//        .AddTestDevice (AdRequest.TestDeviceSimulator)
//        .AddTestDevice ("0123456789ABCDEF0123456789ABCDEF")
//        .AddKeyword ("game")
//        .SetGender (Gender.Male)
//        .SetBirthday (new DateTime (1985, 1, 1))
//        .TagForChildDirectedTreatment (false)
//        .AddExtra ("color_bg", "9B30FF")
//        .Build ();
//    }
//
//    public void HandleAdLoaded (object sender, EventArgs args)
//    {
////        print ("HandleAdLoaded event received.");
//    }
//
//    public void HandleAdFailedToLoad (object sender, AdFailedToLoadEventArgs args)
//    {
////        print("HandleFailedToReceiveAd event received with message: " + args.Message);
//    }
//
//    public void HandleAdOpened (object sender, EventArgs args)
//    {
////        print("HandleAdOpened event received");
//    }
//
//    public void HandleAdClosed (object sender, EventArgs args)
//    {
////        print("HandleAdClosed event received");
//    }
//
//    public void HandleAdLeftApplication (object sender, EventArgs args)
//    {
////        print ("HandleAdLeftApplication event received");
//    }
}
