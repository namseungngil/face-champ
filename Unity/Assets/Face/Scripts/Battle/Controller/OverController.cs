using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using Hellgate;

public class OverController : FaceSceneController
{
    [SerializeField]
    private Image image;

    public static void Main (Sprite sprite)
    {
        FaceSceneManager.Instance.Screen ("Over", sprite);
    }

    public override void OnSet (object data)
    {
        base.OnSet (data);

        image.sprite = (Sprite)data;
    }

    public void OnClick ()
    {
        MainController.Main ();
    }
}
