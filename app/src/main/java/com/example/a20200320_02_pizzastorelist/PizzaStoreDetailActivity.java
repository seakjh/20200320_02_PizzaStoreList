package com.example.a20200320_02_pizzastorelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a20200320_02_pizzastorelist.databinding.ActivityPizzaStoreDetailBinding;
import com.example.a20200320_02_pizzastorelist.datas.PizzaStore;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class PizzaStoreDetailActivity extends BaseActivity {

    PizzaStore store = null;

    ActivityPizzaStoreDetailBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pizza_store_detail);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.storeLogoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, LogoViewActivity.class);
                intent.putExtra("logoUrl", store.getLogoUrl());
                startActivity(intent);

            }
        });

        binding.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                가게의 전화번호를 => CALL 액션으로 연결.
//                사용자의 허가가 OK인 상황에서 실험하도록 => TedPermission 라이브러리 활용

                PermissionListener pl = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
//                        허가가 떨어진 상황->전화걸기 코드 실행
                        String phoneNumUri = String.format("tel:%s", store.getPhoneNum());

                        Uri uri = Uri.parse(phoneNumUri);
                        Intent intent = new Intent(Intent.ACTION_CALL, uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
//                        권한을 사용자가 거부한 상황 -> 전화 권한을 허용해야 사용가능합니다 토스트
                        Toast.makeText(mContext, "전화 권한을 허용해야 사용가능합니다", Toast.LENGTH_SHORT).show();
                    }
                };

//                onClick 실행? 사용자가 손으로 클릭 결과 실행
//                권한 상황 대응 실행? 코드로 권한 요청을 직접 날려서 실행
                TedPermission.with(mContext).setPermissionListener(pl)
                        .setDeniedMessage("설정에서 허용해줘야 사용가능합니다.")
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .check();

            }
        });

    }

    @Override
    public void setValues() {

        store = (PizzaStore) getIntent().getSerializableExtra("store");

        binding.storeNameTxt.setText(store.getStoreName());
        binding.storePhoneTxt.setText(store.getPhoneNum());
        Glide.with(mContext).load(store.getLogoUrl()).into(binding.storeLogoImg);

    }
}
