package com.hds.crop;

import android.graphics.RectF;
import android.view.View;

/**
 * Created by hds on 17-11-15.
 */

public interface Shaper {


    View getShaper();

    RectF getCornerRects();

    int getId();

}
