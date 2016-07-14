using UnityEngine;
using System.Collections;
using Hellgate;

public class MainController : FaceSceneController
{
    public static void Main ()
    {
        FaceSceneManager.Instance.Screen ("Main");
    }

    public override void OnSet (object data)
    {
        base.OnSet (data);
    }

    public void OnClick ()
    {
        BattleController.Main ();
    }
}
