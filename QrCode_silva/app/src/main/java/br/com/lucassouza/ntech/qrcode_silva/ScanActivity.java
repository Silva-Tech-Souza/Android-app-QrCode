package br.com.lucassouza.ntech.qrcode_silva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    Button btn_escanear;
    TextView txt_escaner;
    private static  final int SOLICITAR_PERMISSAO = 1;
    public boolean criado = false;
    public String trocatxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        btn_escanear = findViewById(R.id.btn_escanear);
        txt_escaner = findViewById(R.id.scanertxt);
        checarPermissao();
        
        btn_escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (criado == true){
                    IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    intentIntegrator.setPrompt("QrCode Scan\n");
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setOrientationLocked(true);
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.initiateScan();
                }else{
                    checarPermissao();
                    Toast.makeText(ScanActivity.this, "Primeiro permita o uso da câmera", Toast.LENGTH_SHORT).show();
                }

            }
        });

       

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =  IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result  != null){
            if (result.getContents() != null ){

                Alert(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    public void checarPermissao(){
        int permissaoCheck = ContextCompat.checkSelfPermission(ScanActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissaoCheck != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(ScanActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},SOLICITAR_PERMISSAO);
            checarPermissao();
        }else{
            criado =true;
        }
    }
    public  void Alert(final String msg){
        String[] leitor_codigo1 = msg.split("/");
        String[] leitor_codigo2 = msg.split(".");
        for (int x = leitor_codigo1.length; x>0; x--){

            if (leitor_codigo1[x-1].toLowerCase().equals("HTTPS:".toLowerCase()) || leitor_codigo1[x-1].toLowerCase().equals("HTTP:".toLowerCase())){

                Toast.makeText(ScanActivity.this, "Essa mensagem trata-se de um link", Toast.LENGTH_LONG).show();
                AlertDialog.Builder  builder = new AlertDialog.Builder(ScanActivity.this);
                CharSequence[] itens = new CharSequence[2];//cria array de 3 caompos e cada um ganha uma valor
                itens[0] = "Abri Link";
                itens[1] = "Cancelar";
                builder.setTitle("Selecionar uma Opção:").setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i==0){//
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(msg));
                            startActivity(intent);
                        }else{//
                            return;
                        }

                    }
                });
                AlertDialog dialog = builder.create();//cria o alerta
                dialog.show();//exibi o alerta
                x=0;
            }
        }
        for (int y = leitor_codigo2.length;y>0;y-- ){
            if (leitor_codigo2[y-1].toLowerCase().equals("WWW".toLowerCase()) || leitor_codigo2[y-1].toLowerCase().equals("COM".toLowerCase())){

                Toast.makeText(ScanActivity.this, "Essa mensagem trata-se de um link", Toast.LENGTH_LONG).show();
                AlertDialog.Builder  builder = new AlertDialog.Builder(ScanActivity.this);
                CharSequence[] itens = new CharSequence[2];//cria array de 3 caompos e cada um ganha uma valor
                itens[0] = "Abri Link";
                itens[1] = "Cancelar";
                builder.setTitle("Selecionar uma Opção:").setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i==0){//
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http//:"+msg));
                            startActivity(intent);
                        }else{//
                            return;
                        }

                    }
                });
                AlertDialog dialog = builder.create();//cria o alerta
                dialog.show();//exibi o alerta
                y=0;
            }
        }
        txt_escaner.setText(msg.toString());
   

    }
}