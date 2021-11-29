package com.idowedo.firstproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.FileNotFoundException;
import java.io.InputStream;

import dmax.dialog.SpotsDialog;

public class ChallengeCertifyActivity extends AppCompatActivity {
    static final int REQUEST_CODE = 2;

    ImageView imageView;    // 갤러리에서 가져온 이미지를 보여줄 뷰
    Uri uri;                // 갤러리에서 가져온 이미지에 대한 Uri
    Bitmap bitmap;          // 갤러리에서 가져온 이미지를 담을 비트맵
    InputImage image;       // ML 모델이 인식할 인풋 이미지
    TextView text_info, text_certify;     // ML 모델이 인식한 텍스트를 보여줄 뷰, 인증할 텍스트 문장을 보여주는 뷰
    Button btn_get_image, btn_detection_image, btn_certify;  // 이미지 가져오기 버튼, 이미지 인식 버튼, 챌린지 인증 검사 버튼
    TextRecognizer recognizer;    //텍스트 인식에 사용될 모델
    AlertDialog waitingDialog;   //로딩창
    Integer i;
    String[] array_text = {"Hello everyone!", "Believe in yourself.", "Follow your heart.", "Seize the day.", "You only live once." , "Past is just past." ,
            "Love yourself." , "Life is a journey." , "This too shall pass away." , "No pain, No gain." , "No sweat, No sweet."
            , "The die is cast.", "Appearances are deceptive.", "Be brave.", "Every cloud has a silver lining.", "Hang in there.",
            "This is how life if", "Live positive", "Seeing is believing.", "If not now, then when?", "You deserve to be loved.", "Time waits for no one.",
            "Respect individual.", "Habit is a second nature.", "The will of man is his happiness", "Time is gold", "Your success is up to your efforts",
            "Love what you do.", "Live live there is no tomorrow.", "Time is flying never to return.", "Happiness is a warm puppy."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_certify);

        imageView = findViewById(R.id.imageView);
        text_certify = findViewById(R.id.text_certify);
        text_info = findViewById(R.id.text_info);
        recognizer = TextRecognition.getClient();    //텍스트 인식에 사용될 모델
        text_certify.setText(array_text[1]);

        waitingDialog = new SpotsDialog.Builder().
                setContext(this)
                .setMessage("Please waiting...")
                .setCancelable(false).build();

        // 이미지 업로드 버튼
        btn_get_image = findViewById(R.id.btn_get_image);
        btn_get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 101);
            }
        });

        // 텍스트 인식 버튼
        btn_detection_image = findViewById(R.id.btn_detection_image);
        btn_detection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextRecognition(recognizer);
            }
        });

        // 인증 버튼 (인증 문자랑 인식 문자가 동일할 경우에만 Clickable 가능)
        btn_certify = findViewById(R.id.btn_certify);
        btn_certify.setBackground(getDrawable(R.drawable.attencheckeddrawble));
        btn_certify.setEnabled(false);
        btn_certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengeCertifyActivity.this, Challenge_Wakeup_Activity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 101) {
            // 갤러리에서 선택한 사진에 대한 uri를 가져온다.
            uri = data.getData();

            setImage(uri);
        }
    }

    // uri를 비트맵으로 변환시킨후 이미지뷰에 띄워주고 InputImage를 생성하는 메서드
    private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);

            image = InputImage.fromBitmap(bitmap, 0);
//            Log.e("setImage", "image -> bitmap");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void TextRecognition(TextRecognizer recognizer){
        Task<Text> result = recognizer.process(image)
                // 이미지 인식에 성공하면 실행
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {


                        Log.e("텍스트 인식", "성공");
                        // Task completed successfully
                        String resultText = visionText.getText();
                        text_info.setText(resultText);  // 인식한 텍스트를 TextView에 보여줌
    
                        //text_certify.setText 로 하루마다 바꿔주는 거 해야됨. 지금은 for문 안에서 array_text안에 있는 문자열 중 하나라도 포함되어 있으면 인증 완료
                        String input_text = resultText;

                        for(i = 0; i < array_text.length; i++) {
                            if (input_text.contains(array_text[i])) {
                                btn_certify.setBackground(getDrawable(R.drawable.certify_btnlayout));
                                btn_certify.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "인증 버튼 ->", Toast.LENGTH_SHORT);
                            }
                        }
                    }
                })

                // 이미지 인식에 실패하면 실행
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("텍스트 인식", "실패: " + e.getMessage());
                            }
                        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭시 안 닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막기
        return;
    }

}