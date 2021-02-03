package com.example.watch.test.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SleepPageSlice extends AbilitySlice {
    private static final String TAG = "SleepPageSlice";

    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP,0,TAG);

    private List<ComponentOwner> list = new ArrayList<>();

    private PageSliderProvider  provider = new PageSliderProvider() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object createPageInContainer(ComponentContainer componentContainer, int index) {
            if (index >= list.size() || componentContainer == null){
                HiLog.error(LABEL, "instantiateItem index error");
                return Optional.empty();
            }
            ComponentOwner container = list.get(index);
            componentContainer.addComponent(container.getComponent());
            container.instantiateComponent();
            return container.getComponent();
        }

        @Override
        public void destroyPageFromContainer(ComponentContainer componentContainer, int index, Object object) {
            HiLog.info(LABEL,"destroyItem index:"+index);
            if (index >= list.size() || componentContainer == null){
                return ;
            }
            Component component = list.get(index).getComponent();
            componentContainer.removeComponent(component);
            return;
        }

        @Override
        public boolean isPageMatchToObject(Component component, Object object) {
            return component == object;
        }

        @Override
        public void startUpdate(ComponentContainer container){
            super.startUpdate(container);
            HiLog.info(LABEL,"startUpdate");
        }
    };


    @Override
    public void onStart(Intent intent){
        super.onStart(intent);
        HiLog.info(LABEL,"onStart");

        //添加子页面
        list.add(new SleepComponentOwner(this));
        list.add(new DetailComponentOwner(this));

        //设置主界面
        DirectionalLayout layout = new DirectionalLayout(this);
        ComponentContainer.LayoutConfig config = new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT,
                ComponentContainer.LayoutConfig.MATCH_PARENT
        );
        layout.setLayoutConfig(config);

        //使用PageSlider做滑动效果
        PageSlider slider = new PageSlider(this);

        ComponentContainer.LayoutConfig sliderConfig = new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT,
                ComponentContainer.LayoutConfig.MATCH_PARENT
        );
        slider.setLayoutConfig(sliderConfig);

        slider.setProvider(provider);

        layout.addComponent(slider);

        setUIContent(layout);
    }
}
