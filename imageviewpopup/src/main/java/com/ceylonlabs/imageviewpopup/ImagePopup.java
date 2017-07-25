package com.ceylonlabs.imageviewpopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by Chathura Lakmal on 1/7/17.
 */

public class ImagePopup extends ImageView {
    private Context context;
    private PopupWindow popupWindow;
    View layout;

    private int windowHeight = 0;
    private int windowWidth = 0;
    private boolean imageOnClickClose;
    private boolean hideCloseIcon;

    private int backgroundColor = Color.parseColor("#FFFFFF");


    public ImagePopup(Context context) {
        super(context);
        this.context = context;
    }

    public ImagePopup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    /** Close Options **/

    public void setImageOnClickClose(boolean imageOnClickClose) {
        this.imageOnClickClose = imageOnClickClose;
    }


    public boolean isImageOnClickClose() {
        return imageOnClickClose;
    }

    public boolean isHideCloseIcon() {
        return hideCloseIcon;
    }

    public void setHideCloseIcon(boolean hideCloseIcon) {
        this.hideCloseIcon = hideCloseIcon;
    }

    public void initiatePopup(Drawable drawable){


        try{
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            layout = inflater.inflate(R.layout.popup,(ViewGroup)findViewById(R.id.popup));

            layout.setBackgroundColor(getBackgroundColor());

            ImageView imageView = (ImageView)layout.findViewById(R.id.imageView);
            imageView.setImageDrawable(drawable);



            Log.e("Image","Height--> "+imageView.getDrawable().getMinimumHeight());
            Log.e("Image","Width--> "+imageView.getDrawable().getMinimumWidth());





//            /** Height & Width Adjustments according to the Image size and Device Screen size **/
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Log.e("Phone Height","-->"+metrics.heightPixels);
            Log.e("Phone Width","-->"+metrics.widthPixels);


            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            if(windowHeight != 0 || windowWidth !=0){
                width = windowWidth;
                height = windowHeight;
            }

//            ((Activity) getContext()).getWindow().setLayout((int)(width*.8),(int)(height*.6));
//

            popupWindow = new PopupWindow(layout, (int)(width*.8),(int)(height*.6), true);

            popupWindow.showAtLocation(layout, Gravity.CENTER, 0,0);

            ImageView closeIcon = (ImageView)layout.findViewById(R.id.closeBtn);


            if(isHideCloseIcon()){
                closeIcon.setVisibility(View.GONE);
            }
            closeIcon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });


            if(isImageOnClickClose()) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }

            /** Background dim part **/
//            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) layout.getLayoutParams();
//            layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//            layoutParams.dimAmount = 0.3f;
//            windowManager.updateViewLayout(layout, layoutParams);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setLayoutOnTouchListener(OnTouchListener onTouchListener){
        //Allows for setting the OnTouchListener - using the default method doesn't work
        layout.setOnTouchListener(onTouchListener);
    }
    
    @Override
    public void setImageBitmap(Bitmap bitmap){
        //Allows for changing the Popup image. Using the default method doesn't work.
        imageView.setImageBitmap(bitmap);
    }

}
