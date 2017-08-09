package app.customcomponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;

/**
 * Created by talza on 03-Aug-17.
 */

public class CircleImageButton extends android.support.v7.widget.AppCompatImageButton {
    public CircleImageButton(Context context) {
        super(context);
    }

    public CircleImageButton(Context context, int image) {
        super(context);
        setImage(image);
    }

    public CircleImageButton(Context context, byte[] image, int defaultImageId) {
        super(context);
        setImageBytes(image, defaultImageId);
    }

    public CircleImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImage(int image)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        this.setImageDrawable(roundedBitmapDrawable);
    }

    public void setImage(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        this.setImageDrawable(roundedBitmapDrawable);
    }

    private void setImageBytes(byte[] image, int defaultImageId)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        if (bitmap != null && defaultImageId != 0)
        {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            this.setImageDrawable(roundedBitmapDrawable);
        }
        else
        {
            setImage(defaultImageId);
        }
    }



}
