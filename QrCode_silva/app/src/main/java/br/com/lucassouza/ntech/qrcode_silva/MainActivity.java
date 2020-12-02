package br.com.lucassouza.ntech.qrcode_silva;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText edtqr_palavra;
    Button btn_qr;
    ImageView img_qr, btn_compartilhar;
    private static  final int SOLICITAR_PERMISSAO = 1;
    public boolean criado = false;
    public  Bitmap img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checarPermissao();
       
        edtqr_palavra = findViewById(R.id.edt_palavra);
        btn_qr = findViewById(R.id.btn_codificar);
        img_qr = findViewById(R.id.img_qr);
        btn_compartilhar = findViewById(R.id.btncompart);

        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GerarQRcode();

            }


        });
        btn_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtqr_palavra.toString().length() >= 0 || !edtqr_palavra.toString().equals("") ){
                    if (criado == true){
                        compartilharqrcode();
                    }else{
                        Toast.makeText(MainActivity.this, "Primeiro permita o uso do armazenamento", Toast.LENGTH_SHORT).show();
                        checarPermissao();
                    }

                }

            }
        });

    }

    private  void compartilharqrcode(){
        if(img_qr.getDrawable() == null){
            Toast.makeText(MainActivity.this, "Primerio crie o qrcode", Toast.LENGTH_SHORT).show();
        }else{
            if (criado == true){
                Intent shere = new Intent(Intent.ACTION_SEND);
                shere.setType("*/*");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(),img,"QrCode silva", null);
                Uri imageUri = Uri.parse(path);
                shere.putExtra(Intent.EXTRA_STREAM, imageUri);
                shere.putExtra(Intent.EXTRA_TEXT, getString(R.string.compartilhar_text));
                startActivity(Intent.createChooser(shere,"Selecione"));
            }else{
                Toast.makeText(MainActivity.this, "Primerio crie o qrcode", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void checarPermissao(){
        int permissaoCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissaoCheck != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},SOLICITAR_PERMISSAO);
            checarPermissao();
        }else{
            criado =true;
        }
    }
    private void GerarQRcode() {
        String palavra = edtqr_palavra.getText().toString();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(palavra, BarcodeFormat.QR_CODE,2000,2000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            img = bitmap;
            img_qr.setImageBitmap(bitmap);
            criado = true;
            btn_compartilhar.animate().alpha(1).setDuration(1000);
        }catch (Exception e){

        }
    }
}