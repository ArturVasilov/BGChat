package ru.androidacademy.bgchat.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import ru.androidacademy.bgchat.R;

/**
 * @author Artur Vasilov
 */
public class HobieTextView extends AppCompatTextView {

    private static final int ANIMATION_DURATION = 200;

    private boolean isSelected = false;

    private TransitionDrawable transitionDrawable;

    @Nullable
    private OnSelectionChangedListener onSelectionChangedListener;

    public HobieTextView(Context context) {
        super(context);
        init();
    }

    public HobieTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HobieTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        int padding = getResources().getDimensionPixelSize(R.dimen.hobie_button_padding);
        setPadding(padding, padding, padding, padding);
        setTextColor(ContextCompat.getColor(getContext(), R.color.hobie_button_text_color_unselected));

        Drawable unselected = ContextCompat.getDrawable(getContext(), R.drawable.button_background_unselected);
        Drawable selected = ContextCompat.getDrawable(getContext(), R.drawable.button_background_selected);
        transitionDrawable = new TransitionDrawable(new Drawable[]{unselected, selected});
        transitionDrawable.setCrossFadeEnabled(true);
        setBackground(transitionDrawable);

        super.setOnClickListener(view -> setHobieSelected(!isSelected));
    }

    public boolean isHobieSelected() {
        return isSelected;
    }

    public void setHobieSelected(boolean isSelected) {
        if (this.isSelected == isSelected) {
            return;
        }

        if (isSelected) {
            transitionDrawable.startTransition(ANIMATION_DURATION);
            setTextColor(ContextCompat.getColor(getContext(), R.color.hobie_button_text_color_selected));
        } else {
            transitionDrawable.reverseTransition(ANIMATION_DURATION);
            setTextColor(ContextCompat.getColor(getContext(), R.color.hobie_button_text_color_unselected));
        }
        this.isSelected = isSelected;

        if (onSelectionChangedListener != null) {
            onSelectionChangedListener.onSelectionChanged(this, isSelected);
        }
    }

    public void setOnSelectionChangedListener(@Nullable OnSelectionChangedListener onSelectionChangedListener) {
        this.onSelectionChangedListener = onSelectionChangedListener;
    }

    @Override
    public final void setOnClickListener(@Nullable OnClickListener l) {
        throw new UnsupportedOperationException("Use setOnSelectionChangedListener if you need it");
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(View view, boolean isSelected);
    }
}
