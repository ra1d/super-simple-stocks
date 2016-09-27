package com.ashcheglov;

import com.ashcheglov.service.GBCEFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static com.ashcheglov.domain.trade.TradeType.SELL;
import static com.ashcheglov.service.TradeServiceImpl.SCALE;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Anton
 * @since 26.09.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class GBCEIntegrationTest {

    @Autowired
    private GBCEFacade gbceFacade;

    @Test
    public void shouldCalculateDividendYield() {
        BigDecimal popDividendYield = gbceFacade.calculateDividendYield("POP", valueOf(100));
        assertEqualsScaled(valueOf(0.08), popDividendYield);

        BigDecimal ginDividendYield = gbceFacade.calculateDividendYield("GIN", valueOf(10));
        assertEqualsScaled(valueOf(0.2), ginDividendYield);
    }

    @Test
    public void shouldCalculatePERatio() {
        BigDecimal popPERatio = gbceFacade.calculatePERatio("POP", valueOf(80));
        assertEqualsScaled(valueOf(10), popPERatio);

        BigDecimal ginPERatio = gbceFacade.calculatePERatio("GIN", valueOf(100));
        assertEqualsScaled(valueOf(50), ginPERatio);
    }

    @Test
    public void shouldRecordTrades_CalculateVWSP_CalculateASI() throws InterruptedException {
        gbceFacade.recordTrade(BUY, "POP", 100L, valueOf(5));
        Thread.sleep(1000L);
        gbceFacade.recordTrade(BUY, "GIN", 200L, valueOf(20));
        Thread.sleep(1000L);
        gbceFacade.recordTrade(SELL, "GIN", 100L, valueOf(25));
        Thread.sleep(1000L);
        gbceFacade.recordTrade(SELL, "TEA", 300L, valueOf(15));
        Thread.sleep(1000L);
        gbceFacade.recordTrade(BUY, "POP", 200L, valueOf(5));
        Thread.sleep(1000L);

        BigDecimal popVWSP = gbceFacade.calculateVolumeWeightedStockPrice("POP");
        assertEqualsScaled(valueOf(5), popVWSP);

        BigDecimal ginVWSP = gbceFacade.calculateVolumeWeightedStockPrice("GIN");
        assertEqualsScaled(valueOf(20), ginVWSP);

        try {
            gbceFacade.calculateVolumeWeightedStockPrice("TEA");
            fail("Shouldn't have reached this point");
        } catch (IllegalArgumentException ignored) {
            System.out.println("IllegalArgumentException expected here " +
                    "as no BUY trades available for this stock and period.");
        }

        BigDecimal allShareIndex = gbceFacade.calculateAllShareIndex();
        assertEqualsScaled(valueOf(10), allShareIndex);
    }

    private static void assertEqualsScaled(BigDecimal expected, BigDecimal actual) {
        assertEquals(expected.setScale(SCALE, ROUND_HALF_UP),
                actual.setScale(SCALE, ROUND_HALF_UP));
    }

}
