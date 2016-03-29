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
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-3-21.
 */
public class QuantityView extends AutoLinearLayout implements TextWatcher, View.OnTouchListener {

    public interface OnQuantityChangeListener {
        void onQuantityChange(QuantityView view, int value);
    }

    private OnQuantityChangeListener onQuantityChangeListener;

    private TextView minusBtn;
    private TextView addBtn;
    private EditText quantityEdit;
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
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (clickId == R.id.minusBtn) {
                    quantity--;
                }
                if (clickId == R.id.addBtn) {
                    quantity++;
                }
                if (quantity <= 0) {
                    quantity = 1;
                } else if (quantity > 9999) {
                    quantity = 9999;
                }
                quantityEdit.setText("" + quantity);
                return super.onSingleTapUp(e);
            }
        });
        initViews(attrs);
    }

    private void initViews(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_quantity, this, true);
        minusBtn = (TextView) findViewById(R.id.minusBtn);
        addBtn = (TextView) findViewById(R.id.addBtn);
        quantityEdit = (EditText) findViewById(R.id.quantityEdit);

//        minusBtn.setOnClickListener(this);
//        addBtn.setOnClickListener(this);
        minusBtn.setOnTouchListener(this);
        addBtn.setOnTouchListener(this);
        quantityEdit.addTextChangedListener(this);

    }


    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int height = getHeight();
        int width = getWidth();

//        minusBtn.setTextSize(AutoUtils.getPercentHeightSize((int) (height * 0.4f)));
//        addBtn.setTextSize(AutoUtils.getPercentHeightSize((int) (height * 0.4f)));
//        quantityEdit.setTextSize(AutoUtils.getPercentHeightSize((int) (height * 0.25f)));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        clickId = v.getId();
        return gestureDetector.onTouchEvent(event);
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.minusBtn) {
//            quantity--;
//        }
//        if (v.getId() == R.id.addBtn) {
//            quantity++;
//        }
//        if (quantity <= 0) {
//            quantity = 1;
//        } else if (quantity > 9999) {
//            quantity = 9999;
//        }
//        quantityEdit.setText("" + quantity);
//    }

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
        if (this.quantity == quantity)
            return;
        if (quantity <= 0) {
            quantity = 1;
        } else if (quantity > 9999) {
            quantity = 9999;
        } else {
            this.quantity = quantity;
        }
        quantityEdit.setText("" + quantity);
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }
}
