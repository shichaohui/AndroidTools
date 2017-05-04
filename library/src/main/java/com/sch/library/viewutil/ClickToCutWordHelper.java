/*
 * Copyright 2017 StoneHui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.sch.library.viewutil;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by StoneHui on 17/5/4.
 * <p>
 * A util for get word when {@link TextView} be clicked.
 */
public class ClickToCutWordHelper implements View.OnTouchListener, View.OnClickListener {

    /**
     * Cut out the word that be clicked once.
     */
    public static final int TYPE_CLICK = 1;
    /**
     * Cut out the word that be clicked twice.
     */
    public static final int TYPE_DBLCLICK = 2;

    /**
     * A time interval limit of twice click.
     */
    private final long DBLCLICK_INTERVAL = 1000L;

    private TextView textView;

    private long lastClickTime;
    private String lastClickWord;

    private int touchX, touchY;

    private final OnCutWordListener onCutWordListener;

    private int clickType = TYPE_DBLCLICK;

    public interface OnCutWordListener {
        /**
         * Callback when cut a word.
         *
         * @param word the clicked word
         */
        void onCutWord(String word);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_CLICK, TYPE_DBLCLICK})
    public @interface ClickType {
    }

    public ClickToCutWordHelper(TextView textView, @NonNull OnCutWordListener onCutWordListener) {
        this.textView = textView;
        this.onCutWordListener = onCutWordListener;

        textView.setOnTouchListener(this);
        textView.setOnClickListener(this);
    }

    public void setClickType(@ClickType int clickType) {
        this.clickType = clickType;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = (int) event.getX();
            touchY = (int) event.getY();
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        if (clickType == TYPE_CLICK) {
            onCutWordListener.onCutWord(getClickedWord());
            return;
        }

        long clickTime = System.currentTimeMillis();
        if (clickType == TYPE_DBLCLICK && clickTime - lastClickTime > DBLCLICK_INTERVAL) {
            lastClickTime = clickTime;
            lastClickWord = getClickedWord();
            return;
        }

        String clickWord = getClickedWord();
        if (!TextUtils.isEmpty(lastClickWord) && lastClickWord.equals(clickWord)) {
            onCutWordListener.onCutWord(clickWord);
        }

    }

    private String getClickedWord() {
        String text = textView.getText().toString();
        int offset = getClickedPosition(textView, touchX, touchY);
        if (text.length() <= offset || !Character.isLetter(text.charAt(offset))) {
            return "";
        }
        int offsetStart = offset;
        for (int i = offset - 1; i >= 0; i--) {
            if (Character.isLetter(text.charAt(i))) {
                offsetStart = i;
            } else {
                break;
            }
        }
        int offsetEnd = offset;
        for (int i = offset + 1, N = text.length(); i < N; i++) {
            if (Character.isLetter(text.charAt(i))) {
                offsetEnd = i;
            } else {
                break;
            }
        }
        return text.substring(offsetStart, offsetEnd + 1);
    }

    /**
     * Get the char's position of specify coordinate on TextView.
     *
     * @param textView TextView
     * @param x        coordinate x
     * @param y        coordinate y
     * @return position
     */
    private int getClickedPosition(TextView textView, int x, int y) {
        Layout layout = textView.getLayout();
        if (layout != null) {
            int topVisibleLine = layout.getLineForVertical(y);
            int offset = layout.getOffsetForHorizontal(topVisibleLine, x);
            int offsetX = (int) layout.getPrimaryHorizontal(offset);
            if (offsetX > x) {
                return layout.getOffsetToLeftOf(offset);
            } else {
                return offset;
            }
        } else {
            return -1;
        }
    }

}
