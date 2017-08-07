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

package com.sch.library.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by StoneHui on 2017/8/7.
 * <p>
 * A view that can be click text by specify。
 */
public class ClickableTextView extends AppCompatTextView {

    private Options headClickableOptions = null;
    private Options tailClickableOptions = null;
    private Options notClickableOptions = null;

    public ClickableTextView(Context context) {
        this(context, null);
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHighlightColor(Color.TRANSPARENT);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public ClickableTextView setNotClickableText(Options options) {
        options.onClickListener = null;
        notClickableOptions = options;
        return this;
    }

    /**
     * @return not clickable text. It was set by {{@link #setText(int)}} or other be named setText method.
     */
    public Options getNotClickableText() {
        return notClickableOptions;
    }

    /**
     * Set clickable text, this text will be insert to head.
     *
     * @param options options for clickable text.
     */
    public ClickableTextView setHeadClickableText(@NonNull Options options) {
        headClickableOptions = options;
        return this;
    }

    public Options getHeadClickableOptions() {
        return headClickableOptions;
    }

    /**
     * Set clickable text, this text will be insert to tail.
     *
     * @param options options for clickable text.
     */
    public ClickableTextView setTailClickableText(@NonNull Options options) {
        tailClickableOptions = options;
        return this;
    }

    public Options getTailClickableOptions() {
        return tailClickableOptions;
    }

    public void show() {

        List<Options> optionsList = Arrays.asList(headClickableOptions, notClickableOptions, tailClickableOptions);
        List<ClickableSpanWrapper> clickableSpanWrapperList = new ArrayList<>();

        StringBuilder text = new StringBuilder();

        for (Options options : optionsList) {
            if (options != null) {
                ClickableSpanWrapper wrapper = new ClickableSpanWrapper();
                wrapper.clickableSpan = createClickableSpan(options);
                wrapper.start = text.length();
                text.append(options.text);
                wrapper.end = text.length();
                clickableSpanWrapperList.add(wrapper);
            }
        }

        SpannableString spannableString = new SpannableString(text);
        for (ClickableSpanWrapper wrapper : clickableSpanWrapperList) {
            spannableString.setSpan(wrapper.clickableSpan, wrapper.start, wrapper.end, wrapper.flags);
        }

        setText(spannableString);

    }

    public ClickableSpan createClickableSpan(final Options options) {
        return new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                if (options.onClickListener != null) {
                    options.onClickListener.onClick(widget);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(options.hasUnderline);
                ds.setColor(options.color);
            }

        };
    }

    private static class ClickableSpanWrapper {
        private ClickableSpan clickableSpan;
        private int start;
        private int end;
        private int flags = Spannable.SPAN_INCLUSIVE_EXCLUSIVE;
    }

    public static class Options {

        private WeakReference<Context> contextWeakReference;
        private CharSequence text = "";
        @ColorInt
        private int color = Color.BLACK;
        private boolean hasUnderline = false;
        private OnClickListener onClickListener;

        public Options(Context context) {
            contextWeakReference = new WeakReference<>(context);
        }

        public Options setText(@StringRes int resId) {
            setText(contextWeakReference.get().getApplicationContext().getResources().getString(resId));
            return this;
        }

        public Options setText(@NonNull CharSequence text) {
            this.text = text;
            return this;
        }

        public Options setColorResource(@ColorRes int resId) {
            setColor(contextWeakReference.get().getApplicationContext().getResources().getColor(resId));
            return this;
        }

        public Options setColor(@ColorInt int color) {
            this.color = color;
            return this;
        }

        public Options setUnderline(boolean hasUnderline) {
            this.hasUnderline = hasUnderline;
            return this;
        }

        public Options setOnClickListener(@Nullable OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

    }

}