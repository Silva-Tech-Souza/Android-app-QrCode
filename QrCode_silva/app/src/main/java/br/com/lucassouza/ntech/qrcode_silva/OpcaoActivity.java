package br.com.lucassouza.ntech.qrcode_silva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class OpcaoActivity extends AppCompatActivity {
    TextView btn_criarqr, btn_escanerqr, btn_avalie;
    ImageView btn_img_criarqr, btn_img_escanerqr;
    public boolean criado = false;
    private static  final int SOLICITAR_PERMISSAO = 1;
    LinearLayout btn_line_criarqr, brn_line_escanerqr, btn_avaliar_avabtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcao);
        
        btn_criarqr = findViewById(R.id.btn_criar_qrtxt);
        btn_avaliar_avabtn = findViewById(R.id.btn_avaliar_avabtn);
        btn_img_criarqr = findViewById(R.id.btn_criar_qrimg);
        btn_line_criarqr = findViewById(R.id.btn_criar_qrlinear);

        btn_img_escanerqr = findViewById(R.id.img_qrescaner);
        btn_escanerqr = findViewById(R.id.txt_qrescaner);
        brn_line_escanerqr = findViewById(R.id.linear_qrescaner);

            btn_criarqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checarPermissao();
                    if (criado){
                    Intent intent = new Intent(OpcaoActivity.this, MainActivity.class);
                    startActivity(intent);
                    }else{
                        Toast.makeText(OpcaoActivity.this, "Para continuar aceite as permissões", Toast.LENGTH_SHORT).show();
                        checarPermissao();
                    }
                }
            });
            btn_img_criarqr.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    checarPermissao();
                    if (criado){
                        Intent intent = new Intent(OpcaoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(OpcaoActivity.this, "Para continuar aceite as permissões", Toast.LENGTH_SHORT).show();
                        checarPermissao();
                    }
                }
            });
            btn_line_criarqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checarPermissao();
                    if (criado){
                        Intent intent = new Intent(OpcaoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(OpcaoActivity.this, "Para continuar aceite as permissões", Toast.LENGTH_SHORT).show();
                        checarPermissao();
                    }
                }
            });

            btn_img_escanerqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checarPermissao();
                    if (criado) {
                        Intent intent = new Intent(OpcaoActivity.this, ScanActivity.class);
                        startActivity(intent);
                    }else{
                            Toast.makeText(OpcaoActivity.this, "Para continuar aceite as permissões", Toast.LENGTH_SHORT).show();
                            checarPermissao();
                        }
                }
            });
            btn_escanerqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checarPermissao();
                    if (criado) {
                        Intent intent = new Intent(OpcaoActivity.this, ScanActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(OpcaoActivity.this, "Para continuar aceite as permissões", Toast.LENGTH_SHORT).show();
                        checarPermissao();
                    }
                }
            });
            brn_line_escanerqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checarPermissao();
                    if (criado) {
                        Intent intent = new Intent(OpcaoActivity.this, ScanActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(OpcaoActivity.this, "Para continuar aceite as permissões", Toast.LENGTH_SHORT).show();
                        checarPermissao();
                    }
                }
            });

        btn_avaliar_avabtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(
                            "https://play.google.com/store/apps/details?id=br.com.lucassouza.ntech.qrcode_silva"));
                    intent.setPackage("com.android.vending");
                    startActivity(intent);
                }
            });


    }
    public void checarPermissao(){
        int permissaoCheck = ContextCompat.checkSelfPermission(OpcaoActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissaoCheck != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(OpcaoActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},SOLICITAR_PERMISSAO);
            checarPermissao();
        }else{
            criado =true;
        }
    }
}