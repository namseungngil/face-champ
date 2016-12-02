using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using Hellgate;

public class LoginController : FaceSceneController
{
    [SerializeField]
    private Button image;
    [SerializeField]
    private InputField input;
    [SerializeField]
    private Button man;
    [SerializeField]
    private Button woman;
    private int mType;

    public static void Main ()
    {
        FaceSceneManager.Instance.PopUp ("Login");
    }

    public override void OnSet (object data)
    {
        base.OnSet (data);

        SetManWoman ();
    }

    public override void OnDestroy ()
    {
        base.OnDestroy ();

        GalleryManager.Instance.onImageLoaded += OnImageLoaded;
    }

    private void SetManWoman (int type = 0)
    {
        mType = type;

        float manA = 0.5f;
        float womanA = 0.5f;

        if (type == 1) {
            manA = 1f;
        } else if (type == 2) {
            womanA = 1f;
        }
        man.image.color = new Color (0, 0, 1f, manA);
        woman.image.color = new Color (1f, 0, 0, womanA);
    }

    private void OnImageLoaded (Texture2D texture)
    {
        Sprite sprite = Sprite.Create (texture, new Rect (0, 0, texture.width, texture.height), Vector2.zero);
        sprite.name = texture.name;

        image.image.color = new Color (1f, 1f, 1f, 1f);
        image.image.sprite = sprite;
    }

    public void OnClickImage ()
    {
        GalleryManager.Instance.Open ();
        GalleryManager.Instance.onImageLoaded += OnImageLoaded;
    }

    public void OnClickMan ()
    {
        SetManWoman (1);
    }

    public void OnClickWoman ()
    {
        SetManWoman (2);
    }

    public void OnClick ()
    {
        OnClickClose ();
    }
}
