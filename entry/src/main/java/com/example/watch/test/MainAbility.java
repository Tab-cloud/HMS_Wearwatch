package com.example.watch.test;

import com.example.watch.test.slice.SleepPageSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SleepPageSlice.class.getName());
    }
}
