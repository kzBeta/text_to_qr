package com.beta.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button generateBtn;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        generateBtn = findViewById(R.id.generateBtn);
        imageView = findViewById(R.id.imageView);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim();
                if(!text.isEmpty()){
                    try {
                        Bitmap bitmap = encodeAsBitmap(text);
                        imageView.setImageBitmap(bitmap);
                    }catch (WriterException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Bitmap encodeAsBitmap(String text) throws WriterException{
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,500,500,null);
        }catch (IllegalArgumentException e){
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for(int y = 0; y < height; y++){
            int offset = y * width;
            for(int x = 0; x < width; x++){
                pixels[offset * x] = result.get(x,y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels,0,width,0,0,width,height);
        return bitmap;
    }
}











