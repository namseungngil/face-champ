using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using System.Collections;
using Hellgate;

public class DetailController : FaceSceneController
//, IPointerEnterHandler, IPointerExitHandler
{
    [SerializeField]
    private Image width;
    [SerializeField]
    private Image height;
    [SerializeField]
    private TweenColor color;
    [SerializeField]
    private TweenAlpha[] alpha;
    [SerializeField]
    private float minSize = 1f;
    [SerializeField]
    private float maxSize = 2f;
    [SerializeField]
    private float zoomRate = 5f;
    private RectTransform rect;
    private bool tweenFlag;
    private bool zoomFlag;

    public static void Main (Sprite sprite)
    {
        FaceSceneManager.Instance.PopUp ("Detail", sprite);
    }

    public override void OnSet (object data)
    {
        base.OnSet (data);

        tweenFlag = false;
        zoomFlag = false;

        Sprite sprite = data as Sprite;
        if (sprite.rect.size.x > sprite.rect.size.y) {
            width.sprite = sprite;
            rect = width.GetComponent<RectTransform> ();
            width.gameObject.SetActive (true);
        } else {
            height.sprite = sprite;
            rect = height.GetComponent<RectTransform> ();
            height.gameObject.SetActive (true);
        }
    }

    void Update ()
    {
//        if (zoomFlag) {
//            Zoom (Input.GetAxis ("Mouse ScrollWheel"));
//        }
    }

    public void OnPointerEnter (PointerEventData eventData)
    {
        Debug.Log ("OnPointerEnter");
        zoomFlag = true;
        tweenFlag = true;
        OnClick ();
    }

    public void OnPointerExit (PointerEventData eventData)
    {
        zoomFlag = false;
    }

    private void Zoom (float zoom)
    {
        float rate = 1 + zoomRate * Time.unscaledDeltaTime;
        if (zoom > 0) {
            SetZoom (Mathf.Clamp (rect.localScale.y / rate, minSize, maxSize));
        } else {
            SetZoom (Mathf.Clamp (rect.localScale.y * rate, minSize, maxSize));
        }
    }

    private void SetZoom (float size)
    {
        rect.localScale = new Vector3 (size, size, 1);
    }

    public void OnClick ()
    {
        if (tweenFlag) {
            color.PlayReverse ();
            for (int i = 0; i < alpha.Length; i++) {
                alpha [i].PlayReverse ();
            }
        } else {
            color.Play ();
            for (int i = 0; i < alpha.Length; i++) {
                alpha [i].Play ();
            }
        }
        tweenFlag = !tweenFlag;
    }
}
