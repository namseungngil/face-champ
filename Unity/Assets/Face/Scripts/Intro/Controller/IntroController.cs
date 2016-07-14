using UnityEngine;
using System.Collections;
using Hellgate;

public class IntroController : FaceSceneController
{
    public static void Main ()
    {
    }

    public override void OnSet (object data)
    {
        base.OnSet (data);

        FaceSceneManager.Instance.Wait (2.0f, delegate() {
            MainController.Main ();
        });
    }
}
