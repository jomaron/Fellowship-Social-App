package chatview;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

/**
 * @desc:gif drawable
 */
public class GifDrawalbe extends AnimationDrawable {

    public GifDrawalbe(Context context, int id) {
        GifHelper helper = new GifHelper();
        helper.read(context.getResources().openRawResource(id));
        int gifCount = helper.getFrameCount();
        if (gifCount <= 0) {
            return;
        }
        BitmapDrawable bd = new BitmapDrawable(null, helper.getImage());
        addFrame(bd, helper.getDelay(0));
        for (int i = 1; i < helper.getFrameCount(); i++) {
            addFrame(new BitmapDrawable(null, helper.nextBitmap()),
                    helper.getDelay(i));
        }
        setBounds(0, 0, Util.dip2px(context, helper.getImage().getWidth() / 2),
                Util.dip2px(context, helper.getImage().getHeight() / 2));
        bd.setBounds(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());
        invalidateSelf();
    }

}
