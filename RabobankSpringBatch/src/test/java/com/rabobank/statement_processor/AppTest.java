package com.rabobank.statement_processor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
   /* @Test
    public void shouldImportStores() throws Exception {
        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        StepExecution firstStepExecution = jobExecution.getStepExecutions().iterator().next();

        //then
        assertThat("job execution status", jobExecution.getExitStatus(), is(ExitStatus.COMPLETED));
        assertThat("job read count", firstStepExecution.getReadCount(), is(CSV_FILE_ROWS_COUNT));
        List<Store> allStores = fetchAllStoreRecords();

        assertThat("stores records size", allStores, hasSize(CSV_FILE_ROWS_COUNT));
    }*/
}
