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

package com.sch.androidtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sch.androidtools.viewtools.ClickToCutWordActivity;
import com.sch.androidtools.widget.ClickableTextViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rcv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this, getTargetList()));
    }

    public List<Target> getTargetList() {
        List<Target> targetList = new ArrayList<>();

        targetList.add(new Target("get the word be clicked\n获取点击的单词", ClickToCutWordActivity.class));
        targetList.add(new Target("clickable text view\n可点击的文本视图", ClickableTextViewActivity.class));

        return targetList;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private LayoutInflater layoutInflater;
        private List<Target> targetList;

        MyAdapter(Context context, List<Target> targetList) {
            this.targetList = targetList;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(layoutInflater.inflate(R.layout.item_tool_example, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Target target = targetList.get(position);
            holder.tvTitle.setText(target.title);
            holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, target.targetClass));
                }
            });
        }

        @Override
        public int getItemCount() {
            return targetList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_title)
            TextView tvTitle;

            MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }
    }

    private static class Target {

        String title;
        Class<?> targetClass;

        Target(String title, Class<?> targetClass) {
            this.title = title;
            this.targetClass = targetClass;
        }

    }

}
