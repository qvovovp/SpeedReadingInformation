package com.kaskys.speedreadinginformation.app.text;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.kaskys.speedreadinginformation.app.R;

import java.io.ByteArrayOutputStream;

import static com.kaskys.speedreadinginformation.app.R.id.rl_content;

/**
 * Created by abc on 2015/11/8.
 */
public class Text3Activity extends AppCompatActivity {
    private RelativeLayout rlContent;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text1);
        rlContent = (RelativeLayout) this.findViewById(rl_content);
        btn = (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap  = createViewBitmap(rlContent);
                Intent intent = new Intent(Text3Activity.this,Text4Activity.class);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				byte[] bitmapByte = out.toByteArray();
                intent.putExtra("img",bitmapByte);
                startActivity(intent);
            }
        });
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
