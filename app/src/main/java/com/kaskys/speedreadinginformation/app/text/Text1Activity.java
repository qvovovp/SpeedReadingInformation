package com.kaskys.speedreadinginformation.app.text;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.kaskys.speedreadinginformation.app.R;

import java.util.List;

import static com.kaskys.speedreadinginformation.app.R.id.rl_content;

/**
 * Created by abc on 2015/11/8.
 */
public class Text1Activity extends AppCompatActivity{
    private RelativeLayout rlContent;
    private Button btn;
    private ImageView img1,img2;

    private List<ImagePiece> pieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text1);
        rlContent = (RelativeLayout) this.findViewById(rl_content);
        btn = (Button) this.findViewById(R.id.btn);
        img1 = (ImageView) this.findViewById(R.id.img1);
        img2 = (ImageView) this.findViewById(R.id.img2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap  = createViewBitmap(rlContent);
                pieces = ImageUtils.split(bitmap, 2, 1);
                img1.setImageBitmap(pieces.get(0).bitmap);
				img2.setImageBitmap(pieces.get(1).bitmap);
                rlContent.setVisibility(View.GONE);
            }
        });
    }

    public void removeActivity(){
        if(pieces != null){
            for(int i=0;i<pieces.size();i++){
                pieces.get(i).bitmap = null;
            }
        }
        this.finish();
    }

    public void startText2(){
        startActivity(new Intent(Text1Activity.this,Text2Activity.class));
        overridePendingTransition(R.anim.activity_home_in,0);
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
