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

package com.sch.androidtools.viewtools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sch.androidtools.R;
import com.sch.library.viewutil.ClickToCutWordHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClickToCutWordActivity extends AppCompatActivity implements ClickToCutWordHelper.OnCutWordListener {

    @BindView(R.id.tv)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_to_cut_word);

        ButterKnife.bind(this);

        ClickToCutWordHelper cutWordHelper = new ClickToCutWordHelper(textView, this);
        // Set the way to cut word is click once.
        cutWordHelper.setClickType(ClickToCutWordHelper.TYPE_CLICK);
//        // Set the way to cut word is click twice, this way is default.
//        cutWordHelper.setClickType(ClickToCutWordHelper.TYPE_DBLCLICK);
    }

    @Override
    public void onCutWord(String word) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show();
    }

}
