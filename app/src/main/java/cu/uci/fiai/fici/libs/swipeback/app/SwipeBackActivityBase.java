package cu.uci.fiai.fici.libs.swipeback.app;

import cu.uci.fiai.fici.libs.swipeback.SwipeBackLayout;

/**
 * @author Yrom
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this z_reside_layout_activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the z_reside_layout_activity
     */
    public abstract void scrollToFinishActivity();

}
