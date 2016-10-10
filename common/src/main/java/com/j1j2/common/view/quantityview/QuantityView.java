package com.j1j2.common.view.quantityview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.j1j2.common.R;
import com.j1j2.common.util.EmptyUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-3-21.
 */
public class QuantityView extends AutoLinearLayout implements TextWatcher, View.OnTouchListener {

    public interface OnQuantityChangeListener {
        void onQuantityChange(QuantityView view, int value);
    }

    public interface OnMaxQuantityListener {
        void onMaxQuantity(QuantityView view);
    }

    private OnQuantityChangeListener onQuantityChangeListener;

    private OnMaxQuantityListener onMaxQuantityListener;

    private TextView minusBtn;
    private TextView addBtn;
    private EditText quantityEdit;

    private int maxQuantity = 9999;
    private int quantity = 1;


    private int clickId = 0;

    GestureDetector gestureDetector;

    public QuantityView(Context context) {
        this(context, null);
    }

    public QuantityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuantityView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                int currentQuantity = quantity;
                if (clickId == R.id.minusBtn) {
                    currentQuantity--;
                }
                if (clickId == R.id.addBtn) {
                    currentQuantity++;
                }
                quantityEdit.setText("" + currentQuantity);
                return super.onSingleTapUp(e);
            }
        });
    }

    private void initViews(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_quantity, this, true);
        minusBtn = (TextView) findViewById(R.id.minusBtn);
        addBtn = (TextView) findViewById(R.id.addBtn);
        quantityEdit = (EditText) findViewById(R.id.quantityEdit);


        minusBtn.setOnTouchListener(this);
        addBtn.setOnTouchListener(this);
        quantityEdit.addTextChangedListener(this);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        clickId = v.getId();
        return gestureDetector.onTouchEvent(event);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Integer.valueOf(s.toString()) == quantity) {
            return;
        } else if (EmptyUtils.isEmpty(s)) {
            quantityEdit.setText("" + 1);
            return;
        } else if (Integer.valueOf(s.toString()) <= 0) {
            quantityEdit.setText("" + 1);
            return;
        } else if (Integer.valueOf(s.toString()) > maxQuantity) {
            quantityEdit.setText("" + maxQuantity);
            if (onMaxQuantityListener != null)
                onMaxQuantityListener.onMaxQuantity(QuantityView.this);
            return;
        } else {
            quantity = Integer.valueOf(s.toString());
        }
        if (onQuantityChangeListener != null) {
            onQuantityChangeListener.onQuantityChange(this, quantity);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (this.quantity == quantity)
            return;
        if (quantity <= 0) {
            quantityEdit.setText("" + 1);
        } else if (quantity > maxQuantity) {
            quantityEdit.setText("" + maxQuantity);
            if (onMaxQuantityListener != null)
                onMaxQuantityListener.onMaxQuantity(QuantityView.this);
        } else {
            quantityEdit.setText("" + quantity);
        }

    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }

    public void setOnMaxQuantityListener(OnMaxQuantityListener onMaxQuantityListener) {
        this.onMaxQuantityListener = onMaxQuantityListener;
    }
}
