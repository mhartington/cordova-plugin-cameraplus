package cordova.plugin.cameraplus;

import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.net.Uri;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PreviewActivity  extends Activity {

    static final int RESULT_OK = 1;
    static final int RESULT_KO = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getResources().getIdentifier("preview_layout", "layout", getPackageName()));

        Uri fileUri = (Uri) getIntent().getExtras().get(MediaStore.EXTRA_OUTPUT);

        ImageView imgDisplay = (ImageView) findViewById(getResources().getIdentifier("imgDisplay", "id", getPackageName()));
        ImageButton btnClose = (ImageButton) findViewById(getResources().getIdentifier("btnCancel", "id", getPackageName()));
        ImageButton btnAccept = (ImageButton) findViewById(getResources().getIdentifier("btnAccept", "id", getPackageName()));
        LinearLayout buttonsLayout = (LinearLayout) findViewById(getResources().getIdentifier("buttonsLayout", "id", getPackageName()));

        try {
          buttonsLayout.setBackgroundColor(Color.argb(60, 0, 0, 0));

          Glide
            .with(getApplicationContext())
            .load(fileUri)
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .crossFade()
            .into(imgDisplay);
        }
        catch (Exception e) {
          setResult(RESULT_OK);
          finish();
        }


        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_KO);
                finish();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
        setResult(RESULT_OK);
        finish();
        return true;
    } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
        setResult(RESULT_KO);
        finish();
        return true;
    } else {
      return super.onKeyDown(keyCode, event);
    }
  }
}
