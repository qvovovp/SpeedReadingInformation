package com.kaskys.speedreadinginformation.app.text;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.utils.AnimUtils;

import java.util.List;

import static com.kaskys.speedreadinginformation.app.R.id.rl_content;

/**
 * Created by abc on 2015/11/8.
 */
public class Text4Activity extends AppCompatActivity{
    private RelativeLayout rlContent;
    private LinearLayout  llSplash;
    private ImageView img1,img2;
    private Bitmap bitmap;
    private  List<ImagePiece> pieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text4);
        byte[] bitmapByte = getIntent().getByteArrayExtra("img");
        bitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);

        rlContent = (RelativeLayout) this.findViewById(rl_content);
        llSplash = (LinearLayout) this.findViewById(R.id.ll_splash);
        img1 = (ImageView) this.findViewById(R.id.img1);
        img2 = (ImageView) this.findViewById(R.id.img2);

        pieces = ImageUtils.split(bitmap, 2, 1);
        img1.setImageBitmap(pieces.get(0).bitmap);
        img2.setImageBitmap(pieces.get(1).bitmap);
    }

    public void removeSplash(){
        ((ViewGroup)llSplash.getParent()).removeView(llSplash);
        if(bitmap != null){
            bitmap = null;
        }
        if(pieces != null){
            for(int i=0;i<pieces.size();i++){
                pieces.get(i).bitmap = null;
            }
        }
    }
}
