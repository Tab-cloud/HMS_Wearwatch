package com.example.watch.test.slice;

import com.example.watch.test.ResourceTable;
import com.example.watch.test.page.CircleProgressDrawTask;
import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.element.VectorElement;
import ohos.app.AbilityContext;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class SleepComponentOwner implements ComponentOwner {
    private static final String TAG = "SleepComponentOwner";

    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0, TAG);

    // 目标睡眠时长默认值,单位：分钟
    private static final int DEFAULT_SLEEP_TARGET_TIME = 480;

    // 睡眠时长默认值,单位：分钟
    private static final int DEFAULT_SLEEP_TIME = 0;

    private CircleProgressDrawTask drawTask;

    private AbilityContext myContext;

    private Component root;

    public SleepComponentOwner(AbilityContext context) {
        init(context);
    }

    private void init(AbilityContext context) {
        myContext = context;
        LayoutScatter scatter = LayoutScatter.getInstance(context);
        root = scatter.parse(ResourceTable.Layout_layout_sleep, null, false);
        drawTask = new CircleProgressDrawTask(root);
        drawTask.setMaxValue(DEFAULT_SLEEP_TARGET_TIME);

        Component imageComponent = root.findComponentById(ResourceTable.Id_sleep_moon_img);
//        imageComponent.setBackground(new VectorElement(context, ResourceTable.Media_Graphic_ic_icon_moon));
    }

    @Override
    public Component getComponent() {
        return root;
    }

    @Override
    public void instantiateComponent() {
        return;
    }
}
