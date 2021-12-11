package com.idowedo.firstproject3;

import com.squareup.otto.Bus;
//프래그먼트에서 데이터 이동 시 값을 전달해줄 클래스
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }

}
