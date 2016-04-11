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
import android.widget.Toast;

import com.j1j2.common.R;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by alienzxh on 16-4-9.
 */
public class StateQuantityView extends AutoLinearLayout implements TextWatcher, View.OnTouchListener {

    public interface OnQuantityChangeListener {
        void onQuantityChange(StateQuantityView view, int value);

        void onEnableStateChange(StateQuantityView view, boolean isEnable);

        boolean canEnable();
    }


    private OnQuantityChangeListener onQuantityChangeListener;

    private TextView minusBtn;
    private TextView addBtn;
    private EditText quantityEdit;
    private int quantity = 1;

    private int clickId = 0;

    GestureDetector gestureDetector;

    private boolean isEnable = false;

    public StateQuantityView(Context context) {
        this(context, null);
    }

    public StateQuantityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateQuantityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (clickId == R.id.minusBtn) {
                    quantity--;
                    if (quantity < 1) {
                        setEnable(false);
                    }
                }
                if (clickId == R.id.addBtn) {
                    if (!isEnable) {
                        if (onQuantityChangeListener != null)
                            if (onQuantityChangeListener.canEnable())
                                setEnable(true);
                    } else {
                        quantity++;
                    }

                }
                quantityEdit.setText("" + quantity);
                return super.onSingleTapUp(e);
            }
        });
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_state_quantity, this, true);
        minusBtn = (TextView) findViewById(R.id.minusBtn);
        addBtn = (TextView) findViewById(R.id.addBtn);
        quantityEdit = (EditText) findViewById(R.id.quantityEdit);

        minusBtn.setOnTouchListener(this);
        addBtn.setOnTouchListener(this);
        quantityEdit.addTextChangedListener(this);

        setEnable(false);
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

        if (null == s.toString() || s.length() <= 0) {
            quantity = 1;
            quantityEdit.setText("" + quantity);
            return;
        } else if (Integer.valueOf(s.toString()) <= 0) {
            quantity = 1;
            quantityEdit.setText("" + quantity);
            return;
        } else if (Integer.valueOf(s.toString()) > 9999) {
            quantity = 9999;
            quantityEdit.setText("" + quantity);
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0)
            setEnable(false);
        else
            setEnable(true);
        if (this.quantity == quantity)
            return;
        else if (quantity <= 0) {
            quantity = 1;
        } else if (quantity > 9999) {
            quantity = 9999;
        } else {
            this.quantity = quantity;
        }
        quantityEdit.setText("" + quantity);
    }


    public void setEnable(boolean enable) {
        isEnable = enable;
        if (enable) {
            minusBtn.setVisibility(VISIBLE);
            quantityEdit.setVisibility(VISIBLE);
        } else {
            minusBtn.setVisibility(GONE);
            quantityEdit.setVisibility(GONE);
        }
        if (onQuantityChangeListener != null) {
            onQuantityChangeListener.onEnableStateChange(this, isEnable);
        }
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }
}
