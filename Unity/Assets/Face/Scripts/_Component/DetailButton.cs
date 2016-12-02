using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using System.Collections;
using Hellgate;

public class DetailButton : MonoBehaviour, IPointerDownHandler, IPointerUpHandler
{
    [SerializeField]
    private float time = 1.5f;
    private float current;
    private bool pressing;

    public void OnPointerDown (PointerEventData eventData)
    {
        current = 0;
        pressing = true;
    }

    public void OnPointerUp (PointerEventData eventData)
    {
        pressing = false;
    }

    void Update ()
    {
        if (pressing) {
            current += Time.deltaTime;
            if (current >= time) {
                Image image = gameObject.FindChildObject<Image> ("Image");
                DetailController.Main (image.sprite);
            }
        }
    }
}
