package com.example.watch.test.slice;

import ohos.agp.components.Component;

public interface ComponentOwner {
    // 获取存放的component
    Component getComponent();

    // 当包含的component被添加到容器时回调
    void instantiateComponent();
}
