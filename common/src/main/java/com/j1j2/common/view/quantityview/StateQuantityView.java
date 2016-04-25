package com.j1j2.common.view.quantityview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.j1j2.common.R;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by alienzxh on 16-4-9.
 */
public class StateQuantityView extends AutoLinearLayout implements View.OnTouchListener {

    public interface OnQuantityChangeListener {
        void onQuantityChange(StateQuantityView view, int value);

        void onEnableStateChange(StateQuantityView view, boolean isEnable);

        boolean canEnable();
    }

    private OnQuantityChangeListener onQuantityChangeListener;

    private TextView minusBtn;
    private TextView addBtn;
    private TextView quantityEdit;

    private int clickId = 0;

    GestureDetector gestureDetector;

    private int quantity = 0;
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
                    if (quantity <= 0) {
                        setEnable(false);
                    }
                }
                if (clickId == R.id.addBtn) {
                    quantity++;
                    if (!isEnable) {
                        if (onQuantityChangeListener != null)
                            if (onQuantityChangeListener.canEnable())
                                setEnable(true);
                    }
                }
                if (quantity < 0) {
                    quantity = 0;
                } else if (quantity > 9999) {
                    quantity = 9999;
                }
                quantityEdit.setText("" + quantity);
                if (onQuantityChangeListener != null) {
                    onQuantityChangeListener.onQuantityChange(StateQuantityView.this, quantity);
                }
                return super.onSingleTapUp(e);
            }
        });
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_state_quantity, this, true);
        minusBtn = (TextView) findViewById(R.id.minusBtn);
        addBtn = (TextView) findViewById(R.id.addBtn);
        quantityEdit = (TextView) findViewById(R.id.quantityEdit);

        minusBtn.setOnTouchListener(this);
        addBtn.setOnTouchListener(this);

        setEnable(false);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        clickId = v.getId();
        return gestureDetector.onTouchEvent(event);
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int mQuantity) {
        if (mQuantity <= 0) {
            if (isEnable)
                setEnable(false);
        } else {
            if (!isEnable)
                setEnable(true);
        }

        if (this.quantity == mQuantity)
            return;
        else if (mQuantity <= 0) {
            quantity = 0;
        } else if (mQuantity > 9999) {
            quantity = 9999;
        } else {
            this.quantity = mQuantity;
        }
        quantityEdit.setText("" + quantity);
        if (onQuantityChangeListener != null) {
            onQuantityChangeListener.onQuantityChange(this, quantity);
        }
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
