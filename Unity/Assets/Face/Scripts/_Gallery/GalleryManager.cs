using UnityEngine;
using System;
using System.Collections;
using Hellgate;

#if !UNITY_EDITOR || UNITY_IOS
using System.Runtime.InteropServices;
using System.IO;
#endif

public class GalleryManager : MonoBehaviour
{
#region Const

    protected const string GALLERY_MANAGER = "GalleryManager";
    protected const string CLASS_NAME = "com.hellgate.UnityGalleryActivity";

#endregion

#region Singleton

    private static GalleryManager instance = null;

    /// <summary>
    /// Gets the instance.
    /// </summary>
    /// <value>The instance.</value>
    public static GalleryManager Instance {
        get {
            if (instance == null) {
                GameObject gObj = new GameObject ();
                instance = gObj.AddComponent<GalleryManager> ();
                gObj.name = GALLERY_MANAGER;
            }

            return instance;
        }
    }

#endregion

    public event Action<Texture2D> onImageLoaded;

#if UNITY_EDITOR || UNITY_ANDROID
    private AndroidJavaObject android;
#elif UNITY_IOS
    [DllImport ("__Internal")]
    private static extern void _ImagePickerOpen ();
#endif

    void Awake ()
    {
        if (instance == null) {
            instance = this;
        }

#if UNITY_EDITOR || UNITY_ANDROID
        if (Application.platform == RuntimePlatform.Android) {
            AndroidJavaClass gallery = new AndroidJavaClass (CLASS_NAME);
            android = gallery.CallStatic<AndroidJavaObject> ("getInstance");
        }
#endif
    }

    void OnDestory ()
    {
        instance = null;
    }

    void OnImageLoaded (string result)
    {
        string[] data = result.Split ('|');
#if UNITY_EDITOR || UNITY_ANDROID
        if (data.Length > 1) {
            string image = data [1];
            if (image.Length > 0) {
                byte[] decodedFromBase64 = System.Convert.FromBase64String (image);
                Texture2D texture = new Texture2D (int.Parse (data [2]), int.Parse (data [3]), TextureFormat.DXT5, false);
                texture.LoadImage (decodedFromBase64);

                onImageLoaded (texture);
                return;
            }
        }
#elif UNITY_IOS
        if (File.Exists (data [0])) {
            byte[] decodedFromBase64 = File.ReadAllBytes (data [0]);
            Texture2D texture = new Texture2D (int.Parse (data [1]), int.Parse (data [2]), TextureFormat.DXT5, false);
            texture.LoadImage (decodedFromBase64);

            onImageLoaded (texture);
            return;
        }
#endif

        onImageLoaded (null);
    }

    public void Open ()
    {
#if UNITY_EDITOR || UNITY_ANDROID
        if (Application.platform == RuntimePlatform.Android) {
            android.Call ("openGallery");
        }
#elif UNITY_IOS
        _ImagePickerOpen ();
#endif
    }
}
