package pl.com.gosia.api.invoice.company;


import org.junit.Assert;
import org.junit.Test;

import static pl.com.gosia.api.invoice.company.NipUtils.clearNip;

public class NipUtilsTest {

    @Test
    public void clearNip_argIsOk_assertEquals() {
        //given
        String nip = " a522-24-50-829 ";
        //when
        final var clearNip = clearNip(nip);
        //then
        Assert.assertEquals("5222450829",clearNip);
    }







}
