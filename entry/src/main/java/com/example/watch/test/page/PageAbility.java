package com.example.watch.test.page;

import com.example.watch.test.slice.SleepPageSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class PageAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SleepPageSlice.class.getName());
        setSwipeToDismiss(true);
    }
}