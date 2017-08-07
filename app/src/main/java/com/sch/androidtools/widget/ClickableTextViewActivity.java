/*
 * Copyright 2017 StoneHui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.sch.androidtools.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sch.androidtools.R;
import com.sch.library.widget.ClickableTextView;
import com.sch.library.widget.ClickableTextView.Options;

/**
 * Created by StoneHui on 2017/8/7.
 * <p>
 * TODO 请填写当前类的说明
 */
public class ClickableTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickable_text_view);

        Options notClickableOptions = new Options(this)
                .setText("不能点我哦")
                .setColor(Color.BLUE);
        Options headClickableOptions = new Options(this)
                .setText("头部")
                .setColor(Color.GREEN)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "点头", Toast.LENGTH_SHORT).show();
                    }
                });
        Options tailClickableOptions = new Options(this)
                .setText("尾部")
                .setColor(Color.GREEN)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "点尾", Toast.LENGTH_SHORT).show();
                    }
                });
        ((ClickableTextView) findViewById(R.id.textView))
                .setNotClickableText(notClickableOptions)
                .setHeadClickableText(headClickableOptions)
                .setTailClickableText(tailClickableOptions)
                .show();
    }

}
