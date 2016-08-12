using UnityEngine;
using System.Collections;
using Hellgate;

public class FaceSceneManager : SceneManager
{
    protected override void Awake ()
    {
        base.Awake ();

        GoogleMobileAdsManager ads = gameObject.GetComponent<GoogleMobileAdsManager> ();
        if (ads == null) {
            gameObject.AddComponent<GoogleMobileAdsManager> ();
        }
    }
}
