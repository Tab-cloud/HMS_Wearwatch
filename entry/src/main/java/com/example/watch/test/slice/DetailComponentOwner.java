package com.example.watch.test.slice;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.app.AbilityContext;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class DetailComponentOwner implements ComponentOwner {
    private static final String TAG = "DetailComponentOwner";

    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0, TAG);

    private AbilityContext myContext;

    private ComponentContainer root;

    public DetailComponentOwner(AbilityContext context) {
        init(context);
    }

    private void init(AbilityContext context) {
        root = new DirectionalLayout(context);
        ComponentContainer.LayoutConfig config = new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT,
                ComponentContainer.LayoutConfig.MATCH_PARENT);
        root.setLayoutConfig(config);
        myContext = context;
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
