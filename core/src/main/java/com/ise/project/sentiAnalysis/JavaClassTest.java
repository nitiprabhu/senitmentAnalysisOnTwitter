package com.ise.project.sentiAnalysis;

import com.ise.project.sentiAnalysis.OurAlgorithm.Preprocessing;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by India on 19-Apr-16.
 */

public class JavaClassTest {

    @Autowired
    Preprocessing preprocessing;
    @Autowired
    Processor processor;

    @Test
    public void test() throws Exception {
        assertThat((int) (Math.signum(Preprocessing.ourAlgorithm("Good"))), is(1));

        assertThat((int) (Math.signum(Preprocessing.ourAlgorithm("This is really good book"))), is(1));
        assertThat((int) (Math.signum(Preprocessing.ourAlgorithm("bad"))), is(-1));

//        assertThat(Application.processor.getDatumBoxValuation(Application.processor.sanitizeText("bad")), is("n"));

//        assertThat(processor.getDatumBoxValuation("good"), );
    }



    @Test
    public void test2() throws Exception {
        assertThat((int)(Math.signum(Preprocessing.ourAlgorithm("#IIT plans not wonderfull idea but fantastic one and worst"))), is(1));
//
//        assertThat((int)(Math.signum(preprocessing.ourAlgorithm("This is really good book"))), is(-1));
//        assertThat((int)(Math.signum(preprocessing.ourAlgorithm("bad"))), is(-1));

    }


}

