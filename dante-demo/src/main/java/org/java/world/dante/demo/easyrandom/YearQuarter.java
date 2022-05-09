package org.java.world.dante.demo.easyrandom;

import java.time.LocalDate;

public class YearQuarter {
	private LocalDate startDate;
    private LocalDate endDate;

    public YearQuarter(LocalDate startDate) {
        this.startDate = startDate;
        autoAdjustEndDate();
    }

    private void autoAdjustEndDate() {
        endDate = startDate.plusMonths(3L);
    }
}
